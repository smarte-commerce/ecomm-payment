package com.winnguyen1905.payment.core.feign;

import com.winnguyen1905.payment.core.model.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "currency-service", url = "${services.currency-service.url:http://currency-service:8080}")
public interface CurrencyServiceClient {

    // Currency conversion operations
    @PostMapping("/convert")
    RestResponse<CurrencyConversionResponse> convertCurrency(@RequestBody @Valid CurrencyConversionRequest request);

    @PostMapping("/convert/batch")
    RestResponse<List<CurrencyConversionResponse>> convertCurrencyBatch(@RequestBody @Valid List<CurrencyConversionRequest> requests);

    @GetMapping("/convert")
    RestResponse<CurrencyConversionResponse> convertCurrencySimple(
            @RequestParam @NotNull BigDecimal amount,
            @RequestParam @NotNull String fromCurrency,
            @RequestParam @NotNull String toCurrency,
            @RequestParam(required = false) Instant rateDate);

    // Exchange rate operations
    @GetMapping("/rates/current")
    RestResponse<List<ExchangeRateResponse>> getCurrentExchangeRates(
            @RequestParam(required = false) String baseCurrency,
            @RequestParam(required = false) List<String> targetCurrencies);

    @GetMapping("/rates/current/{baseCurrency}")
    RestResponse<ExchangeRateResponse> getCurrentExchangeRate(
            @PathVariable String baseCurrency,
            @RequestParam String targetCurrency);

    @GetMapping("/rates/historical")
    RestResponse<List<ExchangeRateResponse>> getHistoricalExchangeRates(
            @RequestParam String baseCurrency,
            @RequestParam String targetCurrency,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate);

    @GetMapping("/rates/historical/{date}")
    RestResponse<List<ExchangeRateResponse>> getExchangeRatesForDate(
            @PathVariable Instant date,
            @RequestParam(required = false) String baseCurrency);

    // Currency information operations
    @GetMapping("/currencies")
    RestResponse<List<CurrencyInfoResponse>> getSupportedCurrencies();

    @GetMapping("/currencies/{currencyCode}")
    RestResponse<CurrencyInfoResponse> getCurrencyInfo(@PathVariable String currencyCode);

    @GetMapping("/currencies/active")
    RestResponse<List<CurrencyInfoResponse>> getActiveCurrencies();

    // Rate management operations (for admin use)
    @PostMapping("/rates")
    RestResponse<ExchangeRateResponse> createExchangeRate(@RequestBody @Valid ExchangeRateRequest request);

    @PutMapping("/rates/{rateId}")
    RestResponse<ExchangeRateResponse> updateExchangeRate(@PathVariable UUID rateId, @RequestBody @Valid ExchangeRateRequest request);

    @DeleteMapping("/rates/{rateId}")
    RestResponse<Void> deleteExchangeRate(@PathVariable UUID rateId);

    // Rate provider operations
    @PostMapping("/providers/sync")
    RestResponse<RateSyncResponse> syncRatesFromProviders(@RequestBody @Valid RateSyncRequest request);

    @GetMapping("/providers")
    RestResponse<List<RateProviderResponse>> getRateProviders();

    @GetMapping("/providers/{providerId}/status")
    RestResponse<RateProviderStatusResponse> getProviderStatus(@PathVariable UUID providerId);

    // Currency volatility and analytics
    @GetMapping("/analytics/volatility")
    RestResponse<CurrencyVolatilityResponse> getCurrencyVolatility(
            @RequestParam String currencyPair,
            @RequestParam(required = false) Integer days);

    @GetMapping("/analytics/trends")
    RestResponse<List<CurrencyTrendResponse>> getCurrencyTrends(
            @RequestParam List<String> currencyPairs,
            @RequestParam(required = false) Integer days);

    // DTOs
    class CurrencyConversionRequest {
        @NotNull
        @DecimalMin("0.01")
        private BigDecimal amount;
        
