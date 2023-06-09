package com.alex.serv.services;

import com.alex.serv.entities.Users;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    public Users saveUser(Users users);
    public String deleteUser(int id);
    public Users findByUserId(int id);

}
