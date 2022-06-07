package com.example.imageuploaderapp;

import com.example.imageuploaderapp.models.*;
import com.example.imageuploaderapp.services.HikeService;
import com.example.imageuploaderapp.services.RoleService;
import com.example.imageuploaderapp.services.TrailService;
import com.example.imageuploaderapp.services.UserService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;


/**
 * ToDo rewrite seed data ,
 * ToDO get current tests to run with new data
 *
 */

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{

    @Autowired
    RoleService roleService;


    @Autowired
    UserService userService;

    @Autowired
    HikeService hikeService;

    @Autowired
    TrailService trailService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */

    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        userService.deleteAll();
        roleService.deleteAll();

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
                47.434889,
                121.4643);

        Trail t2 = new Trail("Ravenna Park Trail",
                "go to ravenna",
                47.6687,
                122.3037);

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

        h1 = hikeService.save(h1);
        h2 = hikeService.save(h2);
        h3 = hikeService.save(h3);
        h4 = hikeService.save(h4);
        h5 = hikeService.save(h5);


        if (false)
        {
            // using JavaFaker create a bunch of regular users
            // https://www.baeldung.com/java-faker
            // https://www.baeldung.com/regular-expressions-java

            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User(nameFaker.name()
                    .username(),
                    "password",
                    nameFaker.internet()
                        .emailAddress());
                fakeUser.getRoles()
                    .add(new UserRoles(fakeUser,
                        r3));
                fakeUser.getUseremails()
                    .add(new UserEmail(
                        fakeValuesService.bothify("????##@gmail.com"),
                            fakeUser));
                userService.save(fakeUser);
            }
        }
    }
}
