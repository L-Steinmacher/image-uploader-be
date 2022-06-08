package com.example.imageuploaderapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name = "users")
public class User extends Auditable
{
    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @Column(nullable = false)
    private String username;

    /**
     * The password (String) for this user. Cannot be null. Never get displayed
     */
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Primary email account of user. Could be used as the userid. Cannot be null and must be unique.
     */
    @Column(nullable = false)
    private String primaryemail;

    /**
     * A list of emails for this user
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<UserEmail> useremails = new ArrayList<>();

    /**
     * Part of the join relationship between user and role
     * connects users to the user role combination
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    /**
     * Part of the join relationship between user and ImageModel
     */
//    @OneToMany(mappedBy = "user",
//        cascade =  CascadeType.ALL,
//        orphanRemoval = true)
//    @JsonIgnoreProperties(value = "user",
//        allowSetters = true)
//    private Set<Image> imagetables = new HashSet<>();

    @OneToMany(mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
    allowSetters = true)
    private List<Hike> hikes = new ArrayList<>();

    public User()
    {
    }

    public User(
        String username,
        String password,
        String primaryemail
    )
    {
        setUsername(username);
        setPassword(password);
        this.primaryemail = primaryemail;
    }

    /**
     * Getter for userid
     *
     * @return the userid (long) of the user
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter for userid. Used primary for seeding data
     *
     * @param userid the new userid (long) of the user
     */
    public void setId(long userid)
    {
        this.id = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

    public String getPassword()
    {
        return password;
    }

    /**
     * Setter for password to be used internally, after the password has already been encrypted
     *
     * @param password the new password (String) for the user. Comes in encrypted and stays that way
     */
    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    /**
     * @param password the new password (String) for this user. Comes in plain text and goes out encrypted
     */
    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public List<UserEmail> getUseremails()
    {
        return useremails;
    }

    public void setUseremails(List<UserEmail> useremails)
    {
        this.useremails = useremails;
    }

    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }

    public String getPrimaryemail()
    {
        return primaryemail;
    }

    public void setPrimaryemail(String primaryemail)
    {
        this.primaryemail = primaryemail;
    }

//    public Set<Image> getImagetables()
//    {
//        return imagetables;
//    }
//
//    public void setImagetables(Set<Image> images)
//    {
//        this.imagetables = images;
//    }

    public List<Hike> getHikes() {
        return hikes;
    }

    public void setHikes(List<Hike> hikes) {
        this.hikes = hikes;
    }

    /**
     * Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
     * Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
     *
     * @return The list of authorities, roles, this user object has
     */
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles)
        {
            String myRole = "ROLE_" + r.getRole()
                .getName()
                .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
