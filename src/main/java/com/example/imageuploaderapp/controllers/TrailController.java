package com.example.imageuploaderapp.controllers;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Hike;
import com.example.imageuploaderapp.models.MinHike;
import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.schemes.WeatherData;
import com.example.imageuploaderapp.services.HikeService;
import com.example.imageuploaderapp.services.ImageService;
import com.example.imageuploaderapp.services.TrailService;
import com.example.imageuploaderapp.services.UserService;
import com.example.imageuploaderapp.views.AverageRating;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/trails")
public class TrailController {

    @Autowired
    private TrailService trailService;

    @Autowired
    private HikeService hikeService;

    @Autowired
    private ImageService imageService;

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
     * www.example.com/trails/trail/{trailId}
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

    /**
     *
     * @param updateTrail
     * @param trailid
     * @return
     */
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

    /**
     * www.example.com/trails/trail/9?userid=4
     * @param updateTrial
     * @param trailid
     * @return
     */
    @ApiOperation("Update one-many fields on trail object")
    @PatchMapping(value = "/trail/{trailid}",
        consumes = "application/json",
        produces = "application/json")
    public ResponseEntity<?> updateTrail(
            @RequestBody Trail updateTrial,
            @PathVariable long trailid,
            @RequestParam long userid)
    {
        Trail updatedTrial = trailService.update(updateTrial, trailid, userid);
        return new ResponseEntity<>(updatedTrial, HttpStatus.OK);
    }

    /**
     *
     * @param trailId
     * @return
     */
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
     * @return
     */
    @ApiOperation("Saves new hike to the database.")
    @PutMapping(value = "/trail/hike",
                consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewHike(
            @RequestPart(name = "hike") MinHike hike,
            @RequestPart(name = "file", required = false) MultipartFile file)
    {
        System.out.println(hike.getUserid());
        Hike newHike = new Hike(hike.getComments(),
                hike.getRating(),
                userService.findUserById(hike.getUserid()),
                trailService.findTrailById(hike.getTrailid()));
        newHike = hikeService.save(newHike);
        if (file != null)
        {
            imageService.uploadImage(newHike.getHikeid(), file);
        }

        return new ResponseEntity<>(newHike,HttpStatus.CREATED);
    }

    /**
     *
     * @param trailid
     * @return
     */
    @ApiOperation("Get all hikes for a given trail.")
    @GetMapping(value = "/trail/{trailid}/hikes",
        produces = "application/json")
    public ResponseEntity<?> getTrailHikes(
            @PathVariable long trailid)
    {
        List<Hike> list = trailService.findTrailById(trailid).getHikes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * www.example.com/trails/trail/hikes/14
     * Updates a hike by id
     * @return
     */
    @ApiOperation("Patch/update hike")
    @PatchMapping(value = "/trail/hikes/{hikeid}",
        consumes = "application/json",
        produces = "application/json")
    public ResponseEntity<?> updateHike(
            @PathVariable long hikeid,
            @RequestBody Hike hike)
    {
        Hike updateHike = hikeService.update(hike, hikeid);
        return new ResponseEntity<>(updateHike, HttpStatus.OK);
    }

    /**
     * www.example.com/trails/trail/hikes/10
     * @param hikeid id of the hike to be deleted
     * @return
     */
    @ApiOperation("Deletes a given hike by id")
    @DeleteMapping(value = "/trail/hikes/{hikeid}")
    public ResponseEntity<?> deleteHike(
            @PathVariable long hikeid)
    {
        hikeService.delete(hikeid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * www.example.com/trails/trails/ratings
     *
     */
    @ApiOperation("Gets all Trail id's as well as the average rating for the trail.")
    @GetMapping(value = "/trails/ratings",
        produces = "application/json")
    public ResponseEntity<?> findAverageRating()
    {
        List<AverageRating> list = trailService.getAllAverages();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * www.example.com/trails/trail/9/weather
     */
    @GetMapping(value = "trail/{trailid}/weather",
        produces = "application/json")
    public ResponseEntity<?> getTrailWeather(
            @PathVariable
            long trailid)
    {
        WeatherData currWeather = trailService.getCurrentWeatherForcast(trailid);

        return new ResponseEntity<>(currWeather, HttpStatus.OK);
    }
}
