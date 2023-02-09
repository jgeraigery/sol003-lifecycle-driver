package org.etsi.sol003.lifecyclemanagement;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents a VNF identifier creation notification, which informs the receiver of the creation of a new VNF
 * instance resource and the associated VNF instance identifier.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Represents a VNF identifier creation notification, which informs the receiver of the creation of a new VNF instance resource and the associated VNF instance identifier.")
public class VnfIdentifierCreationNotification implements LifecycleManagementNotification {

    public static final String TYPE = "VnfIdentifierCreationNotification";

    @Schema(name = "Id", required = true, description = "Identifier of this notification. If a notification is sent multiple times due to multiple subscriptions, the \"id\" attribute of all these notifications shall have the same value.")
    private String id;
    @Schema(name = "Notification Type", required = true, description = "Discriminator for the different notification types. Shall be set to \"VnfIdentifierCreationNotification\" for this notification type.")
    private final String notificationType = TYPE;
    @Schema(name = "Subscription Id",required = true, description = "Identifier of the subscription that this notification relates to.")
    private String subscriptionId;
    @Schema(name = "Notification Time", required = true, description = "Date-time of the generation of the notification.")
    private OffsetDateTime timeStamp;
    @Schema(name = "VNF Instance Id", required = true, description = "The created VNF instance identifier.")
    private String vnfInstanceId;
    @Schema(name = "Links", required = true, description = "Links to resources related to this notification.")
    @JsonProperty("_links")
    private LccnLinks links;

}
