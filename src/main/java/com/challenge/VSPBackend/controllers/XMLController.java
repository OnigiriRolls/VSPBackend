package com.challenge.VSPBackend.controllers;

import com.challenge.VSPBackend.services.XMLService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class XMLController {

    private final XMLService xmlService;

    @GetMapping("/getCurrenciesForDay")
    public ResponseEntity<?> getDataFromXml(@RequestParam String day) {
        return ResponseEntity.ok(xmlService.getJSONData(day));
    }

    @PostMapping("/setConfigs")
    public void setConfigs(@RequestBody List<String> currencies) {
        xmlService.saveCurrencies(currencies);
    }

}
