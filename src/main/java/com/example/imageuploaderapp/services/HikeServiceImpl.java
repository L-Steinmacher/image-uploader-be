package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Hike;
import com.example.imageuploaderapp.models.MinHike;
import com.example.imageuploaderapp.repository.HikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "hikeService")
public class HikeServiceImpl implements HikeService{
    @Autowired
    private HikeRepository hikeRepository;

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

    /**
     * Updats a partial hike in the Hikes Table by hike id.
     * @param hike hike object
     * @param id id of the hike to be updated.
     * @return
     */
    @Override
    public Hike update(Hike hike, long id)
    {
        Hike currHike = hikeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hike with id " + id + " not found!"));

        if (hike.getComments() != null) {
            currHike.setComments(hike.getComments());
        }

        if (hike.getRating() != 0) {
            currHike.setRating(hike.getRating());
        }

        return hikeRepository.save(currHike);
    }

    /**
     * Deletes a hike from Hikes table by id
     * @param id id of the hike
     */
    @Override
    public void delete(long id) {
        Hike delHike = hikeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hike with id " + id + " not found!"));
        hikeRepository.delete(delHike);
    }

    /**
     * Nukes the Hikes table database deleting all rows.
     */
    @Override
    public void deleteAll() {
        hikeRepository.deleteAll();
    }
}
