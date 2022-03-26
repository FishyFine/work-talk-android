package com.keli.worktalk.Login;

import android.content.Intent;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.keli.worktalk.ChatActivity;
import com.keli.worktalk.Model.NetCallBack;
import com.keli.worktalk.Model.NetHelper;
import com.keli.worktalk.Model.UserInfo;
import com.keli.worktalk.Register.RegisterActivity;
import com.keli.worktalk.UserData;

import java.io.IOException;
import java.util.Map;

import okhttp3.Request;

public class LoginHandle {
    LoginActivity activity;

    public LoginHandle(LoginActivity activity){
        this.activity = activity;
    }
    public void GoRegister(){
        Intent goRegister = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(goRegister,1);
    }
    public void LoginSuccess(){
        Intent goChat = new Intent(activity, ChatActivity.class);
        activity.startActivity(goChat);
    }
    public UserInfo JsonToMap(String userInfo) {
        Map<String,Object> m = JSONObject.parseObject(userInfo,Map.class);
        UserInfo user = new UserInfo();
        user.username = m.get("username").toString();
        user.password = m.get("password").toString();
        return user;
    }
    public String getResultFromLoginRes(){
        String response = activity.data.loginResponse;
        String result = "";
        try{
            Map<String,Object> m = JSONObject.parseObject(response,Map.class);
            result = m.get("result").toString();
            if(result.equals("success")){
                UserData.getInstance().token = m.get("token").toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void Login(final UserInfo userInfo,final NetCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("http://47.99.166.254:8082/?" +
                        "username="+userInfo.username+"&"+
                        "password="+userInfo.password).build();
                String response = "";
                try {
                    response = NetHelper.requestHttp(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                activity.data.loginResponse = response;
                callBack.finish();
            }
        }).start();
    }
}
