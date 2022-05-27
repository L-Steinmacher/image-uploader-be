package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.views.AverageRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailRepository extends CrudRepository<Trail, Long> {

    Trail findByTrailname(String trailname);

    List<Trail> findByTrailnameContainingIgnoreCase(String trailname);

    @Query(value = "SELECT t.trailid, avg(h.review)\n" +
            "FROM TRAILS as t\n" +
            "left join hikes as h\n" +
            "ON t.trailid = h.trailid\n" +
            "group by t.trailid;",nativeQuery = true)
    List<AverageRating> findAllAverageRating();
}
