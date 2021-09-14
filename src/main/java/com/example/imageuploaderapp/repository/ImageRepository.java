package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.ImageModel;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageModel, Long>
{
}
