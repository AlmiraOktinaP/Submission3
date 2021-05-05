package com.example.submission3github.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission3github.R;
import com.example.submission3github.adapter.UserAdapter;
import com.example.submission3github.database.DatabaseHelper;
import com.example.submission3github.database.UserHelper;
import com.example.submission3github.model.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {
    ArrayList<UserModel> favoriteModels = new ArrayList<>();

    private UserAdapter mUserAdapter;
    private RecyclerView recyclerView;

    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Favorite User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_favorite);

        userHelper = UserHelper.getInstance(FavoriteActivity.this);
        userHelper.open();

        storeDataInArrays();
    }

    private void storeDataInArrays() {
        mUserAdapter = new UserAdapter(this);

        favoriteModels = userHelper.getAllFavorite();
        mUserAdapter.setTasks(favoriteModels);

        recyclerView.setAdapter(mUserAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private ItemTouchHelper.SimpleCallback simpleCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {
                DatabaseHelper db = new DatabaseHelper(FavoriteActivity.this);
                db.deleteUser(favoriteModels.get(position).getLogin());

                mUserAdapter.notifyItemRemoved(position);
                favoriteModels.remove(position);

                mUserAdapter.notifyDataSetChanged();

                Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(FavoriteActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}