package com.winnguyen1905.payment.core.feign;

import com.winnguyen1905.payment.core.model.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.math.BigDecimal;

@FeignClient(name = "audit-service", url = "${services.audit-service.url:http://audit-service:8080}")
public interface AuditServiceClient {

    // Audit log operations
    @PostMapping("/audit-logs")
    RestResponse<AuditLogResponse> createAuditLog(@RequestBody @Valid AuditLogRequest request);

    @PostMapping("/audit-logs/batch")
    RestResponse<List<AuditLogResponse>> createAuditLogsBatch(@RequestBody @Valid List<AuditLogRequest> requests);

    @GetMapping("/audit-logs")
    RestResponse<List<AuditLogResponse>> getAuditLogs(
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) UUID entityId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int size);

    @GetMapping("/audit-logs/{auditId}")
    RestResponse<AuditLogResponse> getAuditLog(@PathVariable UUID auditId);

    @GetMapping("/audit-logs/entity/{entityType}/{entityId}")
    RestResponse<List<AuditLogResponse>> getAuditLogsByEntity(
            @PathVariable String entityType,
            @PathVariable UUID entityId,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate);

    // Security events operations
    @PostMapping("/security-events")
    RestResponse<SecurityEventResponse> recordSecurityEvent(@RequestBody @Valid SecurityEventRequest request);

    @GetMapping("/security-events")
    RestResponse<List<SecurityEventResponse>> getSecurityEvents(
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String sourceIp);

    @GetMapping("/security-events/summary")
    RestResponse<SecurityEventSummaryResponse> getSecurityEventSummary(
            @RequestParam Instant startDate,
            @RequestParam Instant endDate);

    // Compliance operations
    @PostMapping("/compliance/reports")
    RestResponse<ComplianceReportResponse> generateComplianceReport(@RequestBody @Valid ComplianceReportRequest request);

    @GetMapping("/compliance/reports/{reportId}")
    RestResponse<ComplianceReportResponse> getComplianceReport(@PathVariable UUID reportId);

    @GetMapping("/compliance/requirements")
    RestResponse<List<ComplianceRequirementResponse>> getComplianceRequirements(
            @RequestParam(required = false) String standard, // PCI_DSS, SOX, GDPR, etc.
            @RequestParam(required = false) Boolean active);

    @PostMapping("/compliance/assessments")
    RestResponse<ComplianceAssessmentResponse> performComplianceAssessment(@RequestBody @Valid ComplianceAssessmentRequest request);

    // Data retention operations
    @PostMapping("/retention/policies")
    RestResponse<DataRetentionPolicyResponse> createRetentionPolicy(@RequestBody @Valid DataRetentionPolicyRequest request);

    @GetMapping("/retention/policies")
    RestResponse<List<DataRetentionPolicyResponse>> getRetentionPolicies(
            @RequestParam(required = false) String dataType,
            @RequestParam(required = false) Boolean active);

    @PostMapping("/retention/execute")
    RestResponse<RetentionExecutionResponse> executeDataRetention(@RequestBody @Valid RetentionExecutionRequest request);

    @GetMapping("/retention/executions")
    RestResponse<List<RetentionExecutionResponse>> getRetentionExecutions(
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate);

    // Change tracking operations
    @PostMapping("/changes")
    RestResponse<ChangeTrackingResponse> recordChange(@RequestBody @Valid ChangeTrackingRequest request);

    @GetMapping("/changes")
    RestResponse<List<ChangeTrackingResponse>> getChanges(
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) UUID entityId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate);

    @GetMapping("/changes/{changeId}")
    RestResponse<ChangeTrackingResponse> getChange(@PathVariable UUID changeId);

    // Access control audit operations
    @PostMapping("/access-audit")
    RestResponse<AccessAuditResponse> recordAccessAttempt(@RequestBody @Valid AccessAuditRequest request);

    @GetMapping("/access-audit")
    RestResponse<List<AccessAuditResponse>> getAccessAudits(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String resource,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Boolean successful,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate);

    @GetMapping("/access-audit/suspicious")
    RestResponse<List<SuspiciousActivityResponse>> getSuspiciousActivities(
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate,
            @RequestParam(required = false) String activityType);

    // DTOs
    class AuditLogRequest {
        @NotNull
        private String entityType; // PAYMENT, USER, TRANSACTION, etc.
        
        @NotNull
        private UUID entityId;
        
        @NotNull
        private String action; // CREATE, UPDATE, DELETE, VIEW, etc.
        
        @NotNull
        private UUID userId;
        
        private String userRole;
        private String sourceIp;
        private String userAgent;
        private String sessionId;
        
        @NotNull
        private String severity; // INFO, WARN, ERROR, CRITICAL
        
        private String description;
        private Map<String, Object> beforeState;
        private Map<String, Object> afterState;
        private Map<String, Object> metadata;
        
        @NotNull
        private Instant timestamp;

        // Getters and setters
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        
        public UUID getEntityId() { return entityId; }
        public void setEntityId(UUID entityId) { this.entityId = entityId; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getUserRole() { return userRole; }
        public void setUserRole(String userRole) { this.userRole = userRole; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Map<String, Object> getBeforeState() { return beforeState; }
        public void setBeforeState(Map<String, Object> beforeState) { this.beforeState = beforeState; }
        
        public Map<String, Object> getAfterState() { return afterState; }
        public void setAfterState(Map<String, Object> afterState) { this.afterState = afterState; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    }

    class SecurityEventRequest {
        @NotNull
        private String eventType; // LOGIN_ATTEMPT, PERMISSION_DENIED, SUSPICIOUS_ACTIVITY, etc.
        
        @NotNull
        private String severity; // LOW, MEDIUM, HIGH, CRITICAL
        
        private UUID userId;
        private String username;
        
        @NotNull
        private String sourceIp;
        
        private String userAgent;
        private String location; // Geographic location if available
        private String description;
        private Map<String, Object> eventData;
        
        @NotNull
        private Instant timestamp;

        // Getters and setters
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Map<String, Object> getEventData() { return eventData; }
        public void setEventData(Map<String, Object> eventData) { this.eventData = eventData; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    }

    class ComplianceReportRequest {
        @NotNull
        private String standard; // PCI_DSS, SOX, GDPR, etc.
        
        @NotNull
        private Instant startDate;
        
        @NotNull
        private Instant endDate;
        
        private List<String> includedRequirements;
        private String format; // PDF, CSV, XML
        private Map<String, Object> parameters;

        // Getters and setters
        public String getStandard() { return standard; }
        public void setStandard(String standard) { this.standard = standard; }
        
        public Instant getStartDate() { return startDate; }
        public void setStartDate(Instant startDate) { this.startDate = startDate; }
        
        public Instant getEndDate() { return endDate; }
        public void setEndDate(Instant endDate) { this.endDate = endDate; }
        
        public List<String> getIncludedRequirements() { return includedRequirements; }
        public void setIncludedRequirements(List<String> includedRequirements) { this.includedRequirements = includedRequirements; }
        
        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
        
        public Map<String, Object> getParameters() { return parameters; }
        public void setParameters(Map<String, Object> parameters) { this.parameters = parameters; }
    }

    // Response DTOs
    class AuditLogResponse {
        private UUID auditId;
        private String entityType;
        private UUID entityId;
        private String action;
        private UUID userId;
        private String userRole;
        private String sourceIp;
        private String userAgent;
        private String sessionId;
        private String severity;
        private String description;
        private Map<String, Object> beforeState;
        private Map<String, Object> afterState;
        private Map<String, Object> metadata;
        private Instant timestamp;
        private Instant createdAt;

        // Getters and setters
        public UUID getAuditId() { return auditId; }
        public void setAuditId(UUID auditId) { this.auditId = auditId; }
        
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        
        public UUID getEntityId() { return entityId; }
        public void setEntityId(UUID entityId) { this.entityId = entityId; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getUserRole() { return userRole; }
        public void setUserRole(String userRole) { this.userRole = userRole; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Map<String, Object> getBeforeState() { return beforeState; }
        public void setBeforeState(Map<String, Object> beforeState) { this.beforeState = beforeState; }
        
        public Map<String, Object> getAfterState() { return afterState; }
        public void setAfterState(Map<String, Object> afterState) { this.afterState = afterState; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
        
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    }

    // Additional Request DTOs
    class ComplianceAssessmentRequest {
        @NotNull
        private String standard;
        
        private List<String> requirements;
        private Instant assessmentDate;
        private Map<String, Object> parameters;

        // Getters and setters
        public String getStandard() { return standard; }
        public void setStandard(String standard) { this.standard = standard; }
        
        public List<String> getRequirements() { return requirements; }
        public void setRequirements(List<String> requirements) { this.requirements = requirements; }
        
        public Instant getAssessmentDate() { return assessmentDate; }
        public void setAssessmentDate(Instant assessmentDate) { this.assessmentDate = assessmentDate; }
        
        public Map<String, Object> getParameters() { return parameters; }
        public void setParameters(Map<String, Object> parameters) { this.parameters = parameters; }
    }

    class DataRetentionPolicyRequest {
        @NotNull
        private String dataType;
        
        @NotNull
        private Integer retentionDays;
        
        @NotNull
        private String action; // DELETE, ARCHIVE, ANONYMIZE
        
        private String description;
        private Boolean active = true;
        private Map<String, Object> criteria;

        // Getters and setters
        public String getDataType() { return dataType; }
        public void setDataType(String dataType) { this.dataType = dataType; }
        
        public Integer getRetentionDays() { return retentionDays; }
        public void setRetentionDays(Integer retentionDays) { this.retentionDays = retentionDays; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        
        public Map<String, Object> getCriteria() { return criteria; }
        public void setCriteria(Map<String, Object> criteria) { this.criteria = criteria; }
    }

    class RetentionExecutionRequest {
        @NotNull
        private UUID policyId;
        
        private Instant cutoffDate;
        private Boolean dryRun = false;
        private Map<String, Object> filters;

        // Getters and setters
        public UUID getPolicyId() { return policyId; }
        public void setPolicyId(UUID policyId) { this.policyId = policyId; }
        
        public Instant getCutoffDate() { return cutoffDate; }
        public void setCutoffDate(Instant cutoffDate) { this.cutoffDate = cutoffDate; }
        
        public Boolean getDryRun() { return dryRun; }
        public void setDryRun(Boolean dryRun) { this.dryRun = dryRun; }
        
        public Map<String, Object> getFilters() { return filters; }
        public void setFilters(Map<String, Object> filters) { this.filters = filters; }
    }

    class ChangeTrackingRequest {
        @NotNull
        private String entityType;
        
        @NotNull
        private UUID entityId;
        
        @NotNull
        private String changeType; // CREATE, UPDATE, DELETE
        
        @NotNull
        private UUID userId;
        
        private String fieldName;
        private Object oldValue;
        private Object newValue;
        private String reason;
        private Map<String, Object> metadata;
        
        @NotNull
        private Instant timestamp;

        // Getters and setters
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        
        public UUID getEntityId() { return entityId; }
        public void setEntityId(UUID entityId) { this.entityId = entityId; }
        
        public String getChangeType() { return changeType; }
        public void setChangeType(String changeType) { this.changeType = changeType; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getFieldName() { return fieldName; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
        
        public Object getOldValue() { return oldValue; }
        public void setOldValue(Object oldValue) { this.oldValue = oldValue; }
        
        public Object getNewValue() { return newValue; }
        public void setNewValue(Object newValue) { this.newValue = newValue; }
        
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    }

    class AccessAuditRequest {
        @NotNull
        private UUID userId;
        
        @NotNull
        private String resource;
        
        @NotNull
        private String action;
        
        @NotNull
        private Boolean successful;
        
        private String sourceIp;
        private String userAgent;
        private String sessionId;
        private String failureReason;
        private Map<String, Object> context;
        
        @NotNull
        private Instant timestamp;

        // Getters and setters
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getResource() { return resource; }
        public void setResource(String resource) { this.resource = resource; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public Boolean getSuccessful() { return successful; }
        public void setSuccessful(Boolean successful) { this.successful = successful; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getFailureReason() { return failureReason; }
        public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
        
        public Map<String, Object> getContext() { return context; }
        public void setContext(Map<String, Object> context) { this.context = context; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    }

    // Additional Response DTOs
    class SecurityEventResponse {
        private UUID eventId;
        private String eventType;
        private String severity;
        private UUID userId;
        private String username;
        private String sourceIp;
        private String userAgent;
        private String location;
        private String description;
        private Map<String, Object> eventData;
        private Instant timestamp;
        private Instant createdAt;
        private Boolean resolved;

        // Getters and setters
        public UUID getEventId() { return eventId; }
        public void setEventId(UUID eventId) { this.eventId = eventId; }
        
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Map<String, Object> getEventData() { return eventData; }
        public void setEventData(Map<String, Object> eventData) { this.eventData = eventData; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
        
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
        
        public Boolean getResolved() { return resolved; }
        public void setResolved(Boolean resolved) { this.resolved = resolved; }
    }

    class SecurityEventSummaryResponse {
        private Long totalEvents;
        private Map<String, Long> eventsByType;
        private Map<String, Long> eventsBySeverity;
        private Long criticalEvents;
        private Long resolvedEvents;
        private List<String> topSourceIps;
        private Instant calculatedAt;

        // Getters and setters
        public Long getTotalEvents() { return totalEvents; }
        public void setTotalEvents(Long totalEvents) { this.totalEvents = totalEvents; }
        
        public Map<String, Long> getEventsByType() { return eventsByType; }
        public void setEventsByType(Map<String, Long> eventsByType) { this.eventsByType = eventsByType; }
        
        public Map<String, Long> getEventsBySeverity() { return eventsBySeverity; }
        public void setEventsBySeverity(Map<String, Long> eventsBySeverity) { this.eventsBySeverity = eventsBySeverity; }
        
        public Long getCriticalEvents() { return criticalEvents; }
        public void setCriticalEvents(Long criticalEvents) { this.criticalEvents = criticalEvents; }
        
        public Long getResolvedEvents() { return resolvedEvents; }
        public void setResolvedEvents(Long resolvedEvents) { this.resolvedEvents = resolvedEvents; }
        
        public List<String> getTopSourceIps() { return topSourceIps; }
        public void setTopSourceIps(List<String> topSourceIps) { this.topSourceIps = topSourceIps; }
        
        public Instant getCalculatedAt() { return calculatedAt; }
        public void setCalculatedAt(Instant calculatedAt) { this.calculatedAt = calculatedAt; }
    }

    class ComplianceReportResponse {
        private UUID reportId;
        private String standard;
        private String status; // GENERATING, COMPLETED, FAILED
        private Instant startDate;
        private Instant endDate;
        private String format;
        private String downloadUrl;
        private List<ComplianceResult> results;
        private Integer totalRequirements;
        private Integer passedRequirements;
        private Integer failedRequirements;
        private BigDecimal complianceScore;
        private Instant generatedAt;

        // Getters and setters (simplified for brevity)
        public UUID getReportId() { return reportId; }
        public void setReportId(UUID reportId) { this.reportId = reportId; }
        
        public String getStandard() { return standard; }
        public void setStandard(String standard) { this.standard = standard; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Instant getStartDate() { return startDate; }
        public void setStartDate(Instant startDate) { this.startDate = startDate; }
        
        public Instant getEndDate() { return endDate; }
        public void setEndDate(Instant endDate) { this.endDate = endDate; }
        
        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
        
        public String getDownloadUrl() { return downloadUrl; }
        public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
        
        public List<ComplianceResult> getResults() { return results; }
        public void setResults(List<ComplianceResult> results) { this.results = results; }
        
        public Integer getTotalRequirements() { return totalRequirements; }
        public void setTotalRequirements(Integer totalRequirements) { this.totalRequirements = totalRequirements; }
        
        public Integer getPassedRequirements() { return passedRequirements; }
        public void setPassedRequirements(Integer passedRequirements) { this.passedRequirements = passedRequirements; }
        
        public Integer getFailedRequirements() { return failedRequirements; }
        public void setFailedRequirements(Integer failedRequirements) { this.failedRequirements = failedRequirements; }
        
        public java.math.BigDecimal getComplianceScore() { return complianceScore; }
        public void setComplianceScore(java.math.BigDecimal complianceScore) { this.complianceScore = complianceScore; }
        
        public Instant getGeneratedAt() { return generatedAt; }
        public void setGeneratedAt(Instant generatedAt) { this.generatedAt = generatedAt; }
    }

    class ComplianceResult {
        private String requirementId;
        private String description;
        private String status; // PASS, FAIL, NOT_APPLICABLE
        private String evidence;
        private List<String> findings;

        // Getters and setters
        public String getRequirementId() { return requirementId; }
        public void setRequirementId(String requirementId) { this.requirementId = requirementId; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getEvidence() { return evidence; }
        public void setEvidence(String evidence) { this.evidence = evidence; }
        
        public List<String> getFindings() { return findings; }
        public void setFindings(List<String> findings) { this.findings = findings; }
    }

    class ComplianceRequirementResponse {
        private String requirementId;
        private String standard;
        private String title;
        private String description;
        private String category;
        private String severity;
        private Boolean active;
        private List<String> controls;
        private Map<String, Object> metadata;

        // Getters and setters
        public String getRequirementId() { return requirementId; }
        public void setRequirementId(String requirementId) { this.requirementId = requirementId; }
        
        public String getStandard() { return standard; }
        public void setStandard(String standard) { this.standard = standard; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        
        public List<String> getControls() { return controls; }
        public void setControls(List<String> controls) { this.controls = controls; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }

    class ComplianceAssessmentResponse {
        private UUID assessmentId;
        private String standard;
        private String status; // IN_PROGRESS, COMPLETED, FAILED
        private Instant assessmentDate;
        private List<ComplianceResult> results;
        private BigDecimal overallScore;
        private Integer totalChecks;
        private Integer passedChecks;
        private Integer failedChecks;
        private List<String> recommendations;
        private Instant completedAt;

        // Getters and setters
        public UUID getAssessmentId() { return assessmentId; }
        public void setAssessmentId(UUID assessmentId) { this.assessmentId = assessmentId; }
        
        public String getStandard() { return standard; }
        public void setStandard(String standard) { this.standard = standard; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Instant getAssessmentDate() { return assessmentDate; }
        public void setAssessmentDate(Instant assessmentDate) { this.assessmentDate = assessmentDate; }
        
        public List<ComplianceResult> getResults() { return results; }
        public void setResults(List<ComplianceResult> results) { this.results = results; }
        
        public BigDecimal getOverallScore() { return overallScore; }
        public void setOverallScore(BigDecimal overallScore) { this.overallScore = overallScore; }
        
        public Integer getTotalChecks() { return totalChecks; }
        public void setTotalChecks(Integer totalChecks) { this.totalChecks = totalChecks; }
        
        public Integer getPassedChecks() { return passedChecks; }
        public void setPassedChecks(Integer passedChecks) { this.passedChecks = passedChecks; }
        
        public Integer getFailedChecks() { return failedChecks; }
        public void setFailedChecks(Integer failedChecks) { this.failedChecks = failedChecks; }
        
        public List<String> getRecommendations() { return recommendations; }
        public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
        
        public Instant getCompletedAt() { return completedAt; }
        public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
    }

    class DataRetentionPolicyResponse {
        private UUID policyId;
        private String dataType;
        private Integer retentionDays;
        private String action;
        private String description;
        private Boolean active;
        private Map<String, Object> criteria;
        private Instant createdAt;
        private Instant updatedAt;
        private UUID createdBy;

        // Getters and setters
        public UUID getPolicyId() { return policyId; }
        public void setPolicyId(UUID policyId) { this.policyId = policyId; }
        
        public String getDataType() { return dataType; }
        public void setDataType(String dataType) { this.dataType = dataType; }
        
        public Integer getRetentionDays() { return retentionDays; }
        public void setRetentionDays(Integer retentionDays) { this.retentionDays = retentionDays; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        
        public Map<String, Object> getCriteria() { return criteria; }
        public void setCriteria(Map<String, Object> criteria) { this.criteria = criteria; }
        
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
        
        public Instant getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
        
        public UUID getCreatedBy() { return createdBy; }
        public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    }

    class RetentionExecutionResponse {
        private UUID executionId;
        private UUID policyId;
        private String status; // RUNNING, COMPLETED, FAILED
        private Instant startedAt;
        private Instant completedAt;
        private Long recordsProcessed;
        private Long recordsDeleted;
        private Long recordsArchived;
        private Long recordsAnonymized;
        private Boolean dryRun;
        private String errorMessage;
        private Map<String, Object> summary;

        // Getters and setters
        public UUID getExecutionId() { return executionId; }
        public void setExecutionId(UUID executionId) { this.executionId = executionId; }
        
        public UUID getPolicyId() { return policyId; }
        public void setPolicyId(UUID policyId) { this.policyId = policyId; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Instant getStartedAt() { return startedAt; }
        public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
        
        public Instant getCompletedAt() { return completedAt; }
        public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
        
        public Long getRecordsProcessed() { return recordsProcessed; }
        public void setRecordsProcessed(Long recordsProcessed) { this.recordsProcessed = recordsProcessed; }
        
        public Long getRecordsDeleted() { return recordsDeleted; }
        public void setRecordsDeleted(Long recordsDeleted) { this.recordsDeleted = recordsDeleted; }
        
        public Long getRecordsArchived() { return recordsArchived; }
        public void setRecordsArchived(Long recordsArchived) { this.recordsArchived = recordsArchived; }
        
        public Long getRecordsAnonymized() { return recordsAnonymized; }
        public void setRecordsAnonymized(Long recordsAnonymized) { this.recordsAnonymized = recordsAnonymized; }
        
        public Boolean getDryRun() { return dryRun; }
        public void setDryRun(Boolean dryRun) { this.dryRun = dryRun; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public Map<String, Object> getSummary() { return summary; }
        public void setSummary(Map<String, Object> summary) { this.summary = summary; }
    }

    class ChangeTrackingResponse {
        private UUID changeId;
        private String entityType;
        private UUID entityId;
        private String changeType;
        private UUID userId;
        private String fieldName;
        private Object oldValue;
        private Object newValue;
        private String reason;
        private Map<String, Object> metadata;
        private Instant timestamp;
        private Instant createdAt;

        // Getters and setters
        public UUID getChangeId() { return changeId; }
        public void setChangeId(UUID changeId) { this.changeId = changeId; }
        
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        
        public UUID getEntityId() { return entityId; }
        public void setEntityId(UUID entityId) { this.entityId = entityId; }
        
        public String getChangeType() { return changeType; }
        public void setChangeType(String changeType) { this.changeType = changeType; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getFieldName() { return fieldName; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
        
        public Object getOldValue() { return oldValue; }
        public void setOldValue(Object oldValue) { this.oldValue = oldValue; }
        
        public Object getNewValue() { return newValue; }
        public void setNewValue(Object newValue) { this.newValue = newValue; }
        
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
        
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    }

    class AccessAuditResponse {
        private UUID auditId;
        private UUID userId;
        private String resource;
        private String action;
        private Boolean successful;
        private String sourceIp;
        private String userAgent;
        private String sessionId;
        private String failureReason;
        private Map<String, Object> context;
        private Instant timestamp;
        private Instant createdAt;

        // Getters and setters
        public UUID getAuditId() { return auditId; }
        public void setAuditId(UUID auditId) { this.auditId = auditId; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getResource() { return resource; }
        public void setResource(String resource) { this.resource = resource; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public Boolean getSuccessful() { return successful; }
        public void setSuccessful(Boolean successful) { this.successful = successful; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getFailureReason() { return failureReason; }
        public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
        
        public Map<String, Object> getContext() { return context; }
        public void setContext(Map<String, Object> context) { this.context = context; }
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
        
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    }

    class SuspiciousActivityResponse {
        private UUID activityId;
        private String activityType;
        private String description;
        private String severity;
        private UUID userId;
        private String sourceIp;
        private Map<String, Object> activityData;
        private String status; // NEW, INVESTIGATING, RESOLVED, FALSE_POSITIVE
        private Instant detectedAt;
        private Instant resolvedAt;
        private String resolutionNotes;

        // Getters and setters
        public UUID getActivityId() { return activityId; }
        public void setActivityId(UUID activityId) { this.activityId = activityId; }
        
        public String getActivityType() { return activityType; }
        public void setActivityType(String activityType) { this.activityType = activityType; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        
        public String getSourceIp() { return sourceIp; }
        public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
        
        public Map<String, Object> getActivityData() { return activityData; }
        public void setActivityData(Map<String, Object> activityData) { this.activityData = activityData; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Instant getDetectedAt() { return detectedAt; }
        public void setDetectedAt(Instant detectedAt) { this.detectedAt = detectedAt; }
        
        public Instant getResolvedAt() { return resolvedAt; }
        public void setResolvedAt(Instant resolvedAt) { this.resolvedAt = resolvedAt; }
        
        public String getResolutionNotes() { return resolutionNotes; }
        public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }
    }
} 
