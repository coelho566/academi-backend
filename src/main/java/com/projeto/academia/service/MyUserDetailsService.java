package com.projeto.academia.service;

import com.projeto.academia.models.MainUser;
import com.projeto.academia.models.User;
import com.projeto.academia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){

            User user = userOptional.get();
            return new MainUser(user);

        }else{
            throw new UsernameNotFoundException("User not found 404");
        }
    }
}
