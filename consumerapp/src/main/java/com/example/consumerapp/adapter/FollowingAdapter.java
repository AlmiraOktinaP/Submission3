package com.example.consumerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.R;
import com.example.consumerapp.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.CustomViewVolder> {
    private List<UserModel> dataList;

    public FollowingAdapter(List<UserModel> data){
        this.dataList = data;
    }

    @NonNull
    @Override
    public FollowingAdapter.CustomViewVolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new FollowingAdapter.CustomViewVolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.CustomViewVolder holder, int position) {
        holder.textView.setText(dataList.get(position).getLogin());
        Picasso.get().load(dataList.get(position).getAvatar_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomViewVolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        private ImageView imageView;

        public CustomViewVolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textView = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}