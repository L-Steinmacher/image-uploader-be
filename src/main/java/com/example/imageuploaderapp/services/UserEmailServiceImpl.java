package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.UserEmail;
import com.example.imageuploaderapp.repository.UserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "useremailservic")
public class UserEmailServiceImpl implements UserEmailService
{
    /**
     * Connects to email Repository
     */
    @Autowired
    private UserEmailRepository userEmailRepository;

    /**
     * Connects to user Services
     */
    @Autowired
    private UserService userService;

    /**
     * Connects to Helper Functions
     */
    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<UserEmail> findAll()
    {
        List<UserEmail> list = new ArrayList<>();
        userEmailRepository.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserEmail findUserEmailById(long id)
    {
        return userEmailRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Email with id " + id + " not found!"));
    }
}
