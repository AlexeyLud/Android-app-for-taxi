package com.alex.serv.serviceImpl;

import com.alex.serv.entities.Stops;
import com.alex.serv.entities.Users;
import com.alex.serv.repositories.UserRepository;
import com.alex.serv.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users saveUser(Users users) {
        logger.info("-----[save User in repository]-----");
        users = userRepository.save(users);
        return users;
    }

    @Override
    public String deleteUser(int id){
        logger.info("-----[deleteById User in repository]-----");
        userRepository.deleteById(id);
        return "Ok! success deleted with id = " + id;
    }

    @Override
    public Users findByUserId(int id) {
        logger.info("-----[findByUserId get Stop in repository]-----");
        return userRepository.findById(id).get();
    }
}
