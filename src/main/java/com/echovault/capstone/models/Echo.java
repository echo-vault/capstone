package com.echovault.capstone.models;

import com.echovault.capstone.Util.TimeUtil;
import jdk.jfr.Unsigned;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="echoes")
public class Echo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "First name can not be blank")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name can not be blank")
    private String lastName;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(nullable = false)
    @NotBlank(message = "Please enter a birth date")
    private String birthDate;

    @Column(nullable = false)
    @NotBlank(message = "Please enter a passing date")
    private String deathDate;

    @Column
    private String restingPlace;

    @Column
    private String profileImage;

    @Column(columnDefinition = "Text")
    private String summary;

    @Column
    private String backgroundImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "echo")
    private List<Link> links;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "echo")
    private List<Memory> memories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "echo")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Echo(){}

    public Echo(String firstName, String lastName, String birthDate, String deathDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getDisplayedDates(){
        return birthDate + " - " + deathDate;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getRestingPlace() {
        return restingPlace;
    }

    public void setRestingPlace(String restingPlace) {
        this.restingPlace = restingPlace;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<Memory> getMemories() {
        return memories;
    }

    public void setMemories(List<Memory> memories) {
        this.memories = memories;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getUpdatedTime(){
        return TimeUtil.formatTime(this.updatedAt);
    }








}

