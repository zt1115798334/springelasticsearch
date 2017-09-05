package com.zt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableWebMvc
@EnableSwagger2
@Configuration
@ComponentScan(basePackages = {"com.zt.controller"})  //需要扫描的包路径
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("business-api")
                .select()   // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.zt.controller"))    // 对所有api进行监控
                .paths(PathSelectors.any())   // 对所有路径进行监控
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring 中使用Elasticsearch")
                .termsOfServiceUrl("https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html")
                .description("此API提供接口调用")
                .license("License Version 1.0")
                .version("1.0").build();
    }
}
