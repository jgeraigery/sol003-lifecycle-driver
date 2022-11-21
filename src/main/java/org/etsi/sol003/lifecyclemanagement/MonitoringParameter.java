package org.etsi.sol003.lifecyclemanagement;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents a monitoring parameter that is tracked by the VNFM, e.g. for auto-scaling purposes.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents a monitoring parameter that is tracked by the VNFM, e.g. for auto-scaling purposes.")
public class MonitoringParameter {

    @Schema(name = "Id", required = true, description = "Identifier of the monitoring parameter defined in the VNFD.")
    private String id;
    @Schema(name = "VnfdId", description = "Identifier of the VNFD. Shall be present in case the value differs from the vnfdId attribute of the VnfInstance (e.g. during a \"Change current VNF package\" operation or due to its final failure).")
    private String vnfdId;
    @Schema(name = "Name", description = "Human readable name of the monitoring parameter, as defined in the VNFD.")
    private String name;
    @Schema(name = "Performance Metric", required = true, description = "Performance metric that is monitored. This attribute shall contain the related \"Measurement Name\" value")
     private String performanceMetric;


}
