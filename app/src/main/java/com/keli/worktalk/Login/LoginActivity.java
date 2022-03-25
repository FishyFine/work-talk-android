package com.keli.worktalk.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keli.worktalk.R;

public class LoginActivity extends AppCompatActivity {
    public LoginHandle handle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.handle = new LoginHandle(this);
        TextView goRegisterLogin = findViewById(R.id.login_goRegister_text);
        goRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.handle.GoRegister();
            }
        });
    }
}
