package com.accantosystems.stratoss.vnfmdriver.web.etsi;

import static com.accantosystems.stratoss.vnfmdriver.test.TestConstants.EMPTY_JSON;
import static com.accantosystems.stratoss.vnfmdriver.test.TestConstants.loadFileIntoString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.etsi.sol003.common.ProblemDetails;
import org.etsi.sol003.granting.Grant;
import org.etsi.sol003.granting.GrantRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import com.accantosystems.stratoss.vnfmdriver.model.GrantCreationResponse;
import com.accantosystems.stratoss.vnfmdriver.service.GrantRejectedException;
import com.accantosystems.stratoss.vnfmdriver.service.GrantService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GrantControllerTest {

    public static final String GRANTS_ENDPOINT = "/grant/v1/grants";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private GrantService grantService;

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void testRequestGrantSync() throws Exception {

        // grantService responds immediately with the requested grant
        Grant grant = new Grant();
        String grantId = UUID.randomUUID().toString();
        grant.setId(grantId);
        when(grantService.requestGrant(any(GrantRequest.class))).thenReturn(new GrantCreationResponse(grant));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(loadFileIntoString("examples/GrantRequest-INSTANTIATE.json"), headers);
        final ResponseEntity<Grant> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .postForEntity(GRANTS_ENDPOINT, httpEntity, Grant.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(grant.getId());
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo(GRANTS_ENDPOINT + "/" + grantId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testRequestGrantAsync() throws Exception {

        // grantService has no immediate decision on requested grant (returns null)
        String grantId = UUID.randomUUID().toString();
        when(grantService.requestGrant(any(GrantRequest.class))).thenReturn(new GrantCreationResponse(grantId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(loadFileIntoString("examples/GrantRequest-INSTANTIATE.json"), headers);
        final ResponseEntity<Grant> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .postForEntity(GRANTS_ENDPOINT, httpEntity, Grant.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNull();
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo(GRANTS_ENDPOINT + "/" + grantId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    public void testRequestGrantRejected() throws Exception {

        String rejectionReason = "Unable to accept grant";
        when(grantService.requestGrant(any(GrantRequest.class))).thenThrow(new GrantRejectedException(rejectionReason));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(loadFileIntoString("examples/GrantRequest-INSTANTIATE.json"), headers);
        final ResponseEntity<ProblemDetails> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .postForEntity(GRANTS_ENDPOINT, httpEntity, ProblemDetails.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(rejectionReason);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetGrantDecisionMade() throws Exception {

        Grant grant = new Grant();
        String grantId = UUID.randomUUID().toString();
        grant.setId(grantId);
        when(grantService.getGrant(eq(grantId))).thenReturn(grant);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ResponseEntity<Grant> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .getForEntity(GRANTS_ENDPOINT + "/" + grantId, Grant.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(grantId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetGrantDecisionPending() throws Exception {

        String grantId = UUID.randomUUID().toString();
        when(grantService.getGrant(eq(grantId))).thenReturn(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ResponseEntity<Grant> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .getForEntity(GRANTS_ENDPOINT + "/" + grantId, Grant.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    public void testGetGrantRejected() throws Exception {

        String rejectionReason = "Unable to accept grant";
        String grantId = UUID.randomUUID().toString();
        when(grantService.getGrant(eq(grantId))).thenThrow(new GrantRejectedException(rejectionReason));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ResponseEntity<ProblemDetails> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .getForEntity(GRANTS_ENDPOINT + "/" + grantId, ProblemDetails.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(rejectionReason);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testRequestGrantInvalidMessage() {
        final ResponseEntity<ProblemDetails> responseEntity = testRestTemplate.withBasicAuth("user", "password")
                .postForEntity(GRANTS_ENDPOINT, "NOT_VALID_JSON", ProblemDetails.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
      //  assertThat(responseEntity.getBody()).isNotNull();
      //  assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @Test
    public void testRequestGrantNoAuthentication() {
        final ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(GRANTS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        //assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    public void testRequestGrantBadCredentials() {
        final ResponseEntity<String> responseEntity = testRestTemplate.withBasicAuth("invalid_user", "invalid_password")
                .postForEntity(GRANTS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testRequestGrantNoAuthorization() {
        final ResponseEntity<String> responseEntity = testRestTemplate.withBasicAuth("user_with_no_roles", "password")
                .postForEntity(GRANTS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        //assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    public void testRequestGrantLockedUser() {
        final ResponseEntity<String> responseEntity = testRestTemplate.withBasicAuth("locked_user", "password")
                .postForEntity(GRANTS_ENDPOINT, EMPTY_JSON, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
