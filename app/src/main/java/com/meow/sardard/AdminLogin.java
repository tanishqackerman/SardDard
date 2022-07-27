package com.meow.sardard;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AdminLogin extends AppCompatActivity {

    CardView annadmin, dsadmin, studyadmin, adminadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        annadmin = findViewById(R.id.annadmin);
        dsadmin = findViewById(R.id.dsadmin);
        studyadmin = findViewById(R.id.studyadmin);
        adminadmin = findViewById(R.id.adminadmin);

        annadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, Announcement.class));
            }
        });

        dsadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, UploadPDF.class).putExtra("what", "datesheet"));
            }
        });

        studyadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, UploadPDFMaterial.class).putExtra("what", "material"));
            }
        });

        adminadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, AddAdmin.class));
            }
        });
    }
}