package com.hisun.kugga.framework.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.hisun.kugga.framework.swagger.core.SpringFoxHandlerProviderBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.HEADER_TENANT_ID;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * Swagger2 自动配置类
 *
 * @author 芋道源码
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnClass({Docket.class, ApiInfoBuilder.class})
// 允许使用 swagger.enable=false 禁用 Swagger
@ConditionalOnProperty(prefix = "kugga.swagger", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class KuggaSwaggerAutoConfiguration {

    @Bean
    public SpringFoxHandlerProviderBeanPostProcessor springFoxHandlerProviderBeanPostProcessor() {
        return new SpringFoxHandlerProviderBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public Docket createRestApi() {
        SwaggerProperties properties = swaggerProperties();
        // 创建 Docket 对象
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该 API 的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo(properties))
                // 设置扫描指定 package 包下的
                .select()
                .apis(basePackage(properties.getBasePackage()))
//                .apis(basePackage("com.hisun.kugga.module.system")) // 可用于 swagger 无法展示时使用
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                // //去除tentId
                .globalRequestParameters(globalRequestParameters())
                .securityContexts(securityContexts());
    }

    // ========== apiInfo ==========

    /**
     * API 摘要信息
     */
    private static ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .contact(new Contact(properties.getAuthor(), null, null))
                .version(properties.getVersion())
                .build();
    }

    // ========== securitySchemes ==========

    /**
     * 安全模式，这里配置通过请求头 Authorization 传递 token 参数
     */
    private static List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey(HttpHeaders.AUTHORIZATION, "Authorization", "header"));
    }

    /**
     * 安全上下文
     *
     * @see #securitySchemes()
     * @see #authorizationScopes()
     */
    private static List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
    }

    private static List<SecurityReference> securityReferences() {
        return Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes()));
    }

    private static AuthorizationScope[] authorizationScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
    }

    // ========== globalRequestParameters ==========

    private static List<RequestParameter> globalRequestParameters() {
        RequestParameterBuilder tenantParameter = new RequestParameterBuilder()
                .name(HEADER_TENANT_ID).description("租户编号")
                .in(ParameterType.HEADER).example(new ExampleBuilder().value(1L).build());
        return Collections.singletonList(tenantParameter.build());
    }

}
