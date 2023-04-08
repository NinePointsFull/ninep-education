/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.ninep.common.vod.response;

import java.io.Serializable;

public class PolyvGetKeyframeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
