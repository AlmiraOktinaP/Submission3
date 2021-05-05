package com.example.consumerapp.retrofit;

import com.example.consumerapp.model.SearchUserModel;
import com.example.consumerapp.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
    Call<List<UserModel>> user(@Header("Authorization") String authorization);

    @GET("search/users")
    Call<SearchUserModel> searchUser(@Header("Authorization") String authorization,
                                     @Query("q") String username);

    @GET("users/{username}")
    Call<UserModel> detail(@Path("username") String name, @Header("Authorization") String authorization);

    @GET("users/{username}/followers")
    Call<ArrayList<UserModel>> userFollower(@Header("Authorization") String authorization,
                                            @Path("username") String username);

    @GET("users/{username}/following")
    Call<ArrayList<UserModel>> userFollowing(@Header("Authorization") String authorization,
                                             @Path("username") String username);
}
