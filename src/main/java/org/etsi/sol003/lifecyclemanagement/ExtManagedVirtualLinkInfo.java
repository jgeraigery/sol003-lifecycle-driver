package org.etsi.sol003.lifecyclemanagement;

import java.util.List;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about an externally-managed virtual link.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about an externally-managed virtual link.")
public class ExtManagedVirtualLinkInfo {

    @Schema(name = "Id", required = true, description = "Identifier of the externally-managed internal VL and the related externally-managed VL information instance.")
    private String id;
    @Schema(name = "VNF VLD Id", required = true, description = "Identifier of the VNF Virtual Link Descriptor (VLD) in the VNFD.")
    private String vnfVirtualLinkDescId;
    @Schema(name = "VnfdId", description = "Identifier of the VNFD. Shall be present in case the value differs from the vnfdId attribute of the VnfInstance (e.g. during a \"Change current VNF package\" operation or due to its final failure).")
    private String vnfdId;
    @Schema(name = "Network Resource", required = true, description = "Reference to the VirtualNetwork resource.")
    private ResourceHandle networkResource;
    @Schema(name = "Link Ports", description = "Link ports of this VL.")
    private List<ExtLinkPortInfo> vnfLinkPorts;
    @Schema(name = "Ext Managed Multisite VirtualLinkId", description = "Identifier of the externally-managed multi-site VL instance. The identifier is assigned by the NFVMANO entity that manages the externally managed multi-site VL instance. It shall be present when the externally-managed internal VL is part of a multi-site VL.")
    private String extManagedMultisiteVirtualLinkId;

}
