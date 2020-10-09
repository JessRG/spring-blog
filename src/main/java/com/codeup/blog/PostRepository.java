package com.codeup.blog;

import com.codeup.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll(); // select * from posts
    Post getPostById(long id); // select * from posts where id = ?
    Post saveAndFlush(Post post); // update posts set body = ?, title = ? where id = ?
    void delete(Post post);
}
