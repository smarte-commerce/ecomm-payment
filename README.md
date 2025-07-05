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

### 9. Subscription APIs
**Base URL:** `/api/v1/subscriptions`

- `POST /` - Create subscription
- `GET /{id}` - Get subscription by ID
- `GET /number/{subscriptionNumber}` - Get subscription by number
- `GET /provider/{providerSubscriptionId}` - Get subscription by provider ID
- `GET /customer/{customerId}` - Get subscriptions by customer
- `GET /status/{status}` - Get subscriptions by status
- `GET /customer/{customerId}/status/{status}` - Get subscriptions by customer and status
- `GET /plan/{planId}` - Get subscriptions by plan
- `GET /renewals/due/{beforeDate}` - Get subscriptions ready for renewal
- `GET /trials/due/{beforeDate}` - Get trials ready for conversion
- `PUT /{id}` - Update subscription
- `PUT /{id}/status` - Update subscription status
- `PUT /{id}/cancel` - Cancel subscription
- `PUT /{id}/reactivate` - Reactivate subscription
- `PUT /{id}/renew` - Renew subscription
- `PUT /{id}/convert-trial` - Convert trial to active
- `PUT /{id}/payment-method` - Update payment method
- `PUT /{id}/metadata` - Update metadata
- `PUT /{id}/pause` - Pause subscription
- `PUT /{id}/resume` - Resume subscription
- `DELETE /{id}` - Delete subscription

### 10. Commission Rule APIs
**Base URL:** `/api/v1/commission-rules`

- `POST /` - Create commission rule
- `GET /{id}` - Get commission rule by ID
- `GET /` - Get all commission rules
- `GET /vendor/{vendorId}` - Get rules by vendor
- `GET /category/{categoryId}` - Get rules by category
- `GET /type/{ruleType}` - Get rules by type
- `GET /active` - Get active rules
- `GET /effective/{effectiveDate}` - Get rules effective at date
- `GET /vendor/{vendorId}/category/{categoryId}` - Get rules by vendor and category
- `GET /best-applicable` - Get best applicable rule for calculation
- `POST /calculate` - Calculate commission
- `POST /calculate/{ruleId}` - Calculate commission by specific rule
- `PUT /{id}` - Update commission rule
- `PUT /{id}/activate` - Activate rule
- `PUT /{id}/deactivate` - Deactivate rule
- `PUT /{id}/priority` - Update priority
- `PUT /{id}/dates` - Update effective dates
- `PUT /{id}/rate` - Update commission rate
- `DELETE /{id}` - Delete commission rule
- `GET /conflicts` - Check for conflicting rules
- `GET /expiring/{beforeDate}` - Get rules expiring soon

### 11. Payment Webhook APIs
**Base URL:** `/api/v1/webhooks`

- `POST /` - Create payment webhook
- `GET /{id}` - Get webhook by ID
- `GET /` - Get all webhooks
- `GET /provider/{providerId}` - Get webhooks by provider
- `GET /payment/{paymentId}` - Get webhooks by payment
- `GET /subscription/{subscriptionId}` - Get webhooks by subscription
- `GET /type/{webhookType}` - Get webhooks by type
- `GET /event/{eventType}` - Get webhooks by event type
- `GET /event-id/{eventId}` - Get webhook by event ID
- `GET /processed` - Get processed webhooks
- `GET /unprocessed` - Get unprocessed webhooks
- `GET /errors` - Get webhooks with errors
- `GET /received-between` - Get webhooks within date range
- `GET /high-retry/{minAttempts}` - Get webhooks with high retry count
- `PUT /{id}` - Update webhook
- `PUT /{id}/process` - Process webhook
- `PUT /{id}/mark-processed` - Mark as processed
- `PUT /{id}/mark-failed` - Mark as failed
- `PUT /{id}/retry` - Retry processing
- `PUT /{id}/data` - Update webhook data
- `POST /verify-signature` - Verify webhook signature
- `POST /process-payload` - Process webhook payload
- `POST /auto-retry` - Auto-retry failed webhooks
- `DELETE /{id}` - Delete webhook
- `DELETE /cleanup/{olderThan}` - Cleanup old webhooks

