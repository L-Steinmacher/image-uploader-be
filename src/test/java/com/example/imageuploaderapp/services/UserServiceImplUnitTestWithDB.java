package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.ImageUploaderAppApplicationTesting;
import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Role;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.repository.UserRepository;
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
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ImageUploaderAppApplicationTesting.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplUnitTestWithDB
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private HelperFunctions helperFunctions;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        List<User> myUsers = userService.findAll();

        List<Role> roleList = roleService.findAll();

        for (User u : myUsers) {
            System.out.println("Username: " + u.getUsername() + ", Primaryemail: " + u.getPrimaryemail() + ", id: " + u.getId());
        }

        for (Role r : roleList) {
            System.out.println("RoleName: " + r.getName() + ", Role id: " + r.getRoleid());
        }
    }

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void a_findUserById()
    {
        assertEquals("admin",
                userService.findUserById(4).getUsername());
        assertEquals("admin@fake.com",
                userService.findUserById(4).getPrimaryemail());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void aa_notFindUserById()
    {
        assertEquals("User Test".toLowerCase(),
            userService.findUserById(300)
                .getUsername());
    }

    @Test
    public void b_findByNameContaining()
    {
        assertEquals(1, userService.findByNameContaining("indy").size());
    }

    @Test
    public void bb_findByNameContainingFail()
    {
        assertEquals(0,
            userService.findByNameContaining("aksjdf").size());
    }

    @Test
    public void c_findAll()
    {
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void d_delete()
    {
        userService.delete(4);
        assertEquals(3, userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dd_deleteFail()
    {
        userService.delete(44);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void e_findByName()
    {
        assertEquals("indy",
            userService.findByName("indy")
            .getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ee_findByNamefail()
    {
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
                    "new@test.com");
        Role r3 = new Role("User");
        r3.setRoleid(2);

        newUser.getRoles().add(new UserRoles(newUser, r3));

        User addUser = userService.save(newUser);

        assertNotNull(addUser);
        assertEquals(newUser.getUsername().toLowerCase(),
            addUser.getUsername().toLowerCase());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ff_saveFail()
    {
        String uName = "Test New Name";
        User newUser = new User(
                uName,
                "newTest",
                "new@test.com");
        Role r3 = new Role("User");
        r3.setRoleid(2);

        newUser.setId(555);

        User addUser = userService.save(newUser);

        assertNotNull(addUser);
        assertEquals(uName, addUser.getUsername());
    }

    @Test
    public void g_update()
    {
        User currUser = userService.findUserById(6);

        assertNotNull(currUser);
        assertEquals("sassy", currUser.getUsername());

        String uName = "Test New Name";
        User newUser = new User(
                uName,
                "newTest",
                "new@test.com");
        Role r3 = new Role("User");
        r3.setRoleid(2);

        Mockito.when(helperFunctions.isAuthorizedToMakeChange(currUser.getUsername()))
                        .thenReturn(true);

        userService.update(newUser, 6);

        assertEquals(uName.toLowerCase(), userService.findUserById(6).getUsername());
    }

    @Test(expected = OAuth2AccessDeniedException.class)
    public void gg_updateAutherizationFailed()
    {

    }
}