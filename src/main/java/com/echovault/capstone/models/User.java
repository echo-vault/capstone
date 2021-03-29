package com.echovault.capstone.models;

import jdk.jfr.Unsigned;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private long id;

    @Column(nullable = false, unique = true)
    @Email(message = "Please enter a valid email address")
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username can not be blank")
    @Size(min=2, max=30)
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "First name can not be blank")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name can not be blank")
    private String lastName;

    @Column
    private String image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Memory> memories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Echo> echoes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    public User(){}

    public User(User copy){
        id = copy.id;
        username = copy.username;
        email = copy.email;
        password = copy.password;
        firstName = copy.firstName;
        lastName = copy.lastName;
        image = copy.image;
        memories = copy.memories;
        echoes = copy.echoes;
        comments = copy.comments;
    }

    public User(String email, String password, String username, String firstName, String lastName){
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Memory> getMemories() {
        return memories;
    }

    public void setMemories(List<Memory> memories) {
        this.memories = memories;
    }

    public List<Echo> getEchoes() {
        return echoes;
    }

    public void setEchoes(List<Echo> echoes) {
        this.echoes = echoes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
