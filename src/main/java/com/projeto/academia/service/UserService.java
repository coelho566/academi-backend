package com.projeto.academia.service;

import com.projeto.academia.exception.UserAlreadyExistsException;
import com.projeto.academia.exception.UserNotFoundException;
import com.projeto.academia.models.User;
import com.projeto.academia.repository.UserRepository;
import com.projeto.academia.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtils userUtils;

    public void saveUser(User user) {

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {

            throw new UserAlreadyExistsException("User already exists!");

        } else {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);

            userRepository.save(user);
        }
    }

    public void updateUser(Integer id, User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User newUser = userOptional.get();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setDateOfBirth(user.getDateOfBirth());

            String password = encoder.encode(user.getPassword());
            newUser.setPassword(password);
            newUser.setRoles(user.getRoles());

            userRepository.save(newUser);
            log.info("User updated successfully! {}", newUser.getId());

        } else {

            log.error("Error updating user!");
            throw new UserNotFoundException("User not found");
        }
    }

    public User findUserById(Integer id) {

        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public List<User> findAllUser() {

        try {

            List<User> users = new ArrayList<>();
            Iterable<User> all = userRepository.findAll();
            all.forEach(users::add);

            return users;

        } catch (RuntimeException ex) {

            throw new UserNotFoundException(ex.getMessage());
        }
    }

    public void deleteUser(Integer id) {

        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User getUserLogged(){
        Optional<User> user = userRepository.findByEmail(userUtils.getUserNameLogged());
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getNameUser(){

        Optional<User> user = userRepository.findByEmail(userUtils.getUserNameLogged());

        if(user.isPresent()){
            return user.get();

        }else{

            throw new UserNotFoundException("User not found");
        }
    }

}
