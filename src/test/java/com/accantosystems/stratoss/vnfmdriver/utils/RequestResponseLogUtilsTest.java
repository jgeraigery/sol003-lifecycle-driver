package com.accantosystems.stratoss.vnfmdriver.utils;

import com.accantosystems.stratoss.vnfmdriver.test.TestConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class RequestResponseLogUtilsTest {
    @Test
    public void testResponseReceivedProtocolMetaData(){
        Map<String, Object> responseReceivedMetadata = RequestResponseLogUtils.getResponseReceivedProtocolMetaData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(200);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("OK");
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isEqualTo(null);

        HttpHeaders headers = new HttpHeaders();
        headers.put("KEY1", List.of("VALUE1"));
        responseReceivedMetadata = RequestResponseLogUtils.getResponseReceivedProtocolMetaData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), headers);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(201);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("Created");
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        responseReceivedMetadata = RequestResponseLogUtils.getResponseReceivedProtocolMetaData(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), headers);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(400);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("Bad Request");
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        responseReceivedMetadata = RequestResponseLogUtils.getResponseReceivedProtocolMetaData(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), headers);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(500);
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("Internal Server Error");
        assertThat(responseReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);
    }


    @Test
    public void testRequestSentProtocolMetaData(){
        Map<String, Object> requestSentMetadata = RequestResponseLogUtils.getRequestSentProtocolMetaData("uri", HttpMethod.POST.name(), null);
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("POST");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isEqualTo(null);

        HttpHeaders headers = new HttpHeaders();
        headers.put("KEY1", List.of("VALUE1"));
        requestSentMetadata = RequestResponseLogUtils.getRequestSentProtocolMetaData("uri", HttpMethod.GET.name(), headers);
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("GET");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        requestSentMetadata = RequestResponseLogUtils.getRequestSentProtocolMetaData("uri", HttpMethod.PUT.name(), headers);
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("PUT");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        requestSentMetadata = RequestResponseLogUtils.getRequestSentProtocolMetaData("uri", HttpMethod.DELETE.name(), headers);
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("DELETE");
        assertThat(requestSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);
    }

    @Test
    public void testRequestReceivedProtocolMetaData(){
        Map<String, Object> requestReceivedMetadata = RequestResponseLogUtils.getRequestReceivedProtocolMetaData("uri", HttpMethod.POST.name(), null);
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("POST");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isEqualTo(null);

        HttpHeaders headers = new HttpHeaders();
        headers.put("KEY1", List.of("VALUE1"));
        requestReceivedMetadata = RequestResponseLogUtils.getRequestReceivedProtocolMetaData("uri", HttpMethod.GET.name(), headers);
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("GET");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        requestReceivedMetadata = RequestResponseLogUtils.getRequestReceivedProtocolMetaData("uri", HttpMethod.PUT.name(), headers);
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("PUT");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        requestReceivedMetadata = RequestResponseLogUtils.getRequestReceivedProtocolMetaData("uri", HttpMethod.DELETE.name(), headers);
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_URI)).isEqualTo("uri");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_METHOD)).isEqualTo("DELETE");
        assertThat(requestReceivedMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

    }

    @Test
    public void testResponseSentProtocolMetaData(){
        Map<String, Object> responseSentMetadata = RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(200);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("OK");
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isEqualTo(null);

        HttpHeaders headers = new HttpHeaders();
        headers.put("KEY1", List.of("VALUE1"));
        responseSentMetadata = RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), headers);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(201);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("Created");
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        responseSentMetadata = RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), headers);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(400);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("Bad Request");
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);

        responseSentMetadata = RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), headers);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_CODE)).isEqualTo(500);
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_STATUS_REASON)).isEqualTo("Internal Server Error");
        assertThat(responseSentMetadata.get(TestConstants.LOG_META_DATA_HTTP_HEADERS)).isNotEqualTo(null);
    }
    @Test
    public void testFilteringConfidentialInfoInHeaders(){

        HttpHeaders headers = new HttpHeaders();
        headers.put(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION, List.of("authorization value"));
        headers.put(TestConstants.LOG_META_DATA_HTTP_HEADERS_SET_COOKIE, List.of("Cookie value"));
        Map<String, Object> responseReceivedProtocolMetaData = RequestResponseLogUtils.getResponseReceivedProtocolMetaData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), headers);
        HttpHeaders responseReceivedProtocolMetaDataHeaders = (HttpHeaders) responseReceivedProtocolMetaData.get(TestConstants.LOG_META_DATA_HTTP_HEADERS);
        assertThat(responseReceivedProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);
        assertThat(responseReceivedProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);

        Map<String, Object> requestSentProtocolMetaData = RequestResponseLogUtils.getRequestSentProtocolMetaData("uri", HttpMethod.GET.name(), headers);
        HttpHeaders requestSentProtocolMetaDataHeaders = (HttpHeaders) requestSentProtocolMetaData.get(TestConstants.LOG_META_DATA_HTTP_HEADERS);
        assertThat(requestSentProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);
        assertThat(requestSentProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);

        Map<String, Object> requestReceivedProtocolMetaData = RequestResponseLogUtils.getRequestReceivedProtocolMetaData("uri", HttpMethod.DELETE.name(), headers);
        HttpHeaders requestReceivedProtocolMetaDataHeaders = (HttpHeaders) requestReceivedProtocolMetaData.get(TestConstants.LOG_META_DATA_HTTP_HEADERS);
        assertThat(requestReceivedProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);
        assertThat(requestReceivedProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);

        Map<String, Object> responseSentProtocolMetaData = RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), headers);
        HttpHeaders responseSentProtocolMetaDataHeaders = (HttpHeaders) responseSentProtocolMetaData.get(TestConstants.LOG_META_DATA_HTTP_HEADERS);
        assertThat(responseSentProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);
        assertThat(responseSentProtocolMetaDataHeaders.get(TestConstants.LOG_META_DATA_HTTP_HEADERS_AUTHORIZATION)).isEqualTo(null);
    }
}
