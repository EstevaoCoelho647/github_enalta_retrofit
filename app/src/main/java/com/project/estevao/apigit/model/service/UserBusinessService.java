package com.project.estevao.apigit.model.service;


import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.model.persistence.UserRepository;

import java.util.List;

/**
 * Created by Estevao on 07/08/2016.
 */
public class UserBusinessService {

    public static void save(List<User> users) {
        UserRepository.save(users);
    }

    public static List<User> findAll() {
        return UserRepository.getAll();
    }

    public static void deleteAll(){
        UserRepository.deleteAll();
    }
}
