# ADR-002: Project Structure

## Status

Accepted

## Context

Proyek Blogging Platform API memerlukan struktur direktori yang terorganisir dengan baik untuk memudahkan maintenance, scalability, dan collaboration antar developer.

## Decision

Kami menggunakan struktur proyek berdasarkan konvention dari Spring Boot dengan package per feature:

```
src/
├── main/
│   ├── java/
│   │   └── com/Blogging_Platform_API/Blogging_Platform_API/
│   │       ├── BloggingPlatformApiApplication.java    # Main application class
│   │       ├── controller/                            # REST Controllers
│   │       ├── service/                               # Business logic layer
│   │       ├── repository/                            # Data access layer
│   │       ├── model/                                 # Entity classes
│   │       ├── dto/                                   # Data Transfer Objects
│   │       ├── exception/                             # Custom exceptions
│   │       └── config/                                # Configuration classes
│   └── resources/
│       ├── application.yaml                           # Main configuration
│       ├── application-dev.yaml                       # Development profile
│       ├── application-prod.yaml                      # Production profile
│       └── static/                                    # Static resources
├── test/
│   ├── java/                                          # Unit & integration tests
│   └── resources/                                     # Test configuration
└── doc/
    └── ADR/                                           # Architecture Decision Records
```

### Package Organization

- **controller**: Menangani HTTP requests dan responses
- **service**: Berisi business logic dan use cases
- **repository**: Layer data access dengan Spring Data JPA
- **model**: Entity classes yang mapping ke database tables
- **dto**: Data Transfer Objects untuk request/response
- **exception**: Custom exception handling
- **config**: Security, CORS, dan konfigurasi lainnya

## Consequences

### Positive

- Struktur yang jelas memudahkan navigasi kode
- Memisahkan concern (separation of concerns)
- Mudah untuk testing (mock dependencies)
- Scalable untuk fitur-fitur baru
- Memudahkan code review

### Negative

- Memerlukan lebih banyak file dan boilerplate di awal
- Developer baru perlu waktu untuk memahami struktur

## Alternatives Considered

- **Package by layer**: Mengelompokkan berdasarkan tipe (semua controller dalam satu package). Kurang scalable untuk proyek besar.
- **Hexagonal architecture**: Terlalu kompleks untuk MVP.
