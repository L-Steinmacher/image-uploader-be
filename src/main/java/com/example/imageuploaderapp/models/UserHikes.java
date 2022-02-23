package com.example.imageuploaderapp.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "userhikes")
public class UserHikes
    extends Auditable
    implements Serializable
{

    public UserHikes()
    {
    }
}
