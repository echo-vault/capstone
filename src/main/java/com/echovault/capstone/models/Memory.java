package com.echovault.capstone.models;

import com.echovault.capstone.Util.TimeUtil;
import jdk.jfr.Unsigned;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "memories")
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private long id;

    @Column(columnDefinition = "Text", nullable = false)
    private String body;

    @Column
    private String image;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

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
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getNumberOfComments(){
        int num = this.getComments().size();
        if(num == 1){
            return num + " comment";
        } else if(num > 1){
            return num + " comments";
        }
        return null;
    };

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCreatedDate(){
        return TimeUtil.formatDate(this.createdAt);
    }
    public String getCreatedTime(){
        return TimeUtil.formatTime(this.createdAt);
    }

    public String getUpdatedDate(){
        return TimeUtil.formatDate(this.updatedAt);
    }
}