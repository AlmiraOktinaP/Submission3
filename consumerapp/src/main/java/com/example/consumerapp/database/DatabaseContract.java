package com.example.consumerapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY ="com.example.submission3github";

    private static final String SCHEME = "content";

    public static String TABLE_NAME = "user";

    public static final class UserColumns implements BaseColumns {
        public static String TABLE_NAME = "user";

        public static final String LOGIN = "login";
        public static final String AVATAR_URL = "avatar_url";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME).build();
    }
}