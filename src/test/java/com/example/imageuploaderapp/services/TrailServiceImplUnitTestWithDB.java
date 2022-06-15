package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.ImageUploaderAppApplicationTesting;
import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Trail;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.repository.TrailRepository;
import com.example.imageuploaderapp.views.AverageRating;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = ImageUploaderAppApplicationTesting.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrailServiceImplUnitTestWithDB {
    @Autowired
    TrailService trailService;

    @Autowired
    TrailRepository trailRepository;

    @MockBean
    private HelperFunctions helperFunctions;

    String name = "Ravenna Park Trail";

    Trail newTrail = new Trail("test discription",
            "test name 1",
            100.00,
            200.00);

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        List<Trail> trailList = trailService.findAll();

        for (Trail t : trailList)
        {
            System.out.println("TrailId: " +t.getTrailid() + ", TrailName: " + t.getTrailname());

        }
    }

    @After
    public  void tearDown() throws Exception{}

    @Test
    public void a_findAllTrails() {
        List<Trail> list = trailService.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void b_findByNameContaining() {
        assertEquals(1, trailService.findByNameContaining("snake").size());
    }

    @Test
    public void bb_findByNameContainingFail() {
        assertEquals(0, trailService.findByNameContaining("akljsdf").size());
    }

    @Test
    public void c_findById() {
        assertEquals(name.toLowerCase(), trailService.findTrailById(9).getTrailname());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void cc_findByIdFail() {
        assertEquals(name.toLowerCase(),
                trailService.findTrailById(400).getTrailname());
    }

    @Test
    public void d_findByTrailName() {
        assertEquals(9, trailService.findByTrailname(name.toLowerCase()).getTrailid());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dd_findByTrailNameFailed() {
        assertEquals("misspelled",
                trailService.findByTrailname("problem").getTrailname());
    }

    @Test
    public void e_delete() {
        trailService.delete(9);
        assertEquals(1, trailService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ee_deleteFailed() {
        trailService.delete(444);
        assertEquals(2, trailService.findAll().size());
    }

    @Test
    public void f_saveTrail() {
        Trail addTrail = trailService.save(newTrail);

        assertNotNull(addTrail);
        assertEquals(newTrail.getTrailname(), addTrail.getTrailname());
        assertEquals(newTrail.getTraildiscription(), addTrail.getTraildiscription());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ff_saveTrailFail() {
        newTrail.setTrailid(555);
        Trail addTrail = trailService.save(newTrail);

        assertNotNull(addTrail);
    }

    /**
     * TODO check test after HelperFunction.isAuthorizedToMakeChange is on the update method
     */
    @Test
    public void g_updateTrail() {
        String username = "admin";
        long userid = 4;

        Trail currTrail = trailService.findTrailById(9);

        assertEquals(name.toLowerCase(), currTrail.getTrailname());

        Mockito.when(helperFunctions.isAuthorizedToMakeChange(username))
                .thenReturn(true);

        currTrail = trailService.update(newTrail, 9, userid);

        assertEquals(newTrail.getTrailname(), currTrail.getTrailname());
    }

    /**
     * TODO Write this when ready
     */
    @Test()
    public void gg_updateTrailFailIdNotFound() {
    }

    /**
     * TODO Write this when ready
     */
    @Test
    public void ggg_updateTrailFailAutherizationFailed() {
    }

    @Test
    public void h_getAverages() {
        List<AverageRating> list = trailRepository.findAllAverageRating();
        assertEquals(2, list.size());
    }
}
