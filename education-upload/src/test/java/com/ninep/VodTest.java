package com.ninep;


import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.vod20170321.AsyncClient;
import com.aliyun.sdk.service.vod20170321.models.CreateUploadVideoRequest;
import com.aliyun.sdk.service.vod20170321.models.CreateUploadVideoResponse;
import darabonba.core.client.ClientOverrideConfiguration;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class VodTest {

    @Test
    public void vodTest() {
        AsyncClient client=null;
        try {
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId("LTAI5tRtiP4nE4ahZtscohNw")
                    .accessKeySecret("V0wufx0hRUzTw17jgoUIA968JDenrV")
                    //.securityToken("<your-token>") // use STS token
                    .build());

            // Configure the Client
            client= AsyncClient.builder()
                    .region("cn-shanghai") // Region ID
                    //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                    .credentialsProvider(provider)
                    //.serviceConfiguration(Configuration.create()) // Service-level configuration
                    // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    .setEndpointOverride("vod.cn-shanghai.aliyuncs.com")
                            //.setConnectTimeout(Duration.ofSeconds(30))
                    )
                    .build();

            // Parameter settings for API request
            CreateUploadVideoRequest createUploadVideoRequest = CreateUploadVideoRequest.builder()
                    // Request-level configuration rewrite, can set Http request parameters, etc.
                    // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .build();

            // Asynchronously get the return value of the API request
            CompletableFuture<CreateUploadVideoResponse> response = client.createUploadVideo(createUploadVideoRequest);
            // Synchronously get the return value of the API request
            CreateUploadVideoResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));
            // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // Finally, close the client
            client.close();

        }
    }
}
