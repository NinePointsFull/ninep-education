package com.ninep.user.api.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ApiSendCodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
}
