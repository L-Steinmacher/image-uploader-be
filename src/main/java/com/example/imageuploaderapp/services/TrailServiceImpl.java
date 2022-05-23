package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.repository.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "trailService")
public class TrailServiceImpl implements TrailService {
    @Autowired
    TrailRepository trailRepository;

    @Override
    public List<Trail> findAll() {
        return null;
    }

    @Override
    public List<Trail> findByNameContaining(String trailname) {
        return null;
    }

    @Override
    public Trail findTrailById(long id) {
        return null;
    }

    @Override
    public Trail findByTrailname(String name) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Trail save(Trail trail) {
        return null;
    }

    @Override
    public Trail update(Trail trail, long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
