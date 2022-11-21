package org.etsi.sol003.lifecyclemanagement;

import java.util.List;
import java.util.Map;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about added, deleted, modified and temporary VNFCs.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about added, deleted, modified and temporary VNFCs.")
public class AffectedVnfc {

    @Schema(name = "Id", required = true, description = "Identifier of the Vnfc instance, identifying the applicable \"vnfcResourceInfo\" entry in the \"VnfInstance\" data type.")
    private String id;
    @Schema(name = "VDU Id", required = true, description = "Identifier of the related VDU in the VNFD.")
    private String vduId;
    @Schema(name = "VnfdId", description = "Identifier of the VNFD. Shall be present in case of a \"change current VNF Package\" to identify whether the affected VNFC instance is associated to a VDU which is referred from the source or destination VNFD.")
    private String vnfdId;
    @Schema(name = "Change Type", required = true, description = "Signals the type of change.")
    private ChangeType changeType;
    @Schema(name = "Compute Resource", required = true, description = "Reference to the VirtualCompute resource. Detailed information is (for new and modified resources) or has been (for removed resources) available from the VIM.")
    private ResourceHandle computeResource;
    @Schema(name = "ResourceDefinitionId", description = "The identifier of the \"ResourceDefinition\" in the granting exchange related to the LCM operation occurrence. It shall be present when an applicable GrantInfo for the granted resource exists.")
    private String resourceDefinitionId;
    @Schema(name = "ZoneId", description = "The identifier of the resource zone, as managed by the resource management layer (typically, the VIM), where the referenced VirtualCompute resource is placed. Shall be provided if this information is available from the VIM.")
    private String zoneId;
    @Schema(name = "Metadata", description = "Metadata about this resource. The content of this attribute shall be a copy of the content of the \"metadata\" attribute of the VnfcResourceInfo structure.")
    private Map<String, String> metadata;
    @Schema(name = "Affected VNFC CP Ids", description = "Identifiers of CP(s) of the VNFC instance that were affected by the change. Shall be present for those affected CPs of the VNFC instance that are associated to an external CP of the VNF instance. May be present for further affected CPs of the VNFC instance.")
    private List<String> affectedVnfcCpIds;
    @Schema(name = "Added Storage Resource Ids", description = "References to VirtualStorage resources that have been added. Each value refers to a VirtualStorageResourceInfo item in the VnfInstance that was added to the VNFC. It shall be provided if at least one storage resource was added to the VNFC.")
    private List<String> addedStorageResourceIds;
    @Schema(name = "Removed Storage Resource Ids", description = "References to VirtualStorage resources that have been removed. The value contains the identifier of a VirtualStorageResourceInfo item that has been removed from the VNFC, and might no longer exist in the VnfInstance. It shall be provided if at least one storage resource was removed from the VNFC.")
    private List<String> removedStorageResourceIds;

    public enum ChangeType {
        ADDED, REMOVED, MODIFIED, TEMPORARY
    }

}
