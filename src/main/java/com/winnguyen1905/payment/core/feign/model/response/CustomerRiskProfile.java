package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for customer risk profile information
 */
public class CustomerRiskProfile {
    private String riskLevel;
    private BigDecimal riskScore;
    private Integer fraudIncidents;
    private Integer chargebacks;
    private LocalDateTime lastRiskAssessment;
    private Boolean requiresManualReview;
    
    // Getters and setters
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    
    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }
    
    public Integer getFraudIncidents() { return fraudIncidents; }
    public void setFraudIncidents(Integer fraudIncidents) { this.fraudIncidents = fraudIncidents; }
    
    public Integer getChargebacks() { return chargebacks; }
    public void setChargebacks(Integer chargebacks) { this.chargebacks = chargebacks; }
    
    public LocalDateTime getLastRiskAssessment() { return lastRiskAssessment; }
    public void setLastRiskAssessment(LocalDateTime lastRiskAssessment) { this.lastRiskAssessment = lastRiskAssessment; }
    
    public Boolean getRequiresManualReview() { return requiresManualReview; }
    public void setRequiresManualReview(Boolean requiresManualReview) { this.requiresManualReview = requiresManualReview; }
} 
