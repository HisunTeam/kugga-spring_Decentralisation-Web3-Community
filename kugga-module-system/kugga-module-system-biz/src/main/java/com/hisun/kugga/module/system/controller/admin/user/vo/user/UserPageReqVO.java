package com.hisun.kugga.module.system.controller.admin.user.vo.user;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 用户分页 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageReqVO extends PageParam {

    @ApiModelProperty(value = "用户账号", example = "kugga", notes = "模糊匹配")
    private String username;

    @ApiModelProperty(value = "手机号码", example = "kugga", notes = "模糊匹配")
    private String mobile;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "开始时间", example = "2020-10-24")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date beginTime;

    @ApiModelProperty(value = "结束时间", example = "2020-10-24")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date endTime;

    @ApiModelProperty(value = "部门编号", example = "1024", notes = "同时筛选子部门")
    private Long deptId;

}
