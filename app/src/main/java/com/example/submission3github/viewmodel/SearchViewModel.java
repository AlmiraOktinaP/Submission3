package com.example.submission3github.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission3github.BuildConfig;
import com.example.submission3github.model.SearchUserModel;
import com.example.submission3github.model.UserModel;
import com.example.submission3github.retrofit.ApiClient;
import com.example.submission3github.retrofit.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserModel>> listUsers = new MutableLiveData<>();

    public void setUsersView(String username) {
        try {
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            String apiKey = BuildConfig.TOKEN;
            Call<SearchUserModel> eventCall = apiService.searchUser(apiKey, username);

            eventCall.enqueue(new Callback<SearchUserModel>() {
                @Override
                public void onResponse(Call<SearchUserModel> call, Response<SearchUserModel> response) {
                    if (response.body() != null) {
                        if (response.body().items.size() != 0) {
                            listUsers.setValue(response.body().items);
                        }
                    } else {
                        Log.e("Error", "Data not found.");
                    }
                }

                @Override
                public void onFailure(Call<SearchUserModel> call, Throwable t) {
                    Log.e("Error", t.getLocalizedMessage());
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