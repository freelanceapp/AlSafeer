package com.alsafeer.uis.activity_home.fragments;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.alsafeer.R;
import com.alsafeer.databinding.FragmentProfileBinding;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.uis.activity_home.HomeActivity;
import com.alsafeer.uis.activity_language.LanguageActivity;
import com.alsafeer.uis.activity_update_profile.UpdateProfileActivity;

import io.paperdb.Paper;

public class Fragment_Profile extends Fragment{
    private FragmentProfileBinding binding;
    private String lang;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;


    public static Fragment_Profile newInstance() {
        return new Fragment_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(userModel);
        binding.cardLogout.setOnClickListener(v -> activity.logout());
        binding.cardEditProfile.setOnClickListener(v ->{
            Intent intent = new Intent(activity, UpdateProfileActivity.class);
            startActivityForResult(intent,200);
        });

        binding.cardChangeLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(activity, LanguageActivity.class);
            startActivityForResult(intent,100);
        });

    }

    public void updateUserData(){
        userModel = preferences.getUserData(activity);
        binding.setModel(userModel);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            String lang = data.getStringExtra("lang");
            activity.refreshActivity(lang);

        }else if (requestCode == 200 && resultCode == Activity.RESULT_OK ) {
           updateUserData();

        }
    }

}