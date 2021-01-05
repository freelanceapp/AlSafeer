package com.alsafeer.uis.activity_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alsafeer.R;
import com.alsafeer.databinding.ActivityHomeBinding;
import com.alsafeer.language.Language;
import com.alsafeer.uis.activity_home.fragments.Fragment_Profile;
import com.alsafeer.uis.activity_home.fragments.fragment_bills.Fragment_Bills;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Deals;
import com.alsafeer.uis.activity_notifications.NotificationActivity;

import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity{
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private Fragment_Deals fragment_deals;
    private Fragment_Bills fragment_bills;
    private Fragment_Profile fragment_profile;

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
            fragment_bills.refreshFragmentPreviousDeals();
        }
    }
}