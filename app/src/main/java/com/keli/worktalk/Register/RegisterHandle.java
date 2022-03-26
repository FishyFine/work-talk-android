package com.keli.worktalk.Register;

import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.keli.worktalk.Model.NetCallBack;
import com.keli.worktalk.Model.NetHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class RegisterHandle {
    private RegisterActivity activity;
    public RegisterHandle(RegisterActivity activity){
        this.activity = activity;
    }
    public boolean CheckPassword(String password,String passwordAgain){
        if(password.equals(passwordAgain)){
            return true;
        }else {
            return false;
        }
    }
    public String getResultFromRegisterRes(){
        String response = activity.data.registerResponse;
        String result = "";
        try{
            Map<String,Object> m = JSONObject.parseObject(response,Map.class);
            result = m.get("result").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void RegisterSuccess(){
        Intent goBackLogin = new Intent();
        Map<String,Object> m = new HashMap<>();
        m.put("username",activity.data.userName);
        m.put("password",activity.data.password);
        String json = JSON.toJSONString(m);
        goBackLogin.putExtra("UserInfo",json);
        this.activity.setResult(1,goBackLogin);
        this.activity.finish();
    }
    public void Register(final String username, final String password, final NetCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("http://47.99.166.254:8081/?" +
                        "username="+username+"&"+
                        "password="+password).build();
                String response = "";
                try {
                    response = NetHelper.requestHttp(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                activity.data.registerResponse = response;
                callBack.finish();
            }
        }).start();
    }
}