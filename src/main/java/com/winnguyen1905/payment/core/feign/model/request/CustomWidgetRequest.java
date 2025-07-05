package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

/**
 * Request DTO for creating custom dashboard widgets
 */
public class CustomWidgetRequest {
    @NotNull
    private String widgetType; // CHART, TABLE, METRIC, GAUGE
    
    @NotNull
    private String name;
    
    @NotNull
    private String dataSource;
    
    private Map<String, Object> configuration;
    private Map<String, Object> filters;
    private String refreshInterval; // REAL_TIME, HOURLY, DAILY
    private UUID vendorId;

    // Getters and setters
    public String getWidgetType() { return widgetType; }
    public void setWidgetType(String widgetType) { this.widgetType = widgetType; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    
    public Map<String, Object> getConfiguration() { return configuration; }
    public void setConfiguration(Map<String, Object> configuration) { this.configuration = configuration; }
    
    public Map<String, Object> getFilters() { return filters; }
    public void setFilters(Map<String, Object> filters) { this.filters = filters; }
    
    public String getRefreshInterval() { return refreshInterval; }
    public void setRefreshInterval(String refreshInterval) { this.refreshInterval = refreshInterval; }
    
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
} 
