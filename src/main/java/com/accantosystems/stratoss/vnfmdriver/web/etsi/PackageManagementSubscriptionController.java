package com.accantosystems.stratoss.vnfmdriver.web.etsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.etsi.sol003.packagemanagement.PkgmSubscription;
import org.etsi.sol003.packagemanagement.PkgmSubscriptionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;

@RestController("PackageManagementSubscriptionController")
@RequestMapping(PackageManagementSubscriptionController.API_PATH)
public class PackageManagementSubscriptionController {

    public static final String API_PATH = "/vnfpkgm/v1/subscriptions";

    private static final Logger logger = LoggerFactory.getLogger(PackageManagementSubscriptionController.class);

    private final Map<String, PkgmSubscription> localSubscriptionCache = new ConcurrentHashMap<>();

    @PostMapping()
    @Operation(summary = "Create New Subscription", description = "Creates a new subscription for packages")
    public ResponseEntity<PkgmSubscription> createNewSubscription(PkgmSubscriptionRequest subscriptionRequest, HttpServletRequest servletRequest) {
        final String newSubscriptionId = UUID.randomUUID().toString();
        try (BufferedReader messageReader = servletRequest.getReader()) {
            String rawMessage = messageReader.lines().collect(Collectors.joining("\n"));
            logger.info("Received request to create a new package management subscription with id [{}]:\n{}", newSubscriptionId, rawMessage);
        } catch (IOException e) {
            logger.warn(String.format("Exception caught logging PkgmSubscriptionRequest message: %s", e.getMessage()), e);
        }

        PkgmSubscription newSubscription = new PkgmSubscription();
        newSubscription.setId(newSubscriptionId);
        newSubscription.setFilter(subscriptionRequest.getFilter());
        newSubscription.setCallbackUri(subscriptionRequest.getCallbackUri());
        localSubscriptionCache.put(newSubscriptionId, newSubscription);

        final ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        URI location = uriBuilder.pathSegment("{id}").buildAndExpand(newSubscriptionId).toUri();
        return ResponseEntity.created(location).body(newSubscription);
    }

    @GetMapping(path = "/{subscriptionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Reads the information of an individual Subscription", description = "This resource represents an individual Subscription. The client can use this resource to read information of the Subscription.")
    public ResponseEntity<PkgmSubscription> getSubscription(@PathVariable String subscriptionId) {
        logger.info("Received request to retrieve Package Management Subscription [{}]", subscriptionId);
        return localSubscriptionCache.containsKey(subscriptionId) ? ResponseEntity.ok(localSubscriptionCache.get(subscriptionId)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{subscriptionId}")
    @Operation(summary = "Removes an individual Subscription", description = "The client can use this delete an individual Subscription.")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String subscriptionId) {
        logger.info("Received request to remove Package Management Subscription [{}]", subscriptionId);
        return localSubscriptionCache.remove(subscriptionId) != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
