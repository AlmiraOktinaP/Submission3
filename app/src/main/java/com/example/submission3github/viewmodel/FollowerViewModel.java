package com.example.submission3github.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission3github.BuildConfig;
import com.example.submission3github.model.UserModel;
import com.example.submission3github.retrofit.ApiClient;
import com.example.submission3github.retrofit.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserModel>> listUsers = new MutableLiveData<>();

    public void setUsersView(String username) {
        try {
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            String apiKey = BuildConfig.TOKEN;
            Call<ArrayList<UserModel>> eventCall = apiService.userFollower(apiKey, username);
            eventCall.enqueue(new Callback<ArrayList<UserModel>>() {
                private Response<ArrayList<UserModel>> response;

                @Override
                public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                    this.response = response;
                    listUsers.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });

        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<UserModel>> getUsers() {
        if (listUsers == null) {
            listUsers = new MutableLiveData<>();
        }
        return listUsers;
    }
}