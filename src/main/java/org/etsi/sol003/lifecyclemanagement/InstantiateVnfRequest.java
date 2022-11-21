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
 * Represents request parameters for the "Instantiate VNF" operation.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents request parameters for the \"Instantiate VNF\" operation.")
public class InstantiateVnfRequest {

    @Schema(name = "Flavour Id", required = true, description = "Identifier of the VNF deployment flavour applied to this VNF instance.")
    private String flavourId;
    @Schema(name = "Instantiation Level Id", description = "Identifier of the instantiation level of the deployment flavour to be instantiated. If not present, the default instantiation level as declared in the VNFD is instantiated.")
    private String instantiationLevelId;
    @Schema(name = "External Virtual Link Information", description = "Information about external VLs to connect the VNF to.")
    private List<ExtVirtualLinkData> extVirtualLinks;
    @Schema(name = "External Managed Virtual Link Information", description = "Information about internal VLs that are managed by other entities than the VNFM.")
    private List<ExtManagedVirtualLinkData> extManagedVirtualLinks;
    @Schema(name = "VIM Connection Information", description = "Information about VIM connections to be used for managing the resources for the VNF instance, or refer to external / externally-managed virtual links. This attribute shall only be supported and may be present if VNF-related resource management in direct mode is applicable.")
    private Map<String,VimConnectionInfo> vimConnectionInfo;
    @Schema(name = "Localization Language", description = "Localization language of the VNF to be instantiated. The value shall comply with the format defined in IETF RFC 5646.")
    private String localizationLanguage;
    @Schema(name = "Additional Parameters", description = "Additional input parameters for the instantiation process, specific to the VNF being instantiated, as declared in the VNFD as part of \"InstantiateVnfOpConfig\".")
    private Map<String, String> additionalParams;
    @Schema(name = "Extensions", description = "If present, this attribute provides modifications to the default values, as obtained from the VNFD, of the\"extensions\" attribute in \"VnfInstance\".")
    private Map<String, String> extensions;


}
