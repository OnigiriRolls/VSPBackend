package com.challenge.VSPBackend.controllers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class XMLController {
    @Value("${xml.endpoint.url}") // Retrieve the URL from application.properties or application.yml
    private String xmlEndpointUrl;

    @GetMapping
    public ResponseEntity<?> getDataFromXml() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String xmlData = restTemplate.getForObject(xmlEndpointUrl, String.class);


            return ResponseEntity.ok(xmlData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
