/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.ninep.common.vod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 获取课时code值实体类
 *
 * @author NineP
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PolyvSign implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 课时vid
	 */
	private String vid;

	/**
	 * 播放ip
	 */
	private String ip;

	/**
	 * 用户编号
	 */
	private Long userNo;

}
