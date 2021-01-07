package com.alsafeer.uis.activity_home.fragments.fragment_bills;

import android.os.Bundle;
import android.util.Log;
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
import com.alsafeer.databinding.FragmentBillsBinding;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.uis.activity_home.HomeActivity;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Deal;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Joint_Deal;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Bills extends Fragment {
    private FragmentBillsBinding binding;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private MyPagerAdapters adapter;


    public static Fragment_Bills newInstance() {

        return new Fragment_Bills();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bills, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add(getString(R.string.current));
        titles.add(getString(R.string.previous));
        fragmentList.add(Fragment_Current.newInstance());
        fragmentList.add(Fragment_Previous.newInstance());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);

        adapter = new MyPagerAdapters(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addTitles_Fragments(titles,fragmentList);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.tab.setupWithViewPager(binding.pager);

    }


    public void refreshFragmentPreviousDeals() {
        Log.e("3","3");
        Fragment_Previous fragment_previous = (Fragment_Previous) fragmentList.get(1);
        fragment_previous.getData();
        Fragment_Current fragment_current = (Fragment_Current) fragmentList.get(0);
        fragment_current.getData();

    }
}
