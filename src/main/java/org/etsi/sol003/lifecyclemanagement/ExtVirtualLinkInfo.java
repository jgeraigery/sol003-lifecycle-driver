package org.etsi.sol003.lifecyclemanagement;

import java.util.List;

import org.etsi.sol003.common.ResourceHandle;
import org.etsi.sol003.common.VnfExtCpData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about an external VL.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about an external VL.")
public class ExtVirtualLinkInfo {

    @Schema(name = "Id", required = true, description = "Identifier of the external VL and the related external VL information instance.")
    private String id;
    @Schema(name = "Resource Handle", required = true, description = "Reference to the resource realizing this VL.")
    private ResourceHandle resourceHandle;
    @Schema(name = "Link Ports", description = "Link ports of this VL.")
    private List<ExtLinkPortInfo> extLinkPorts;
    @Schema(name = "Current VnfExtCpData", required = true, description = "Allows the API consumer to read the current CP configuration information for the connection of external CPs to the external virtual link.")
    private List<VnfExtCpData> currentVnfExtCpData;

}
