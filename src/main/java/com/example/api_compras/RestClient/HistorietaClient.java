package com.example.api_compras.RestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.api_compras.DTO.RestClientDTO.HistorietaExternoDTO;

@Component 
public class HistorietaClient {

    @Autowired
    private RestClient historietaRestClient;

    public HistorietaExternoDTO obtenerHistorietaPorId(Long id_hist) {
        try {
            return historietaRestClient.get()
                    .uri("/{id_historieta}", id_hist)
                    .retrieve()
                    .body(HistorietaExternoDTO.class);
        } catch (Exception e) {
            // IMPRIME EL ERROR REAL EN LA TERMINAL
            System.err.println("ERROR AL CONECTAR CON HISTORIETA CLIENT: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
