package com.project.estevao.apigit.model.service;

import android.util.Log;

import com.project.estevao.apigit.model.entity.Repository;
import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.model.persistence.RepositoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Estevao on 08/08/2016.
 */
public class RepositoryBusinessService {
    public static void save(List<Repository> repositories) {
        RepositoryRepository.save(repositories);
    }

    public static ArrayList<Repository> findAllByIdUser(User user) {
        return RepositoryRepository.getByIdUser(user.getIdWeb());
    }

    public static void deleteAll(){
        RepositoryRepository.deleteAll();
    }

}
