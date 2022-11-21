package org.etsi.sol003.lifecyclemanagement;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents a link port of an internal VL of a VNF.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents a link port of an internal VL of a VNF.")
public class VnfLinkPortInfo {

    @Schema(name = "Id", required = true, description = "Identifier of this link port as provided by the entity that has created the link port.")
    private String id;
    @Schema(name = "Resource Handle", required = true, description = "Reference to the virtualised network resource realizing this link port.")
    private ResourceHandle resourceHandle;
    @Schema(name = "Connection Point Instance Id", description = "Identifier of the external CP of the VNF to be connected to this link port. Shall be present when the link port is used for external connectivity by the VNF. May be present if used to reference a VNFC CP instance. There shall be at most one link port associated with any external connection point instance or internal connection point (i.e. VNFC CP) instance. The value refers to an \"extCpInfo\" item in the VnfInstance or a \"vnfcCpInfo\" item of a \"vnfcResouceInfo\" item in the VnfInstance.")
    private String cpInstanceId;
    @Schema(name = "CP Instance Type",  description = "Type of the CP instance that is identified by cpInstanceId.")
    private CpInstanceType cpInstanceType;
    @Schema(name = "VIP CP Instance Id", description = "VIP CP instance of the VNF connected to this link port. May be present.")
    private String vipCpInstanceId;
    @Schema(name = "Trunk Resource Id", description = "Identifier of the trunk resource in the VIM. Shall be present if the present link port corresponds to the parent port that the trunk resource is associated with.")
    private String trunkResourceId;

    public enum CpInstanceType {
        /**
         * he link port is connected to a VNFC CP.
         */
        VNFC_CP,
        /**
         * he link port is connected to a external CP.
         */
        EXT_CP
    }

}
