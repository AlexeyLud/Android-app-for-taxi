package com.alex.serv.repositories;

import com.alex.serv.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Boolean existsByLogin(String login);
    Boolean existsByPassword(String password);

    public Driver findByLogin(String login);
}
