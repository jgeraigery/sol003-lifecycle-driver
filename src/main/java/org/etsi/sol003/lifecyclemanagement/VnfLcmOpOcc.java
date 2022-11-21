package org.etsi.sol003.lifecyclemanagement;

import java.time.OffsetDateTime;
import java.util.List;

import org.etsi.sol003.common.ProblemDetails;
import org.etsi.sol003.common.Link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents a VNF lifecycle management operation occurrence.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents a VNF lifecycle management operation occurrence.")
public class VnfLcmOpOcc {

    @Schema(name = "Id", required = true, description = "Identifier of this VNF lifecycle management operation occurrence.")
    private String id;
    @Schema(name = "Operation State", required = true, description = "The state of the LCM operation.")
    private LcmOperationStateType operationState;
    @Schema(name = "State Entered Time", required = true, description = "Date-time when the current state was entered.")
    private OffsetDateTime stateEnteredTime;
    @Schema(name = "Start Time", required = true, description = "Date-time of the start of the operation.")
    private OffsetDateTime startTime;
    @Schema(name = "VNF Instance Id", required = true, description = "Identifier of the VNF instance to which the operation applies.")
    private String vnfInstanceId;
    @Schema(name = "Grant Id", description = "Identifier of the grant related to this VNF LCM operation occurrence, if such grant exists.")
    private String grantId;
    @Schema(name = "Operation Type", required = true, description = "Type of the actual LCM operation represented by this VNF LCM operation occurrence.")
    private LcmOperationType operation;
    @Schema(name = "Automatic Invocation", required = true, description = "Set to true if this VNF LCM operation occurrence has been triggered by an automated procedure inside the VNFM (i.e. ScaleVnf / ScaleVnfToLevel triggered by auto-scale, or HealVnf triggered by auto-heal). Set to false otherwise.")
    @JsonProperty("isAutomaticInvocation")
    private boolean automaticInvocation;
    /*
     * The following mapping between operationType and the data type of this attribute shall apply:
     *   • INSTANTIATE: InstantiateVnfRequest
     *   • SCALE: ScaleVnfRequest
     *   • SCALE_TO_LEVEL: ScaleVnfToLevelRequest
     *   • CHANGE_FLAVOUR: ChangeVnfFlavourRequest
     *   • OPERATE: OperateVnfRequest
     *   • HEAL: HealVnfRequest
     *   • CHANGE_EXT_CONN: ChangeExtVnfConnectivityRequest
     *   • TERMINATE: TerminateVnfRequest
     *   • MODIFY_INFO: VnfInfoModifications
     */
    @Schema(name = "Operation Parameters", description = "Input parameters of the LCM operation. This attribute shall be formatted according to the request data type of the related LCM operation.")
    private Object operationParams;
    @Schema(name = "Cancel Pending", required = true, description = "If the VNF LCM operation occurrence is in \"STARTING\", \"PROCESSING\" or \"ROLLING_BACK\" state and the operation is being cancelled, this attribute shall be set to true. Otherwise, it shall be set to false.")
    @JsonProperty("isCancelPending")
    private boolean cancelPending;
    @Schema(name = "Cancel Mode", description = "The mode of an ongoing cancellation. Shall be present when isCancelPending=true, and shall be absent otherwise.")
    private CancelModeType cancelMode;
    @Schema(name = "Error", description = "If \"operationState\" is \"FAILED_TEMP\" or \"FAILED\" or \"operationState\" is \"PROCESSING\" or \"ROLLING_BACK\" and previous value of \"operationState\" was \"FAILED_TEMP\", this attribute shall be present and contain error information, unless it has been requested to be excluded via an attribute selector.")
    private ProblemDetails error;
    @Schema(name = "Resource Changes", description = "This attribute contains information about the cumulative changes to virtualised resources that were performed so far by the LCM operation since its start, if applicable.")
    private ResourceChanges resourceChanges;
    @Schema(name = "Changed Information", description = "Information about the changed VNF instance information, including VNF configurable properties, if applicable.")
    private VnfInfoModificationRequest changedInfo;
    @Schema(name = "affected VIP CPs", description = "Information about virtual IP CP instances that were affected during the execution of the lifecycle management operation.")
    private List<AffectedVipCp> affectedVipCps;
    @Schema(name = "Changed External Connectivity", description = "Information about changed external connectivity, if applicable.")
    private List<ExtVirtualLinkInfo> changedExtConnectivity;
    @Schema(name = "Modifications Triggered By VnfPkgChange", description = "Information about performed changes of \"VnfInstance\" attributes triggered by changing the current VNF package, if applicable. Shall be absent if the \"operation\" attribute is different from \"CHANGE_VNFPKG\".")
    private ModificationsTriggeredByVnfPkgChange modificationsTriggeredByVnfPkgChange;
    @Schema(name = "vnf Snapshot Info Id", description = "Identifier of the \"Individual VNF snapshot\" resource. Shall be present if applicable to the type of LCM operation, i.e. if the value of the \"operation\" attribute is either \"CREATE_SNAPSHOT\" or \"REVERT_TO_SNAPSHOT\".")
    private String vnfSnapshotInfoId;
    @Schema(name = "lcm Coordinations", description = "This attribute contains information about  LCM coordination actions (see clause 10 in ETSI GS NFV-SOL 002 [i.2]) related to this LCM operation occurrence.")
    private List<LcmCoordinations> lcmCoordinations;
    @Schema(name = "rejectedLcmCoordinationss", description = "This attribute contains information about LCM coordination actions (see clause 10 in ETSI GS NFV-SOL 002 [i.2]) that were rejected by 503 error which means they can be tried again after a delay.")
    private List<RejectedLcmCoordinations> rejectedLcmCoordinations;
    @Schema(name = "Links", required = true, description = "Links to resources related to this resource.")
    @JsonProperty("_links")
    private Links links;

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "Links to resources related to this resource.")
    public static class Links {

        @Schema(name = "self", required = true, description = "URI of this resource.")
        private Link self;
        @Schema(name = "vnfInstance", required = true, description = "Link to the VNF instance that the operation applies to.")
        private Link vnfInstance;
        @Schema(name = "grant", description = "Link to the grant for this operation, if one exists.")
        private Link grant;
        @Schema(name = "cancel", description = "Link to the task resource that represents the \"cancel\" operation for this VNF LCM operation occurrence, if cancelling is currently allowed.")
        private Link cancel;
        @Schema(name = "retry", description = "Link to the task resource that represents the \"retry\" operation for this VNF LCM operation occurrence, if retrying is currently allowed.")
        private Link retry;
        @Schema(name = "rollback", description = "Link to the task resource that represents the \"rollback\" operation for this VNF LCM operation occurrence, if rolling back is currently allowed.")
        private Link rollback;
        @Schema(name = "fail", description = "Link to the task resource that represents the \"fail\" operation for this VNF LCM operation occurrence, if declaring as failed is currently allowed.")
        private Link fail;
        @Schema(name = "vnfSnapshot", description = "Link to the VNF snapshot resource, if the VNF LCM operation occurrence is related to a VNF snapshot. Shall be present if operation=\"CREATE_SNAPSHOT\" or operation=\"REVERT_TO_SNAPSHOT\".")
        private Link vnfSnapshot;

    }

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "Information about the cumulative changes to virtualised resources that were performed so far by the LCM operation since its start.")
    public static class ResourceChanges {

        @Schema(name = "Affected VNFCs", description = "Information about VNFC instances that were affected during the lifecycle operation.")
        private List<AffectedVnfc> affectedVnfcs;
        @Schema(name = "Affected Virtual Links", description = "Information about VL instances that were affected during the lifecycle operation.")
        private List<AffectedVirtualLink> affectedVirtualLinks;
        @Schema(name = "Affected ExtLink Links", description = "Information about external VNF link ports that were affected during the lifecycle operation.")
        private List<AffectedVirtualLink> affectedExtLinkPorts;
        @Schema(name = "Affected Virtual Storage", description = "Information about virtualised storage instances that were affected during the lifecycle operation.")
        private List<AffectedVirtualStorage> affectedVirtualStorages;

    }

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "Information about LCM coordination actions (see clause 10 in ETSI GS NFV-SOL 002 [i.2]) related to this LCM operation occurrence.")
    public static class LcmCoordinations {

        @Schema(name = "Id", required = true, description = "Identifier of this coordination action.")
        private String id;
        @Schema(name = "Coordination Action Name", required = true, description = "Indicator of the actual coordination action.")
        private String coordinationActionName; 
        @Schema(name = "Coordination Result",  description = "The result of executing the coordination action which also implies the action to be performed by the VNFM as the result of this coordination.")
        private LcmCoordResultType coordinationResult;
        @Schema(name = "Start Time", required = true, description = "The time when the VNFM has received the confirmation that the coordination action has been started.")
        private OffsetDateTime startTime;
        @Schema(name = "End Time",  description = "The time when the VNFM has received the confirmation that the coordination action has finished or has been cancelled, or the time when a coordination action has timed out.")
        private OffsetDateTime endTime;
        @Schema(name = "delay",  description = "The end of the delay period. This attribute shall be present if the last known HTTP response related to this coordination has contained a \"Retry-After\" header, and shall be absent otherwise.")
        private OffsetDateTime delay;



    }

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "This attribute contains information about LCM coordination actions (see clause 10 in ETSI GS NFV-SOL 002 [i.2]) that were rejected by 503 error which means they can be tried again after a delay.")
    public static class RejectedLcmCoordinations {

        @Schema(name = "Coordination Action Name", required = true, description = "Indicator of the actual coordination action.")
        private String coordinationActionName; 
        @Schema(name = "Rejection Time", required = true, description = "The time when the VNFM has received the 503 response  that rejects the actual coordination.")
        private OffsetDateTime rejectionTime;
        @Schema(name = "Endpoint Type", required = true, description = "The endpoint type used by this coordination action.")
        private EndpointType endpointType;
        @Schema(name = "delay", required = true, description = "The end of the delay period. as calculated from the startTime and \"Retry-After\" header.")
        private OffsetDateTime delay;
        @Schema(name = "Warnings",  description = "Warning messages that were generated while the operation was executing.If the operation has included LCM coordination actions and these have resulted in warnings, such warnings should be added to this attribute.")
        private List<String> warnings;

        public enum EndpointType {

            /*
              * coordination with other operation supporting management systems (e.g. EM)
            */
            MGMT, 
            /*
              * coordination with the VNF instance
            */
            VNF
        }
        
    }

}
