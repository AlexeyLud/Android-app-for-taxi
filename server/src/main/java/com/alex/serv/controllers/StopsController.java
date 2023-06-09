package com.alex.serv.controllers;

import com.alex.serv.entities.Stops;
import com.alex.serv.services.StopsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stops")
public class StopsController {

    private static final Logger logger = LoggerFactory.getLogger(StopsController.class);
    @Autowired
    private StopsService stopsService;
    public List<Stops> stops = new ArrayList<>();
    public List<Stops> stopsByRoute = new ArrayList<>();

    @GetMapping("/get-all")
    public String getAllStops(){
        logger.info("-----[GET ALL Stops]-----");
        stops = stopsService.getStops();
        String s = "";
        for (Stops stop : stops) {
            s += stop.getInfo() + "\n";
        }
        return s;
    }

    @GetMapping("/get-all-stops-by-route/{number}")
    public String getAllStopsByRoute(@PathVariable int number){
        logger.info("-----[GET ALL Stops By Route]-----");
        stopsByRoute = stopsService.getStopsByRoute(number);
        String s = "";
        for (Stops stop : stopsByRoute) {
            s += stop.getName() + "\n";
        }
        return s;
    }

    @GetMapping("/get-stop/{id}")
    public String getOneStop(@PathVariable int id){
        logger.info("-----[GET ONE Stop]-----");
        Stops stop = stopsService.findByStopId(id);
        return stop.getInfo();
    }

}
