package com.alex.serv.services;

import com.alex.serv.entities.Driver;
import org.springframework.stereotype.Component;

@Component
public interface DriverService {
    public Driver findByDriverId(int id);
    public Driver findDriverByLogin(String login);
}
