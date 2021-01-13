package com.prgers.teacher.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 使用Swagger注解
 *  @Api(tags="")
 *      用在请求的类上，表示对类的说明
 *      tags"说明该类的作用，可以在UI界面上看到的注解"
 *  @ApiOperation(value="")
 *      用在请求的方法上，说明方法的用途、作用
 *      value="说明方法的用途、作用"
 *  @ApiImplicitParams
 *      用在请求的方法上，表示一组参数说明
 *  @ApiImplicitParam
 *      @ApiImplicitParam:指定一个请求参数的各个方面
 *      value：参数的汉字说明、解释
 *      required：参数是否必须传
 *      paramType：参数放在哪个地方
 *      header –> 请求头的获取：@RequestHeader
 *      query –> 请求参数的获取：@RequestParam
 *      path（用于restful接口）–> 请求路径变量的获取：@PathVariable
 *      body（不常用）
 *      form（不常用）
 *      dataType：参数类型，默认String，其它值dataType="Integer"
 *      defaultValue：参数的默认值
 *  @ApiResponses
 *      用在请求的方法上，表示一组响应
 *  @ApiResponse
 *      用在@ApiResponses中，一般用于表达一个错误的响应信息
 *      code：数字，例如400
 *      message：信息，例如"请求参数没填好"
 *      response：抛出异常的类
 *  @ApiModel
 *      主要有两种用途：
 *      用于响应类上，表示一个返回响应数据的信息
 *      入参实体：使用@RequestBody这样的场景， 请求参数无法使用@ApiImplicitParam注解进行描述的时候
 *      @ApiModelProperty
 *      用在属性上，描述响应类的属性
 */


@Configuration
@EnableSwagger2 //开启swagger2自动生成api文档的功能
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-讲师API文档")
                .description("本文档描述了讲师服务接口定义")
                .version("1.0")
                .contact(new Contact("潘小懒", "http://prger.com", "prgers@163.com"))
                .build();
    }

}
