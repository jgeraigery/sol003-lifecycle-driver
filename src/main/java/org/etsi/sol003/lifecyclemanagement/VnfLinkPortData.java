package org.etsi.sol003.lifecyclemanagement;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents an externally provided link port to be used to connect a VNFC connection point to an exernallymanaged VL.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents an externally provided link port to be used to connect a VNFC connection point to an exernallymanaged VL.")
public class VnfLinkPortData {
    @Schema(name = "VNF Link Port Id", required = true, description = "Identifier of this link port as provided by the entity that has created the link port.")
    private String vnfLinkPortId;
    @Schema(name = "Resource Handle", required = true, description = "Reference to the virtualised network resource realizing this link port.")
    private ResourceHandle resourceHandle;


    
}
