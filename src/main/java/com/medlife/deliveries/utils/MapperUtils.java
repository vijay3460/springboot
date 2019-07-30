package com.medlife.deliveries.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medlife.deliveries.model.data.PartnerDetails;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
public class MapperUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String getJSONString(Object object){

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.info("getJSONString : Failed to convert to JSON String : {}",object);
            return object.toString();
        }
    }

    public static <T> T getDeepCopy(T object, Class objectClass){
        try {
            T deepCopy = (T) mapper.readValue(getJSONString(object),objectClass);
            return deepCopy;
        } catch (IOException e) {
            log.info("failed To Do deep Copy : {}",object);
            return null;
        }
    }

    public static <T> List<T> getClassTypeListFromHashMapType(List hashMappedList, Class classType){
        List<T> convertedList = new ArrayList<>();
        int listSize = hashMappedList.size();
        for(int i=0;i<listSize;i++){
            convertedList.add((T)mapper.convertValue(hashMappedList.get(i),classType));
        }
        return convertedList;
    }

}
