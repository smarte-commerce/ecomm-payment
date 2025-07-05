package com.winnguyen1905.payment.core.feign;

import com.winnguyen1905.payment.core.feign.model.request.StockReservationRequest;
import com.winnguyen1905.payment.core.feign.model.request.StockUpdateRequest;
import com.winnguyen1905.payment.core.feign.model.request.InventoryMovementRequest;
import com.winnguyen1905.payment.core.feign.model.response.ProductAvailabilityResponse;
import com.winnguyen1905.payment.core.feign.model.response.StockInfoResponse;
import com.winnguyen1905.payment.core.feign.model.response.ReservationResponse;
import com.winnguyen1905.payment.core.feign.model.response.StockUpdateResponse;
import com.winnguyen1905.payment.core.feign.model.response.ProductInfoResponse;
import com.winnguyen1905.payment.core.feign.model.response.ProductVariantResponse;
import com.winnguyen1905.payment.core.feign.model.response.InventoryMovementResponse;
import com.winnguyen1905.payment.core.feign.model.response.LowStockAlertResponse;
import com.winnguyen1905.payment.core.model.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Client interface for communicating with the inventory service
 */
@FeignClient(name = "inventory-service", url = "${services.inventory-service.url:http://inventory-service:8080}")
public interface InventoryServiceClient {

  // Product availability operations
  @GetMapping("/products/{productId}/availability")
  RestResponse<ProductAvailabilityResponse> checkProductAvailability(@PathVariable UUID productId);

  @PostMapping("/products/availability/batch")
  RestResponse<List<ProductAvailabilityResponse>> checkProductsAvailability(
      @RequestBody @Valid List<UUID> productIds);

  @GetMapping("/products/{productId}/stock")
  RestResponse<StockInfoResponse> getProductStockInfo(@PathVariable UUID productId);

  // Stock reservation operations
  @PostMapping("/reservations")
  RestResponse<ReservationResponse> createReservation(@RequestBody @Valid StockReservationRequest request);

  @PutMapping("/reservations/{reservationId}/confirm")
  RestResponse<ReservationResponse> confirmReservation(@PathVariable UUID reservationId);

  @PutMapping("/reservations/{reservationId}/cancel")
  RestResponse<ReservationResponse> cancelReservation(@PathVariable UUID reservationId);

  @GetMapping("/reservations/{reservationId}")
  RestResponse<ReservationResponse> getReservation(@PathVariable UUID reservationId);

  @GetMapping("/reservations")
  RestResponse<List<ReservationResponse>> getReservationsByOrderId(@RequestParam UUID orderId);

  // Stock update operations
  @PutMapping("/products/{productId}/stock/increase")
  RestResponse<StockUpdateResponse> increaseStock(
      @PathVariable UUID productId,
      @RequestBody @Valid StockUpdateRequest request);

  @PutMapping("/products/{productId}/stock/decrease")
  RestResponse<StockUpdateResponse> decreaseStock(
      @PathVariable UUID productId,
      @RequestBody @Valid StockUpdateRequest request);

  @PostMapping("/products/stock/bulk-update")
  RestResponse<List<StockUpdateResponse>> bulkUpdateStock(
      @RequestBody @Valid List<StockUpdateRequest> requests);

  // Product catalog operations
  @GetMapping("/products/{productId}")
  RestResponse<ProductInfoResponse> getProductInfo(@PathVariable UUID productId);

  @PostMapping("/products/batch")
  RestResponse<List<ProductInfoResponse>> getProductsInfo(@RequestBody List<UUID> productIds);

  @GetMapping("/products/{productId}/variants")
  RestResponse<List<ProductVariantResponse>> getProductVariants(@PathVariable UUID productId);

  // Inventory tracking operations
  @PostMapping("/movements")
  RestResponse<InventoryMovementResponse> recordInventoryMovement(
      @RequestBody @Valid InventoryMovementRequest request);

  @GetMapping("/movements")
  RestResponse<List<InventoryMovementResponse>> getInventoryMovements(
      @RequestParam(required = false) UUID productId,
      @RequestParam(required = false) String movementType,
      @RequestParam(required = false) Instant startDate,
      @RequestParam(required = false) Instant endDate);

  // Low stock alerts
  @GetMapping("/alerts/low-stock")
  RestResponse<List<LowStockAlertResponse>> getLowStockAlerts(
      @RequestParam(required = false) UUID vendorId);

  @PostMapping("/alerts/low-stock/{productId}/acknowledge")
  RestResponse<Void> acknowledgeLowStockAlert(@PathVariable UUID productId);

}
