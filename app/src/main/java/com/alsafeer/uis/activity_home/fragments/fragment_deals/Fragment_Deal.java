package com.alsafeer.uis.activity_home.fragments.fragment_deals;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alsafeer.R;
import com.alsafeer.databinding.FragmentDealJointBinding;
import com.alsafeer.uis.activity_home.HomeActivity;

public class Fragment_Deal extends Fragment  {
    private FragmentDealJointBinding binding;
    private double lat = 0.0, lng = 0.0;
    private HomeActivity activity;




    public static Fragment_Deal newInstance() {
        return new Fragment_Deal();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deal_joint, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));




    }


}