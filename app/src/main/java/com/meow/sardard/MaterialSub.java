package com.meow.sardard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.meow.sardard.Models.PDFData;

public class MaterialSub extends AppCompatActivity implements SelectListener{

    private RecyclerView subrv;
    private FirebaseDBManager firebaseDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_sub);

        subrv = findViewById(R.id.subrv);

        String folder = "material/Sem " + getIntent().getStringExtra("sem");
        firebaseDBManager = new FirebaseDBManager(this, folder, this);
        firebaseDBManager.viewpdf(subrv);
    }

    @Override
    public void OnPDFClicked(PDFData data) {
        startActivity(new Intent(MaterialSub.this, Pdfview.class).putExtra("data", data));
    }

    @Override
    public void OnSemClicked(String s, int semname) {

    }
}