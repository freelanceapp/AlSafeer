package com.alsafeer.uis.activity_home.fragments;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.alsafeer.R;
import com.alsafeer.databinding.FragmentProfileBinding;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.remote.Api;
import com.alsafeer.tags.Tags;
import com.alsafeer.uis.activity_home.HomeActivity;
import com.alsafeer.uis.activity_language.LanguageActivity;
import com.alsafeer.uis.activity_update_profile.UpdateProfileActivity;

import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        binding.swipeRefresh.setOnRefreshListener(this::getData);

    }


    public void updateUserData(){
        userModel = preferences.getUserData(activity);
        binding.setModel(userModel);
    }


    public void getData()
    {

        Api.getService(Tags.base_url)
                .getUserById(userModel.getData().getId())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        binding.swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful()) {

                            if (response.body().getStatus()==200){
                                if(response.body() != null&&response.body().getData()!=null){
                                    preferences.create_update_userdata(activity,response.body());
                                    updateUserData();
                                }
                            }else {
                                Toast.makeText(activity,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {
                            binding.swipeRefresh.setRefreshing(false);

                            switch (response.code()){
                                case 500:
                                    Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;

                                default:
                                    Toast.makeText(activity,getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code",response.code()+"_"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            binding.swipeRefresh.setRefreshing(false);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                }
                                else if (t.getMessage().toLowerCase().contains("socket")||t.getMessage().toLowerCase().contains("canceled")){ }
                                else {
                                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
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