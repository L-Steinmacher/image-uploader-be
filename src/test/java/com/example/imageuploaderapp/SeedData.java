package com.example.imageuploaderapp;

import com.example.imageuploaderapp.models.Role;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserEmail;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.services.RoleService;
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
    matchIfMissing = true
)
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

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

        userService.deleteAll();
        roleService.deleteAll();
        Role r1 = new Role("Testadmin");
        Role r2 = new Role("Testdata");
        Role r3 = new Role("Testuser");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // Admin Test User
        User u1 = new User("Admin Test", "passwordTest", "admin@test.com", "");
        u1.setId(10);
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1,r2));
        u1.getRoles().add(new UserRoles(u1,r3));

        u1.getUseremails().add(new UserEmail( "admin@test.com", u1));
        u1.getUseremails().get(0).setUseremailid(11);

        u1.getUseremails().add(new UserEmail("test@admin.com", u1));
        u1.getUseremails().get(1).setUseremailid(12);

        userService.save(u1);

        User u2 = new User("Data Test", "dataPasswordTest", "data@test.com", "");
        u2.setId(20);
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));

        u2.getUseremails().add(new UserEmail("data@test.com", u2));
        u2.getUseremails().get(0).setUseremailid(21);

        userService.save(u2);

        User u3 = new User("User Test", "UserPasswordTest", "user@test.com", "");
        u3.setId(30);
        u3.getRoles().add(new UserRoles(u3, r3));

        u3.getUseremails().add(new UserEmail("user@test.com", u3));
        u3.getUseremails().get(0).setUseremailid(31);

        userService.save(u3);

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
                        .emailAddress(),
                    "");
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
