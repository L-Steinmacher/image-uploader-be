package com.example.imageuploaderapp.models;

public class MinHike {

    private String comments;

    private long rating;

    private long userid;

    private long trailid;



    public String getComments() {
        return comments;
    }

    public void setComment(String comments) {
        this.comments = comments;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getTrailid() {
        return trailid;
    }

    public void setTrailid(long trailid) {
        this.trailid = trailid;
    }
}
