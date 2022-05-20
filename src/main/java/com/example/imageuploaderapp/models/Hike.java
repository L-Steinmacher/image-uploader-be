package com.example.imageuploaderapp.models;

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

    @Column(name = "hikedate", nullable = false)
    private Date hikedate;

    @Column(name = "comment")
    private String comments;

    @Column(name = "review")
    private Long rating;


    private List<Image> images = new ArrayList<>();

    private Set<UserHikes> users = new HashSet<>();
    /**
    *Todo
     * one to many one hike to many images
     * many to one user
    */

    @ManyToOne
    @JoinColumn(name = "trail_trailid")
    private Trail trail;

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }


}
