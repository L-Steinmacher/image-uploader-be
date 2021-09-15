package com.example.imageuploaderapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "imagemodels")
public class ImageModel
{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    private String type;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Column(name = "picByte", length = 1000, nullable = false)
    private byte[] picbyte;

    @ManyToOne()
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties(value = "imagemodels",
        allowSetters = true)
    private User user;

    public ImageModel(){
//        super();
    }

    public ImageModel(
        String name,
        String type,
        byte[] picByte,
        User user)
    {
        this.name = name;
        this.type = type;
        this.picbyte = picByte;
        this.user = user;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public byte[] getPicByte()
    {
        return picbyte;
    }

    public void setPicByte(byte[] picByte)
    {
        this.picbyte = picByte;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
