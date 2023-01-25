package com.accantosystems.stratoss.vnfmdriver.utils;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class RequestResponseLogUtils {
    public static Map<String,Object> getResponseReceivedProtocolMetaData(int statusCode, String statusReasonPhrase, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("status_code",statusCode);
        protocolMetadata.put("status_reason_phrase", statusReasonPhrase);
        protocolMetadata.put("headers", headers);
        return protocolMetadata;

    }

    public static Map<String,Object> getRequestSentProtocolMetaData(String uri, String method, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("uri",uri);
        protocolMetadata.put("method", method);
        protocolMetadata.put("headers", headers);
        return protocolMetadata;
    }

    public static Map<String,Object> getRequestReceivedProtocolMetaData(String uri, String method, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("uri", uri);
        protocolMetadata.put("method", method);
        protocolMetadata.put("headers", headers);
        return protocolMetadata;
    }

    public static Map<String,Object> getResponseSentProtocolMetadata(int status_code, String statusReasonPhrase, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put("status_code",status_code);
        protocolMetadata.put("status_reason_phrase", statusReasonPhrase);
        protocolMetadata.put("headers", headers);
        return protocolMetadata;
    }
}
