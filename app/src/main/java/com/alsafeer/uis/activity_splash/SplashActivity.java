package com.alsafeer.uis.activity_splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.alsafeer.R;
import com.alsafeer.databinding.ActivitySplashBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.uis.activity_home.HomeActivity;
import com.alsafeer.uis.activity_login.LoginActivity;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Preferences preferences = Preferences.getInstance();
            UserModel userModel = preferences.getUserData(this);
            Intent intent;
            if(userModel==null){
                intent = new Intent(this, LoginActivity.class);
            }else {
                intent = new Intent(this, HomeActivity.class);
            }
            startActivity(intent);
            finish();

        },2000);
    }
}