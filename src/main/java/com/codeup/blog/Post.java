package com.codeup.blog;

import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 250)
    private String title;

    @Column
    private String body;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    // Default Constructor
    public Post() {
    }

    // Constructor
    public Post(long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
