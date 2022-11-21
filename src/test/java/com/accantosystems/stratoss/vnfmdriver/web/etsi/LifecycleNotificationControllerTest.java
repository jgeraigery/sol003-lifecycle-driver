package com.accantosystems.stratoss.vnfmdriver.web.etsi;

import static com.accantosystems.stratoss.vnfmdriver.test.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionAsyncResponse;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionStatus;
import com.accantosystems.stratoss.vnfmdriver.model.alm.FailureDetails;
import org.etsi.sol003.common.ProblemDetails;
import com.accantosystems.stratoss.vnfmdriver.service.ExternalMessagingService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class LifecycleNotificationControllerTest {

    public static final String NOTIFICATIONS_ENDPOINT = "/vnflcm/v2/notifications";

    @Autowired private TestRestTemplate testRestTemplate;
    @MockBean private ExternalMessagingService externalMessagingService;

    @Test
    public void testReceiveNotification() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(loadFileIntoString("examples/VnfLcmOperationOccurrenceNotification-INSTANTIATE-PROCESSING.json"), headers);
        final ResponseEntity<Void> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                                                                    .postForEntity(NOTIFICATIONS_ENDPOINT, httpEntity, Void.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verifyNoInteractions(externalMessagingService);
    }

    @Test
    public void testReceiveOpOccCompletedNotification() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(loadFileIntoString("examples/VnfLcmOperationOccurrenceNotification-INSTANTIATE-COMPLETED.json"), headers);
        final ResponseEntity<Void> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                                                                    .postForEntity(NOTIFICATIONS_ENDPOINT, httpEntity, Void.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        final ArgumentCaptor<ExecutionAsyncResponse> argument = ArgumentCaptor.forClass(ExecutionAsyncResponse.class);
        verify(externalMessagingService).sendExecutionAsyncResponse(argument.capture());

        final ExecutionAsyncResponse asyncResponse = argument.getValue();
        assertThat(asyncResponse).isNotNull();
        assertThat(asyncResponse.getRequestId()).isEqualTo("8dbe6621-f6b9-49ba-878b-26803f107f27");
        assertThat(asyncResponse.getStatus()).isEqualTo(ExecutionStatus.COMPLETE);
        assertThat(asyncResponse.getFailureDetails()).isNull();
        assertThat(asyncResponse.getOutputs()).isEmpty();
    }

    @Test
    public void testReceiveOpOccFailedNotification() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(loadFileIntoString("examples/VnfLcmOperationOccurrenceNotification-INSTANTIATE-FAILED.json"), headers);
        final ResponseEntity<Void> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                                                                    .postForEntity(NOTIFICATIONS_ENDPOINT, httpEntity, Void.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        final ArgumentCaptor<ExecutionAsyncResponse> argument = ArgumentCaptor.forClass(ExecutionAsyncResponse.class);
        verify(externalMessagingService).sendExecutionAsyncResponse(argument.capture());

        final ExecutionAsyncResponse asyncResponse = argument.getValue();
        assertThat(asyncResponse).isNotNull();
        assertThat(asyncResponse.getRequestId()).isEqualTo("8dbe6621-f6b9-49ba-878b-26803f107f27");
        assertThat(asyncResponse.getStatus()).isEqualTo(ExecutionStatus.FAILED);
        assertThat(asyncResponse.getVersion()).isEqualTo("1.0.0");
        assertThat(asyncResponse.getFailureDetails()).isNotNull();
        assertThat(asyncResponse.getFailureDetails().getFailureCode()).isEqualTo(FailureDetails.FailureCode.INTERNAL_ERROR);
        assertThat(asyncResponse.getFailureDetails().getDescription()).isEqualTo("Error instantiating VNF");
        assertThat(asyncResponse.getOutputs()).isEmpty();
    }

    @Test
    public void testReceiveNotificationInvalidJSONMessage() {
        final ResponseEntity<ProblemDetails> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                                                                              .postForEntity(NOTIFICATIONS_ENDPOINT, EMPTY_JSON_ENTITY, ProblemDetails.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(responseEntity.getBody().getDetail()).isNotEmpty();
    }

    @Test
    public void testReceiveNotificationInvalidMessage() {
        final ResponseEntity<ProblemDetails> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                                                                              .postForEntity(NOTIFICATIONS_ENDPOINT, "NOT_VALID_JSON", ProblemDetails.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        assertThat(responseEntity.getBody().getDetail()).isNotEmpty();
    }

    @Test
    public void testReceiveNotificationNoAuthentication() {
        final ResponseEntity<ProblemDetails> responseEntity = testRestTemplate.postForEntity(NOTIFICATIONS_ENDPOINT, EMPTY_JSON, ProblemDetails.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        //assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//        assertThat(responseEntity.getBody().getDetail()).isNotEmpty();
    }

    @Test
    public void testReceiveNotificationBadCredentials() {
        final ResponseEntity<String> responseEntity = testRestTemplate.withBasicAuth("invalid_user", "invalid_password")
                                                                              .postForEntity(NOTIFICATIONS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//        assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//        assertThat(responseEntity.getBody().getDetail()).isNotEmpty();
    }

    @Test
    public void testReceiveNotificationNoAuthorization() {
        final ResponseEntity<String> responseEntity = testRestTemplate.withBasicAuth("user_with_no_roles", "password")
                                                                              .postForEntity(NOTIFICATIONS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        //assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
//        assertThat(responseEntity.getBody().getDetail()).isNotEmpty();
    }

    @Test
    public void testReceiveNotificationLockedUser() {
        final ResponseEntity<String> responseEntity = testRestTemplate.withBasicAuth("locked_user", "password")
                                                                              .postForEntity(NOTIFICATIONS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//        assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//        assertThat(responseEntity.getBody().getDetail()).isNotEmpty();
    }

}
