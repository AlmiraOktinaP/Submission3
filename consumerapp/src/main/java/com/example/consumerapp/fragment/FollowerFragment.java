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
import com.example.consumerapp.adapter.FollowerAdapter;
import com.example.consumerapp.model.UserModel;
import com.example.consumerapp.viewmodel.FollowerViewModel;

import java.util.ArrayList;

public class FollowerFragment extends Fragment implements LifecycleOwner {
    private String username;
    private RecyclerView recyclerView;
    private FollowerAdapter followerAdapter;
    private FollowerViewModel followerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        recyclerView = view.findViewById(R.id.rv_follower);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
             username = getArguments().getString("username");
        }

        followerViewModel = ViewModelProviders.of(this).get(FollowerViewModel.class);
        followerViewModel.setUsersView(username);

        followerViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                followerAdapter = new FollowerAdapter(userModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(followerAdapter);
            }
        });

    }
}