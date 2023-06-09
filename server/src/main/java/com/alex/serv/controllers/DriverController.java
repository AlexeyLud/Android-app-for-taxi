package com.alex.serv.controllers;

import com.alex.serv.entities.Driver;
import com.alex.serv.repositories.DriverRepository;
import com.alex.serv.services.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);
    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverRepository driverRepository;

    @GetMapping("/get-one/{id}")
    public String getOneDriver(@PathVariable int id){
        logger.info("-----[GET ONE Driver]-----");
        Driver driver = driverService.findByDriverId(id);
        return driver.getInfo();
    }

    @GetMapping("/singin")
    public String singinDriver(@RequestParam String login, @RequestParam String password){
        logger.info("-----[GET ONE Driver]-----");
        if(driverRepository.existsByLogin(login)){
            logger.info("-----[Login found]-----");
        }else {
            logger.info("-----[Login NOT found]-----");
        }

        if(driverRepository.existsByPassword(password)){
            logger.info("-----[Password found]-----");
        }else {
            logger.info("-----[Password NOT found]-----");
        }

        Driver driver = driverService.findDriverByLogin(login);
        return driver.getInfo();
    }

}
