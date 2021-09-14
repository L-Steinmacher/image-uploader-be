package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.repository.RoleRepository;
import com.example.imageuploaderapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements the RoleService Interface
 */
@Transactional
@Service(value = "roleService")
public class RoleServiceImpl
    implements RoleService
{
    /**
     * Connects this service to the Role Model
     */
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
}
