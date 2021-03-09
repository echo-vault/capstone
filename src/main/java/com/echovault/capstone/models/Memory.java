package com.echovault.capstone.models;

import jdk.jfr.Unsigned;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "memories")
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private long id;

    @Column(nullable = false)
    private String body;

    @Column
    private String image;

    @Column
    private Date timestamp(nullable = false);

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "echo_id")
    private Echo echo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memory")
    private List<Comment> comments;

    public Memory(){}

    public Memory(String body){
        this.body = body;
        this.timestamp = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Echo getEcho() {
        return echo;
    }

    public void setEcho(Echo echo) {
        this.echo = echo;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}