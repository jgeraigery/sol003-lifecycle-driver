package org.etsi.sol003.lifecyclemanagement;

import java.util.List;
import java.util.Map;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents the information that allows addressing a virtualised resource that is used by an internal VL instance in a VNF instance.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents the information that allows addressing a virtualised resource that is used by an internal VL instance in a VNF instance.")
public class VnfVirtualLinkResourceInfo {

    @Schema(name = "Id", required = true, description = "Identifier of this VnfVirtualLinkResourceInfo instance.")
    private String id;
    @Schema(name = "Virtual Link Descriptor Id", required = true, description = "Identifier of the VNF Virtual Link Descriptor (VLD) in the VNFD.")
    private String vnfVirtualLinkDescId;
    @Schema(name = "VnfdId", description = "Shall be present in case the value differs from the vnfdId attribute of the VnfInstance (e.g. during a \"Change current VNF package\" operation or due to its final failure).")
    private String vnfdId;
    @Schema(name = "Network Resource", required = true, description = "Reference to the VirtualNetwork resource.")
    private ResourceHandle networkResource;
    @Schema(name = "ZoneId", description = "The identifier of the resource zone, as managed by the resource management layer (typically, the VIM), where the referenced VirtualStorage resource is placed. Shall be provided if this information is available from the VIM.")
    private String zoneId;
    @Schema(name = "Reservation Id", description = "The reservation identifier applicable to the resource. It shall be present when an applicable reservation exists.")
    private String reservationId;
    @Schema(name = "VNFC Link Ports", description = "Links ports of this VL. Shall be present when the linkPort is used for external connectivity by the VNF (refer to VnfLinkPort). May be present otherwise.")
    private List<VnfLinkPortInfo> vnfLinkPorts;
    @Schema(name = "Metadata", description = "Metadata about this resource.")
    private Map<String, String> metadata;

}
