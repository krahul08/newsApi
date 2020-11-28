package com.rhltech.newsapp.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitleList = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
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

    public void setTabData(List<Fragment> fragmentList, List<String> fragmentTitleList) {
        this.fragmentList = fragmentList;
        this.fragmentTitleList = fragmentTitleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}

