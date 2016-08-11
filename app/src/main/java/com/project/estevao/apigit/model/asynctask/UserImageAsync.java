package com.project.estevao.apigit.model.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.model.http.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Estevao on 09/08/2016.
 */
public class UserImageAsync extends AsyncTask<User, String, List<User>> {
    Activity context;
    SyncInterface activity;

    public UserImageAsync(Activity context, SyncInterface activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected List<User> doInBackground(User... user) {
        return UserService.getUserImageByWeb(Arrays.asList(user));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        activity.sinchronizeUserAndImage(users);
        super.onPostExecute(users);
    }
}