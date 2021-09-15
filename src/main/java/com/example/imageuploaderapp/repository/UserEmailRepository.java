package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.UserEmail;
import org.springframework.data.repository.CrudRepository;

public interface UserEmailRepository
    extends CrudRepository<UserEmail, Long>
{
}
