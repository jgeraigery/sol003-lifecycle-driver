package org.etsi.sol003.lifecyclemanagement;

import java.util.List;
import java.util.Map;

import org.etsi.sol003.common.ExtManagedVirtualLinkData;
import org.etsi.sol003.common.ExtVirtualLinkData;
import org.etsi.sol003.common.VimConnectionInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents request parameters for the "Change current VNF package" operation to replace the VNF package on which a VNF instance is based.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents request parameters for the \"Change current VNF package\" operation to replace the VNF package on which a VNF instance is based.")
public class ChangeCurrentVnfPkgRequest {

    @Schema(name = "VnfdId",required = true, description = "Identifier of the VNFD which defines the destination VNF Package for the change.")
    private String vnfdId;
    @Schema(name = "External Virtual Link Information",  description = "Information about external VLs to connect the VNF to. Entries in the list that are unchanged need not be supplied as part of this request.")
    private List<ExtVirtualLinkData> extVirtualLinks;
    @Schema(name = "External Managed Virtual Link Information",  description = "Information about internal VLs that are managed by the NFVO.")
    private List<ExtManagedVirtualLinkData> extManagedVirtualLinks;
    @Schema(name = "VIM Connection Information", description = "Information about VIM connections to be used for managing the resources for the VNF instance, or refer to external virtual links. This attribute shall only be supported and may be present if VNF-related resource management in direct mode is applicable.")
    private Map<String,VimConnectionInfo> vimConnectionInfo;
    @Schema(name = "Additional Parameters", description = "Additional parameters passed by the NFVO as input to the process, specific to the VNF of which the underlying VNF package is changed,, as declared in the VNFD as part of \" ChangeCurrentVnfPkgOpConfig\".")
    private Map<String, String> additionalParams;
    @Schema(name = "Extensions", description = "If present, this attribute provides modifications to the values of the \"extensions\" attribute in \"VnfInstance\".")
    private Map<String, String> extensions;
    @Schema(name = "VNF Configurable Properties", description = "If present, this attribute provides modifications to the values of the \"vnfConfigurableProperties\" attribute in \"VnfInstance\".")
    private Map<String, String> vnfConfigurableProperties;

}
