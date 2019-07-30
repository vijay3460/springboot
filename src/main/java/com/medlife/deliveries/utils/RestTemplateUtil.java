package com.medlife.deliveries.utils;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Harshit Singh Chauhan
 */
public class RestTemplateUtil {

    private static final Integer CONNNECT_TIMEOUT = 5000; //5 seconds

    private static final Integer READ_TIMEOUT = 5000; //5 seconds

    private static RestTemplate restTemplate;

    private static Map<Integer,RestTemplate> restTemplateMap = new HashMap<>();

    public static RestTemplate getRestTemplate() {
        if( restTemplate != null) {
            return restTemplate;
        }
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(CONNNECT_TIMEOUT);
        httpRequestFactory.setReadTimeout(READ_TIMEOUT);
        restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }


    public static RestTemplate getRestTemplate(int miliSeconds) {
        if(restTemplateMap.containsKey(miliSeconds)) {
            return restTemplateMap.get(miliSeconds);
        }
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(miliSeconds);
        httpRequestFactory.setReadTimeout(miliSeconds);
        RestTemplate restTemplateWithCustomTimeOut = new RestTemplate(httpRequestFactory);
        restTemplateMap.put(miliSeconds,restTemplateWithCustomTimeOut);
        return restTemplateWithCustomTimeOut;
    }

    @SuppressWarnings("rawtypes")
    public static MultiValueMap getHttpHeaders(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
