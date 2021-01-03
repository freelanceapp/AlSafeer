package com.alsafeer.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapters extends FragmentPagerAdapter {
    private List<String> titles;
    private List<Fragment> fragmentList;
    public MyPagerAdapters(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addTitles_Fragments(List<String> titles,List<Fragment> fragmentList){
        this.titles  = titles;
        this.fragmentList = fragmentList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
