package com.hisun.kugga.module.pay.controller.admin.merchant.vo.app;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 支付应用信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayAppCreateReqVO extends PayAppBaseVO {

}
