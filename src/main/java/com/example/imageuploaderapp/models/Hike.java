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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long hikeid;

//    @Column(name = "hikedate", nullable = false)
//    private Date hikedate;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "review")
    private Long rating;

//    @OneToMany(mappedBy = "hike",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnoreProperties(value = "hike",
//        allowSetters = true)
//    private List<Image> images = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "hikes", allowSetters = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "trailid", nullable = false)
    @JsonIgnoreProperties(value = "hikes", allowSetters = true)
    private Trail trail;

    public Hike() {
    }

    public Hike(String comments,
                long rating,
                User user,
                Trail trail) {
        this.comments = comments;
        this.rating = rating;
        this.user = user;
        this.trail = trail;
    }

    public long getHikeid() {
        return hikeid;
    }

    public void setHikeid(long hikeid) {
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
