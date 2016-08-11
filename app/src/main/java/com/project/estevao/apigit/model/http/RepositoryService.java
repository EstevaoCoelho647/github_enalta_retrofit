package com.project.estevao.apigit.model.http;

import android.util.Log;

import com.project.estevao.apigit.model.asynctask.SyncInterface;
import com.project.estevao.apigit.model.entity.Repository;
import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.model.persistence.RepositoryRepository;
import com.project.estevao.apigit.model.service.RepositoryBusinessService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Estevao on 08/08/2016.
 */
public class RepositoryService {

    static int count = 0;

    public static void getRepositoryByWeb(final List<User> users, final SyncInterface syncInterface) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitApiService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitApiService service = retrofit.create(GitApiService.class);
        for (final User user : users) {
            Call<List<Repository>> listCall = service.listRepositories(user.getLogin());


            listCall.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    if (response.body() != null) {
                        for (Repository repository : response.body()) {
                            repository.setIdUser(user.getIdWeb());
                        }
                        RepositoryBusinessService.save(response.body());
                        count++;

                        if (count == users.size())
                            syncInterface.onSuccess();
                    }
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    syncInterface.onError(t);
                }
            });
        }
    }
}
