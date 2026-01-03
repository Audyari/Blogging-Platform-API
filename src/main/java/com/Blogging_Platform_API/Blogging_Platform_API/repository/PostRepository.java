package com.Blogging_Platform_API.Blogging_Platform_API.repository;

import com.Blogging_Platform_API.Blogging_Platform_API.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // untuk search
    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE %:term% OR " +
            "p.content LIKE %:term% OR " +
            "p.category LIKE %:term%")
    List<Post> findByTitleContainingOrContentContainingOrCategoryContaining(@Param("term") String term);
}

// Spring "membelikan" kamu 20+ metode kerja siap pakai secara otomatis:
// save() (Simpan)
// findAll() (Ambil Semua)
// findById() (Cari Satu)
// deleteById() (Hapus)
// count() (Hitung jumlah)
// ...dan banyak lagi.