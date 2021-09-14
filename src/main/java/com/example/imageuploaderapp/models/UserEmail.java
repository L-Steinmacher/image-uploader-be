package com.example.imageuploaderapp.models;

import javax.persistence.*;

@Entity
@Table(name = "useremails")
public class UserEmail
    extends Auditable
{
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long useremailid;

    @Column(nullable = false)
    private String useremail;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    public UserEmail()
    {
    }

    public UserEmail(
        String useremail,
        User user)
    {
        this.useremail = useremail;
        this.user = user;
    }

    public long getUseremainid()
    {
        return useremailid;
    }

    public void setUseremainid(long useremainid)
    {
        this.useremailid = useremainid;
    }

    public String getUseremail()
    {
        return useremail;
    }

    public void setUseremail(String useremail)
    {
        this.useremail = useremail;
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