### 12. Subscription Invoice APIs
**Base URL:** `/api/v1/subscription-invoices`

- `POST /` - Create subscription invoice
- `GET /{id}` - Get invoice by ID
- `GET /number/{invoiceNumber}` - Get invoice by number
- `GET /provider/{providerInvoiceId}` - Get invoice by provider ID
- `GET /subscription/{subscriptionId}` - Get invoices by subscription
- `GET /payment/{paymentId}` - Get invoices by payment
- `GET /status/{status}` - Get invoices by status
- `GET /overdue/{asOfDate}` - Get overdue invoices
- `GET /status/{status}/due-before/{beforeDate}` - Get invoices by status and due date
- `GET /period` - Get invoices within period
- `GET /high-attempts/{minAttempts}` - Get invoices with high attempt count
- `PUT /{id}` - Update invoice
- `PUT /{id}/status` - Update invoice status
- `PUT /{id}/mark-paid` - Mark as paid
- `PUT /{id}/mark-uncollectible` - Mark as uncollectible
- `PUT /{id}/void` - Void invoice
- `PUT /{id}/finalize` - Finalize draft invoice
- `PUT /{id}/next-attempt` - Update next payment attempt
- `PUT /{id}/increment-attempts` - Increment attempt count
- `PUT /{id}/metadata` - Update metadata
- `POST /generate/{subscriptionId}` - Generate next invoice
- `POST /process-retries` - Process payment retries
- `DELETE /{id}` - Delete invoice

## Microservice Integration - Feign Clients

The payment service integrates with external microservices through Feign clients for comprehensive payment ecosystem functionality:

### 1. PaymentGatewayServiceClient
**Purpose:** External payment gateway integration
**Endpoints:**
- Payment processing (create, confirm, capture, cancel)
- Refund management
- Customer management
- Payment methods
- Subscription handling
- Webhook management
- Balance and payout operations

### 2. InventoryServiceClient
**Purpose:** Product availability and stock management
**Endpoints:**
- Availability checks
- Stock reservations
- Stock updates
- Product catalog operations
- Inventory tracking
- Low stock alerts

### 3. TaxServiceClient
**Purpose:** Tax calculation and compliance
**Endpoints:**
- Tax calculation (single/batch)
- Tax rates management
- Exemption checking
- Compliance reporting
- Tax filing
- Tax authority integration

### 4. CurrencyServiceClient
**Purpose:** Currency conversion and exchange rates
**Endpoints:**
- Currency conversion operations
- Exchange rate management
- Currency information
- Rate provider operations
- Volatility analytics

### 5. AnalyticsServiceClient
**Purpose:** Payment analytics and reporting
**Endpoints:**
- Payment analytics
- Revenue analytics
- Customer analytics
- Fraud analytics
- Performance analytics
- Report generation
- Dashboard operations

### 6. AuditServiceClient
**Purpose:** Audit logging and compliance tracking
**Endpoints:**
- Audit log operations
- Security events
- Compliance operations
- Data retention
- Change tracking
- Access control audit

## Service Implementations

The following service implementations provide comprehensive business logic:

### Core Payment Services
- **SubscriptionServiceImpl** - Complete subscription lifecycle management
- **CommissionRuleServiceImpl** - Advanced commission calculation with conflict checking
- **PaymentWebhookServiceImpl** - Webhook processing with retry mechanisms
- **SubscriptionInvoiceServiceImpl** - Invoice management with payment retry logic

### Repository Layer
- **CommissionRuleRepository** - Commission rule queries with date-based filtering
- **PaymentWebhookRepository** - Webhook tracking with processing status
- **SubscriptionRepository** - Subscription queries with status and period filtering
- **SubscriptionInvoiceRepository** - Invoice queries with overdue tracking

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
