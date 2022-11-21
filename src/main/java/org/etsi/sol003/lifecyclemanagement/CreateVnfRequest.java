package org.etsi.sol003.lifecyclemanagement;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents request parameters for the "Create VNF identifier" operation.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents request parameters for the \"Create VNF identifier\" operation.")
public class CreateVnfRequest {

    @Schema(name = "VNFD Id", required = true, description = "Identifier that identifies the VNFD which defines the VNF instance to be created.")
    private String vnfdId;
    @Schema(name = "VNF Instance Name", description = "Human-readable name of the VNF instance to be created.")
    private String vnfInstanceName;
    @Schema(name = "VNF Instance Description", description = "Human-readable description of the VNF instance to be created.")
    private String vnfInstanceDescription;
    @Schema(name = "meta data", description = "If present, this attribute provides additional initial values,overriding those obtained from the VNFD, for the \"metadata\" attribute in \"VnfInstance\".")
    private Map<String, String> metadata;

}
