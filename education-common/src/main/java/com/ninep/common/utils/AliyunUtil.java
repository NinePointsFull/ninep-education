package com.ninep.common.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.ninep.common.DTO.UploadDTO;
import com.ninep.common.enums.SysConfigEnum;
import com.ninep.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

//调用阿里云工具类
@Slf4j
public final class AliyunUtil {
    private  AliyunUtil() {
    }


    /**
     * 上传文件后获取路径
     * @param picFile
     * @param uploadDTO
     * @return
     */
    public static String getOssUrl(MultipartFile picFile, UploadDTO uploadDTO){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint =uploadDTO.getAliyunOssEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = uploadDTO.getAliyunAccessKeyId();
        String accessKeySecret = uploadDTO.getAliyunAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName =uploadDTO.getAliyunOssBucket();
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String suffix = picFile.getOriginalFilename().split("\\.")[1];
        //当前日期做文件夹
        String folder = LocalDate.now().toString();
        String prefix=picFile.getOriginalFilename().split("\\.")[0];
        String objectName = folder+"/"+prefix +"."+suffix;
        log.info("存储位置:{}",objectName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                log.info("您已经创建Bucket：{} ", bucketName );
            } else {
                log.info("您的Bucket不存在，创建Bucket：{}" ,bucketName);
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }
            //https://ninepointsfull.oss-cn-hangzhou.aliyuncs.com/labixiaoxin.png
            ossClient.putObject(bucketName, objectName,picFile.getInputStream());
            String url="https://"+bucketName+"."+endpoint+"/"+objectName;
            log.info("url:{}",url);
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        throw new BaseException(SysConfigEnum.CONFIG_ERROR.getCode(), SysConfigEnum.CONFIG_ERROR.getMsg());
    }
}
