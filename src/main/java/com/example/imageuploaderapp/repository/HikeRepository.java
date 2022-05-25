package com.example.imageuploaderapp.repository;

import com.example.imageuploaderapp.models.Hike;

import org.springframework.data.repository.CrudRepository;

public interface HikeRepository extends CrudRepository<Hike, Long> {
}
