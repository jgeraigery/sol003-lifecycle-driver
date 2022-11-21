package com.accantosystems.stratoss.vnfmdriver.web.alm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.accantosystems.stratoss.vnfmdriver.model.alm.FindReferenceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionAcceptedResponse;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionRequest;
import com.accantosystems.stratoss.vnfmdriver.service.LifecycleManagementService;
import com.accantosystems.stratoss.vnfmdriver.service.MessageConversionException;

import io.swagger.v3.oas.annotations.Operation;



@RestController("LifecycleController")
@RequestMapping("/api/driver")
public class LifecycleController {

    private final static Logger logger = LoggerFactory.getLogger(LifecycleController.class);

    private final LifecycleManagementService lifecycleManagementService;

    @Autowired
    public LifecycleController(final LifecycleManagementService lifecycleManagementService) {
        this.lifecycleManagementService = lifecycleManagementService;
    }

    @PostMapping("/lifecycle/execute")
    @Operation( summary = "Execute a lifecycle against a VNFM", description = "Initiates a lifecycle against a VNF, managed by a VNFM")
    public ResponseEntity<ExecutionAcceptedResponse> executeLifecycle(@RequestBody ExecutionRequest executionRequest, HttpServletRequest servletRequest) throws MessageConversionException {
        try (BufferedReader messageReader = servletRequest.getReader()) {
            String rawMessage = messageReader.lines().collect(Collectors.joining("\n"));
            logger.info("Received ExecutionRequest:\n{}", rawMessage);
        } catch (IOException e) {
            logger.warn(String.format("Exception caught logging ExecutionRequest message: %s", e.getMessage()), e);
        }
        logger.info("Received request to execute a lifecycle [{}] at deployment location [{}]", executionRequest.getLifecycleName(), executionRequest.getDeploymentLocation().getName());
        final ExecutionAcceptedResponse executionAcceptedResponse = lifecycleManagementService.executeLifecycle(executionRequest);
        return ResponseEntity.accepted().body(executionAcceptedResponse);
    }

    @PostMapping("/references/find")
    @Operation(summary = "Execute a lifecycle against a VNFM", description = "Initiates a lifecycle against a VNF, managed by a VNFM")
    public ResponseEntity<ExecutionAcceptedResponse> findReference(@RequestBody FindReferenceRequest findReferenceRequest, HttpServletRequest servletRequest) throws MessageConversionException, NotImplementedException {
        throw new NotImplementedException("Find References API is not implemented");
    }

}
