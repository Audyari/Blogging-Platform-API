# ADR-004: Database Design

## Status

Accepted

## Context

Blogging Platform API memerlukan database relational untuk menyimpan data user, posts, comments, dan relasi antar entitas dengan integritas data yang baik.

## Decision

Kami menggunakan MySQL dengan desain tabel sebagai berikut:

**Catatan**: Database dijalankan menggunakan Docker untuk konsistensi lingkungan development dan production.

### Database Schema

#### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    bio TEXT,
    avatar_url VARCHAR(255),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Docker Database Setup

**MySQL Docker Configuration:**

- **Image**: mysql:8.0
- **Port**: 3306
- **Database**: blogging_platform
- **Root Password**: password
- **User**: bloguser / blogpassword

**Docker Compose Command:**

```bash
docker-compose up -d
```

**Connection URL:**

```
jdbc:mysql://localhost:3306/blogging_platform?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```
