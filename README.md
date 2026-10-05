# microservic# Multi-Tenant E-Commerce Microservices Assessment

## Overview
A production-ready, multi-tenant e-commerce backend built with Java 21, Spring Boot 3, and PostgreSQL, consisting of an `order-service` and a `notification-service`.

## Architecture & Design
* **System Design**: Two independent microservices communicating synchronously via REST (`RestClient`). Orders trigger automated notification events over HTTP.
* **Multi-Tenancy Design**: Shared database with shared schema using row-level isolation via a mandatory `tenant_id` column.
* **Tenant Context Propagation**: Inbound HTTP requests require an `X-Tenant-ID` header. A Spring `HandlerInterceptor` intercepts requests, extracts the tenant, stores it in request-scoped `ThreadLocal` (`TenantContext`), and clears it post-completion to prevent thread-pool leakage.
* **Resilience & Failure Handling**: Synchronous service calls are wrapped to handle partial failures cleanly. (Add Resilience4j circuit breakers/retries here if desired).

## Assumptions
* Tenants are identified by unique string identifiers passed via HTTP headers.
* Notifications do not require third-party integrations (SendGrid/Twilio) and are simulated via structured audit logs.

## Running the System
1. Clone the repository.
2. Run the full system via Docker Compose:
   ```bash
   docker compose up --buildes-assessment