        @NotNull
        @Size(min = 3, max = 3)
        private String fromCurrency;
        
        @NotNull
        @Size(min = 3, max = 3)
        private String toCurrency;
        
        private Instant rateDate;
        private String rateProvider; // CENTRAL_BANK, COMMERCIAL, REAL_TIME
        private Boolean useCache = true;
        private String conversionId; // For tracking/reference
        private Map<String, Object> metadata;

        // Getters and setters
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getFromCurrency() { return fromCurrency; }
        public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }
        
        public String getToCurrency() { return toCurrency; }
        public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }
        
        public Instant getRateDate() { return rateDate; }
        public void setRateDate(Instant rateDate) { this.rateDate = rateDate; }
        
        public String getRateProvider() { return rateProvider; }
        public void setRateProvider(String rateProvider) { this.rateProvider = rateProvider; }
        
        public Boolean getUseCache() { return useCache; }
        public void setUseCache(Boolean useCache) { this.useCache = useCache; }
        
        public String getConversionId() { return conversionId; }
        public void setConversionId(String conversionId) { this.conversionId = conversionId; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }

    class ExchangeRateRequest {
        @NotNull
        @Size(min = 3, max = 3)
        private String baseCurrency;
        
        @NotNull
        @Size(min = 3, max = 3)
        private String targetCurrency;
        
        @NotNull
        @DecimalMin("0.000001")
        private BigDecimal rate;
        
        @NotNull
        private Instant effectiveDate;
        
        private Instant expiryDate;
        private String provider;
        private String rateType; // BUY, SELL, MID
        private Boolean active = true;

        // Getters and setters
        public String getBaseCurrency() { return baseCurrency; }
        public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
        
        public String getTargetCurrency() { return targetCurrency; }
        public void setTargetCurrency(String targetCurrency) { this.targetCurrency = targetCurrency; }
        
        public BigDecimal getRate() { return rate; }
        public void setRate(BigDecimal rate) { this.rate = rate; }
        
        public Instant getEffectiveDate() { return effectiveDate; }
        public void setEffectiveDate(Instant effectiveDate) { this.effectiveDate = effectiveDate; }
        
        public Instant getExpiryDate() { return expiryDate; }
        public void setExpiryDate(Instant expiryDate) { this.expiryDate = expiryDate; }
        
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        
        public String getRateType() { return rateType; }
        public void setRateType(String rateType) { this.rateType = rateType; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
    }

    class RateSyncRequest {
        private List<String> currencyPairs;
        private List<String> providers;
        private Boolean forceSync = false;
        private Instant syncDate;

        // Getters and setters
        public List<String> getCurrencyPairs() { return currencyPairs; }
        public void setCurrencyPairs(List<String> currencyPairs) { this.currencyPairs = currencyPairs; }
        
        public List<String> getProviders() { return providers; }
        public void setProviders(List<String> providers) { this.providers = providers; }
        
        public Boolean getForceSync() { return forceSync; }
        public void setForceSync(Boolean forceSync) { this.forceSync = forceSync; }
        
        public Instant getSyncDate() { return syncDate; }
        public void setSyncDate(Instant syncDate) { this.syncDate = syncDate; }
    }

    // Response DTOs
    class CurrencyConversionResponse {
        private BigDecimal originalAmount;
        private String fromCurrency;
        private BigDecimal convertedAmount;
        private String toCurrency;
        private BigDecimal exchangeRate;
        private Instant rateDate;
        private String rateProvider;
        private String conversionId;
        private Instant convertedAt;
        private Boolean fromCache;
        private BigDecimal margin; // If applicable
        private Map<String, Object> metadata;

        // Getters and setters
        public BigDecimal getOriginalAmount() { return originalAmount; }
        public void setOriginalAmount(BigDecimal originalAmount) { this.originalAmount = originalAmount; }
        
        public String getFromCurrency() { return fromCurrency; }
        public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }
        
        public BigDecimal getConvertedAmount() { return convertedAmount; }
        public void setConvertedAmount(BigDecimal convertedAmount) { this.convertedAmount = convertedAmount; }
        
        public String getToCurrency() { return toCurrency; }
        public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }
        
        public BigDecimal getExchangeRate() { return exchangeRate; }
        public void setExchangeRate(BigDecimal exchangeRate) { this.exchangeRate = exchangeRate; }
        
        public Instant getRateDate() { return rateDate; }
        public void setRateDate(Instant rateDate) { this.rateDate = rateDate; }
        
        public String getRateProvider() { return rateProvider; }
        public void setRateProvider(String rateProvider) { this.rateProvider = rateProvider; }
        
        public String getConversionId() { return conversionId; }
        public void setConversionId(String conversionId) { this.conversionId = conversionId; }
        
        public Instant getConvertedAt() { return convertedAt; }
        public void setConvertedAt(Instant convertedAt) { this.convertedAt = convertedAt; }
        
        public Boolean getFromCache() { return fromCache; }
        public void setFromCache(Boolean fromCache) { this.fromCache = fromCache; }
        
        public BigDecimal getMargin() { return margin; }
        public void setMargin(BigDecimal margin) { this.margin = margin; }
        
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }

    class ExchangeRateResponse {
        private UUID rateId;
        private String baseCurrency;
        private String targetCurrency;
        private BigDecimal rate;
        private Instant effectiveDate;
        private Instant expiryDate;
        private String provider;
        private String rateType;
        private Boolean active;
        private Instant createdAt;
        private Instant updatedAt;
        private BigDecimal previousRate;
        private BigDecimal changePercentage;

        // Getters and setters
        public UUID getRateId() { return rateId; }
        public void setRateId(UUID rateId) { this.rateId = rateId; }
        
        public String getBaseCurrency() { return baseCurrency; }
        public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
        
        public String getTargetCurrency() { return targetCurrency; }
        public void setTargetCurrency(String targetCurrency) { this.targetCurrency = targetCurrency; }
        
        public BigDecimal getRate() { return rate; }
        public void setRate(BigDecimal rate) { this.rate = rate; }
        
        public Instant getEffectiveDate() { return effectiveDate; }
        public void setEffectiveDate(Instant effectiveDate) { this.effectiveDate = effectiveDate; }
        
        public Instant getExpiryDate() { return expiryDate; }
        public void setExpiryDate(Instant expiryDate) { this.expiryDate = expiryDate; }
        
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        
        public String getRateType() { return rateType; }
        public void setRateType(String rateType) { this.rateType = rateType; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
        
        public Instant getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
        
        public BigDecimal getPreviousRate() { return previousRate; }
        public void setPreviousRate(BigDecimal previousRate) { this.previousRate = previousRate; }
        
        public BigDecimal getChangePercentage() { return changePercentage; }
        public void setChangePercentage(BigDecimal changePercentage) { this.changePercentage = changePercentage; }
    }

    class CurrencyInfoResponse {
        private String currencyCode;
        private String currencyName;
        private String symbol;
        private Integer decimalPlaces;
        private String country;
        private String region;
        private Boolean active;
        private Boolean supported;
        private List<String> supportedOperations; // CONVERSION, TRADING, SETTLEMENT
        private Instant lastUpdated;

        // Getters and setters
        public String getCurrencyCode() { return currencyCode; }
        public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
        
        public String getCurrencyName() { return currencyName; }
        public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }
        
        public String getSymbol() { return symbol; }
        public void setSymbol(String symbol) { this.symbol = symbol; }
        
        public Integer getDecimalPlaces() { return decimalPlaces; }
        public void setDecimalPlaces(Integer decimalPlaces) { this.decimalPlaces = decimalPlaces; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        
        public Boolean getSupported() { return supported; }
        public void setSupported(Boolean supported) { this.supported = supported; }
        
        public List<String> getSupportedOperations() { return supportedOperations; }
        public void setSupportedOperations(List<String> supportedOperations) { this.supportedOperations = supportedOperations; }
        
        public Instant getLastUpdated() { return lastUpdated; }
        public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }
    }

    class RateSyncResponse {
        private Integer totalSynced;
        private Integer successful;
        private Integer failed;
        private List<String> syncedCurrencyPairs;
        private List<String> failedCurrencyPairs;
        private Map<String, String> errors;
        private Instant syncStartedAt;
        private Instant syncCompletedAt;
        private String status; // IN_PROGRESS, COMPLETED, FAILED

        // Getters and setters
        public Integer getTotalSynced() { return totalSynced; }
        public void setTotalSynced(Integer totalSynced) { this.totalSynced = totalSynced; }
        
        public Integer getSuccessful() { return successful; }
        public void setSuccessful(Integer successful) { this.successful = successful; }
        
        public Integer getFailed() { return failed; }
        public void setFailed(Integer failed) { this.failed = failed; }
        
        public List<String> getSyncedCurrencyPairs() { return syncedCurrencyPairs; }
        public void setSyncedCurrencyPairs(List<String> syncedCurrencyPairs) { this.syncedCurrencyPairs = syncedCurrencyPairs; }
        
        public List<String> getFailedCurrencyPairs() { return failedCurrencyPairs; }
        public void setFailedCurrencyPairs(List<String> failedCurrencyPairs) { this.failedCurrencyPairs = failedCurrencyPairs; }
        
        public Map<String, String> getErrors() { return errors; }
        public void setErrors(Map<String, String> errors) { this.errors = errors; }
        
        public Instant getSyncStartedAt() { return syncStartedAt; }
        public void setSyncStartedAt(Instant syncStartedAt) { this.syncStartedAt = syncStartedAt; }
        
        public Instant getSyncCompletedAt() { return syncCompletedAt; }
        public void setSyncCompletedAt(Instant syncCompletedAt) { this.syncCompletedAt = syncCompletedAt; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    class RateProviderResponse {
        private UUID providerId;
        private String providerName;
        private String providerType; // CENTRAL_BANK, COMMERCIAL, API
        private Boolean active;
        private Integer priority;
        private List<String> supportedCurrencies;
        private String apiEndpoint;
        private Instant lastSync;
        private String status; // ONLINE, OFFLINE, ERROR

        // Getters and setters
        public UUID getProviderId() { return providerId; }
        public void setProviderId(UUID providerId) { this.providerId = providerId; }
        
        public String getProviderName() { return providerName; }
        public void setProviderName(String providerName) { this.providerName = providerName; }
        
        public String getProviderType() { return providerType; }
        public void setProviderType(String providerType) { this.providerType = providerType; }
        
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        
        public Integer getPriority() { return priority; }
        public void setPriority(Integer priority) { this.priority = priority; }
        
        public List<String> getSupportedCurrencies() { return supportedCurrencies; }
        public void setSupportedCurrencies(List<String> supportedCurrencies) { this.supportedCurrencies = supportedCurrencies; }
        
        public String getApiEndpoint() { return apiEndpoint; }
        public void setApiEndpoint(String apiEndpoint) { this.apiEndpoint = apiEndpoint; }
        
        public Instant getLastSync() { return lastSync; }
        public void setLastSync(Instant lastSync) { this.lastSync = lastSync; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    class RateProviderStatusResponse {
        private UUID providerId;
        private String status;
        private Boolean available;
        private Integer responseTime;
        private Instant lastChecked;
        private String errorMessage;
        private Map<String, Object> healthMetrics;

        // Getters and setters
        public UUID getProviderId() { return providerId; }
        public void setProviderId(UUID providerId) { this.providerId = providerId; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Boolean getAvailable() { return available; }
        public void setAvailable(Boolean available) { this.available = available; }
        
        public Integer getResponseTime() { return responseTime; }
        public void setResponseTime(Integer responseTime) { this.responseTime = responseTime; }
        
        public Instant getLastChecked() { return lastChecked; }
        public void setLastChecked(Instant lastChecked) { this.lastChecked = lastChecked; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public Map<String, Object> getHealthMetrics() { return healthMetrics; }
        public void setHealthMetrics(Map<String, Object> healthMetrics) { this.healthMetrics = healthMetrics; }
    }

    class CurrencyVolatilityResponse {
        private String currencyPair;
        private BigDecimal volatility;
        private BigDecimal averageRate;
        private BigDecimal highestRate;
        private BigDecimal lowestRate;
        private Integer daysAnalyzed;
        private String volatilityLevel; // LOW, MEDIUM, HIGH, EXTREME
        private Instant calculatedAt;

        // Getters and setters
        public String getCurrencyPair() { return currencyPair; }
        public void setCurrencyPair(String currencyPair) { this.currencyPair = currencyPair; }
        
        public BigDecimal getVolatility() { return volatility; }
        public void setVolatility(BigDecimal volatility) { this.volatility = volatility; }
        
        public BigDecimal getAverageRate() { return averageRate; }
        public void setAverageRate(BigDecimal averageRate) { this.averageRate = averageRate; }
        
        public BigDecimal getHighestRate() { return highestRate; }
        public void setHighestRate(BigDecimal highestRate) { this.highestRate = highestRate; }
        
        public BigDecimal getLowestRate() { return lowestRate; }
        public void setLowestRate(BigDecimal lowestRate) { this.lowestRate = lowestRate; }
        
        public Integer getDaysAnalyzed() { return daysAnalyzed; }
        public void setDaysAnalyzed(Integer daysAnalyzed) { this.daysAnalyzed = daysAnalyzed; }
        
        public String getVolatilityLevel() { return volatilityLevel; }
        public void setVolatilityLevel(String volatilityLevel) { this.volatilityLevel = volatilityLevel; }
        
        public Instant getCalculatedAt() { return calculatedAt; }
        public void setCalculatedAt(Instant calculatedAt) { this.calculatedAt = calculatedAt; }
    }

    class CurrencyTrendResponse {
        private String currencyPair;
        private String trend; // UPWARD, DOWNWARD, STABLE
        private BigDecimal trendStrength; // 0-100
        private BigDecimal changePercentage;
        private BigDecimal currentRate;
        private BigDecimal periodStartRate;
        private Integer daysAnalyzed;
        private Instant calculatedAt;

        // Getters and setters
        public String getCurrencyPair() { return currencyPair; }
        public void setCurrencyPair(String currencyPair) { this.currencyPair = currencyPair; }
        
        public String getTrend() { return trend; }
        public void setTrend(String trend) { this.trend = trend; }
        
        public BigDecimal getTrendStrength() { return trendStrength; }
        public void setTrendStrength(BigDecimal trendStrength) { this.trendStrength = trendStrength; }
        
        public BigDecimal getChangePercentage() { return changePercentage; }
        public void setChangePercentage(BigDecimal changePercentage) { this.changePercentage = changePercentage; }
        
        public BigDecimal getCurrentRate() { return currentRate; }
        public void setCurrentRate(BigDecimal currentRate) { this.currentRate = currentRate; }
        
        public BigDecimal getPeriodStartRate() { return periodStartRate; }
        public void setPeriodStartRate(BigDecimal periodStartRate) { this.periodStartRate = periodStartRate; }
        
        public Integer getDaysAnalyzed() { return daysAnalyzed; }
        public void setDaysAnalyzed(Integer daysAnalyzed) { this.daysAnalyzed = daysAnalyzed; }
        
        public Instant getCalculatedAt() { return calculatedAt; }
        public void setCalculatedAt(Instant calculatedAt) { this.calculatedAt = calculatedAt; }
    }
} 
