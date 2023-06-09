package com.alex.serv.serviceImpl;

import com.alex.serv.entities.Bus;
import com.alex.serv.repositories.BusRepository;
import com.alex.serv.services.BusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("busServiceImpl")
public class BusServiceImpl implements BusService {

    private static final Logger logger = LoggerFactory.getLogger(BusServiceImpl.class);

    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus findByBusId(int id) {
        logger.info("-----[findByBusId get Bus in repository]-----");
        return busRepository.findById(id).get();
    }

}
