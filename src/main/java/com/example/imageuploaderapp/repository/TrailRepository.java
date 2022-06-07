package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.views.AverageRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailRepository extends CrudRepository<Trail, Long>
{

    Trail findByTrailname(String trailname);

    List<Trail> findByTrailnameContainingIgnoreCase(String trailname);

    @Query(value = "SELECT t.trailid as trailid, avg(h.rating) as average " +
            "FROM trails t LEFT JOIN hikes h " +
            "ON t.trailid = h.trailid " +
            "GROUP BY t.trailid",
            nativeQuery = true)
    List<AverageRating> findAllAverageRating();
}
