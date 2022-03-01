package com.example.imageuploaderapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image
{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path", nullable = false)
    private String link;

    @ManyToOne()
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties(value = "images",
        allowSetters = true)
    private User user;

    public Image(){}

    public Image(
        String name,
        String link,
        User user)
    {
        this.name = name;
        this.link = link;
        this.user = user;
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

    public void setLink(String link)
    {
        this.link = link;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Image image = (Image) o;
        return getId().equals(image.getId()) && getName().equals(image.getName()) && getLink().equals(image.getLink()) && getUser().equals(image.getUser());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(),
            getName(),
            getLink(),
            getUser());
    }
}
