package com.hisun.kugga.module.system.service.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.hisun.kugga.module.system.controller.admin.common.vo.CaptchaImageRespVO;
import com.hisun.kugga.module.system.convert.common.CaptchaConvert;
import com.hisun.kugga.module.system.dal.redis.common.CaptchaRedisDAO;
import com.hisun.kugga.module.system.framework.captcha.config.CaptchaProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证码 Service 实现类
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private CaptchaProperties captchaProperties;

    /**
     * 验证码是否开关
     * <p>
     * 虽然 {@link CaptchaProperties#getEnable()} 有该属性，但是 Apollo 在 Spring Boot 下无法刷新 @ConfigurationProperties 注解，
     * 所以暂时只能这么处理~
     */
    @Value("${kugga.captcha.enable}")
    private Boolean enable;

    @Resource
    private CaptchaRedisDAO captchaRedisDAO;

    @Override
    public CaptchaImageRespVO getCaptchaImage() {
        if (!Boolean.TRUE.equals(enable)) {
            return CaptchaImageRespVO.builder().enable(enable).build();
        }
        // 生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
        // 缓存到 Redis 中
        String uuid = IdUtil.fastSimpleUUID();
        captchaRedisDAO.set(uuid, captcha.getCode(), captchaProperties.getTimeout());
        // 返回
        return CaptchaConvert.INSTANCE.convert(uuid, captcha).setEnable(enable);
    }

    @Override
    public Boolean isCaptchaEnable() {
        return enable;
    }

    @Override
    public String getCaptchaCode(String uuid) {
        return captchaRedisDAO.get(uuid);
    }

    @Override
    public void deleteCaptchaCode(String uuid) {
        captchaRedisDAO.delete(uuid);
    }

}
