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

    @Column(name = "hikedate", nullable = false)
    private Date hikedate;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trailid")
    private Trail trail;




}
