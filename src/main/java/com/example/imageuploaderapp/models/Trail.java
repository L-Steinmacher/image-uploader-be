package com.example.imageuploaderapp.models;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "trails")
public class Trail extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long trailid;

    private String trailname;

    private float averagerating;

    private String discription;


}
