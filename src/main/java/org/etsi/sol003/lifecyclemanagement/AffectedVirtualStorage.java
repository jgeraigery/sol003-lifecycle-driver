package org.etsi.sol003.lifecyclemanagement;

import java.util.Map;

import org.etsi.sol003.common.ResourceHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about added, deleted, modified and temporary virtual storage resources.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about added, deleted, modified and temporary virtual storage resources.")
public class AffectedVirtualStorage {

    @Schema(name = "Id", required = true, description = "Identifier of the storage instance, identifying the applicable \"virtualStorageResourceInfo\" entry in the \"VnfInstance\" data type.")
    private String id;
    @Schema(name = "Virtual Link Descriptor Id", required = true, description = "Identifier of the related VirtualStorage descriptor in the VNFD.")
    private String virtualStorageDescId;
    @Schema(name = "VnfdId", description = "Identifier of the VNFD. Shall be present in case of a \"change current VNF Package\" to identify whether the affected virtual storage is associated to a VirtualStorage descriptor which is referred from the source or destination VNFD.")
    private String vnfdId;
    @Schema(name = "Change Type", required = true, description = "Signals the type of change.")
    private ChangeType changeType;
    @Schema(name = "Storage Resource", required = true, description = "Reference to the VirtualStorage resource. Detailed information is (for new and modified resources) or has been (for removed resources) available from the VIM.")
    private ResourceHandle storageResource;
    @Schema(name = "ResourceDefinitionId", description = "The identifier of the \"ResourceDefinition\" in the granting exchange related to the LCM operation occurrence. It shall be present when an applicable GrantInfo for the granted resource exists.")
    private String resourceDefinitionId;
    @Schema(name = "ZoneId", description = "The identifier of the resource zone, as managed by the resource management layer (typically, the VIM), where the referenced VirtualStorage resource is placed. Shall be provided if this information is available from the VIM.")
    private String zoneId;
    @Schema(name = "Metadata", description = "Metadata about this resource. The content of this attribute shall be a copy of the content of the \"metadata\" attribute of the VnfcResourceInfo structure.")
    private Map<String, String> metadata;

    public enum ChangeType {
        ADDED, REMOVED, MODIFIED, TEMPORARY
    }

}
