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
import org.springframework.http.*;
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

    public final static String LOG_URI_PREFIX = "...";
    public static final String GRANTS_ENDPOINT = "/grant/v1/grants";
    private static final String GRANT_LOCATION = GrantController.GRANTS_ENDPOINT + "/{grantId}";

    private final GrantService grantService;

    @Autowired
    public GrantController(GrantService grantService) {
        this.grantService = grantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Requests a grant for a particular VNF lifecycle operation.")
    public ResponseEntity<Grant> requestGrant(@RequestBody GrantRequest grantRequest, @RequestHeader HttpHeaders headers) throws GrantRejectedException, GrantProviderException {
        logger.info("Received grant request:\n{}", grantRequest);
        UUID uuid = UUID.randomUUID();
        final String driverRequestId;
        if(grantRequest != null){
            driverRequestId = grantRequest.getVnfLcmOpOccId();
            LoggingUtils.logEnabledMDC(RequestResponseLogUtils.convertToJson(grantRequest), MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(), MediaType.APPLICATION_JSON_VALUE, "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(LOG_URI_PREFIX+GRANTS_ENDPOINT, HttpMethod.POST.name(), headers), driverRequestId);
            GrantCreationResponse grantCreationResponse = grantService.requestGrant(grantRequest);
            final ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentContextPath();
            URI location = uriBuilder.path(GRANT_LOCATION).buildAndExpand(grantCreationResponse.getGrantId()).toUri();
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.add("Location", location.toString());
            if (grantCreationResponse.getGrant() != null) {
                LoggingUtils.logEnabledMDC(RequestResponseLogUtils.convertToJson(grantCreationResponse), MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), MediaType.APPLICATION_JSON_VALUE, "http",
                        RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), responseHeader), driverRequestId);
                return ResponseEntity.created(location).body(grantCreationResponse.getGrant());
            } else {
                LoggingUtils.logEnabledMDC(RequestResponseLogUtils.convertToJson(grantCreationResponse), MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), MediaType.APPLICATION_JSON_VALUE, "http",
                        RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), responseHeader), driverRequestId);
                return ResponseEntity.accepted().location(location).build();
            }
        }else{
            // error case: since grantRequest object payload is null, can't get the driverRequestId and need to log both request received and response sent
            LoggingUtils.logEnabledMDC("", MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(), "", "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANTS_ENDPOINT, HttpMethod.POST.name(), null), null);
            LoggingUtils.logEnabledMDC("", MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), "", "http",
                    RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null), null);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = { "/{grantId}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Reads a grant", description = "Returns a previously created grant resource if a granting decision has been made.")
    public ResponseEntity<Grant> getGrant(@PathVariable String grantId, @RequestHeader HttpHeaders headers) throws GrantRejectedException, GrantProviderException {
        logger.info("Received grant fetch for id [{}]", grantId);
        UUID uuid = UUID.randomUUID();
        Grant grant = grantService.getGrant(grantId);
        if (grant != null) {
            // The below line is intended to add the request received log after getting the grant object, so that the driverRequestId can be added for both request received and response sent
            LoggingUtils.logEnabledMDC("", MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(), "", "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANT_LOCATION, HttpMethod.GET.name(), headers), grant.getVnfLcmOpOccId());
            LoggingUtils.logEnabledMDC(RequestResponseLogUtils.convertToJson(grant), MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), MediaType.APPLICATION_JSON_VALUE, "http",
                    RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null), grant.getVnfLcmOpOccId());
            return ResponseEntity.ok(grant);
        } else {
            // grant object is null, so can't have driverRequestId in this case.
            LoggingUtils.logEnabledMDC("", MessageType.REQUEST, MessageDirection.RECEIVED, uuid.toString(), "", "http",
                    RequestResponseLogUtils.getRequestReceivedProtocolMetaData(GRANT_LOCATION, HttpMethod.GET.name(), headers), null);
            LoggingUtils.logEnabledMDC("", MessageType.RESPONSE, MessageDirection.SENT, uuid.toString(), "", "http",
                    RequestResponseLogUtils.getResponseSentProtocolMetaData(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), null), null);
            return ResponseEntity.accepted().build();
        }
    }
}
