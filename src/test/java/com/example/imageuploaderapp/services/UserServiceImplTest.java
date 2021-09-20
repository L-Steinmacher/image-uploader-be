package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.ImageUploaderAppApplicationTesting;
import com.example.imageuploaderapp.models.Role;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserEmail;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageUploaderAppApplicationTesting.class,
    properties = {"command.line.runner.enabled=false"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private HelperFunctions helperFunctions;

    private List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

        Role r1 = new Role("Admin");
        Role r2 = new Role("Data");
        Role r3 = new Role("User");

        r1.setRoleid(1);
        r2.setRoleid(2);
        r3.setRoleid(3);

        // Admin Test User
        User u1 = new User("Admin Test", "password", "admin@test.com");
        u1.setUserid(10);
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1,r2));
        u1.getRoles().add(new UserRoles(u1,r3));

        u1.getUseremails().add(new UserEmail( "admin@test.com", u1));
        u1.getUseremails().get(0).setUseremailid(11);

        u1.getUseremails().add(new UserEmail("test@admin.com", u1));
        u1.getUseremails().get(1).setUseremailid(12);

        userList.add(u1);

        User u2 = new User("Data Test", "dataPassword", "data@test.com");
        u2.setUserid(20);
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));

        u2.getUseremails().add(new UserEmail("data@test.com", u2));
        u2.getUseremails().get(0).setUseremailid(21);

        userList.add(u2);

        User u3 = new User("User Test", "UserPassword", "user@test.com");
        u3.setUserid(30);
        u3.getRoles().add(new UserRoles(u3, r3));

        u3.getUseremails().add(new UserEmail("user@test.com", u3));
        u3.getUseremails().get(0).setUseremailid(31);

        userList.add(u3);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {}

    @Test
    void a_findUserById()
    {
        System.out.println("------>      user list: " + userList);
        Mockito.when(userRepository.findById(20L))
            .thenReturn(Optional.of(userList.get(1)));

        assertEquals("Data Test".toLowerCase(),
            userService.findUserById(20).getUsername());
    }

    @Test
    void b_findByNameContaining()
    {
    }


    @Test
    void c_findAll()
    {
    }

    @Test
    void d_delete()
    {
    }

    @Test
    void e_findByName()
    {
    }

    @Test
    void f_deleteAll()
    {
    }

    @Test
    void g_save()
    {
    }

    @Test
    void h_update()
    {
    }
}