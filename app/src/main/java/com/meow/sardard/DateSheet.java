package com.meow.sardard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.meow.sardard.Models.PDFData;

public class DateSheet extends AppCompatActivity implements SelectListener{

    private RecyclerView dsrv;
    private FirebaseDBManager firebaseDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_sheet);

        dsrv = findViewById(R.id.dsrv);

        firebaseDBManager = new FirebaseDBManager(this, "datesheet", this);
        firebaseDBManager.viewpdf(dsrv);
    }

    @Override
    public void OnPDFClicked(PDFData data) {
        startActivity(new Intent(DateSheet.this, Pdfview.class).putExtra("data", data));
    }

    @Override
    public void OnSemClicked(String s, int semname) {

    }
}