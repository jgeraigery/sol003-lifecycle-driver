package org.etsi.sol003.lifecyclemanagement;

import java.util.Map;

import org.etsi.sol003.common.VimConnectionInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents attribute modifications for an "Individual VNF instance" resource, i.e. modifications to a resource representation based on the "VnfInstance" data type.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents attribute modifications for an \"Individual VNF instance\" resource, i.e. modifications to a resource representation based on the \"VnfInstance\" data type.")
public class VnfInfoModificationRequest {

    @Schema(name = "VNF Instance Name", description = "New value of the \"vnfInstanceName\" attribute in \"VnfInstance\", or \"null\" to remove the attribute.")
    private String vnfInstanceName;
    @Schema(name = "VNF Instance Description", description = "New value of the \"vnfInstanceDescription\" attribute in \"VnfInstance\", or \"null\" to remove the attribute.")
    private String vnfInstanceDescription;
    @Schema(name = "vnfdId", description = "New value of the \"vnfdId\" attribute in \"VnfInstance\". The value \"null\" is not permitted.")
    private String vnfdId;
    @Schema(name = "VNF Configurable Properties", description = "Modifications of the \"vnfConfigurableProperties\" attribute in \"VnfInstance\". If present, these modifications shall be applied according to the rules of JSON Merge PATCH (see IETF RFC 7396)")
    private Map<String, String> vnfConfigurableProperties;
    @Schema(name = "Metadata", description = "Modifications of the \"metadata\" attribute in \"VnfInstance\". If present, these modifications shall be applied according to the rules of JSON Merge PATCH (see IETF RFC 7396)")
    private Map<String, String> metadata;
    @Schema(name = "Extensions", description = "Modifications of the \"extensions\" attribute in \"VnfInstance\". If present, these modifications shall be applied according to the rules of JSON Merge PATCH (see IETF RFC 7396).")
    private Map<String, String> extensions;
    @Schema(name = "VIM Connection Information", description = "New content of certain entries in the \"vimConnectionInfo\" attribute array in \"VnfInstance\", as defined below this table.")
    private Map<String,VimConnectionInfo> vimConnectionInfo;

}
