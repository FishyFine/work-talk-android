package com.keli.worktalk.Login;

import android.content.Intent;

import com.keli.worktalk.Register.RegisterActivity;

public class LoginHandle {
    LoginActivity activity;

    public LoginHandle(LoginActivity activity){
        this.activity = activity;
    }
    public void GoRegister(){
        Intent goRegister = new Intent(activity, RegisterActivity.class);
        activity.startActivity(goRegister);
    }
}
