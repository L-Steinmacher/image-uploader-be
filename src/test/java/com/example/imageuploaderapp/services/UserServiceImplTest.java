package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.ImageUploaderAppApplicationTesting;
import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Role;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserEmail;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageUploaderAppApplicationTesting.class,
    properties = {"command.line.runner.enabled=false"})
public class UserServiceImplTest
{
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    private List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        System.out.println("Start Setup !!!!");
        userList = new ArrayList<>();

        Role r1 = new Role("Admin");
        Role r2 = new Role("Data");
        Role r3 = new Role("User");

        r1.setRoleid(1);
        r2.setRoleid(2);
        r3.setRoleid(3);

        // Admin Test User
        User u1 = new User("admin test", "password", "admin@test.com", "");
        u1.setId(10);
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1,r2));
        u1.getRoles().add(new UserRoles(u1,r3));

        u1.getUseremails().add(new UserEmail( "admin@test.com", u1));
        u1.getUseremails().get(0).setUseremailid(11);

        u1.getUseremails().add(new UserEmail("test@admin.com", u1));
        u1.getUseremails().get(1).setUseremailid(12);

        userList.add(u1);

        User u2 = new User("Data test", "1234", "data@test.com", "");
        u2.setId(20);
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));

        u2.getUseremails().add(new UserEmail("data@test.com", u2));
        u2.getUseremails().get(0).setUseremailid(21);

        userList.add(u2);

        User u3 = new User("User Test", "UserPassword", "user@test.com", "");
        u3.setId(30);
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
    public void a_findUserById()
    {
        Mockito.when(userRepository.findById(20L))
            .thenReturn(Optional.of(userList.get(1)));

                assertEquals("Data Test".toLowerCase(),
                    userService.findUserById(20).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void aa_notFindUserById()
    {
        Mockito.when(userRepository.findById(300L))
            .thenThrow(ResourceNotFoundException.class);

        assertEquals("User Test".toLowerCase(),
            userService.findUserById(300)
                .getUsername());
    }

    @Test
    public void b_findByNameContaining()
    {
        Mockito.when(userRepository.findByUsernameContainingIgnoreCase("data"))
            .thenReturn(userList);

        assertEquals(3, userService.findByNameContaining("data").size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void bb_findByNameContainingfail()
    {
        Mockito.when(userRepository.findByUsernameContainingIgnoreCase("aksjdf"))
            .thenThrow(ResourceNotFoundException.class);

        assertEquals(0,
            userService.findByNameContaining("aksjdf"));
    }

    @Test
    public void c_findAll()
    {
        Mockito.when(userRepository.findAll())
            .thenReturn(userList);

        assertEquals(3, userService.findAll().size());
    }

    @Test
    public void d_delete()
    {
        Mockito.when(userRepository.findById(3L))
            .thenReturn(Optional.of(userList.get(0)));

        Mockito.doNothing()
            .when(userRepository)
            .deleteById(3L);

        userService.delete(3);
        assertEquals(3, userList.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dd_deleteFail()
    {
        Mockito.when(userRepository.findById(44L))
            .thenReturn(Optional.empty());

        Mockito.doNothing()
            .when(userRepository)
            .deleteById(44L);

        userService.delete(44);
        assertEquals(3, userList.size());
    }

    @Test
    public void e_findByName()
    {
        Mockito.when(userRepository.findByUsername("User Test"))
            .thenReturn(userList.get(0));

        assertEquals("User Test",
            userService.findByName("User Test")
            .getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ee_findByNamefail()
    {
        Mockito.when(userRepository.findByUsername("Testing tester"))
            .thenThrow(ResourceNotFoundException.class);

        assertEquals("Testing tester",
            userService.findByName("Stumps")
            .getUsername());
    }

    @Test
    public void f_save()
    {
        User newUser = new User(
                    "Test New Name",
                    "newTest",
                    "new@test.com",
            "");
        Role r3 = new Role("User");

        newUser.getRoles().add(new UserRoles(newUser, r3));

        Mockito.when(userRepository.save(any(User.class)))
            .thenReturn(newUser);

        Mockito.when(roleService.findRoleById(10L))
            .thenReturn(r3);

        User addUser = userService.save(newUser);
        assertNotNull(addUser);
        assertEquals(newUser.getUsername().toLowerCase(),
            addUser.getUsername().toLowerCase());
    }

    @Test
    public void g_update()
    {
    }
}