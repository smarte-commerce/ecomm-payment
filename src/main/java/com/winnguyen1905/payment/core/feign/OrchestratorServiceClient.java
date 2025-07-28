package com.winnguyen1905.payment.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.winnguyen1905.payment.core.model.request.PaymentStatusUpdateRequest;
import com.winnguyen1905.payment.core.model.response.RestResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "ORCHESTRATOR-SERVICE", url = "${microservices.orchestrator-service.url}")
@CircuitBreaker(name = "orchestratorService")
@Retry(name = "orchestratorService")
public interface OrchestratorServiceClient {

    /**
     * Notify orchestrator of payment status update
     * 
     * @param request Payment status update request
     * @return Response from orchestrator
     */
    @PostMapping("/internal/payment-status")
    ResponseEntity<RestResponse<String>> updatePaymentStatus(@RequestBody PaymentStatusUpdateRequest request);
} 
