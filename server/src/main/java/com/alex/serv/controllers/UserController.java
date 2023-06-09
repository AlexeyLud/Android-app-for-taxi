package com.alex.serv.controllers;

import com.alex.serv.entities.Users;
import com.alex.serv.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public String createUser(@RequestParam String start_stop, @RequestParam String end_stop){
        logger.info("-----[CREATE User]-----");
        Users user = new Users(start_stop, end_stop);
        Users u = userService.saveUser(user);
        String id = String.valueOf(user.getId());
        return id;
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteTrip(@PathVariable int id){
        logger.info("-----[DELETE User]-----");
        return userService.deleteUser(id);
    }

}
