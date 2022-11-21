package com.accantosystems.stratoss.vnfmdriver.web.etsi;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionAsyncResponse;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionStatus;
import com.accantosystems.stratoss.vnfmdriver.model.alm.FailureDetails;
import org.etsi.sol003.lifecyclemanagement.LcmOperationStateType;
import org.etsi.sol003.lifecyclemanagement.LifecycleManagementNotification;
import org.etsi.sol003.lifecyclemanagement.VnfLcmOperationOccurenceNotification;
import com.accantosystems.stratoss.vnfmdriver.service.ExternalMessagingService;

import io.swagger.v3.oas.annotations.Operation;



@RestController("LifecycleNotificationController")
@RequestMapping("/vnflcm/v2/notifications")
public class LifecycleNotificationController {

    private final static Logger logger = LoggerFactory.getLogger(LifecycleNotificationController.class);

    private final ExternalMessagingService externalMessagingService;

    @Autowired
    public LifecycleNotificationController(ExternalMessagingService externalMessagingService) {
        this.externalMessagingService = externalMessagingService;
    }

    @PostMapping
    @Operation(summary  = "Receives a lifecycle operation occurrence notification from a VNFM")
    public ResponseEntity<Void> receiveNotification(@RequestBody LifecycleManagementNotification notification) {
        // TODO This should be reduced to DEBUG level, but it assists in development testing to see all notification messages being received
        logger.info("Received notification:\n{}", notification);

        if (notification instanceof VnfLcmOperationOccurenceNotification) {
            final VnfLcmOperationOccurenceNotification vnfLcmOpOccNotification = (VnfLcmOperationOccurenceNotification) notification;
            // Send an update if this is completed
            if (vnfLcmOpOccNotification.getNotificationStatus() == VnfLcmOperationOccurenceNotification.NotificationStatus.RESULT){
                ExecutionAsyncResponse asyncResponse = new ExecutionAsyncResponse(vnfLcmOpOccNotification.getVnfLcmOpOccId(), ExecutionStatus.COMPLETE, null, Collections.emptyMap(), Collections.emptyMap());
                // If the operation state is anything other than COMPLETED, than assume we've failed (could be FAILED, FAILED_TEMP or ROLLED_BACK)
                if (vnfLcmOpOccNotification.getOperationState() != LcmOperationStateType.COMPLETED) {
                    asyncResponse.setStatus(ExecutionStatus.FAILED);
                    // Set the failure details if we have an error message
                    if (vnfLcmOpOccNotification.getError() != null && !StringUtils.isEmpty(vnfLcmOpOccNotification.getError().getDetail())) {
                        asyncResponse.setFailureDetails(new FailureDetails(FailureDetails.FailureCode.INTERNAL_ERROR, vnfLcmOpOccNotification.getError().getDetail()));
                    }
                }
                externalMessagingService.sendExecutionAsyncResponse(asyncResponse);
            }
        }

        return ResponseEntity.noContent().build();
    }

}
