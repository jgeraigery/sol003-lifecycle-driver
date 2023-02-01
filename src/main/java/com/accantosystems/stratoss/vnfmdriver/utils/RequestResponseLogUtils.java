package com.accantosystems.stratoss.vnfmdriver.utils;

import com.accantosystems.stratoss.vnfmdriver.driver.SOL003ResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class RequestResponseLogUtils {
    public static final String LOG_META_DATA_HTTP_URI= "uri";
    public static final String LOG_META_DATA_HTTP_METHOD= "method";
    public static final String LOG_META_DATA_HTTP_STATUS_CODE= "status_code";
    public static final String LOG_META_DATA_HTTP_STATUS_REASON= "status_reason_phrase";
    public static final String LOG_META_DATA_HTTP_HEADERS= "headers";
    public static Map<String,Object> getResponseReceivedProtocolMetaData(int statusCode, String statusReasonPhrase, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put(LOG_META_DATA_HTTP_STATUS_CODE,statusCode);
        protocolMetadata.put(LOG_META_DATA_HTTP_STATUS_REASON, statusReasonPhrase);
        protocolMetadata.put(LOG_META_DATA_HTTP_HEADERS, headers);
        return protocolMetadata;

    }

    public static Map<String,Object> getRequestSentProtocolMetaData(String uri, String method, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put(LOG_META_DATA_HTTP_URI,uri);
        protocolMetadata.put(LOG_META_DATA_HTTP_METHOD, method);
        protocolMetadata.put(LOG_META_DATA_HTTP_HEADERS, headers);
        return protocolMetadata;
    }

    public static Map<String,Object> getRequestReceivedProtocolMetaData(String uri, String method, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put(LOG_META_DATA_HTTP_URI, uri);
        protocolMetadata.put(LOG_META_DATA_HTTP_METHOD, method);
        protocolMetadata.put(LOG_META_DATA_HTTP_HEADERS, headers);
        return protocolMetadata;
    }

    public static Map<String,Object> getResponseSentProtocolMetaData(int status_code, String statusReasonPhrase, Object headers){
        Map<String,Object> protocolMetadata=new HashMap<>();
        protocolMetadata.put(LOG_META_DATA_HTTP_STATUS_CODE,status_code);
        protocolMetadata.put(LOG_META_DATA_HTTP_STATUS_REASON, statusReasonPhrase);
        protocolMetadata.put(LOG_META_DATA_HTTP_HEADERS, headers);
        return protocolMetadata;
    }

    public static String convertToJson(String message){
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw  new SOL003ResponseException("Error in parsing protocol_metadata "+ message, e);
        }
    }
}
