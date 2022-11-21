package org.etsi.sol003.lifecyclemanagement;

import java.util.List;
import java.util.Map;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Links to resources related to this notification.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Links to resources related to this notification.")
public class VnfcResourceInfo {

    @Schema(name = "Id", required = true, description = "Identifier of this VnfcResourceInfo instance.")
    private String id;
    @Schema(name = "VDU Id", required = true, description = "Reference to the applicable VDU in the VNFD.")
    private String vduId;
    @Schema(name = "VnfdId", description = "Shall be present in case the value differs from the vnfdId attribute of the VnfInstance (e.g. during a \"Change current VNF package\" operation or due to its final failure).")
    private String vnfdId;
    @Schema(name = "Compute Resource", required = true, description = "Reference to the VirtualCompute resource.")
    private ResourceHandle computeResource;
    @Schema(name = "ZoneId", description = "The identifier of the resource zone, as managed by the resource management layer (typically, the VIM), where the referenced VirtualStorage resource is placed. Shall be provided if this information is available from the VIM.")
    private String zoneId;
    @Schema(name = "Storage Resource Ids", description = "References to the VirtualStorage resources. The value refers to a VirtualStorageResourceInfo item in the VnfInstance.")
    private List<String> storageResourceIds;
    @Schema(name = "Reservation Id", description = "The reservation identifier applicable to the resource. It shall be present when an applicable reservation exists.")
    private String reservationId;
    @Schema(name = "VNFC Connection Point Information", description = "CPs of the VNFC instance. Shall be present when that particular CP of the VNFC instance is associated to an external CP of the VNF instance. May be present otherwise.")
    private List<VnfcCpInfo> vnfcCpInfo;
    @Schema(name = "Metadata", description = "Metadata about this resource.")
    private Map<String, String> metadata;

    /**
     * Represents VNFC Connection Point Information
     */
    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "Represents VNFC Connection Point Information.")
    public static class VnfcCpInfo {

        @Schema(name = "Id", required = true, description = "Identifier of this VNFC CP instance and the associated array entry.")
        private String id;
        @Schema(name = "CPD Id", required = true, description = "Identifier of the VDU CPD, cpdId, in the VNFD.")
        private String cpdId;
        @Schema(name = "VNF External Connection Point Id", description = "When the VNFC CP is exposed as external CP of the VNF, the identifier of this external VNF CP.")
        private String vnfExtCpId;
        @Schema(name = "Connection Point Protocol Information", description = "Network protocol information for this CP.")
        private List<CpProtocolInfo> cpProtocolInfo;
        @Schema(name = "VNF Link Port Id", description = "Identifier of the \"vnfLinkPorts\" structure in the \"vnfVirtualLinkResourceInfo\" structure. Shall be present if the CP is associated to a link port.")
        private String vnfLinkPortId;
        @Schema(name = "Parent Cp Id", description = "Identifier of another VNFC CP instance that corresponds to the parent port of a trunk that the present VNFC CP instance participates in.")
        private String parentCpId;
        @Schema(name = "Metadata", description = "Metadata about this CP.")
        private Map<String, String> metadata;

    }

}
