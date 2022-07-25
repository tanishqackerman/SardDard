package com.meow.sardard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ViewAnnouncements extends AppCompatActivity {

    private RecyclerView dsrv;
    private FirebaseDBManager firebaseDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcements);

        dsrv = findViewById(R.id.dsrv);

        firebaseDBManager = new FirebaseDBManager(this, "announcement");
        firebaseDBManager.viewann(dsrv);
    }
}