package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhou_xiong
 */
@ApiModel("个人首页 - 推荐报告 Response VO")
@Data
public class MemberRecommendationPageRspVO extends RecommendationPageRspBaseVO {
    @ApiModelProperty(value = "推荐人编号")
    private Long recommenderId;

    @ApiModelProperty(value = "推荐人公会编号")
    private Long recommenderLeagueId;

    @ApiModelProperty(value = "名")
    private String lastName;

    @ApiModelProperty(value = "姓")
    private String firstName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "收藏标识，true-已收藏，false-未收藏")
    private Boolean favoriteFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
