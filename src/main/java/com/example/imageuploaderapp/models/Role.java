package com.example.imageuploaderapp.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The entity allowing interaction with the roles table.
 */
@Entity
@Table(name = "roles")
public class Role
    extends Auditable
{

}
