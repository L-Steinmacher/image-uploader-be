package com.example.imageuploaderapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trails")
public class Trail extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long trailid;

    @Column(nullable = false)
    private String trailname;

    @Column(nullable = false)
    private String traildiscription;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "trail",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "trail",
            allowSetters = true)
    private List<Hike> hikes = new ArrayList<>();

    public Trail() {
    }

    public Trail(String trailname,
                 String traildiscription,
                 double latitude,
                 double longitude) {
        this.trailname = trailname;
        this.traildiscription = traildiscription;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getTrailid() {
        return trailid;
    }

    public void setTrailid(long trailid) {
        this.trailid = trailid;
    }

    public String getTrailname() {
        return trailname;
    }

    public void setTrailname(String trailname) {
        this.trailname = trailname;
    }

    public String getTraildiscription() {
        return traildiscription;
    }

    public void setTraildiscription(String traildiscription) {
        this.traildiscription = traildiscription;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Hike> getHikes() {
        return hikes;
    }

    public void setHikes(List<Hike> hikes) {
        this.hikes = hikes;
    }
}
