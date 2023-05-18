package com.challenge.VSPBackend.services;

import com.challenge.VSPBackend.exceptions.JSONException;
import com.challenge.VSPBackend.parsers.XMLParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XMLService {

    @Value("${xml.endpoint.url}") // Retrieve the URL from application.properties or application.yml
    private String xmlEndpointUrl;
    private ArrayList<String> currencies;

    public String getJSONData(String day){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String xmlData = restTemplate.getForObject(xmlEndpointUrl, String.class);

            XMLParser xmlParser = new XMLParser(currencies);

            xmlParser.parse(xmlData, day);
            return xmlParser.getJSONString();


        } catch (Exception e) {
            e.printStackTrace();
            throw new JSONException("JSON failure!");
        }
    }

    public void saveCurrencies(List<String> currencies) {
        this.currencies = (ArrayList<String>) currencies;
    }

}
