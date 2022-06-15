package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.Hike;
import com.example.imageuploaderapp.models.MinHike;

public interface HikeService {
    Hike findHikeById(long id);

    Hike save(Hike hike);

    Hike update(Hike hike, long id);

    void delete(long id);

    public void deleteAll();
}
