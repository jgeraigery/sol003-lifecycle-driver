package org.etsi.sol003.lifecyclemanagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents the scale level of a VNF instance related to a scaling aspect.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents the scale level of a VNF instance related to a scaling aspect.")
public class ScaleInfo {

    @Schema(name = "Scaling Aspect Id", required = true, description = "Identifier of the scaling aspect.")
    private String aspectId;
    @Schema(name = "VnfdId", description = "Identifier of the VNFD. Shall be present in case the value differs from the vnfdId attribute of the VnfInstance (e.g. during a \"Change current VNF package\" operation or due to its final failure).")
    private String vnfdId;
    @Schema(name = "Scale Level", required = true, description = "Indicates the scale level. The minimum value shall be 0 and the maximum value shall be <= maxScaleLevel as described in the VNFD.")
    private Integer scaleLevel;

}
