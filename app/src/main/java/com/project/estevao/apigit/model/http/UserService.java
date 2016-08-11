package com.project.estevao.apigit.model.http;

import com.bumptech.glide.Glide;
import com.project.estevao.apigit.model.asynctask.SyncInterface;
import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.util.ApplicationUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Estevao on 07/08/2016.
 */
public class UserService {


    public static void getUsersByWeb(final SyncInterface syncInterface) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitApiService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitApiService service = retrofit.create(GitApiService.class);
        Call<List<User>> listCall = service.listUsers();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                syncInterface.onUserSuccess(users);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public static List<User> getUserImageByWeb(List<User> users) {
        for (User user : users) {
            byte[] bytes;
            try {
                bytes = Glide.with(ApplicationUtil.getContext()).
                        load(user.getAvatar()).
                        asBitmap().
                        toBytes().
                        into(100, 100).
                        get();

                user.setAvatarBytes(bytes);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}
