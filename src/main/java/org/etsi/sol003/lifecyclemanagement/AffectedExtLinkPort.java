package org.etsi.sol003.lifecyclemanagement;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about added and deleted external link ports.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about added and deleted external link ports.")
public class AffectedExtLinkPort {

    @Schema(name = "Id", required = true, description = "Identifier of the storage instance, identifying the applicable \"extLinkPorts\" entry in the \"ExtVirtualLinkInfo\" data type.")
    private String id;
    @Schema(name = "Change Type", required = true, description = "Signals the type of change.")
    private ChangeType changeType;
    @Schema(name = "Ext CP Instance Id", required = true, description = "Identifier of the related external CP instance.")
    private String extCpInstanceId;
    @Schema(name = "Resource Handle", required = true, description = "Reference to the link port resource. Detailed information is (for added resources) or has been (for removed resources) available from the VIM.")
    private ResourceHandle resourceHandle;
    @Schema(name = "ResourceDefinitionId", description = "The identifier of the \"ResourceDefinition\" in the granting exchange related to the LCM operation occurrence. It shall be present when an applicable GrantInfo for the granted resource exists.")
    private String resourceDefinitionId;
   
    public enum ChangeType {
        ADDED, REMOVED
    }

}
