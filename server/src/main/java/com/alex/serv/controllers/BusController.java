package com.alex.serv.controllers;

import com.alex.serv.entities.Bus;
import com.alex.serv.services.BusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus")
public class BusController {

    private static final Logger logger = LoggerFactory.getLogger(BusController.class);
    @Autowired
    private BusService busService;

    @GetMapping("/get-one/{id}")
    public String getOneBus(@PathVariable int id){
        logger.info("-----[GET ONE Bus]-----");
        Bus bus = busService.findByBusId(id);
        return bus.getBus();
    }

    @GetMapping("/get-count/{id}")
    public int getCountBus(@PathVariable int id){
        logger.info("-----[GET Count Bus]-----");
        Bus bus = busService.findByBusId(id);
        return bus.getCount();
    }
}
