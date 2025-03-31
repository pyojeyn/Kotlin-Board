package com.example.kdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

@Configuration
class SwaggerConfig{

    @Bean
    fun openApi(): OpenAPI = OpenAPI().components(Components()).info(apiInfo())

    private fun apiInfo() = Info()
        .title("코프링으로 게시판 만들어봐요^^")
        .description("재밌겠죠?")
        .version("0.0.1")
}