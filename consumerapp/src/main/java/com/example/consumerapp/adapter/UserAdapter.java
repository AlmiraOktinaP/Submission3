package com.example.consumerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.R;
import com.example.consumerapp.activity.DetailActivity;
import com.example.consumerapp.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> implements Filterable {
    public static final String DATA_USER = "datauser";

    private static List<UserModel> dataList;
    private Context context;

    public UserAdapter(Context context, List<UserModel> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    public UserAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.CustomViewHolder holder, int i) {
        holder.mUsername.setText(dataList.get(i).getLogin());
        Picasso.get().load(dataList.get(i).getAvatar_url()).into(holder.mImage);

        holder.cardView.setOnClickListener(new CustomOnItemClickListener(holder.getAdapterPosition(), (view, position) -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DATA_USER, dataList.get(position));

            context.startActivity(intent);
        }));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataList);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (UserModel item : dataList) {
                    if (item.getLogin().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setTasks(ArrayList<UserModel> favoriteModels) {
        this.dataList.clear();
        this.dataList.addAll(favoriteModels);
        notifyDataSetChanged();

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        final TextView mUsername;
        final CardView cardView;

        private ImageView mImage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            mUsername = itemView.findViewById(R.id.username);
            mImage = itemView.findViewById(R.id.image);
        }
    }

    public void addItem(UserModel mUserModel) {
        this.dataList.add(mUserModel);
        notifyItemInserted(dataList.size() -1);
    }

    public void updateItem(int position, UserModel mUserModel) {
        this.dataList.set(position, mUserModel);
        notifyItemChanged(position, mUserModel);
    }

    public void removeItem(int position) {
        this.dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }

}