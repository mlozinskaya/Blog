package com.example.Demo.utils;

import com.example.Demo.models.Role;
import com.example.Demo.models.User;
import com.example.Demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class DbInitializer {
    private UserRepository userRepository;

    private static final String ANON_USER_NAME = "Аноним";
    private static final String ANON_USER_LOGIN = "anon";
    private static final String ANON_USER_PASSWORD = "anon";


    @Autowired
    public DbInitializer(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getAnonUser(){
        User anonUser = userRepository.findByLogin(ANON_USER_LOGIN);

        if (anonUser == null) {
            anonUser = new User();
            anonUser.setActive(true);
            anonUser.setRoles(new HashSet<>(){{add(Role.GUEST);}});
            anonUser.setUsername(ANON_USER_NAME);
            anonUser.setLogin(ANON_USER_LOGIN);
            anonUser.setPassword(ANON_USER_PASSWORD);
            userRepository.save(anonUser);
        }

        return anonUser;
    }
}
