# ADR-003: API Design Standards

## Status

Accepted

## Context

Blogging Platform API memerlukan standar desain API yang konsisten, RESTful, dan mudah digunakan oleh frontend maupun client lainnya.

## Decision

Kami menggunakan pendekatan RESTful API dengan standar sebagai berikut:

### Base URL & Versioning

```
/api/v1/...
```

- Versi API di URL path untuk memudahkan versioning
- Base URL: `http://localhost:8080/api/v1`

### HTTP Methods

- **GET**: Mendapatkan data (read)
- **POST**: Membuat data baru (create)
- **PUT**: Update data lengkap (replace)
- **PATCH**: Update data sebagian (partial update)
- **DELETE**: Menghapus data

### Response Format

Semua response menggunakan JSON dengan format standar:

**Success Response (200, 201):**

```json
{
  "success": true,
  "message": "Success description",
  "data": {...},
  "timestamp": "2024-01-01T00:00:00Z"
}
```

**Error Response (4xx, 5xx):**

```json
{
  "success": false,
  "message": "Error description",
  "errors": [
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ],
  "timestamp": "2024-01-01T00:00:00Z"
}
```

### Endpoints Structure

#### Authentication

```
POST   /api/v1/auth/register      # Register new user
POST   /api/v1/auth/login         # Login user
POST   /api/v1/auth/logout        # Logout user
POST   /api/v1/auth/refresh       # Refresh token
```

#### Users

```
GET    /api/v1/users              # Get all users (admin only)
GET    /api/v1/users/{id}         # Get user by ID
PUT    /api/v1/users/{id}         # Update user
DELETE /api/v1/users/{id}         # Delete user
```

#### Posts

```
GET    /api/v1/posts              # Get all posts (with pagination)
GET    /api/v1/posts/{id}         # Get post by ID
POST   /api/v1/posts              # Create new post
PUT    /api/v1/posts/{id}         # Update post
DELETE /api/v1/posts/{id}         # Delete post
GET    /api/v1/posts/user/{id}    # Get posts by user
GET    /api/v1/posts/search?q=... # Search posts
```

#### Comments

```
GET    /api/v1/posts/{id}/comments # Get comments for post
POST   /api/v1/posts/{id}/comments # Add comment to post
PUT    /api/v1/comments/{id}      # Update comment
DELETE /api/v1/comments/{id}      # Delete comment
```

### Pagination

For list endpoints:

```
GET /api/v1/posts?page=0&size=10&sort=createdAt,desc

Response:
{
  "content": [...],
  "page": 0,
  "size": 10,
  "totalElements": 100,
  "totalPages": 10,
  "last": false
}
```

### Status Codes

- **200 OK**: Request successful
- **201 Created**: Resource created successfully
- **204 No Content**: Request successful, no content to return
- **400 Bad Request**: Invalid request data
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **409 Conflict**: Resource conflict (e.g., duplicate)
- **422 Unprocessable Entity**: Validation errors
- **500 Internal Server Error**: Server error

## Consequences

### Positive

- RESTful design is widely understood and adopted
- Consistent structure across all endpoints
- Easy to cache responses
- Scalable and maintainable
- Good separation of concerns

### Negative

- Over-fetching/under-fetching possible with REST
- Multiple requests needed for complex data requirements

## Alternatives Considered

- **GraphQL**: More flexible but adds complexity
- **gRPC**: Better performance but less human-readable
