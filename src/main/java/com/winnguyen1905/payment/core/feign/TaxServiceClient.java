package com.winnguyen1905.payment.core.feign;

import com.winnguyen1905.payment.core.feign.model.request.TaxAddress;
import com.winnguyen1905.payment.core.feign.model.request.TaxCalculationRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxRateRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxValidationRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxExemptionCheckRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxExemptionRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxReportRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxFilingRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxNumberValidationRequest;
import com.winnguyen1905.payment.core.feign.model.request.TaxRateLookupRequest;

import com.winnguyen1905.payment.core.feign.model.response.TaxBreakdown;
import com.winnguyen1905.payment.core.feign.model.response.TaxCalculationResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxRateResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxValidationResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxExemptionResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxReportResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxFilingResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxNumberValidationResponse;
import com.winnguyen1905.payment.core.feign.model.response.TaxRateLookupResponse;

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

@FeignClient(name = "tax-service", url = "${services.tax-service.url:http://tax-service:8080}")
public interface TaxServiceClient {

  // Tax calculation operations
  @PostMapping("/calculate")
  RestResponse<TaxCalculationResponse> calculateTax(@RequestBody @Valid TaxCalculationRequest request);

  @PostMapping("/calculate/batch")
  RestResponse<List<TaxCalculationResponse>> calculateTaxBatch(
      @RequestBody @Valid List<TaxCalculationRequest> requests);

  @PostMapping("/validate")
  RestResponse<TaxValidationResponse> validateTaxCalculation(@RequestBody @Valid TaxValidationRequest request);

  // Tax rates operations
  @GetMapping("/rates")
  RestResponse<List<TaxRateResponse>> getTaxRates(
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String state,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String taxType);

  @GetMapping("/rates/{rateId}")
  RestResponse<TaxRateResponse> getTaxRate(@PathVariable UUID rateId);

  @PostMapping("/rates")
  RestResponse<TaxRateResponse> createTaxRate(@RequestBody @Valid TaxRateRequest request);

  @PutMapping("/rates/{rateId}")
  RestResponse<TaxRateResponse> updateTaxRate(@PathVariable UUID rateId, @RequestBody @Valid TaxRateRequest request);

  // Tax exemption operations
  @PostMapping("/exemptions/check")
  RestResponse<TaxExemptionResponse> checkTaxExemption(@RequestBody @Valid TaxExemptionCheckRequest request);

  @GetMapping("/exemptions/{customerId}")
  RestResponse<List<TaxExemptionResponse>> getCustomerExemptions(@PathVariable UUID customerId);

  @PostMapping("/exemptions")
  RestResponse<TaxExemptionResponse> createTaxExemption(@RequestBody @Valid TaxExemptionRequest request);

  // Tax compliance operations
  @PostMapping("/reports/generate")
  RestResponse<TaxReportResponse> generateTaxReport(@RequestBody @Valid TaxReportRequest request);

  @GetMapping("/reports/{reportId}")
  RestResponse<TaxReportResponse> getTaxReport(@PathVariable UUID reportId);

  @GetMapping("/reports")
  RestResponse<List<TaxReportResponse>> getTaxReports(
      @RequestParam(required = false) String reportType,
      @RequestParam(required = false) Instant startDate,
      @RequestParam(required = false) Instant endDate);

  // Tax filing operations
  @PostMapping("/filings")
  RestResponse<TaxFilingResponse> createTaxFiling(@RequestBody @Valid TaxFilingRequest request);

  @GetMapping("/filings/{filingId}")
  RestResponse<TaxFilingResponse> getTaxFiling(@PathVariable UUID filingId);

  @PutMapping("/filings/{filingId}/submit")
  RestResponse<TaxFilingResponse> submitTaxFiling(@PathVariable UUID filingId);

  // Tax authority integration
  @PostMapping("/authorities/validate-number")
  RestResponse<TaxNumberValidationResponse> validateTaxNumber(@RequestBody @Valid TaxNumberValidationRequest request);

  @PostMapping("/authorities/lookup-rates")
  RestResponse<TaxRateLookupResponse> lookupTaxRatesFromAuthority(@RequestBody @Valid TaxRateLookupRequest request);
}
