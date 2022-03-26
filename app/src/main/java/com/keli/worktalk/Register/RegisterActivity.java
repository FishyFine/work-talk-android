package com.keli.worktalk.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keli.worktalk.Model.NetCallBack;
import com.keli.worktalk.R;

public class RegisterActivity extends AppCompatActivity {
    private RegisterHandle handle;
    public RegisterData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.handle = new RegisterHandle(this);
        this.data = new RegisterData();
        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        String res = handle.getResultFromRegisterRes();
                        if(res.equals("")){
                            Toast.makeText(RegisterActivity.this,R.string.register_unKnownError,Toast.LENGTH_SHORT).show();
                        }else if(res.equals("success")){
                            Toast.makeText(RegisterActivity.this,R.string.register_registerSuccess,Toast.LENGTH_SHORT).show();
                            handle.RegisterSuccess();
                        }else{
                            Toast.makeText(RegisterActivity.this,res,Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
        final EditText usernameEdit = findViewById(R.id.register_username_edit);
        final EditText passwordEdit = findViewById(R.id.register_password_edit);
        final EditText passwordAgainEdit = findViewById(R.id.register_passwordAgain_edit);
        Button registerBtn = findViewById(R.id.register_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String passwordAgain = passwordAgainEdit.getText().toString();
                if(!handle.CheckPassword(password,passwordAgain)){
                    Toast.makeText(RegisterActivity.this,R.string.register_passwordNotEqual,Toast.LENGTH_SHORT).show();
                }else{
                    data.userName = username;
                    data.password = password;
                    handle.Register(username, password, new NetCallBack() {
                        @Override
                        public void finish() {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        });
    }
}
