package br.com.testewk.service;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.testewk.dto.CandidatoDTO;

@Service
public class CandidatosService {

    public Object recuperarInfoCandidatos() {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "https://s3.amazonaws.com/gupy5/production/companies/52441/emails/1669646172212/e8330670-6f23-11ed-91a8-05f5cf6759fb/data_1.json";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<?> result = restTemplate.exchange(uri, HttpMethod.GET, entity, CandidatoDTO[].class);
        return result.getBody();
    }
    
}
