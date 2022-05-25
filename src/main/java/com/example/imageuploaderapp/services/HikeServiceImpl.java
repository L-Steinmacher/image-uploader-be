package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Hike;
import com.example.imageuploaderapp.repository.HikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "hikeService")
public class HikeServiceImpl implements HikeService{
    @Autowired
    HikeRepository hikeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TrailService trailService;

    @Override
    public Hike findHikeById(long id) throws ResourceNotFoundException {
        return hikeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hike with id " +id+ " not found!"));
    }


    @Override
    public Hike save(Hike hike) {
        Hike newHike = new Hike();

        if (hike.getHikeid() != 0)
        {
            hikeRepository.findById(hike.getHikeid())
                    .orElseThrow(() -> new ResourceNotFoundException("Hike with id " + hike.getHikeid() + " not found!"));
            newHike.setHikeid(hike.getHikeid());
        }

        newHike.setComments(hike.getComments());
        newHike.setRating(hike.getRating());
        newHike.setUser(userService.findUserById(hike.getUser().getId()));
        newHike.setTrail(trailService.findTrailById(hike.getTrail().getTrailid()));

        return hikeRepository.save(newHike);
    }

    @Override
    public Hike update(Hike hike, long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
