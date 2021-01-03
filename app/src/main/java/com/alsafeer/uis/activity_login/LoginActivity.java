package com.alsafeer.uis.activity_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alsafeer.R;
import com.alsafeer.databinding.ActivityLoginBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.LoginModel;
import com.alsafeer.uis.activity_home.HomeActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginModel loginModel;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initView();
    }

    private void initView() {
        loginModel = new LoginModel();
        binding.setModel(loginModel);
        binding.btnLogin.setOnClickListener(v -> {
            if (loginModel.isDataValid(this)){
                login();
            }
        });
    }

    private void login() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}