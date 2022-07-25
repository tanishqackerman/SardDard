package com.meow.sardard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    private EditText pin;
    private Button login;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        pin = findViewById(R.id.pin);
        login = findViewById(R.id.login);
        error = findViewById(R.id.error);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pin.getText().toString().equals("6369")) startActivity(new Intent(LoginScreen.this, AdminLogin.class));
                else error.setText("Wrong Pin!!!");
            }
        });
    }
}