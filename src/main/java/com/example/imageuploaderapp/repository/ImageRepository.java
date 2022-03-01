package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long>
{
    @Query(value = "SELECT * " +
        "FROM images i " +
        "LEFT JOIN users u " +
        "ON i.userid = u.id "
        ,
        nativeQuery = true)
    Iterable<Image> findCurrentUserImages(@Param("userid") Long userid);

}
