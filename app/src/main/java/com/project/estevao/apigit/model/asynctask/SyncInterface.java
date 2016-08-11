package com.project.estevao.apigit.model.asynctask;

import com.project.estevao.apigit.model.entity.Repository;
import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.model.service.UserBusinessService;

import java.util.List;

/**
 * Created by estevao on 09/08/16.
 */
public interface SyncInterface {

    void onUserSuccess(List<User> users);
    void sinchronizeUserAndImage(List<User> users);
    void onSuccess();
    void onError(Throwable t);

}
