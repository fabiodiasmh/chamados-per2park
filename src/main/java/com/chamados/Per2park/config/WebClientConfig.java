package com.chamados.Per2park.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("webClientAuth")
    public WebClient webClientAuth() {
        return WebClient.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(50* 1024 * 1024)) // 1 MB
                .baseUrl("https://cloud.per2park.com.br:32204") // <-- ajuste o host
                .build();
    }

    @Bean
    @Qualifier("webClientChamados")
    public WebClient webClientChamados() {
        return WebClient.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(50* 1024 * 1024)) // 1 MB
                .baseUrl("https://cloud.per2park.com.br:32202") // <-- ajuste o host
                .build();
    }

    @Bean
    @Qualifier("webClientMonitoramento")
    public WebClient webClientMonitoramento() {
        return WebClient.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(50* 1024 * 1024)) // 1 MB
                .baseUrl("https://cloud.per2park.com.br:32203") // <-- ajuste o host
                .build();
    }
}