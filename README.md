# Payment Service - Comprehensive RESTful APIs

## Overview
This Payment Service provides a comprehensive set of RESTful APIs for managing all aspects of payment processing, including transactions, fraud detection, refunds, settlements, and subscription management.

## Architecture
The service follows a layered architecture with:
- **Controllers** - REST endpoints handling HTTP requests
- **Services** - Business logic layer
- **DTOs** - Data Transfer Objects for request/response
- **Entities** - JPA entities for database mapping
- **Repositories** - Data access layer

## API Endpoints

### 1. Fraud Check APIs
**Base URL:** `/api/v1/fraud-checks`

- `POST /` - Create fraud check
- `GET /{id}` - Get fraud check by ID
- `GET /payment/{paymentId}` - Get fraud checks by payment
- `GET /order/{orderId}` - Get fraud checks by order
- `GET /customer/{customerId}` - Get fraud checks by customer
- `GET /status/{status}` - Get fraud checks by status
- `GET /risk-level/{riskLevel}` - Get fraud checks by risk level
- `GET /check-type/{checkType}` - Get fraud checks by type
- `PUT /{id}/status` - Update fraud check status
- `PUT /{id}/review` - Add reviewer decision
- `GET /pending/risk-levels` - Get pending checks by risk levels
- `GET /customer/{customerId}/average-risk-score` - Get average risk score

### 2. Payment APIs
**Base URL:** `/api/v1/payments/core`

- `POST /` - Create payment
- `GET /{id}` - Get payment by ID
- `GET /number/{paymentNumber}` - Get payment by number
- `GET /transaction/{transactionId}` - Get payment by transaction ID
- `GET /order/{orderId}` - Get payments by order
- `GET /customer/{customerId}` - Get payments by customer
- `GET /status/{status}` - Get payments by status
- `GET /order/{orderId}/status/{status}` - Get payments by order and status
- `GET /customer/{customerId}/status/{status}` - Get payments by customer and status
- `PUT /{id}/status` - Update payment status
- `PUT /{id}/authorize` - Authorize payment
- `PUT /{id}/capture` - Capture payment
- `PUT /{id}/complete` - Complete payment
- `PUT /{id}/fail` - Fail payment
- `PUT /{id}/cancel` - Cancel payment
- `PUT /{id}/metadata` - Update payment metadata

### 3. Payment Transaction APIs
**Base URL:** `/api/v1/payments`

- `POST /` - Create payment transaction
- `GET /{id}` - Get payment transaction by ID
- `GET /transaction/{transactionId}` - Get transaction by ID
- `GET /order/{orderId}` - Get transactions by order
- `GET /customer/{customerId}` - Get transactions by customer
- `GET /vendor/{vendorId}` - Get transactions by vendor
- `PUT /{id}/status` - Update transaction status
- `PUT /{id}/complete` - Complete transaction
- `PUT /{id}/fail` - Fail transaction
- `POST /{id}/refund` - Refund transaction

### 4. Escrow Account APIs
**Base URL:** `/api/v1/escrow`

- `POST /` - Create escrow account
- `GET /{id}` - Get escrow account by ID
- `GET /order/{orderId}` - Get escrow accounts by order
- `GET /vendor/{vendorId}` - Get escrow accounts by vendor
- `GET /transaction/{transactionId}` - Get escrow by transaction
- `PUT /{id}/status` - Update escrow status
- `PUT /{id}/release` - Release funds
- `PUT /{id}/partial-release` - Release partial funds
- `PUT /{id}/refund` - Refund funds
- `GET /due-for-release` - Get accounts due for auto-release

### 5. Vendor Settlement APIs
**Base URL:** `/api/v1/settlements`

- `POST /` - Create settlement
- `GET /{id}` - Get settlement by ID
- `GET /vendor/{vendorId}` - Get settlements by vendor
- `GET /order/{orderId}` - Get settlements by order
- `PUT /{id}/status` - Update settlement status
- `GET /calculate-commission` - Calculate commission
- `POST /{id}/process` - Process settlement payment
- `GET /due-for-payment` - Get settlements due for payment
- `POST /{id}/notify` - Notify vendor account service
- `GET /vendor/{vendorId}/account-info` - Get vendor account info
- `GET /vendor/{vendorId}/settlement-info` - Get vendor settlement info

### 6. Payment Method APIs (Service Interface Created)
**Features:**
- CRUD operations for payment methods
- Customer payment method management
- Default payment method handling
- Payment method verification
- Active/inactive status management

### 7. Payment Provider APIs (Service Interface Created)
**Features:**
- Payment provider management
- Provider configuration management
- Active/inactive provider handling
- Provider type categorization
- Fee structure management

### 8. Refund APIs (Service Interface Created)
**Features:**
- Refund creation and processing
- Refund status management
- Payment-specific refunds
- Gateway integration handling
- Refund completion tracking

### 9. Subscription APIs (DTOs Created)
**Features:**
- Subscription lifecycle management
- Billing cycle handling
- Trial period management
- Payment method association
- Provider integration

### 10. Commission Rule APIs (DTOs Created)
**Features:**
- Commission rate management
- Rule-based commission calculation
- Vendor-specific rules
- Category-based commission
- Time-based rule activation

## Additional Entities Available for API Generation

The following entities have repositories and can be extended with full APIs:

### Financial Management
- **CodCollection** - Cash on delivery payment tracking
- **DeliveryConfirmation** - Delivery verification for fund release
- **RefundRequest** - Customer refund request workflow

### Core Payment Processing
- **EPaymentItem** - Individual payment line items
- **EPaymentDispute** - Payment dispute management
- **EPaymentWebhook** - Webhook event handling
- **EPaymentAuditLog** - Payment audit trail
- **ERefundItem** - Refund line item details

### Subscription Management
- **ESubscriptionInvoice** - Subscription billing invoices

## Request/Response Patterns

### Standard Request Structure
```json
{
  "field1": "value1",
  "field2": "value2",
  "metadata": "optional_json_string"
}
```

### Standard Response Structure
```json
{
  "statusCode": 200,
  "message": "Operation successful",
  "data": {
    "id": "uuid",
    "field1": "value1",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

## Validation
All APIs include comprehensive validation:
- Required field validation
- Data type validation
- Business rule validation
- Format validation (email, phone, currency codes)

## Error Handling
Standardized error responses across all APIs:
- `400` - Bad Request (validation errors)
- `404` - Resource Not Found
- `409` - Resource Already Exists
- `500` - Internal Server Error

## Database Integration
- JPA entities with proper relationships
- Optimistic locking with version control
- Audit trails with created/updated timestamps
- Soft delete capabilities where applicable

## Security Features
- Request validation
- Authorization checks
- Audit logging
- Secure payment data handling

## Feign Client Integration
- AccountServiceClient for vendor account communication
- Settlement notification handling
- External service integration patterns

## Future Extensibility
The service architecture supports easy addition of:
- New payment methods
- Additional fraud detection rules
- Enhanced reporting capabilities
- Integration with new payment providers
- Advanced analytics and monitoring

## Getting Started
1. Ensure database is properly configured
2. Run the application
3. Access API documentation at `/swagger-ui.html`
4. Use the provided DTOs for request/response handling

## Dependencies
- Spring Boot 3.x
- Spring Data JPA
- Spring Cloud OpenFeign
- Bean Validation
- Lombok
- Jackson for JSON processing

This comprehensive API suite provides complete payment processing capabilities with proper separation of concerns, robust error handling, and extensible architecture for future enhancements.
