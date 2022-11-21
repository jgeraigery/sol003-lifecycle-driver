package org.etsi.sol003.lifecyclemanagement;

import java.util.Map;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents information about attribute modifications that were performed on an "Individual VNF instance" resource when changing the current VNF package.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents information about attribute modifications that were performed on an \"Individual VNF instance\" resource when changing the current VNF package.")
public class ModificationsTriggeredByVnfPkgChange {
    @Schema(name = "VNF Configurable Properties", description = "This attribute signals the modifications of the \"vnfConfigurableProperties\" attribute in \"VnfInstance\" performed by the operation and shall be present if that  attribute was modified during the operation.")
    private Map<String, String> vnfConfigurableProperties;
    @Schema(name = "Metadata", description = "This attribute signals the modifications of the \"metadata\" attribute in \"VnfInstance\" performed by the operation and shall be present if that attribute was modified during the operation.")
    private Map<String, String> metadata;
    @Schema(name = "Extensions", description = "This attribute signals the modifications of the \"extensions\" attribute in \"VnfInstance\" performed by the operation and shall be present if that attribute was modified during the operation.")
    private Map<String, String> extensions;
    @Schema(name = "VnfdId", description = "If present, this attribute signals the new value of the \"vnfdId\" attribute in \"VnfInstance\".")
    private String vnfdId;
    @Schema(name = "vnf Provider", description = "If present, this attribute signals the new value of the \"vnfProvider\" attribute in \"VnfInstance\".")
    private String vnfProvider;
    @Schema(name = "vnf Product Name", description = "If present, this attribute signals the new value of the \"vnfProductName\" attribute in \"VnfInstance\".")
    private String vnfProductName;
    @Schema(name = "vnf Software Version", description = "If present, this attribute signals the new value of the \"vnfSoftwareVersion\" attribute in \"VnfInstance\".")
    private String vnfSoftwareVersion;
    @Schema(name = "vnfd Version", description = "If present, this attribute signals the new value of the \"vnfdVersion\" attribute in \"VnfInstance\".")
    private String  vnfdVersion;

   

}
