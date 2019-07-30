package com.medlife.deliveries.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonTransformer {

    public static List<String> convertToStringList(JsonNode jsonNode){
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
        List<String> list =  new ArrayList<>();
        try {
            list = reader.readValue(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static HttpHeaders convertToHttpHeaders(JsonNode jsonNode){

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> hm = mapper.convertValue(jsonNode, Map.class);
        Iterator hmIterator = hm.entrySet().iterator();
        HttpHeaders headers = new HttpHeaders();

        while(hmIterator.hasNext()){
            Map.Entry mapElement  = (Map.Entry)hmIterator.next() ;
            headers.add((String)mapElement.getKey(),(String)mapElement.getValue());
        }

        return headers;
    }
}
