package com.example.imageuploaderapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config
{
    /**
     * Configures what to document using Swagger
     *
     * @return A Docket which is the primary interface for Swagger configuration
     */
//    @Bean
//    public Docket api()
//    {
//        return new Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors
//                /*
//                *ToDo
//                * Get api endpoint for swagger documentaion
//                 */
//                .basePackage(pass))
//            .paths(PathSelectors.regex("/.*"))
//            .build()
//            .apiInfo(apiEndPointsInfo());
//    }

}
