package com.hisun.kugga.module.system.controller.admin.auth.vo;

import com.hisun.kugga.framework.common.validation.InEnum;
import com.hisun.kugga.framework.common.validation.Mobile;
import com.hisun.kugga.module.system.enums.sms.SmsSceneEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 发送手机验证码 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSmsSendReqVO {

    @ApiModelProperty(value = "手机号", required = true, example = "kuggayuanma")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "短信场景", required = true, example = "1")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
