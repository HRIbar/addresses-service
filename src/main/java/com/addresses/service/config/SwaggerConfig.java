package com.addresses.service.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi addressApi() {
        return GroupedOpenApi.builder()
                .group("addresses")
                .pathsToMatch("/addresses-service/address/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/addresses-service/user/**")
                .build();
    }
}
