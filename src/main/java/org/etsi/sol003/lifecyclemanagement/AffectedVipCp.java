package org.etsi.sol003.lifecyclemanagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about added,deleted and modified virtual IP CP instances.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about added,deleted and modified virtual IP CP instances.")
public class AffectedVipCp {

    @Schema(name = "CP InstanceId", required = true, description = "Identifier of the virtual IP CP instance and the related \"VipCpInfo\" structure in \"VnfInstance\".")
    private String cpInstanceId;
    @Schema(name = "Cpd Id", required = true, description = "Identifier of the VipCpd in the VNFD.")
    private String cpdId;
    @Schema(name = "VnfdId", description = "Reference to the VNFD. Shall be present in case of a \"change current VNF Package\" to identify whether the virtual CP instance is associated to a VipCpd which is referred from the source or destination VNFD.")
    private String vnfdId;
    @Schema(name = "Change Type", required = true, description = "Signals the type of change.")
    private ChangeType changeType;
   
    public enum ChangeType {
        ADDED, REMOVED, MODIFIED
    }

}
