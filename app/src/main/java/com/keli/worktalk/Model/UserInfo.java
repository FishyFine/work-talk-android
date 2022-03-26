package com.keli.worktalk.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfo {
    public String username;
    public String password;

    public void SaveUserInfo(AppCompatActivity activity){
        SharedPreferences.Editor editor = activity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit();
        editor.putString("username",this.username);
        editor.putString("password",this.password);
        editor.apply();
    }
}
