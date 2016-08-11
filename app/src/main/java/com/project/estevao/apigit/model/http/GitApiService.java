package com.project.estevao.apigit.model.http;

import com.project.estevao.apigit.model.entity.Repository;
import com.project.estevao.apigit.model.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by estevao on 09/08/16.
 */
public interface GitApiService {

    String URL = "https://api.github.com/";
    String IMAGE_URL = "https://avatars.githubusercontent.com/u/{id}?v=3";

    //getUsers
    @GET("users?client_id=14892c7d9e130dc3e87c&client_secret=42932fc3eb882c3c540854f2d694561545282394")
    Call<List<User>> listUsers();

    @GET("users/{user}/repos?client_id=14892c7d9e130dc3e87c&client_secret=42932fc3eb882c3c540854f2d694561545282394")
    Call<List<Repository>> listRepositories(@Path("user") String user);
}
