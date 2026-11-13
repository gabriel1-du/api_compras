package com.example.api_compras.RestClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration 
public class RestClientConfig {
    
    
    @Value("${usuarios.service.url}")
    private String usuariosUrl;

    @Value("${inventario.service.url}")
    private String inventarioUrl;

    @Bean
    public RestClient usuariosRestClient() {
        return RestClient.builder()
                .baseUrl(usuariosUrl.trim())
                .build();
    }

    @Bean
    public RestClient historietaRestClient() {
        return RestClient.builder()
                .baseUrl(inventarioUrl.trim())
                .build();
    }
    
}
