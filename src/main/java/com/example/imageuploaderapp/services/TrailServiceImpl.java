package com.example.imageuploaderapp.services;

import com.amazonaws.services.lambda.model.transform.AccountUsageJsonUnmarshaller;
import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Hike;
import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.repository.TrailRepository;
import com.example.imageuploaderapp.views.AverageRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "trailService")
public class TrailServiceImpl implements TrailService {
    @Autowired
    TrailRepository trailRepository;

    @Override
    public List<Trail> findAll() {
        List<Trail> trailsList = new ArrayList<>();

        trailRepository.findAll()
                .iterator()
                .forEachRemaining(trailsList::add);

        return trailsList;
    }

    @Override
    public List<Trail> findByNameContaining(String trailname) {
        return trailRepository.findByTrailnameContainingIgnoreCase(trailname.toLowerCase());
    }

    @Override
    public Trail findTrailById(long id) throws ResourceNotFoundException {
        return trailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trail with id " + id +" not found!"));
    }

    @Override
    public Trail findByTrailname(String name) {
        Trail t = trailRepository.findByTrailname(name.toLowerCase());
        if (t == null)
        {
            throw new ResourceNotFoundException("Trail name " + name + " not found!");
        }
        return t;
    }

    @Transactional
    @Override
    public void delete(long id) {
        trailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trail with id " + id +" not found!"));
        trailRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Trail save(Trail trail) {
        Trail newTrail = new Trail();
        if (trail.getTrailid() != 0)
        {
            trailRepository.findById(trail.getTrailid())
                    .orElseThrow(() -> new ResourceNotFoundException("Trail with id " + trail.getTrailid() +" not found!"));
            newTrail.setTrailid(trail.getTrailid());
        }

        newTrail.setTrailname(trail.getTrailname().toLowerCase());
        newTrail.setTraildiscription(trail.getTraildiscription());
        newTrail.setLatitude(trail.getLatitude());
        newTrail.setLongitude(trail.getLongitude());

        newTrail.getHikes().clear();

        for (Hike h : trail.getHikes())
        {
            newTrail.getHikes()
                    .add(new Hike(h.getComments(),
                            h.getRating(),
                            h.getUser(),
                            newTrail));
        }

        return trailRepository.save(newTrail);
    }

    /**
     * Updates the trail object by trail id. May update one or more fields.
     * @param trail partial trail object to update with
     * @param id id of the trail
     * @return
     */
    @Transactional
    @Override
    public Trail update(Trail trail, long id)
    {
        Trail currTrail = trailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trail with id " + id + " not found!"));
        if (trail.getTrailname() != null) {
            currTrail.setTrailname(trail.getTrailname());
        }

        if (trail.getTraildiscription() != null){
            currTrail.setTraildiscription(trail.getTraildiscription());
        }

        if (trail.getLatitude() != 0.0) {
            currTrail.setLatitude(trail.getLatitude());
        }

        if (trail.getLongitude() != 0.0) {
            currTrail.setLongitude(trail.getLongitude());
        }

        return trailRepository.save(currTrail);
    }

    @Transactional
    @Override
    public void deleteAll() {
        trailRepository.deleteAll();
    }

    @Override
    public List<AverageRating> getAllAverages() {
        List<AverageRating> list = trailRepository.findAllAverageRating();
        return list;
    }

}
