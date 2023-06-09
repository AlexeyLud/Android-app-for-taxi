package com.alex.serv.services;

import com.alex.serv.entities.Bus;
import org.springframework.stereotype.Component;

@Component
public interface BusService {
    public Bus findByBusId(int id);
}
