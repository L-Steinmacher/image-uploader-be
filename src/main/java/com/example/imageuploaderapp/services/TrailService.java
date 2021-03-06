package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.schemes.WeatherData;
import com.example.imageuploaderapp.views.AverageRating;

import java.util.List;

public interface TrailService {
    List<Trail> findAll();

    List<Trail> findByNameContaining(String trailname);

    Trail findTrailById(long id);

    Trail findByTrailname(String name);

    void delete(long id);

    Trail save(Trail trail);

    Trail update(
            Trail trail,
            long trailid,
            long userid);

    void deleteAll();

    List<AverageRating> getAllAverages();

    WeatherData getCurrentWeatherForcast(long trailid);

}
