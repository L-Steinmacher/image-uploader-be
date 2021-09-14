package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long>
{
    Optional<ImageModel> findByName(String name);
}
