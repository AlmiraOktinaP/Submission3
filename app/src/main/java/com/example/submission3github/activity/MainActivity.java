package com.example.submission3github.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.submission3github.BuildConfig;
import com.example.submission3github.R;
import com.example.submission3github.adapter.UserAdapter;
import com.example.submission3github.model.UserModel;
import com.example.submission3github.retrofit.ApiClient;
import com.example.submission3github.retrofit.ApiService;
import com.example.submission3github.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    ArrayList<UserModel> userModels = new ArrayList<>();

    private UserAdapter mUserAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Github User's Search");
        }

        getApi();

        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        showProgress(true);

        SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (mSearchManager != null) {
            SearchView searchView = findViewById(R.id.search_view);
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (s != null) {
                        SearchViewModel searchViewModel = ViewModelProviders.of(MainActivity.this).get(SearchViewModel.class);
                        searchViewModel.setUsersView(s);
                    } else {
                        Toast.makeText(MainActivity.this, "Please insert username", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    showProgress(true);
                    mRecyclerView.setVisibility(View.GONE);

                    if (s.isEmpty()) {
                        getApi();

                    } else {
                        userModels.clear();
                        SearchViewModel searchViewModel = ViewModelProviders.of(MainActivity.this).get(SearchViewModel.class);
                        searchViewModel.setUsersView(s);

                        searchViewModel.getUsers().observe(MainActivity.this, new Observer<ArrayList<UserModel>>() {
                            @Override
                            public void onChanged(ArrayList<UserModel> userModels) {
                                mRecyclerView.setVisibility(View.GONE);
                                mUserAdapter.notifyDataSetChanged();
                                mUserAdapter = new UserAdapter(MainActivity.this, userModels);
                                mRecyclerView.setAdapter(mUserAdapter);
                                showProgress(false);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    return true;
                }
            });
        }
    }

    private void showProgress(boolean b) {
        if (b) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void getApi() {
        String apiKey = BuildConfig.TOKEN;
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<UserModel>> call = apiService.user(apiKey);

        Log.e("Masuk ", "Get API");

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                Log.e("Response", response.message());
                if (response.body() != null) {
                    userModels.clear();
                    userModels = new ArrayList<>(response.body());
                    mUserAdapter = new UserAdapter(MainActivity.this, userModels);
                    mUserAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mUserAdapter);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                Intent intent1 = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent1);
                return true;
            case R.id.setting:
                Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}