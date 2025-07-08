# Payment Service

## Overview

The Payment Service handles all payment processing and financial operations within the e-commerce platform. It manages payment methods, transaction processing, refunds, and integration with external payment providers. The service implements robust security measures and follows financial compliance standards.

## Key Features

- **Multiple Payment Methods**: Support for credit cards, digital wallets, bank transfers, and more
- **Payment Processing**: Authorization, capture, and settlement
- **Refund Management**: Full and partial refunds
- **Secure Storage**: PCI-compliant data handling
- **Fraud Detection**: Basic fraud prevention
- **Payment Status Tracking**: Comprehensive transaction status monitoring
- **Payment Provider Integration**: Configurable payment gateways
- **Payment Analytics**: Transaction reporting and financial insights
- **Multi-currency Support**: International payment processing
- **Tokenization**: Secure payment method storage

## Technical Stack

- Spring Boot 3.x
- Spring Data JPA
- Spring Security with encryption and secure handling
- Spring Cloud for microservice integration
- PostgreSQL/CockroachDB for transaction storage
- Redis for caching
- Resilience4j for fault tolerance

## Project Structure

- `/src/main/java/com/winnguyen1905/payment/`
  - `/config/`: Configuration classes including security settings
  - `/core/`: Core business logic
    - `/controller/`: REST controllers
    - `/model/`: Domain models and DTOs
    - `/service/`: Payment service implementations
  - `/exception/`: Exception handling
  - `/PaymentApplication.java`: Main application class
  
## API Endpoints

### Payment Operations

- `POST /api/v1/payments`: Create payment
- `GET /api/v1/payments/{id}`: Get payment by ID
- `GET /api/v1/payments`: List payments with filtering
- `POST /api/v1/payments/{id}/capture`: Capture authorized payment
- `POST /api/v1/payments/{id}/void`: Void payment
- `POST /api/v1/payments/{id}/refund`: Refund payment

### Payment Methods

- `GET /api/v1/payment-methods`: List available payment methods
- `POST /api/v1/payment-methods`: Create payment method
- `GET /api/v1/payment-methods/{id}`: Get payment method details
- `DELETE /api/v1/payment-methods/{id}`: Delete payment method
- `GET /api/v1/payment-methods/customer/{customerId}`: List customer's payment methods

### Payment Providers

- `GET /api/v1/payment-providers`: List available payment providers
- `POST /api/v1/payment-providers/{id}/test-connection`: Test provider connection

### Webhooks

- `POST /api/v1/payments/webhooks/{provider}`: Receive provider webhook notifications

## Transaction Flow

```
Customer → Payment Request → Authorization → Capture → Settlement
                                   ↓
                               Declined
```

## Refund Flow

```
Refund Request → Validation → Provider Refund → Confirmation
```

## Payment Statuses

- `PENDING`: Initial state before processing
- `AUTHORIZED`: Payment authorized but not captured
- `CAPTURED`: Payment successfully captured
- `SETTLED`: Payment settled with provider
- `DECLINED`: Payment declined by provider
- `FAILED`: Payment processing failed
- `REFUNDED`: Payment refunded
- `PARTIALLY_REFUNDED`: Payment partially refunded
- `VOIDED`: Authorized payment voided
- `EXPIRED`: Payment authorization expired

## Security Measures

- PCI DSS compliance considerations
- Encryption for sensitive data
- Tokenization for payment methods
- HTTPS-only communication
- Input validation and sanitization
- Role-based access control
- Audit logging for financial operations

## Integration with Other Services

- **Order Service**: Payment status updates for orders
- **Notification Service**: Payment notifications
- **Customer Service**: Customer payment methods
- **Analytics Service**: Financial reporting

## Error Handling

The service implements comprehensive error handling for payment scenarios:

- `PaymentNotFoundException`: Payment not found
- `PaymentMethodInvalidException`: Invalid payment method
- `PaymentDeclinedException`: Payment declined by provider
- `InsufficientFundsException`: Insufficient funds
- `PaymentAlreadyProcessedException`: Payment already processed
- `RefundExceededException`: Refund amount exceeds original payment

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- PostgreSQL database
- Docker (optional)

### Setup

1. Configure database in `application.yaml`
2. Configure payment providers in `application.yaml`
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Docker

```bash
# Build Docker image
docker build -t payment-service .

# Run with Docker
docker run -p 8083:8083 payment-service
```

## Payment Provider Configuration

```yaml
payment:
  providers:
    stripe:
      enabled: true
      api-key: ${STRIPE_API_KEY}
      webhook-secret: ${STRIPE_WEBHOOK_SECRET}
      sandbox-mode: true
    paypal:
      enabled: true
      client-id: ${PAYPAL_CLIENT_ID}
      client-secret: ${PAYPAL_CLIENT_SECRET}
      sandbox-mode: true
  default-provider: stripe
```

## Documentation

- Swagger UI: `/swagger-ui.html` (when application is running)
- Database schema: See `src/main/resources/payment_service_sql.sql`
