package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Response model for tax filing operations
 */
public class TaxFilingResponse {
    private UUID filingId;
    private String filingType;
    private String jurisdiction;
    private Instant periodStart;
    private Instant periodEnd;
    private String status; // DRAFT, SUBMITTED, ACCEPTED, REJECTED
    private String confirmationNumber;
    private Instant submittedAt;
    private Instant dueDate;
    private Map<String, BigDecimal> taxAmounts;
    private String rejectionReason;
    private Instant createdAt;

    // Getters and setters
    public UUID getFilingId() {
      return filingId;
    }

    public void setFilingId(UUID filingId) {
      this.filingId = filingId;
    }

    public String getFilingType() {
      return filingType;
    }

    public void setFilingType(String filingType) {
      this.filingType = filingType;
    }

    public String getJurisdiction() {
      return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
      this.jurisdiction = jurisdiction;
    }

    public Instant getPeriodStart() {
      return periodStart;
    }

    public void setPeriodStart(Instant periodStart) {
      this.periodStart = periodStart;
    }

    public Instant getPeriodEnd() {
      return periodEnd;
    }

    public void setPeriodEnd(Instant periodEnd) {
      this.periodEnd = periodEnd;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getConfirmationNumber() {
      return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
      this.confirmationNumber = confirmationNumber;
    }

    public Instant getSubmittedAt() {
      return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
      this.submittedAt = submittedAt;
    }

    public Instant getDueDate() {
      return dueDate;
    }

    public void setDueDate(Instant dueDate) {
      this.dueDate = dueDate;
    }

    public Map<String, BigDecimal> getTaxAmounts() {
      return taxAmounts;
    }

    public void setTaxAmounts(Map<String, BigDecimal> taxAmounts) {
      this.taxAmounts = taxAmounts;
    }

    public String getRejectionReason() {
      return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
      this.rejectionReason = rejectionReason;
    }

    public Instant getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
      this.createdAt = createdAt;
    }
} 
