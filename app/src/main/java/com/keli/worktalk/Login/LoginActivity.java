package com.keli.worktalk.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keli.worktalk.Model.NetCallBack;
import com.keli.worktalk.Model.UserInfo;
import com.keli.worktalk.R;
import com.keli.worktalk.Register.RegisterActivity;
import com.keli.worktalk.UserData;

public class LoginActivity extends AppCompatActivity {
    public LoginHandle handle;
    public LoginData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.handle = new LoginHandle(this);
        this.data = new LoginData();

        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        String res = handle.getResultFromLoginRes();
                        if(res==""){
                            Toast.makeText(LoginActivity.this,R.string.login_unKnownError,Toast.LENGTH_SHORT).show();
                        }else if(res.equals("success")){
                            Toast.makeText(LoginActivity.this,R.string.login_loginSuccess,Toast.LENGTH_SHORT).show();
                            handle.LoginSuccess();
                        }else{
                            Toast.makeText(LoginActivity.this,res,Toast.LENGTH_SHORT).show();
                        }
                }
            }
        };
        TextView goRegisterLogin = findViewById(R.id.login_goRegister_text);
        goRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.handle.GoRegister();
            }
        });

        final EditText login_username_edit = findViewById(R.id.login_username_edit);
        final EditText login_password_edit = findViewById(R.id.login_password_edit);
        final CheckBox login_login_check = findViewById(R.id.login_save_check);
        Button login_login_btn = findViewById(R.id.login_login_btn);
        login_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.username = login_username_edit.getText().toString();
                userInfo.password = login_password_edit.getText().toString();
                if(login_login_check.isChecked()){
                    userInfo.SaveUserInfo(LoginActivity.this);
                }
                UserData.getInstance().username = userInfo.username;
                UserData.getInstance().password = userInfo.password;
                handle.Login(userInfo, new NetCallBack() {
                    @Override
                    public void finish() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==1){
                    String json = data.getStringExtra("UserInfo");
                    UserInfo userInfo = handle.JsonToMap(json);
                    EditText login_username_edit = findViewById(R.id.login_username_edit);
                    EditText login_password_edit = findViewById(R.id.login_password_edit);
                    login_username_edit.setText(userInfo.username);
                    login_password_edit.setText(userInfo.password);
                }
        }
    }
}
