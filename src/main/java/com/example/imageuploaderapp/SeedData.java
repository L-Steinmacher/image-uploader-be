package com.example.imageuploaderapp;

import com.example.imageuploaderapp.models.*;
import com.example.imageuploaderapp.repository.ImageRepository;
import com.example.imageuploaderapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TrailService trailService;

    @Autowired
    private HikeService hikeService;

    @Transactional
    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@fake.com");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        u1.getRoles()
            .add(new UserRoles(u1,
                r2));
        u1.getRoles()
            .add(new UserRoles(u1,
                r3));

        // data, user
        User u2 = new User("Indy",
            "Dog",
            "indy_dog@fake.com");
        u2.getRoles()
            .add(new UserRoles(u2,
                r2));
        u2.getRoles()
            .add(new UserRoles(u2,
                r3));

        // data, user
        User u3 = new User("Sassy",
            "iLoveIndy",
            "dogzilla@fake.com");

        u3.getRoles()
            .add(new UserRoles(u3,
                r3));

        User u4 = new User("George",
            "ftCat",
            "blahcat@fake.com");
        u4.getRoles()
            .add(new UserRoles(u4,
                r2));


        Trail t1 = new Trail("Rattlesnake Ledge",
                             "Head out on this 5.3-mile out-and-back trail near North Bend, Washington. Generally considered a moderately challenging route, it takes an average of 2 h 46 min to complete. This is a very popular area for hiking, so you'll likely encounter other people while exploring. The trail is open year-round and is beautiful to visit anytime. Dogs are welcome, but must be on a leash.",
                            47.4458,
                            -121.7950);

        Trail t2 = new Trail("Ravenna Park Trail",
                "go to ravenna",
                47.6727,
                -122.3091);

        u1 = userService.save(u1);
        u2 = userService.save(u2);
        u3 = userService.save(u3);
        u4 = userService.save(u4);

        t1 = trailService.save(t1);
        t2 = trailService.save(t2);

        Hike h1 = new Hike(
                "it was fun!",
                3.0d,
                u2,
                t1);

        Hike h2 = new Hike(
                "sunny and fun!",
                4.0d,
                u1,
                t1);

        Hike h3 = new Hike(
                "booo!",
                1.0d,
                u3,
                t1);

        Hike h4 = new Hike(
                "yay!",
                5.0d,
                u4,
                t1);

        Hike h5 = new Hike(
                "more fun!",
                3.0d,
                u3,
                t2);
        Hike h6 = new Hike(
                "Took a photo!",
                5.0d,
                u1,
                t2);
        h1 = hikeService.save(h1);
        h2 = hikeService.save(h2);
        h3 = hikeService.save(h3);
        h4 = hikeService.save(h4);
        h5 = hikeService.save(h5);
        h6 = hikeService.save(h6);

        Image i1 = new Image(
                "lucas-image-uploader-123/com.example.imageuploaderapp.models.User@f2c939a",
                "whidbey.jpg-d878752c-980e-409e-9f13-9e0af4cb1277",
                h6
        );

        i1 = imageRepository.save(i1);
    }
}
