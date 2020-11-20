package com.projeto.academia.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {

    public String getUserNameLogged(){

        Object info = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name;

        if (info instanceof UserDetails) {
            name = ((UserDetails)info).getUsername();
        } else {
            name = info.toString();
        }

        return name;
    }

}
