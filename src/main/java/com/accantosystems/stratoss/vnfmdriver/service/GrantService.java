package com.accantosystems.stratoss.vnfmdriver.service;

import java.util.Optional;
import java.util.UUID;

import org.etsi.sol003.granting.Grant;
import org.etsi.sol003.granting.GrantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accantosystems.stratoss.vnfmdriver.config.VNFMDriverProperties;
import com.accantosystems.stratoss.vnfmdriver.driver.GrantDriver;
import com.accantosystems.stratoss.vnfmdriver.driver.GrantProviderException;
import com.accantosystems.stratoss.vnfmdriver.model.GrantCreationResponse;
import com.accantosystems.stratoss.vnfmdriver.web.etsi.BadRequestException;

@Service("GrantService")
public class GrantService {

    private final static Logger logger = LoggerFactory.getLogger(GrantService.class);

    private final VNFMDriverProperties vnfmDriverProperties;
    private final GrantDriver grantDriver;

    @Autowired
    public GrantService(VNFMDriverProperties vnfmDriverProperties, Optional<GrantDriver> grantDriver) {
        this.vnfmDriverProperties = vnfmDriverProperties;
        if (grantDriver.isPresent()) {
            this.grantDriver = grantDriver.get();
        } else if (!vnfmDriverProperties.getGrant().isAutomatic()) {
            throw new BeanInitializationException("Expected instance of GrantDriver when operating in non-automatic grant mode");
        } else {
            this.grantDriver = null;
        }
    }

    public GrantCreationResponse requestGrant(GrantRequest grantRequest) throws GrantRejectedException, GrantProviderException {

        if (grantRequest == null) {
            throw new BadRequestException("Grant Request cannot be null");
        } else if (grantRequest.getVnfInstanceId() == null) {
            throw new BadRequestException("Grant request missing vnfInstanceId");
        } else if (grantRequest.getOperation() == null) {
            throw new BadRequestException(String.format("Grant request for vnfInstanceId [%s] contained a null operation", grantRequest.getVnfInstanceId()));
        }

        GrantCreationResponse grantCreationResponse;
        if (vnfmDriverProperties.getGrant().isAutomatic()) {
            // when grants are automatic, all grants requests will be accepted immediately and not persisted.
            // the created grantId will be randomly generated and will always correspond to an accepted grant
            // for now the grant response will only contain the id. Not yet sure what else it needs
            Grant grant = new Grant();
            grant.setId(UUID.randomUUID().toString());
            //grant.setVnfInstanceId(grantRequest.getVnfInstanceId());
            //grant.setVnfLcmOpOccId(grantRequest.getVnfLcmOpOccId());
            grantCreationResponse = new GrantCreationResponse(grant);
            logger.info("Auto-authorised grant for operation to {} VNF instance [{}]", grantRequest.getOperation(), grantRequest.getVnfInstanceId());
        } else {
            logger.info("Requesting grant operation to {} VNF instance [{}]", grantRequest.getOperation(), grantRequest.getVnfInstanceId());
            grantCreationResponse = grantDriver.requestGrant(grantRequest);
            if (grantCreationResponse.getGrant() == null) {
                // asynchronous grant flow. Must poll get Grant API for granting decision
                logger.info("Grant request for operation to {} VNF instance [{}] has no immediate response and should be polled", grantRequest.getOperation(), grantRequest.getVnfInstanceId());
            } else {
                // synchronous grant flow. Response immediately available and grant resource can be returned
                logger.info("Grant request for operation to {} VNF instance [{}] was immediately granted", grantRequest.getOperation(), grantRequest.getVnfInstanceId());
            }
        }
        return grantCreationResponse;
    }

    public Grant getGrant(String grantId) throws GrantRejectedException, GrantProviderException {

        if (grantId == null) {
            throw new BadRequestException("grantId cannot be null");
        }

        Grant grant;
        if (vnfmDriverProperties.getGrant().isAutomatic()) {
            // automatic granting mode
            // for now we are not persisting grants so the only information we can return in the grant response is the provided id
            logger.info("Auto-authorised grant for grantId [{}]. Returning empty grant resource", grantId);
            grant = new Grant();
            grant.setId(grantId);
            return grant;
        } else {
            logger.info("Getting grant with grantId [{}]", grantId);
            grant = grantDriver.getGrant(grantId);
            if (grant == null) {
                logger.info("No grant decision yet available for request with grantId [{}]", grantId);
            } else {
                logger.info("Grant decision available for request with grantId [{}]. Returning resource", grantId);
            }
        }
        return grant;
    }

}
