package com.example.imageuploaderapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hikes")
public class Hike
{
    @Id
    @Column(name = "hikeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hikeid;

//    @Column(name = "hikedate", nullable = false)
//    private Date hikedate;

    @Column(name = "comment")
    private String comments;

    @Column(name = "review")
    private Long rating;

//    @OneToMany(mappedBy = "hike",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnoreProperties(value = "hike",
//        allowSetters = true)
//    private List<Image> images = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trailid")
    private Trail trail;

    public Hike() {
    }

    public Hike(String comments,
                Long rating,
                User user,
                Trail trail) {
        this.comments = comments;
        this.rating = rating;
        this.user = user;
        this.trail = trail;
    }

    public Long getHikeid() {
        return hikeid;
    }

    public void setHikeid(Long hikeid) {
        this.hikeid = hikeid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }
}
