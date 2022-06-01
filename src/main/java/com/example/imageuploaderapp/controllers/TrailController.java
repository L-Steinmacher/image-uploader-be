package com.example.imageuploaderapp.controllers;

import com.example.imageuploaderapp.models.Hike;
import com.example.imageuploaderapp.models.MinHike;
import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.services.HikeService;
import com.example.imageuploaderapp.services.TrailService;
import com.example.imageuploaderapp.services.UserService;
import com.example.imageuploaderapp.views.AverageRating;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/trails")
public class TrailController {

    @Autowired
    public TrailService trailService;

    @Autowired
    private HikeService hikeService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "list all trails")
    @GetMapping(value = "/trails",
        produces = "application/json")
    public ResponseEntity<?> listAllTrails()
    {
        List<Trail> myTrails = trailService.findAll();
        return new ResponseEntity<>(myTrails, HttpStatus.OK);
    }

    @ApiOperation("get trail by id")
    @GetMapping(value = "/trail/{trailid}",
        produces = "application/json")
    public ResponseEntity<?> getTrailById(
            @PathVariable Long trailid)
    {
        Trail trail = trailService.findTrailById(trailid);
        return new ResponseEntity<>(trail, HttpStatus.OK);
    }

    /**
     * www.examole.com/trails/trail/{trailId}
     * @param newTrail
     * @return
     * @throws URISyntaxException
     */
    @ApiOperation("Post new trail")
    @PostMapping(value = "/trail",
            consumes = "application/json")
    public ResponseEntity<?> createNewTrail(
            @Valid
            @RequestBody
            Trail newTrail) throws URISyntaxException
    {
        newTrail.setTrailid(0);
        newTrail = trailService.save(newTrail);

        HttpHeaders responseHeader = new HttpHeaders();
        URI newTrailURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{trailid}")
                .buildAndExpand(newTrail.getTrailid())
                .toUri();
        responseHeader.setLocation(newTrailURI);

        return new ResponseEntity<>(null,
                responseHeader,
                HttpStatus.CREATED);
    }

    @ApiOperation("Update Entire Trail Object")
    @PutMapping(value = "/trail/{trailid}",
        consumes = "application/json")
    public ResponseEntity<?> updateEntireTrail(
            @Valid
            @RequestBody Trail updateTrail,
            @PathVariable long trailid)
    {
        updateTrail.setTrailid(trailid);
        trailService.save(updateTrail);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Update one-many fields on trail object")
    @PatchMapping(value = "/trail/{trailid}",
        consumes = "applicaioin/json")
    public ResponseEntity<?> updateTrail(
            @RequestBody Trail updateTrial,
            @PathVariable long trailid)
    {
        trailService.update(updateTrial, trailid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Delete trail by id")
    @DeleteMapping(value = "/trail/{trailId}")
    public ResponseEntity<?> deleteTrailById(
            @PathVariable long trailId)
    {
        trailService.delete(trailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param minHike
     * @param trailId
     * @param id
     * @return
     */
    @PostMapping(value = "/trail/{trailId}/",
            consumes = "application/json")
    public ResponseEntity<?> createNewHike(
            @RequestBody MinHike minHike,
            @PathVariable long trailId,
            @RequestParam long id)
    {
        Hike newHike = new Hike();
        newHike.setComments(minHike.getComment());
        newHike.setRating(minHike.getRating());
        newHike.setUser(userService.findUserById(id));
        newHike.setTrail(trailService.findTrailById(trailId));

        hikeService.save(newHike);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/trail/{trailid}/hikes",
        produces = "application/json")
    public ResponseEntity<?> getTrailHikes(
            @PathVariable long trailid
    )
    {
        List<Hike> list = trailService.findTrailById(trailid).getHikes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    /**
     * www.example.com/trails/trails/ratings
     *
     */
    @GetMapping(value = "/trails/ratings",
        produces = "application/json")
    public ResponseEntity<?> findAverageRating()
    {
        List<AverageRating> list = trailService.getAllAverages();
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
