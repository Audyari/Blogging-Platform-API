package com.Blogging_Platform_API.Blogging_Platform_API.repository;

import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<Post, Long> {
}

// Spring "membelikan" kamu 20+ metode kerja siap pakai secara otomatis:
// save() (Simpan)
// findAll() (Ambil Semua)
// findById() (Cari Satu)
// deleteById() (Hapus)
// count() (Hitung jumlah)
// ...dan banyak lagi.