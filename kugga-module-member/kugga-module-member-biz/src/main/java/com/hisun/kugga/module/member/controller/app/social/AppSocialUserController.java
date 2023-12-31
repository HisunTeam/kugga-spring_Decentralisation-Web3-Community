package com.hisun.kugga.module.member.controller.app.social;

import com.hisun.kugga.framework.common.enums.UserTypeEnum;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.module.member.controller.app.social.vo.AppSocialUserBindReqVO;
import com.hisun.kugga.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import com.hisun.kugga.module.member.convert.social.SocialUserConvert;
import com.hisun.kugga.module.system.api.social.SocialUserApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Api(tags = "用户 App - 社交用户")
@RestController
@RequestMapping("/system/social-user")
@Validated
public class AppSocialUserController {

    @Resource
    private SocialUserApi socialUserApi;

    @PostMapping("/bind")
    @ApiOperation("社交绑定，使用 code 授权码")
    public CommonResult<Boolean> socialBind(@RequestBody @Valid AppSocialUserBindReqVO reqVO) {
        socialUserApi.bindSocialUser(SocialUserConvert.INSTANCE.convert(getLoginUserId(), UserTypeEnum.MEMBER.getValue(), reqVO));
        return CommonResult.success(true);
    }

    @DeleteMapping("/unbind")
    @ApiOperation("取消社交绑定")
    public CommonResult<Boolean> socialUnbind(@RequestBody AppSocialUserUnbindReqVO reqVO) {
        socialUserApi.unbindSocialUser(SocialUserConvert.INSTANCE.convert(getLoginUserId(), UserTypeEnum.MEMBER.getValue(), reqVO));
        return CommonResult.success(true);
    }

}
