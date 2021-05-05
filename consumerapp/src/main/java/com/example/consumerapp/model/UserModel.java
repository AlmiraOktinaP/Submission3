package com.example.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    public int id;
    public String login;
    public String avatar_url;
    public String type;
    public String name;
    public String company;
    public String location;

    public UserModel() {
        this.login = login;
        this.avatar_url = avatar_url;
        this.type = type;
        this.name = name;
        this.company = company;
        this.location = location;
    }

    public UserModel(int id, String login, String avatar_url, String type, String name, String company, String location) {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
        this.type = type;
        this.name = name;
        this.company = company;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    protected UserModel(Parcel in) {
        id = in.readInt();
        login = in.readString();
        avatar_url = in.readString();
        name = in.readString();
        company = in.readString();
        location = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(avatar_url);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(location);
    }
}