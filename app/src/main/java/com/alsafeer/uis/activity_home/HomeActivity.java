package com.alsafeer.uis.activity_home;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alsafeer.R;
import com.alsafeer.databinding.ActivityHomeBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.ResponseModel;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.remote.Api;
import com.alsafeer.share.Common;
import com.alsafeer.tags.Tags;
import com.alsafeer.uis.activity_home.fragments.Fragment_Profile;
import com.alsafeer.uis.activity_home.fragments.fragment_bills.Fragment_Bills;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Deals;
import com.alsafeer.uis.activity_login.LoginActivity;
import com.alsafeer.uis.activity_notifications.NotificationActivity;
import com.alsafeer.uis.activity_pay.PayActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.List;

import io.paperdb.Paper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity{
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private Fragment_Deals fragment_deals;
    private Fragment_Bills fragment_bills;
    private Fragment_Profile fragment_profile;
    private Preferences preferences;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
    }


    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        fragmentManager = getSupportFragmentManager();
        displayFragmentDeals();
        binding.flNotification.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        });
        binding.navigationView.setSelectedItemId(R.id.deal);
        binding.navigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.bill :
                    displayFragmentBills();
                    break;
                case R.id.profile :
                    displayFragmentProfile();
                    break;
                default:
                    displayFragmentDeals();
                    break;
            }
            return true;
        });

        if (userModel!=null){
            updateFirebaseToken();
        }

    }


    private void updateFirebaseToken()
    {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();
                        try {
                            Api.getService(Tags.base_url)
                                    .updateFirebaseToken(userModel.getData().getId(), token, "android")
                                    .enqueue(new Callback<ResponseModel>() {
                                        @Override
                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                Log.e("token", "updated successfully");
                                            } else {
                                                try {

                                                    Log.e("errorToken", response.code() + "_" + response.errorBody().string());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                            try {

                                                if (t.getMessage() != null) {
                                                    Log.e("errorToken2", t.getMessage());
                                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                                        Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            } catch (Exception e) {
                                            }
                                        }
                                    });
                        } catch (Exception e) {

                        }
                    }
                });

    }

    private void displayFragmentDeals(){
        if (fragment_deals ==null){
            fragment_deals = Fragment_Deals.newInstance();
        }

        if (fragment_bills !=null&& fragment_bills.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_bills).commit();
        }

        if (fragment_profile !=null&& fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }


        if (fragment_deals.isAdded()){
            fragmentManager.beginTransaction().show(fragment_deals).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_deals,"fragment_deals").commit();
        }
    }

    private void displayFragmentBills(){
        if (fragment_bills ==null){
            fragment_bills = Fragment_Bills.newInstance();
        }

        if (fragment_deals !=null&& fragment_deals.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_deals).commit();
        }

        if (fragment_profile !=null&& fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }


        if (fragment_bills.isAdded()){
            fragmentManager.beginTransaction().show(fragment_bills).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_bills,"fragment_bills").commit();
        }
    }

    private void displayFragmentProfile(){
        if (fragment_profile ==null){
            fragment_profile = Fragment_Profile.newInstance();
        }


        if (fragment_deals !=null&& fragment_deals.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_deals).commit();
        }

        if (fragment_bills !=null&& fragment_bills.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_bills).commit();
        }


        if (fragment_profile.isAdded()){
            fragmentManager.beginTransaction().show(fragment_profile).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_profile,"fragment_profile").commit();
        }
    }


    public void refreshActivity(String lang) {
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            Paper.init(this);
            Paper.book().write("lang",lang);
            Language.updateResources(this,lang);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        },1500);


    }

    public void logout()
    {
        Preferences preferences = Preferences.getInstance();
        UserModel userModel = preferences.getUserData(this);
        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .logout(userModel.getData().getId())
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body().getStatus()==200){
                                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                if (manager!=null){
                                    manager.cancel(Tags.not_tag,Tags.not_id);
                                }
                                preferences.clear(HomeActivity.this);
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(HomeActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            dialog.dismiss();

                            switch (response.code()){
                                case 500:
                                    Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(HomeActivity.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code",response.code()+"_");
                            } catch (NullPointerException e){

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(HomeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                }
                                else if (t.getMessage().toLowerCase().contains("socket")||t.getMessage().toLowerCase().contains("canceled")){ }
                                else {
                                    Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onBackPressed() {
        if (fragment_deals!=null&&fragment_deals.isAdded()&&fragment_deals.isVisible()){
            finish();
        }else {
            binding.navigationView.setSelectedItemId(R.id.deal);
        }
    }

    public void refreshFragmentPreviousDeals() {
        if (fragment_bills!=null&&fragment_bills.isAdded()){
            Log.e("2","2");
            fragment_bills.refreshFragmentPreviousDeals();
        }
    }
}