package com.project.estevao.apigit.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.estevao.apigit.R;
import com.project.estevao.apigit.model.asynctask.SyncInterface;
import com.project.estevao.apigit.model.asynctask.UserImageAsync;
import com.project.estevao.apigit.model.entity.Repository;
import com.project.estevao.apigit.model.entity.User;
import com.project.estevao.apigit.model.http.RepositoryService;
import com.project.estevao.apigit.model.http.UserService;
import com.project.estevao.apigit.model.persistence.DatabaseHelper;
import com.project.estevao.apigit.model.persistence.UserRepository;
import com.project.estevao.apigit.model.service.RepositoryBusinessService;
import com.project.estevao.apigit.model.service.UserBusinessService;
import com.project.estevao.apigit.util.InternetConnectionUtil;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements SyncInterface {

    private LinearLayout buttonUpdate;
    private LinearLayout buttonEnter;
    private ProgressBar progress;
    private TextView txtView;
    private ImageView imgViewLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        bindLoadItens();
        bindOptions();
    }

    private void bindLoadItens() {
        imgViewLogo = (ImageView) findViewById(R.id.git_logo);
        Glide.with(SplashActivity.this).load(R.drawable.git_logo).asBitmap().into(imgViewLogo);

        progress = (ProgressBar) findViewById(R.id.progress);
        txtView = (TextView) findViewById(R.id.text);
    }

    private void bindOptions() {
        buttonEnter = (LinearLayout) findViewById(R.id.button_enter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserRepository.getAll() == null || UserRepository.getAll().size() > 0)
                    startListUserActivity();
                else
                    Toast.makeText(SplashActivity.this, "Database empty, please update.", Toast.LENGTH_SHORT).show();
            }
        });
        buttonUpdate = (LinearLayout) findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUserRequest();
            }
        });

    }

    private void initUserRequest() {
        if (InternetConnectionUtil.isConnected(SplashActivity.this)) {
            setItemsVisibility(View.VISIBLE);
            UserBusinessService.deleteAll();
            RepositoryBusinessService.deleteAll();
            UserService.getUsersByWeb(this);
        } else
            showRequestError();
    }

    private void setItemsVisibility(int state) {
        if (state == View.VISIBLE) {
            buttonEnter.setVisibility(View.GONE);
            buttonUpdate.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.VISIBLE);
        } else {
            buttonEnter.setVisibility(View.VISIBLE);
            buttonUpdate.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            txtView.setVisibility(View.GONE);
        }
    }


    private void startListUserActivity() {
        Intent intent = new Intent(SplashActivity.this, UserListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserSuccess(List<User> users) {
        if (users != null) {
            initUserImageRequest(users);
        } else showRequestError();
    }

    @Override
    public void sinchronizeUserAndImage(List<User> users) {
        UserBusinessService.save(users);
        initRepositoryRequest();
    }

    @Override
    public void onSuccess() {
        startListUserActivity();

    }

    private void initUserImageRequest(List<User> users) {
        UserImageAsync userImageAsync = new UserImageAsync(this, this);
        userImageAsync.execute(users.toArray(new User[users.size()]));
    }

    private void initRepositoryRequest() {
        List<User> users = UserRepository.getAll();
        if (users != null && users.size() > 0) {
            RepositoryService.getRepositoryByWeb(users, this);
        }
    }

    @Override
    public void onError(Throwable t) {
        showRequestError();
        t.printStackTrace();
    }

    private void showRequestError() {
        setItemsVisibility(View.GONE);
        Toast.makeText(SplashActivity.this, "Internet error", Toast.LENGTH_SHORT).show();
    }
}










