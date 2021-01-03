package com.alsafeer.uis.activity_home.fragments.fragment_deals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;


import com.alsafeer.R;
import com.alsafeer.adapters.MyPagerAdapters;
import com.alsafeer.databinding.FragmentDealsBinding;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Deals extends Fragment  {
    private FragmentDealsBinding binding;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private MyPagerAdapters adapter;

    public static Fragment_Deals newInstance() {
        return new Fragment_Deals();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deals, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add(getString(R.string.deals));
        titles.add(getString(R.string.joint_deals));
        fragmentList.add(Fragment_Deal.newInstance());
        fragmentList.add(Fragment_Joint_Deal.newInstance());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);

        adapter = new MyPagerAdapters(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addTitles_Fragments(titles,fragmentList);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.tab.setupWithViewPager(binding.pager);

    }


}
