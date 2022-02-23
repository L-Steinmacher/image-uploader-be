package com.example.imageuploaderapp.models;

import javax.persistence.*;

@Entity
@Table(name = "hikes")
public class Hike
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hikename", nullable = false)
    private String hikename;
    @Column(name = "discription")
    private String discription;
    @Column(name = "distance")
    private long distance;
    @Column(name = "rating")
    private Long rating;
    /**
    *Todo
     * one to many one hike to many images
     * many to one user
    */

    public Hike()
    {
    }
}
