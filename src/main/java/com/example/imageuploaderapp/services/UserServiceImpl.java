package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.Role;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.models.UserEmail;
import com.example.imageuploaderapp.models.UserRoles;
import com.example.imageuploaderapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implements UserService Interface
 */
@Transactional
@Service(value = "userService")
public class UserServiceImpl
    implements UserService
{
    /**
     * Connects this service to the User table.
     */
    @Autowired
    private UserRepository userrepos;

    /**
     * Connects this service to the Role table
     */
    @Autowired
    private RoleService roleService;

    /**
     * Connects this service to the HelperFunctions
     */
    @Autowired
    private HelperFunctions helperFunctions;

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username)
    {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    /**
     * returns all users
     * @return
     */
    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        userrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    /**
     * deletes a user
     * @param id id The primary key (long) of the user you seek.
     */
    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    /**
     * Nukes your user Table
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll()
    {
        userrepos.deleteAll();
    }

    /**
     * Saves new user and their data.
     */
    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            userrepos.findById(user.getUserid())
                .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
            .toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail());

        newUser.getRoles()
            .clear();
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleService.findRoleById(ur.getRole()
                .getRoleid());
            newUser.getRoles()
                .add(new UserRoles(newUser, addRole));
        }

        newUser.getUseremails()
            .clear();
        for (UserEmail ue : user.getUseremails())
        {
            newUser.getUseremails()
                .add(new UserEmail(ue.getUseremail(), newUser));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(
        User user,
        long id)
    {
        User currentUser = findUserById(id);

        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername().toLowerCase());
            }


            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getPrimaryemail() != null)
            {
                currentUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());
            }

            if (user.getRoles().size() > 0 )
            {
                currentUser.getRoles()
                    .clear();
                for (UserRoles ur : user.getRoles())
                {
                    Role addRole = roleService.findRoleById(ur.getRole()
                        .getRoleid());

                    currentUser.getRoles()
                        .add(new UserRoles(currentUser, addRole));
                }
            }

            if ( user.getUseremails().size() > 0 )
            {
                currentUser.getUseremails()
                    .clear();
                for (UserEmail ue : user.getUseremails())
                {
                    currentUser.getUseremails()
                        .add(new UserEmail(
                            ue.getUseremail(), currentUser));
                }
            }
            return  userrepos.save(currentUser);
        }else
        {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new OAuth2AccessDeniedException();
        }
    }
}