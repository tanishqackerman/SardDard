package com.meow.sardard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class AddAdmin extends AppCompatActivity {

    FirebaseDBManager firebaseDBManager;
    EditText enteremail;
    Button addadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        enteremail = findViewById(R.id.enteremail);
        addadmin = findViewById(R.id.addadmin);

        firebaseDBManager = new FirebaseDBManager(this, "admin");

        addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDBManager.addAdmin(enteremail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddAdmin.this, enteremail.getText().toString() + " is an Admin now!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddAdmin.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}