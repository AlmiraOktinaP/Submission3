package com.example.consumerapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.R;
import com.example.consumerapp.adapter.FollowingAdapter;
import com.example.consumerapp.model.UserModel;
import com.example.consumerapp.viewmodel.FollowingViewModel;

import java.util.ArrayList;

public class FollowingFragment extends Fragment implements LifecycleOwner {
    private String username;
    private RecyclerView recyclerView;
    private FollowingAdapter followingAdapter;
    private FollowingViewModel followingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        recyclerView = view.findViewById(R.id.rv_following);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            username = getArguments().getString("username");
        }

        followingViewModel = ViewModelProviders.of(this).get(FollowingViewModel.class);
        followingViewModel.setUsersView(username);

        followingViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                followingAdapter = new FollowingAdapter(userModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(followingAdapter);
            }
        });

    }
}