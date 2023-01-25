package com.accantosystems.stratoss.vnfmdriver.web.etsi;

import java.net.URI;
import java.util.UUID;

import com.accantosystems.stratoss.common.utils.LoggingUtils;
import com.accantosystems.stratoss.vnfmdriver.model.MessageDirection;
import com.accantosystems.stratoss.vnfmdriver.model.MessageType;
import com.accantosystems.stratoss.vnfmdriver.utils.RequestResponseLogUtils;
import org.etsi.sol003.granting.Grant;
import org.etsi.sol003.granting.GrantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.accantosystems.stratoss.vnfmdriver.driver.GrantProviderException;
import com.accantosystems.stratoss.vnfmdriver.model.GrantCreationResponse;
import com.accantosystems.stratoss.vnfmdriver.service.GrantRejectedException;
import com.accantosystems.stratoss.vnfmdriver.service.GrantService;

import io.swagger.v3.oas.annotations.Operation;



@RestController("GrantController")
@RequestMapping(GrantController.GRANTS_ENDPOINT)
public class GrantController {

    private final static Logger logger = LoggerFactory.getLogger(GrantController.class);

    public static final String GRANTS_ENDPOINT = "/grant/v1/grants";
    private static final String GRANT_LOCATION = GrantController.GRANTS_ENDPOINT + "/{grantId}";

    private final GrantService grantService;

    @Autowired
    public GrantController(GrantService grantService) {
        this.grantService = grantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Requests a grant for a particular VNF lifecycle operation.")
    public ResponseEntity<Grant> requestGrant(@RequestBody GrantRequest grantRequest) throws GrantRejectedException, GrantProviderException {
        logger.info("Received grant request:\n{}", grantRequest);
        UUID uuid = UUID.randomUUID();
        final String driverRequestId;
        if(grantRequest != null){
            driverRequestId = grantRequest.getVnfLcmOpOccId();
            LoggingUtils.logEnabledMDC(grantRequest.toString(), MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(),MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANTS_ENDPOINT, HttpMethod.POST.name(), null) , driverRequestId);
            GrantCreationResponse grantCreationResponse = grantService.requestGrant(grantRequest);
            final ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentContextPath();
            URI location = uriBuilder.path(GRANT_LOCATION).buildAndExpand(grantCreationResponse.getGrantId()).toUri();
            if (grantCreationResponse.getGrant() != null) {
                LoggingUtils.logEnabledMDC(grantCreationResponse.toString(), MessageType.RESPONSE,MessageDirection.SENT,uuid.toString(),MediaType.APPLICATION_JSON.toString(), "http",
                        RequestResponseLogUtils.getResponseSentProtocolMetadata(HttpStatus.OK.value(), "200 OK", null), driverRequestId);
                return ResponseEntity.created(location).body(grantCreationResponse.getGrant());
            } else {
                LoggingUtils.logEnabledMDC(grantCreationResponse.toString(), MessageType.RESPONSE,MessageDirection.SENT,uuid.toString(),MediaType.APPLICATION_JSON.toString(), "http",
                        RequestResponseLogUtils.getResponseSentProtocolMetadata(HttpStatus.ACCEPTED.value(), "202 ACCEPTED", null), driverRequestId);
                return ResponseEntity.accepted().location(location).build();
            }
        }else{
            // error case: since grantRequest object payload is null, can't get the driverRequestId and need to log both request received and response sent
            LoggingUtils.logEnabledMDC(null, MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(),MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANTS_ENDPOINT, HttpMethod.PATCH.name(), null) , null);
            LoggingUtils.logEnabledMDC(null, MessageType.RESPONSE,MessageDirection.SENT,uuid.toString(),MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getResponseSentProtocolMetadata(HttpStatus.BAD_REQUEST.value(), "400 BAD REQUeST", null), null);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = { "/{grantId}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Reads a grant", description = "Returns a previously created grant resource if a granting decision has been made.")
    public ResponseEntity<Grant> getGrant(@PathVariable String grantId) throws GrantRejectedException, GrantProviderException {
        logger.info("Received grant fetch for id [{}]", grantId);
        UUID uuid = UUID.randomUUID();
        Grant grant = grantService.getGrant(grantId);
        if (grant != null) {
            // The below line is intended to add the request received log after getting the grant object, so that the driverRequestId can be added for both request received and response sent
            LoggingUtils.logEnabledMDC(null, MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(), MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANT_LOCATION, HttpMethod.GET.name(), null), grant.getVnfLcmOpOccId());
            LoggingUtils.logEnabledMDC(grant.toString(), MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getResponseSentProtocolMetadata(HttpStatus.OK.value(), "200 OK", null), grant.getVnfLcmOpOccId());
            return ResponseEntity.ok(grant);
        } else {
            // grant object is null, so can't have driverRequestId in this case.
            LoggingUtils.logEnabledMDC(null, MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(), MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANT_LOCATION, HttpMethod.GET.name(), null), null);
            LoggingUtils.logEnabledMDC(null, MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), MediaType.APPLICATION_JSON.toString(), "http",
                    RequestResponseLogUtils.getResponseSentProtocolMetadata(HttpStatus.ACCEPTED.value(), "202 ACCEPTED", null), null);
            return ResponseEntity.accepted().build();
        }
    }
}
