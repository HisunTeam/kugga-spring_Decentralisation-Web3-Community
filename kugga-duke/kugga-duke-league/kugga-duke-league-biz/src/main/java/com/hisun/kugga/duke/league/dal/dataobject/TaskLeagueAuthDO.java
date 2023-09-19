package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-08 15:31
 */
@TableName("duke_task_league_auth")
@Data
@ApiModel("待认证公会表 任务类型为公会认证，任务关联 待认证公会与认证公会")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLeagueAuthDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "被认证公会ID")
    private Long byAuthLeagueId;
    @ApiModelProperty(value = "任务ID")
    private Long taskId;
    @ApiModelProperty(value = "公会公告栏ID")
    private Long noticeId;
    @ApiModelProperty(value = "订单号")
    private String appOrderNo;
    @ApiModelProperty(value = "子订单价格")
    private BigDecimal amount;
    @ApiModelProperty(value = "付费类型 0免费 1付费")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "状态 0默认值  1未支付 2已支付 3已分账 4待退款 5已退款")
    private PayStatusEnum status;
}
