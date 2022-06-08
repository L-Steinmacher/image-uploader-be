package com.example.imageuploaderapp.models;

public class MinHike {

    private String comment;

    private long rating;


    public MinHike(String comment, long rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
