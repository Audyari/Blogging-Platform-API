# Blogging Platform API

Projek API untuk platform blogging yang dibangun dengan Spring Boot. API ini mendukung versi 1 dan versi 2 dengan berbagai fitur manajemen posting blog.

## 🚀 Fitur Utama

- **Versi 1 (V1)**: API dasar untuk manajemen posting blog
- **Versi 2 (V2)**: API dengan logika pemrosesan body_text yang lebih fleksibel
- **Database**: MySQL dengan Docker
- **Testing**: Unit test lengkap
- **Environment**: Multi-profile (dev, MySQL, prod)

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.x
- **Database**: MySQL 8.x
- **Container**: Docker & Docker Compose
- **Build Tool**: Maven Wrapper (mvnw)
- **Java**: JDK 17+

## 📁 Struktur Proyek

```
src/main/java/com/Blogging_Platform_API/Blogging_Platform_API/
├── controller/          # 5. Resepsionis/Pintu Masuk
│   ├── BlogPostController.java
│   ├── PostController.java
│   └── V2PostController.java
├── dto/                 # 3. Input/Output User
│   ├── PostRequest.java
│   ├── PostRequestV2.java
│   └── PostResponse.java
├── entity/              # 1. Representasi Tabel Database
│   └── Post.java
├── repository/          # 2. Ambil/Simpan Data
│   ├── BlogPostRepository.java
│   └── PostRepository.java
└── service/             # 4. Otak Aplikasi (Validasi, Logika Bisnis)
    ├── BlogPostService.java
    └── PostService.java
```

## 📋 Database Schema

### Tabel: `blog_posts`

| Field      | Type         | Null | Key | Default | Extra          |
| ---------- | ------------ | ---- | --- | ------- | -------------- |
| created_at | datetime(6)  | NO   |     | NULL    |                |
| id         | bigint       | NO   | PRI | NULL    | auto_increment |
| updated_at | datetime(6)  | YES  |     | NULL    |                |
| category   | varchar(255) | YES  |     | NULL    |                |
| tags       | text         | YES  |     | NULL    |                |
| title      | varchar(255) | NO   |     | NULL    |                |
| content    | text         | NO   |     | NULL    |                |

## 🚀 Cara Menjalankan Aplikasi

### 1. Jalankan dengan Maven (Development)

```bash
# Jalankan aplikasi Spring Boot
mvnw.cmd spring-boot:run

# Compile proyek
mvnw.cmd clean compile

# Jalankan dengan profile MySQL
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=MySQL
```

### 2. Jalankan dengan Docker

```bash
# Lihat status container
docker ps -a

# Jalankan Docker Compose
docker-compose up -d
```

### 3. Akses Database

```bash
# Akses sebagai root
docker exec -it blogging-platform-mysql mysql -uroot -ppassword

# Akses sebagai user blog
docker exec -it blogging-platform-mysql mysql -ubloguser -pblogpassword
```

### 4. Perintah Database

```sql
USE blogging_platform;
SHOW TABLES;
DESCRIBE blog_posts;
SELECT * FROM blog_posts;
```

## 🧪 Testing

### Jalankan Semua Test

```bash
mvnw.cmd test
```

### Jalankan Test Spesifik

```bash
mvnw.cmd test -Dtest=V2PostControllerTest
```

## 🔄 API Versi 2 (V2) - Update Guide

### Perubahan dari V1 ke V2

**V1 (Lama):**

- Input: `title`, `content`, `category`, `tags`
- Service: Langsung simpan ke database

**V2 (Baru):**

- Input: `title`, `body_text`, `category`, `tags`
- Service: `body_text` → `content` → Database
- Output: Tetap mengembalikan `content` (bukan `body_text`)

### Flow Logika V2

```
DTO V2 (PostRequestV2)
    ↓ [body_text]
Service V2
    ↓ [copy body_text → content]
Repository
    ↓ [content]
Entity (blog_posts table)
```

### Implementasi V2

**1. Entity (Tetap)**

```java
// Tidak berubah - tetap pakai field 'content'
@Entity
@Table(name = "blog_posts")
public class Post {
    private String content;
    // ... other fields
}
```

**2. Repository (Tetap)**

```java
// Tidak berubah - save() biasa
public interface PostRepository extends JpaRepository<Post, Long> {
}
```

**3. Service (Diubah)**

```java
// Logika baru: Terima body_text → Salin ke content → Kirim ke Repository
public PostResponse createPostV2(PostRequestV2 request) {
    Post post = new Post();
    post.setTitle(request.getTitle());
    post.setContent(request.getBody_text()); // Copy body_text → content
    post.setCategory(request.getCategory());
    post.setTags(request.getTags());

    Post saved = repository.save(post);
    return mapToResponse(saved);
}
```

**4. DTO (Baru)**

```java
public class PostRequestV2 {
    private String title;
    private String body_text;  // Field baru
    private String category;
    private String tags;
    // getters & setters
}
```

**5. Controller (Baru)**

```java
@RestController
@RequestMapping("/v2/posts")
public class V2PostController {
    // Menerima PostRequestV2
}
```

## 📚 API Endpoints

### V1 Endpoints

- `POST /posts` - Create post
- `GET /posts` - Get all posts
- `GET /posts/{id}` - Get post by ID
- `PUT /posts/{id}` - Update post
- `DELETE /posts/{id}` - Delete post

### V2 Endpoints

- `POST /v2/posts` - Create post with body_text
- `GET /v2/posts` - Get all posts
- `GET /v2/posts/{id}` - Get post by ID
- `PUT /v2/posts/{id}` - Update post with body_text
- `DELETE /v2/posts/{id}` - Delete post

## 🔧 Konfigurasi Environment

### application.yaml

- Default profile: dev

### application-mysql.yaml

- Database URL: jdbc:mysql://localhost:3306/bloggging_platform
- Username: bloguser
- Password: blogpassword

### application-dev.yaml

- H2 in-memory database (untuk development cepat)

### application-prod.yaml

- Production configuration

## 📖 Referensi

- [Roadmap.sh - Blogging Platform API](https://roadmap.sh/projects/blogging-platform-api)

## 🐛 Troubleshooting

### Database Connection Issues

```bash
# Pastikan Docker container berjalan
docker ps -a

# Cek logs
docker logs blogging-platform-mysql
```

### Port Already in Use

```bash
# Cek port yang digunakan
netstat -ano | findstr :8080

# Kill process
taskkill /PID <PID> /F
```

### Maven Wrapper Issues

```bash
# Berikan executable permission (Linux/Mac)
chmod +x mvnw

# Windows: gunakan mvnw.cmd
```

## 📝 Notes

- API V2 menggunakan logika transformasi data di service layer
- Field `content` di database tetap digunakan untuk semua versi
- V2 hanya mengubah cara input diterima (`body_text` vs `content`)
- Semua test menggunakan V2 untuk validasi fitur terbaru

---

_Last Updated: 2026-01-04_
