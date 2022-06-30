package com.raychal.uasmobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.raychal.uasmobile.model.User;

import java.util.HashMap;

public class SessionManager {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public SessionManager (Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void createLoginSession(User user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getId());
        editor.putString(USERNAME, user.getUserName());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail() {
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
