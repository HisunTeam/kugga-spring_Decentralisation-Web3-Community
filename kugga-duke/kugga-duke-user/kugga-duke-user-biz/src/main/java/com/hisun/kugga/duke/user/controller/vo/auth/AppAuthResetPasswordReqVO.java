package com.hisun.kugga.duke.user.controller.vo.auth;

import com.hisun.kugga.duke.entity.VerifyBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("用户 APP - 重置密码 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthResetPasswordReqVO extends VerifyBaseVo {

    @ApiModelProperty(value = "邮箱", required = true, example = "1073920692@qq.com")
    @NotNull(message = "Please enter a valid email address")
    @Email(message = "Incorrect mailbox format")
    private String email;

    @ApiModelProperty(value = "密码", required = true, example = "admin123")
    @NotNull(message = "Password cannot be empty")
    private String password;

    @ApiModelProperty(value = "确认密码", required = false)
//    @NotNull(message = "Confirm password cannot be empty")
    private String ensurePwd;

    @ApiModelProperty(value = "邮箱验证码", required = true, example = "123456")
    @NotEmpty(message = "Verification code cannot be empty")
    private String captcha;

}
