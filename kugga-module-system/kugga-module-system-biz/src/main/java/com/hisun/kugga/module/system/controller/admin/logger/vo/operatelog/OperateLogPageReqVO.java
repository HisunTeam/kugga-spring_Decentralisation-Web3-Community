package com.hisun.kugga.module.system.controller.admin.logger.vo.operatelog;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 操作日志分页列表 Request VO")
@Data
public class OperateLogPageReqVO extends PageParam {

    @ApiModelProperty(value = "操作模块", example = "订单", notes = "模拟匹配")
    private String module;

    @ApiModelProperty(value = "用户昵称", example = "芋道", notes = "模拟匹配")
    private String userNickname;

    @ApiModelProperty(value = "操作分类", example = "1", notes = "参见 OperateLogTypeEnum 枚举类")
    private Integer type;

    @ApiModelProperty(value = "操作状态", example = "true")
    private Boolean success;

    @ApiModelProperty(value = "开始时间", example = "2020-10-24")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date beginTime;

    @ApiModelProperty(value = "结束时间", example = "2020-10-24")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date endTime;

}
