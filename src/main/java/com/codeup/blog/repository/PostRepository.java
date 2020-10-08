package com.codeup.blog.repository;

import com.codeup.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll(); // select * from posts
    Post findById(long id); // select * from posts where id = ?
    Post save(Post post);
    void delete(Post p);
}
