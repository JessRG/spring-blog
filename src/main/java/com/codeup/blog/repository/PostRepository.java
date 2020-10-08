package com.codeup.blog.repository;

import com.codeup.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(long id); // select * from posts where id = ?
    List<Post> findAll();
    Long save(Post p);
    void delete(Post p);

    // The following method is equivalent to the built in 'getOne' method
//    @Query("FROM Post p WHERE p.id LIKE ?1")
//    Post getPostById(long id);

//    // The following method shows you how to use named parameters in a HQL custom query
//    @Query("FROM Post p WHERE p.title LIKE %:term%")
//    List<Post> searchByTitleLike(@Param("term") String term);
}
