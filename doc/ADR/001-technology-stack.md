# ADR-001: Technology Stack Selection

## Status

Accepted

## Context

Proyek Blogging Platform API memerlukan teknologi yang tepat untuk membangun RESTful API yang scalable, maintainable, dan memiliki fitur yang lengkap untuk platform blogging.

## Decision

Kami memutuskan untuk menggunakan teknologi berikut:

### Backend Framework

- **Spring Boot 4.0.1**: Framework utama untuk membangun RESTful API dengan fitur lengkap dan ecosystem yang mature

### Bahasa Pemrograman

- **Java 17**: Versi LTS terbaru yang stabil dan memiliki fitur modern

### Build Tool

- **Maven**: Build tool standar untuk proyek Java/Spring Boot dengan dependency management yang baik

### Database

- **MySQL**: Database relational yang powerful untuk menyimpan data user, posts, comments, dan relasi lainnya
- **Docker**: Untuk menjalankan MySQL database dalam container untuk konsistensi lingkungan development dan production

### ORM

- **Spring Data JPA**: Untuk mempermudah interaksi dengan database dan mengurangi boilerplate code

### Authentication & Authorization

- **Spring Security**: Untuk mengamankan API dengan JWT token-based authentication

### Additional Libraries

- **Lombok**: Mengurangi boilerplate code (getters, setters, constructors)
- **Spring Boot Starter Validation**: Untuk validasi input data
- **Spring Boot Starter Data JPA**: Untuk data access layer

## Consequences

### Positive

- Spring Boot menyediakan convention over configuration yang mempercepat development
- Java 17 memberikan performance yang baik dan fitur modern seperti records, sealed classes
- Maven memiliki integrasi yang baik dengan IDE dan CI/CD tools
- MySQL memiliki fitur yang lengkap untuk relational data modeling dan widely adopted
- Docker menyediakan konsistensi lingkungan antara development, staging, dan production
- Lombok mengurangi kode boilerplate dan meningkatkan readability

### Negative

- Java memiliki memory footprint yang lebih besar dibandingkan beberapa alternatif
- Spring Boot memiliki learning curve yang cukup steep untuk developer baru
- MySQL mungkin kurang powerful dibandingkan PostgreSQL untuk beberapa fitur advanced
- Docker memerlukan additional setup dan learning curve untuk containerization
