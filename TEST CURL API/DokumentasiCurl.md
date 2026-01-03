1. gunakan untuk membuat post baru.

```
curl -X POST http://localhost:8080/api/v2/posts -H "Content-Type: application/json" -d @post_data.json
```

2. gunakan untuk update post.

```
curl -X PUT http://localhost:8080/api/v2/posts/1 -H "Content-Type: application/json" -d @update_post.json
```

3. gunakan untuk delete post.

```
curl -X DELETE http://localhost:8080/api/v2/posts/1
```

4. gunakan untuk search post.

```
curl -X GET http://localhost:8080/api/v2/posts?term=basreng
```
