package com.challenge.VSPBackend.services;

import com.challenge.VSPBackend.exceptions.JSONException;
import com.challenge.VSPBackend.parsers.XMLParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XMLService {

    @Value("${xml.endpoint.url}")
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
