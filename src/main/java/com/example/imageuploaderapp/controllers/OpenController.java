package com.example.imageuploaderapp.controllers;

import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserMinimum;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.services.RoleService;
import com.example.imageuploaderapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * The class allows access to endpoints that are open to all users regardless of authentication status.
 * Its most important function is to allow a person to create their own username
 */
@RestController
public class OpenController
{
    /**
     * A method in this controller adds a new user to the application so needs access to User Services to do this.
     */
    @Autowired
    private UserService userService;

    /**
     * A method in this controller adds a new user to the application with the role User so needs access to Role Services to do this.
     */
    @Autowired
    private RoleService roleService;

    /**
     * This endpoint always anyone to create an account with the default role of USER. That role is hardcoded in this method.
     *
     * @param httpServletRequest the request that comes in for creating the new user
     * @param newminuser         A special minimum set of data that is needed to create a new user
     * @return The token access and other relevent data to token access. Status of CREATED. The location header to look up the new user.
     * @throws URISyntaxException we create some URIs during this method. If anything goes wrong with that creation, an exception is thrown.
     */
    @PostMapping(value = "/createnewuser",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> addSelf(
        HttpServletRequest httpServletRequest,
        @Valid
        @RequestBody
            UserMinimum newminuser)
        throws  URISyntaxException
    {
        //  Create new User
        User newuser = new User();

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
        newuser.setPrimaryemail(newminuser.getPrimaryemail());

        //  Add default role for user
        Set<UserRoles> newRoles = new HashSet<>();
        newRoles.add(new UserRoles(newuser,
            roleService.findByName("user")));
        newuser.setRoles(newRoles);

//        newuser.userService.save(newuser);
//
//        // set the location header for the newly created resource
//        // The location comes from a different controller!
//        HttpHeaders httpHeaders = new HttpHeaders();
//        URI newUserURI =
//
//        /**
//         * Todo Finish this after the Services are written.
//         */

    }


}
