package com.example.submission3github.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.submission3github.R;
import com.example.submission3github.fragment.FollowerFragment;
import com.example.submission3github.fragment.FollowingFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final String mUsername;

    public TabPagerAdapter(@NonNull FragmentManager fm, Context context, String username) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mUsername = username;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.title_follower,
            R.string.title_following
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("username", mUsername);
                fragment = new FollowerFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", mUsername);
                fragment = new FollowingFragment();
                fragment.setArguments(bundle1);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
