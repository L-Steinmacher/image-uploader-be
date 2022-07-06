package com.example.imageuploaderapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image
{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path", nullable = false)
    private String link;


    @ManyToOne()
    @JoinColumn(name = "hikeid", nullable = false)
    @JsonIgnoreProperties(value = "images",
    allowSetters = true)
    private Hike hike;

    public Image(){}

    public Image(
        String name,
        String link,
        Hike hike)
    {
        this.name = name;
        this.link = link;
        this.hike = hike;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String path)
    {
        this.link = path;
    }


    public Hike getHike() { return hike; }

    public void setHike(Hike hike) { this.hike = hike; }
}
