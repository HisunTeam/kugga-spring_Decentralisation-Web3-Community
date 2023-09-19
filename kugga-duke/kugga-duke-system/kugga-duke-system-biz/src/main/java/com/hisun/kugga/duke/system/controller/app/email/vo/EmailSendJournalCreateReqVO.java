package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 邮件发送流水创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailSendJournalCreateReqVO extends EmailSendJournalBaseVO {

}
