package com.example.submission3github.model;

import java.util.ArrayList;

public class SearchUserModel {
    int total_count;

    boolean incomplete_results;

    public ArrayList<UserModel> items;

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getTotal_count() {
        return this.total_count;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public boolean getIncomplete_results() {
        return this.incomplete_results;
    }

    public void setItems(ArrayList<UserModel> items) {
        this.items = items;
    }

    public ArrayList<UserModel> getItems() {
        return this.items;
    }
}

