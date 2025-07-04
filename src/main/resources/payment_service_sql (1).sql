-- =============================================
-- PAYMENT SERVICE DATABASE SCHEMA
-- =============================================

-- Create database
CREATE DATABASE payment_service;
USE payment_service;

-- Payment providers table
CREATE TABLE payment_providers (
    provider_id INT PRIMARY KEY AUTO_INCREMENT,
    provider_name VARCHAR(100) UNIQUE NOT NULL,
    provider_code VARCHAR(20) UNIQUE NOT NULL,
    provider_type ENUM('credit_card', 'digital_wallet', 'bank_transfer', 'cryptocurrency', 'bnpl') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    supported_countries JSON,
    supported_currencies JSON,
    api_endpoint VARCHAR(255),
    webhook_endpoint VARCHAR(255),
    configuration JSON,
    fee_structure JSON, -- {"percentage": 2.9, "fixed_fee": 0.30, "currency": "USD"}
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_provider_code (provider_code),
    INDEX idx_provider_type (provider_type),
    INDEX idx_is_active (is_active)
);

-- Payment methods table (customer saved payment methods)
CREATE TABLE payment_methods (
    payment_method_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    provider_id INT NOT NULL,
    method_type ENUM('credit_card', 'debit_card', 'paypal', 'apple_pay', 'google_pay', 'bank_account', 'crypto_wallet') NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    card_last_four VARCHAR(4),
    card_brand VARCHAR(20),
    card_expiry_month INT,
    card_expiry_year INT,
    billing_address JSON,
    provider_payment_method_id VARCHAR(255), -- External provider's payment method ID
    verification_status ENUM('pending', 'verified', 'failed') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (provider_id) REFERENCES payment_providers(provider_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_provider_id (provider_id),
    INDEX idx_method_type (method_type),
    INDEX idx_is_default (is_default),
    INDEX idx_is_active (is_active)
);

-- Payments table
CREATE TABLE payments (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    payment_number VARCHAR(50) UNIQUE NOT NULL,
    provider_id INT NOT NULL,
    payment_method_id BIGINT,
    payment_intent_id VARCHAR(255), -- Provider's payment intent ID
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    fee_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    net_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    status ENUM('pending', 'processing', 'authorized', 'captured', 'completed', 'failed', 'cancelled', 'refunded', 'partially_refunded') DEFAULT 'pending',
    payment_type ENUM('purchase', 'refund', 'subscription', 'deposit', 'withdrawal') DEFAULT 'purchase',
    description TEXT,
    metadata JSON,
    authorization_code VARCHAR(100),
    transaction_id VARCHAR(255), -- Provider's transaction ID
    gateway_response JSON,
    failure_reason TEXT,
    authorized_at TIMESTAMP NULL,
    captured_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,
    expires_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (provider_id) REFERENCES payment_providers(provider_id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id),
    INDEX idx_order_id (order_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_payment_number (payment_number),
    INDEX idx_provider_id (provider_id),
    INDEX idx_status (status),
    INDEX idx_payment_type (payment_type),
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_created_at (created_at)
);

-- Payment items table (itemized payment breakdown)
CREATE TABLE payment_items (
    payment_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,
    order_item_id BIGINT,
    item_type ENUM('product', 'shipping', 'tax', 'discount', 'fee') NOT NULL,
    description VARCHAR(255) NOT NULL,
    quantity INT DEFAULT 1,
    unit_amount DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    metadata JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id) ON DELETE CASCADE,
    INDEX idx_payment_id (payment_id),
    INDEX idx_order_item_id (order_item_id),
    INDEX idx_item_type (item_type)
);

-- Refunds table
CREATE TABLE refunds (
    refund_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    refund_number VARCHAR(50) UNIQUE NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    refund_type ENUM('full', 'partial', 'chargeback') NOT NULL,
    reason ENUM('customer_request', 'fraud', 'duplicate', 'product_not_received', 'product_defective', 'chargeback', 'other') NOT NULL,
    description TEXT,
    status ENUM('pending', 'processing', 'completed', 'failed', 'cancelled') DEFAULT 'pending',
    provider_refund_id VARCHAR(255), -- Provider's refund ID
    gateway_response JSON,
    failure_reason TEXT,
    processed_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_order_id (order_id),
    INDEX idx_refund_number (refund_number),
    INDEX idx_status (status),
    INDEX idx_refund_type (refund_type),
    INDEX idx_reason (reason),
    INDEX idx_created_at (created_at)
);

-- Refund items table
CREATE TABLE refund_items (
    refund_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    refund_id BIGINT NOT NULL,
    payment_item_id BIGINT NOT NULL,
    order_item_id BIGINT,
    quantity INT DEFAULT 1,
    unit_amount DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (refund_id) REFERENCES refunds(refund_id) ON DELETE CASCADE,
    FOREIGN KEY (payment_item_id) REFERENCES payment_items(payment_item_id),
    INDEX idx_refund_id (refund_id),
    INDEX idx_payment_item_id (payment_item_id),
    INDEX idx_order_item_id (order_item_id)
);

-- Subscriptions table
CREATE TABLE subscriptions (
    subscription_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    subscription_number VARCHAR(50) UNIQUE NOT NULL,
    payment_method_id BIGINT NOT NULL,
    provider_id INT NOT NULL,
    provider_subscription_id VARCHAR(255), -- Provider's subscription ID
    plan_name VARCHAR(100) NOT NULL,
    plan_id VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    interval_type ENUM('day', 'week', 'month', 'year') NOT NULL,
    interval_count INT NOT NULL DEFAULT 1,
    trial_period_days INT DEFAULT 0,
    status ENUM('active', 'trialing', 'past_due', 'cancelled', 'unpaid', 'incomplete') DEFAULT 'active',
    current_period_start TIMESTAMP NOT NULL,
    current_period_end TIMESTAMP NOT NULL,
    trial_start TIMESTAMP NULL,
    trial_end TIMESTAMP NULL,
    cancelled_at TIMESTAMP NULL,
    ended_at TIMESTAMP NULL,
    metadata JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id),
    FOREIGN KEY (provider_id) REFERENCES payment_providers(provider_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_subscription_number (subscription_number),
    INDEX idx_payment_method_id (payment_method_id),
    INDEX idx_provider_id (provider_id),
    INDEX idx_status (status),
    INDEX idx_current_period_end (current_period_end)
);

-- Subscription invoices table
CREATE TABLE subscription_invoices (
    invoice_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subscription_id BIGINT NOT NULL,
    payment_id BIGINT,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    status ENUM('draft', 'open', 'paid', 'uncollectible', 'void') DEFAULT 'draft',
    provider_invoice_id VARCHAR(255), -- Provider's invoice ID
    period_start TIMESTAMP NOT NULL,
    period_end TIMESTAMP NOT NULL,
    due_date TIMESTAMP NOT NULL,
    paid_at TIMESTAMP NULL,
    next_payment_attempt TIMESTAMP NULL,
    attempt_count INT DEFAULT 0,
    metadata JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(subscription_id) ON DELETE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    INDEX idx_subscription_id (subscription_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_invoice_number (invoice_number),
    INDEX idx_status (status),
    INDEX idx_due_date (due_date),
    INDEX idx_period_start (period_start)
);

-- Fraud detection table
CREATE TABLE fraud_checks (
    fraud_check_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    check_type ENUM('automated', 'manual', 'provider') NOT NULL,
    risk_score DECIMAL(5,2) NOT NULL CHECK (risk_score >= 0 AND risk_score <= 100),
    risk_level ENUM('low', 'medium', 'high', 'critical') NOT NULL,
    status ENUM('pending', 'approved', 'declined', 'review_required') DEFAULT 'pending',
    rules_triggered JSON, -- Array of triggered fraud rules
    provider_response JSON,
    reviewer_id BIGINT,
    reviewer_notes TEXT,
    decision_reason TEXT,
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP NULL,
    
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_order_id (order_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_check_type (check_type),
    INDEX idx_risk_level (risk_level),
    INDEX idx_status (status),
    INDEX idx_checked_at (checked_at)
);

-- Payment webhooks table
CREATE TABLE payment_webhooks (
    webhook_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    webhook_type VARCHAR(50) NOT NULL,
    payment_id BIGINT,
    subscription_id BIGINT,
    event_id VARCHAR(255), -- Provider's event ID
    event_type VARCHAR(100) NOT NULL,
    webhook_data JSON NOT NULL,
    processed BOOLEAN DEFAULT FALSE,
    processing_attempts INT DEFAULT 0,
    last_processing_error TEXT,
    received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP NULL,
    
    FOREIGN KEY (provider_id) REFERENCES payment_providers(provider_id),
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(subscription_id),
    INDEX idx_provider_id (provider_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_subscription_id (subscription_id),
    INDEX idx_webhook_type (webhook_type),
    INDEX idx_event_type (event_type),
    INDEX idx_processed (processed),
    INDEX idx_received_at (received_at)
);

-- Payment disputes table (chargebacks, disputes)
CREATE TABLE payment_disputes (
    dispute_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,
    dispute_number VARCHAR(50) UNIQUE NOT NULL,
    provider_dispute_id VARCHAR(255), -- Provider's dispute ID
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    dispute_type ENUM('chargeback', 'inquiry', 'retrieval_request', 'pre_arbitration', 'arbitration') NOT NULL,
    reason_code VARCHAR(20),
    reason_description TEXT,
    status ENUM('warning_needs_response', 'warning_under_review', 'warning_closed', 'needs_response', 'under_review', 'charge_refunded', 'won', 'lost') NOT NULL,
    evidence_due_by TIMESTAMP,
    evidence_submitted BOOLEAN DEFAULT FALSE,
    evidence_details JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_dispute_number (dispute_number),
    INDEX idx_provider_dispute_id (provider_dispute_id),
    INDEX idx_dispute_type (dispute_type),
    INDEX idx_status (status),
    INDEX idx_evidence_due_by (evidence_due_by)
);

-- Payment audit log table
CREATE TABLE payment_audit_log (
    audit_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT,
    refund_id BIGINT,
    subscription_id BIGINT,
    action_type ENUM('create', 'update', 'delete', 'authorize', 'capture', 'refund', 'cancel', 'webhook') NOT NULL,
    old_values JSON,
    new_values JSON,
    user_id BIGINT,
    user_type ENUM('customer', 'admin', 'system') NOT NULL,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    FOREIGN KEY (refund_id) REFERENCES refunds(refund_id),
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(subscription_id),
    INDEX idx_payment_id (payment_id),
    INDEX idx_refund_id (refund_id),
    INDEX idx_subscription_id (subscription_id),
    INDEX idx_action_type (action_type),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
);

-- =============================================
-- SAMPLE DATA FOR PAYMENT SERVICE
-- =============================================

-- Sample payment providers
INSERT INTO payment_providers (provider_name, provider_code, provider_type, is_active, supported_countries, supported_currencies, api_endpoint, configuration, fee_structure) VALUES
('Stripe', 'STRIPE', 'credit_card', TRUE, 
 '["US", "CA", "GB", "AU", "DE", "FR", "IT", "ES", "NL", "BE", "AT", "CH", "SE", "NO", "DK", "FI"]', 
 '["USD", "EUR", "GBP", "CAD", "AUD", "JPY", "CHF", "SEK", "NOK", "DKK"]',
 'https://api.stripe.com/v1/',
 '{"publishable_key": "pk_test_...", "secret_key": "sk_test_...", "webhook_secret": "whsec_..."}',
 '{"percentage": 2.9, "fixed_fee": 0.30, "currency": "USD"}'),
('PayPal', 'PAYPAL', 'digital_wallet', TRUE,
 '["US", "CA", "GB", "AU", "DE", "FR", "IT", "ES", "NL", "BE", "AT", "CH", "SE", "NO", "DK", "FI", "JP", "IN", "BR", "MX"]',
 '["USD", "EUR", "GBP", "CAD", "AUD", "JPY", "CHF", "SEK", "NOK", "DKK", "BRL", "MXN", "INR"]',
 'https://api.paypal.com/v1/',
 '{"client_id": "client_id_here", "client_secret": "client_secret_here", "mode": "sandbox"}',
 '{"percentage": 3.49, "fixed_fee": 0.49, "currency": "USD"}'),
('Square', 'SQUARE', 'credit_card', TRUE,
 '["US", "CA", "AU", "GB", "IE", "ES", "FR", "JP"]',
 '["USD", "CAD", "AUD", "GBP", "EUR", "JPY"]',
 'https://connect.squareup.com/v2/',
 '{"application_id": "app_id_here", "access_token": "access_token_here", "environment": "sandbox"}',
 '{"percentage": 2.6, "fixed_fee": 0.10, "currency": "USD"}'),
('Authorize.Net', 'AUTHNET', 'credit_card', TRUE,
 '["US", "CA", "GB", "AU"]',
 '["USD", "CAD", "GBP", "AUD"]',
 'https://apitest.authorize.net/xml/v1/request.api',
 '{"api_login_id": "login_id_here", "transaction_key": "trans_key_here", "environment": "sandbox"}',
 '{"percentage": 2.9, "fixed_fee": 0.30, "currency": "USD"}');

-- Sample payment methods
INSERT INTO payment_methods (customer_id, provider_id, method_type, display_name, is_default, card_last_four, card_brand, card_expiry_month, card_expiry_year, billing_address, provider_payment_method_id, verification_status) VALUES
(1001, 1, 'credit_card', 'Visa ending in 4242', TRUE, '4242', 'Visa', 12, 2025, 
 '{"street": "123 Main St", "city": "New York", "state": "NY", "zip": "10001", "country": "US"}', 
 'pm_1234567890', 'verified'),
(1002, 2, 'paypal', 'PayPal Account', TRUE, NULL, NULL, NULL, NULL,
 '{"street": "456 Oak Ave", "city": "Los Angeles", "state": "CA", "zip": "90210", "country": "US"}',
 'BA-12345678901234567', 'verified'),
(1003, 1, 'credit_card', 'Mastercard ending in 5555', TRUE, '5555', 'Mastercard', 8, 2026,
 '{"street": "789 Pine Rd", "city": "Chicago", "state": "IL", "zip": "60601", "country": "US"}',
 'pm_0987654321', 'verified');

-- Sample payments
INSERT INTO payments (order_id, customer_id, payment_number, provider_id, payment_method_id, amount, currency, fee_amount, net_amount, status, payment_type, description, authorization_code, transaction_id, authorized_at, captured_at, completed_at) VALUES
(1, 1001, 'PAY-2024-001', 1, 1, 157.59, 'USD', 4.87, 152.72, 'completed', 'purchase', 'Payment for order ORD-2024-001', 'AUTH123456', 'ch_1234567890abcdef', '2024-07-04 10:00:00', '2024-07-04 10:01:00', '2024-07-04 10:01:00'),
(2, 1002, 'PAY-2024-002', 2, 2, 103.16, 'USD', 4.10, 99.06, 'completed', 'purchase', 'Payment for order ORD-2024-002', 'PP123456789', 'PAYID-M123456', '2024-07-04 11:00:00', '2024-07-04 11:00:00', '2024-07-04 11:00:00'),
(3, 1003, 'PAY-2024-003', 1, 3, 291.57, 'USD', 8.76, 282.81, 'completed', 'purchase', 'Payment for order ORD-2024-003', 'AUTH789012', 'ch_0987654321fedcba', '2024-07-04 12:00:00', '2024-07-04 12:01:00', '2024-07-04 12:01:00');

-- Sample payment items
INSERT INTO payment_items (payment_id, order_item_id, item_type, description, quantity, unit_amount, total_amount, currency) VALUES
(1, 1, 'product', 'Wireless Headphones', 1, 150.00, 150.00, 'USD'),
(1, NULL, 'tax', 'Sales Tax', 1, 12.60, 12.60, 'USD'),
(1, NULL, 'shipping', 'UPS Ground Shipping', 1, 9.99, 9.99, 'USD'),
(1, NULL, 'discount', '10% Off Electronics', 1, -15.00, -15.00, 'USD'),
(2, 2, 'product', 'Smartphone Case', 2, 24.99, 49.98, 'USD'),
(2, 3, 'product', 'USB Cable', 2, 19.99, 39.98, 'USD'),
(2, NULL, 'tax', 'Sales Tax', 1, 7.20, 7.20, 'USD'),
(2, NULL, 'shipping', 'Standard Shipping', 1, 5.99, 5.99, 'USD'),
(3, 4, 'product', 'Laptop Stand', 1, 89.99, 89.99, 'USD'),
(3, 5, 'product', 'Mechanical Keyboard', 1, 139.99, 139.99, 'USD'),
(3, 6, 'product', 'Wireless Mouse', 1, 69.99, 69.99, 'USD'),
(3, NULL, 'tax', 'Sales Tax', 1, 21.60, 21.60, 'USD'),
(3, NULL, 'discount', 'Free Shipping + 20% Off', 1, -29.99, -29.99, 'USD');

-- Sample fraud checks
INSERT INTO fraud_checks (payment_id, order_id, customer_id, check_type, risk_score, risk_level, status, rules_triggered, decision_reason, checked_at) VALUES
(1, 1, 1001, 'automated', 15.5, 'low', 'approved', '[]', 'Low risk score, approved automatically', '2024-07-04 10:00:00'),
(2, 2, 1002, 'automated', 25.2, 'low', 'approved', '["new_customer"]', 'New customer but low risk, approved', '2024-07-04 11:00:00'),
(3, 3, 1003, 'automated', 8.1, 'low', 'approved', '[]', 'Very low risk score, approved automatically', '2024-07-04 12:00:00');

-- Sample subscription (for demonstration)
INSERT INTO subscriptions (customer_id, subscription_number, payment_method_id, provider_id, provider_subscription_id, plan_name, plan_id, amount, currency, interval_type, interval_count, status, current_period_start, current_period_end) VALUES
(1001, 'SUB-2024-001', 1, 1, 'sub_1234567890', 'Premium Plan', 'premium_monthly', 29.99, 'USD', 'month', 1, 'active', '2024-07-01 00:00:00', '2024-08-01 00:00:00');

-- Sample subscription invoice
INSERT INTO subscription_invoices (subscription_id, invoice_number, amount, currency, status, period_start, period_end, due_date, paid_at) VALUES
(1, 'INV-2024-001', 29.99, 'USD', 'paid', '2024-07-01 00:00:00', '2024-08-01 00:00:00', '2024-07-01 00:00:00', '2024-07-01 10:00:00');

-- =============================================
-- USEFUL QUERIES FOR PAYMENT SERVICE
-- =============================================

-- Get complete payment details with items and fraud check
SELECT 
    p.payment_number,
    p.amount,
    p.currency,
    p.status,
    p.completed_at,
    pp.provider_name,
    pm.display_name as payment_method,
    pi.description as item_description,
    pi.total_amount as item_amount,
    fc.risk_score,
    fc.risk_level
FROM payments p
JOIN payment_providers pp ON p.provider_id = pp.provider_id
LEFT JOIN payment_methods pm ON p.payment_method_id = pm.payment_method_id
LEFT JOIN payment_items pi ON p.payment_id = pi.payment_id
LEFT JOIN fraud_checks fc ON p.payment_id = fc.payment_id
WHERE p.payment_id = 1;

-- Get customer payment history with totals
SELECT 
    p.payment_number,
    p.order_id,
    p.amount,
    p.currency,
    p.status,
    p.completed_at,
    pp.provider_name,
    COUNT(pi.payment_item_id) as item_count,
    SUM(CASE WHEN pi.item_type = 'product' THEN pi.total_amount ELSE 0 END) as product_total,
    SUM(CASE WHEN pi.item_type = 'tax' THEN pi.total_amount ELSE 0 END) as tax_total,
    SUM(CASE WHEN pi.item_type = 'shipping' THEN pi.total_amount ELSE 0 END) as shipping_total
FROM payments p
JOIN payment_providers pp ON p.provider_id = pp.provider_id
LEFT JOIN payment_items pi ON p.payment_id = pi.payment_id
WHERE p.customer_id = 1001
GROUP BY p.payment_id
ORDER BY p.created_at DESC;

-- Get refund summary by order
SELECT 
    r.refund_number,
    r.amount,
    r.currency,
    r.refund_type,
    r.reason,
    r.status,
    r.completed_at,
    p.payment_number,
    COUNT(ri.refund_item_id) as refunded_items
FROM refunds r
JOIN payments p ON r.payment_id = p.payment_id
LEFT JOIN refund_items ri ON r.refund_id = ri.refund_id
WHERE r.order_id = 1
GROUP BY r.refund_id;

-- Get payment provider performance metrics
SELECT 
    pp.provider_name,
    COUNT(p.payment_id) as total_payments,
    SUM(p.amount) as total_volume,
    AVG(p.amount) as avg_payment_amount,
    COUNT(CASE WHEN p.status = 'completed' THEN 1 END) as successful_payments,
    COUNT(CASE WHEN p.status = 'failed' THEN 1 END) as failed_payments,
    ROUND(COUNT(CASE WHEN p.status = 'completed' THEN 1 END) * 100.0 / COUNT(p.payment_id), 2) as success_rate,
    SUM(p.fee_amount) as total_fees
FROM payment_providers pp
LEFT JOIN payments p ON pp.provider_id = p.provider_id
WHERE pp.is_active = TRUE
GROUP BY pp.provider_id
ORDER BY total_volume DESC;

-- Get subscription revenue analysis
SELECT 
    s.plan_name,
    COUNT(s.subscription_id) as active_subscriptions,
    SUM(s.amount) as monthly_recurring_revenue,
    AVG(s.amount) as avg_subscription_value,
    COUNT(CASE WHEN s.status = 'active' THEN 1 END) as active_count,
    COUNT(CASE WHEN s.status = 'cancelled' THEN 1 END) as cancelled_count
FROM subscriptions s
GROUP BY s.plan_name
ORDER BY monthly_recurring_revenue DESC;

-- Get fraud analysis by risk level
SELECT 
    fc.risk_level,
    COUNT(fc.fraud_check_id) as total_checks,
    COUNT(CASE WHEN fc.status = 'approved' THEN 1 END) as approved_count,
    COUNT(CASE WHEN fc.status = 'declined' THEN 1 END) as declined_count,
    AVG(fc.risk_score) as avg_risk_score,
    SUM(p.amount) as total_amount_checked
FROM fraud_checks fc
JOIN payments p ON fc.payment_id = p.payment_id
GROUP BY fc.risk_level
ORDER BY avg_risk_score DESC;