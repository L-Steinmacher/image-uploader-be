package com.example.imageuploaderapp;

import com.example.imageuploaderapp.models.Role;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.services.ImageService;
import com.example.imageuploaderapp.services.RoleService;
import com.example.imageuploaderapp.services.UserService;
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
    ImageService imageService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

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
        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        userService.save(u4);

    }
}
