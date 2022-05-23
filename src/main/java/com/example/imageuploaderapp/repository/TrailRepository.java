package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.Trail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailRepository extends CrudRepository<Trail, Long> {

    Trail findByTrailname(String trailname);

    List<Trail> findByTrailnameContainingIgnoreCase(String trailname);
}
