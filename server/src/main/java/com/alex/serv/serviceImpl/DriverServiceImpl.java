package com.alex.serv.serviceImpl;

import com.alex.serv.entities.Driver;
import com.alex.serv.repositories.DriverRepository;
import com.alex.serv.services.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("driverServiceImpl")
public class DriverServiceImpl implements DriverService {

    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Driver findByDriverId(int id) {
        logger.info("-----[findByDriverId get Driver in repository]-----");
        return driverRepository.findById(id).get();
    }
    @Override
    public Driver findDriverByLogin(String login){
        logger.info("-----[findDriverByLogin get Driver in repository]-----");
        return driverRepository.findByLogin(login);
    }

}
