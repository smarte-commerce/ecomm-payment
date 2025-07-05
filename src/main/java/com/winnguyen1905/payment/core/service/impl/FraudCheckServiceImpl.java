package com.winnguyen1905.payment.core.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.payment.core.model.request.FraudCheckRequest;
import com.winnguyen1905.payment.core.model.response.FraudCheckResponse;
import com.winnguyen1905.payment.core.service.FraudCheckService;
import com.winnguyen1905.payment.exception.ResourceNotFoundException;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck;
import com.winnguyen1905.payment.persistance.entity.EPayment;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckStatus;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckType;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.RiskLevel;
import com.winnguyen1905.payment.persistance.repository.FraudCheckRepository;
import com.winnguyen1905.payment.persistance.repository.PaymentRepository;

/**
 * Implementation of FraudCheckService for managing fraud checks and risk assessment.
 */
@Service
@Transactional
public class FraudCheckServiceImpl implements FraudCheckService {

    private final FraudCheckRepository fraudCheckRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public FraudCheckServiceImpl(FraudCheckRepository fraudCheckRepository, PaymentRepository paymentRepository) {
        this.fraudCheckRepository = fraudCheckRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public FraudCheckResponse createFraudCheck(FraudCheckRequest request) {
        EPayment payment = null;
        if (request.getPaymentId() != null) {
            payment = paymentRepository.findById(request.getPaymentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + request.getPaymentId()));
        }

        EFraudCheck fraudCheck = EFraudCheck.builder()
                .payment(payment)
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .checkType(request.getCheckType())
                .riskScore(request.getRiskScore())
                .riskLevel(request.getRiskLevel())
                .status(CheckStatus.PENDING)
                .rulesTriggered(request.getRulesTriggered())
                .providerResponse(request.getProviderResponse())
                .reviewerId(request.getReviewerId())
                .reviewerNotes(request.getReviewerNotes())
                .decisionReason(request.getDecisionReason())
                .checkedAt(Instant.now())
                .build();

        EFraudCheck savedFraudCheck = fraudCheckRepository.save(fraudCheck);
        return mapToResponse(savedFraudCheck);
    }

    @Override
    @Transactional(readOnly = true)
    public FraudCheckResponse getFraudCheckById(UUID id) {
        EFraudCheck fraudCheck = fraudCheckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud check not found with ID: " + id));
        return mapToResponse(fraudCheck);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getFraudChecksByPaymentId(UUID paymentId) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findByPaymentId(paymentId);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getFraudChecksByOrderId(Long orderId) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findByOrderId(orderId);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getFraudChecksByCustomerId(Long customerId) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findByCustomerId(customerId);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getFraudChecksByStatus(CheckStatus status) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findByStatus(status);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getFraudChecksByRiskLevel(RiskLevel riskLevel) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findByRiskLevel(riskLevel);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getFraudChecksByCheckType(CheckType checkType) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findByCheckType(checkType);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FraudCheckResponse updateFraudCheckStatus(UUID id, CheckStatus status) {
        EFraudCheck fraudCheck = fraudCheckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud check not found with ID: " + id));

        fraudCheck.setStatus(status);
        if (status == CheckStatus.APPROVED || status == CheckStatus.DECLINED) {
            fraudCheck.setReviewedAt(Instant.now());
        }

        EFraudCheck updatedFraudCheck = fraudCheckRepository.save(fraudCheck);
        return mapToResponse(updatedFraudCheck);
    }

    @Override
    public FraudCheckResponse addReviewerDecision(UUID id, Long reviewerId, String reviewerNotes, String decisionReason) {
        EFraudCheck fraudCheck = fraudCheckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud check not found with ID: " + id));

        fraudCheck.setReviewerId(reviewerId);
        fraudCheck.setReviewerNotes(reviewerNotes);
        fraudCheck.setDecisionReason(decisionReason);
        fraudCheck.setReviewedAt(Instant.now());

        EFraudCheck updatedFraudCheck = fraudCheckRepository.save(fraudCheck);
        return mapToResponse(updatedFraudCheck);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudCheckResponse> getPendingFraudChecksByRiskLevels(List<RiskLevel> riskLevels) {
        List<EFraudCheck> fraudChecks = fraudCheckRepository.findPendingByRiskLevels(riskLevels);
        return fraudChecks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRiskScoreByCustomerId(Long customerId) {
        return fraudCheckRepository.averageRiskScoreByCustomerId(customerId);
    }

    private FraudCheckResponse mapToResponse(EFraudCheck fraudCheck) {
        return FraudCheckResponse.builder()
                .id(fraudCheck.getId())
                .paymentId(fraudCheck.getPayment() != null ? fraudCheck.getPayment().getId() : null)
                .orderId(fraudCheck.getOrderId())
                .customerId(fraudCheck.getCustomerId())
                .checkType(fraudCheck.getCheckType())
                .riskScore(fraudCheck.getRiskScore())
                .riskLevel(fraudCheck.getRiskLevel())
                .status(fraudCheck.getStatus())
                .rulesTriggered(fraudCheck.getRulesTriggered())
                .providerResponse(fraudCheck.getProviderResponse())
                .reviewerId(fraudCheck.getReviewerId())
                .reviewerNotes(fraudCheck.getReviewerNotes())
                .decisionReason(fraudCheck.getDecisionReason())
                .checkedAt(fraudCheck.getCheckedAt())
                .reviewedAt(fraudCheck.getReviewedAt())
                .build();
    }
} 
