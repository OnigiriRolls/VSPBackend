package com.challenge.VSPBackend.parsers;

import com.challenge.VSPBackend.exceptions.JSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLParser extends DefaultHandler {

    private SAXParser saxParser;
    private XMLParser myHandler;
    private String day;
    private boolean isCurrency;
    private boolean isDay;
    private boolean isMultiplied;
    private float multiply;
    private String currentCurrency;
    private Map<String, Float> jsonResult = new HashMap<>();
    private ArrayList<String> currencies;

    public XMLParser(ArrayList<String> currencies) {
        try {
            myHandler = this;
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
            this.currencies = currencies;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void parse(String xmlData, String day) {
        try {
            this.day = day;
            isDay = false;
            InputSource src = new InputSource(new StringReader(xmlData));
            saxParser.parse(src, myHandler);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Input could not be read!");
        }
    }

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts)
            throws SAXException {

        if (qName.equals("Cube")) {
            if (atts.getValue("date") != null) {
                String date = atts.getValue("date");
                String[] tokens = date.split("-");

                if (tokens.length >= 3)
                    if (tokens[2].equals(day))
                        isDay = true;
            }
        }

        if (qName.equals("Rate") && isDay) {
            if (atts.getValue("currency") != null) {
                isCurrency = true;
                currentCurrency = atts.getValue("currency");
            }

            if (atts.getValue("multiplier") != null) {
                isMultiplied = true;
                multiply = Float.valueOf(atts.getValue("multiplier"));
            }

        }
    }

    public void endElement(java.lang.String uri,
                           java.lang.String localName,
                           java.lang.String qName)
            throws SAXException {

        if (qName.equals("Rate") && isDay) {
            isCurrency = false;
        }

    }

    public void characters(char buf[], int offset, int len)
            throws SAXException {
        String s = new String(buf, offset, len);
        s = s.trim();
        if (!s.equals("")) {
            if (isCurrency && isDay) {
                if (currencies == null || currencies.contains(currentCurrency)) {
                    float aux = Float.valueOf(s);

                    if (isMultiplied) {
                        aux = aux * multiply;
                        isMultiplied = false;
                    }

                    jsonResult.put(currentCurrency, aux);
                }
            }
        }
    }

    public String getJSONString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(jsonResult);

            return jsonString;
        } catch (Exception e) {
            throw new JSONException("JSON failure!");
        }
    }

}
