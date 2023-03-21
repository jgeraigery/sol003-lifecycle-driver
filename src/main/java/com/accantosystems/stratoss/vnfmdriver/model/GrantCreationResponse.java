package com.accantosystems.stratoss.vnfmdriver.model;

import lombok.Data;
import org.etsi.sol003.granting.Grant;

@Data
public class GrantCreationResponse {

    private final Grant grant;
    private final String grantId;

    public GrantCreationResponse(String grantId) {
        this.grant = null;
        this.grantId = grantId;
    }

    public GrantCreationResponse(Grant grant) {
        this.grant = grant;
        this.grantId = grant.getId();
    }

    public Grant getGrant() {
        return grant;
    }

    public String getGrantId() {
        return grantId;
    }

}
