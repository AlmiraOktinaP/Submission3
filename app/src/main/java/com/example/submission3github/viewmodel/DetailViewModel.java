package com.example.submission3github.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission3github.BuildConfig;
import com.example.submission3github.model.UserModel;
import com.example.submission3github.retrofit.ApiClient;
import com.example.submission3github.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<UserModel> users = new MutableLiveData<>();

    public void setUsersView(String username) {
        try {
            String apiKey = BuildConfig.TOKEN;
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<UserModel> eventCall = apiService.detail(username, apiKey);

            eventCall.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    users.setValue(response.body());
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.e("Error", t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            Log.d("Error", String.valueOf(e));
        }
    }

    public LiveData<UserModel> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
        }
        return users;
    }
}