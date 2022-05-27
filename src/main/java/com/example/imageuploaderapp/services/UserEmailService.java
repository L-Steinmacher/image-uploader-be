package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.UserEmail;

import java.util.List;

public interface UserEmailService
{
    /**
     * Find all emails
     */
    List<UserEmail> findAll();

    /**
     * Find email my id
     */

    UserEmail findUserEmailById(long id);
}
