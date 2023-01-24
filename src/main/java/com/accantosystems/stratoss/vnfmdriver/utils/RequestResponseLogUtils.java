package com.accantosystems.stratoss.vnfmdriver.utils;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class RequestResponseLogUtils {
    public static Map<String,Object> getResponseReceivedProtocolMetaData(int statusCode){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("status_code",statusCode);
        return protocolMetadata;

    }

    public static Map<String,Object> getRequestSentProtocolMetaData(String url){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("url",url);
        return protocolMetadata;
    }

    public static Map<String,Object> getRequestReceivedProtocolMetaData(String uri){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("uri", uri);
        return protocolMetadata;
    }

    public static Map<String,Object> getResponseSentProtocolMetadata(int status_code){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("status_code",status_code);
        return protocolMetadata;
    }
}
