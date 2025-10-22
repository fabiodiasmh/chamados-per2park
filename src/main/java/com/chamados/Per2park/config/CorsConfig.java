package com.chamados.Per2park.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:9000",
//                                "https://chamados-per2park.web.app") // seu frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:9000",
                                "http://10.6.10.73:9000",
                                "http://10.6.102.9:9000",
                                "http://10.6.10.60:9000",
                                "http://10.6.10.147:9000",
                                "http://10.6.10.56:9000",
                                "http://192.168.0.155:9000",
                                "https://chamados-per2park.web.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Aplica para todos os endpoints
//                        .allowedOrigins("*") // Permite todas as origens
//                        .allowedMethods("*") // Permite todos os métodos HTTP
////                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*") // Permite todos os headers
//                        .exposedHeaders("Content-Disposition");
//            }
//        };
//    }
