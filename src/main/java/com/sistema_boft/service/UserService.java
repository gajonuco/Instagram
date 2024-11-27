package com.sistema_boft.service;


import com.sistema_boft.model.User;
import com.sistema_boft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByInstagramId(String instagramId) {
        return userRepository.findByInstagramId(instagramId);
    }

    public void logoutUser(User user) {
        user.setLoggedOut(true);
        userRepository.save(user);
    }
}

