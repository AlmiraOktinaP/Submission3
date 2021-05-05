package com.example.submission3github.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.submission3github.BuildConfig;
import com.example.submission3github.R;
import com.example.submission3github.adapter.TabPagerAdapter;
import com.example.submission3github.database.DatabaseHelper;
import com.example.submission3github.database.UserHelper;
import com.example.submission3github.model.UserModel;
import com.example.submission3github.retrofit.ApiClient;
import com.example.submission3github.retrofit.ApiService;
import com.example.submission3github.viewmodel.DetailViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements LifecycleOwner {
    public static final String EXTRA_USER = "datauser";

    private UserModel mUserModel;

    TextView mUsername, mName, mCompany, mLocation;
    ImageView imageView;
    Button button;
    ProgressDialog mProgressDialog;

    UserHelper userHelper;
    DatabaseHelper myDB;

    public static String login, type, follower, following;

    Boolean action = true;
    Boolean insert = true;
    Boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserModel = getIntent().getParcelableExtra(EXTRA_USER);

        myDB = new DatabaseHelper(DetailActivity.this);

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), this, mUserModel.login);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(viewPager);

        imageView = findViewById(R.id.image);
        mUsername = findViewById(R.id.username);
        mName = findViewById(R.id.name_detail);
        mCompany = findViewById(R.id.company_detail);
        mLocation = findViewById(R.id.location_detail);
        button = findViewById(R.id.button_favorite);

        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.show();

        DetailViewModel detailViewModel = ViewModelProviders.of(DetailActivity.this).get(DetailViewModel.class);
        detailViewModel.getUsers();
        detailViewModel.setUsersView(mUserModel.login);

        detailViewModel.getUsers().observe(DetailActivity.this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                mName.setText(userModel.name);
            }
        });

        follower = mUserModel.getLogin();
        following = mUserModel.getLogin();

        mUsername.setText(mUserModel.login);

        Glide.with(getApplicationContext())
                .load(mUserModel.getAvatar_url())
                .into(imageView);

        userHelper = UserHelper.getInstance(DetailActivity.this);

        setDetail();
        getApiDetail();

        isFavorite();

        button.setOnClickListener(v -> setFavorite());
    }

    private boolean isFavorite() {
        Cursor cursor = myDB.queryByLogin(mUserModel.login);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            button.setBackgroundResource(R.drawable.ic_favorite_red);
            return true;
        }
        button.setBackgroundResource(R.drawable.ic_favorite_grey);
        return false;
    }

    private void setFavorite() {
        if (isFavorite()){
            myDB.deleteUser(mUserModel.getLogin());
            button.setBackgroundResource(R.drawable.ic_favorite_grey);
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            mUserModel.setLogin(mUserModel.getLogin());
            mUserModel.setAvatar_url(mUserModel.getAvatar_url());

            long result = myDB.addUser(mUserModel);

            if (result > 0) {
                Toast.makeText(DetailActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                button.setBackgroundResource(R.drawable.ic_favorite_red);
            } else {
                Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDetail() {
        mUserModel = new UserModel();
        mUserModel = getIntent().getParcelableExtra(EXTRA_USER);
        type = mUserModel.getType();
        login = mUserModel.getLogin();
    }

    private void getApiDetail(){
        String apiKey = BuildConfig.TOKEN;
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<UserModel> call = apiService.detail(mUserModel.login, apiKey);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    mCompany.setText(response.body().company);
                    mLocation.setText(response.body().location);
                    mProgressDialog.hide();
                } else {
                    Log.e("Error", "Data tidak masuk");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(DetailActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}