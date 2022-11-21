package org.etsi.sol003.lifecyclemanagement;

import java.util.Map;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents the information that allows addressing a virtualised resource that is used by a VNF instance.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents the information that allows addressing a virtualised resource that is used by a VNF instance.")
public class VirtualStorageResourceInfo {

    @Schema(name = "Id", required = true, description = "Identifier of this VirtualStorageResourceInfo instance.")
    private String id;
    @Schema(name = "Virtual Storage Descriptor Id", required = true, description = "Identifier of the VirtualStorageDesc in the VNFD.")
    private String virtualStorageDescId;
    @Schema(name = "VnfdId", description = "Shall be present in case the value differs from the vnfdId attribute of the VnfInstance (e.g. during a \"Change current VNF package\" operation or due to its final failure).")
    private String vnfdId;
    @Schema(name = "Storage Resource", required = true, description = "Reference to the VirtualStorage resource.")
    private ResourceHandle storageResource;
    @Schema(name = "ZoneId", description = "The identifier of the resource zone, as managed by the resource management layer (typically, the VIM), where the referenced VirtualStorage resource is placed. Shall be provided if this information is available from the VIM.")
    private String zoneId;
    @Schema(name = "Reservation Id", description = "The reservation identifier applicable to the resource. It shall be present when an applicable reservation exists.")
    private String reservationId;
    @Schema(name = "Metadata", description = "Metadata about this resource.")
    private Map<String, String> metadata;

}
