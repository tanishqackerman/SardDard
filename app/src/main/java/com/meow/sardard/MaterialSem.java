package com.meow.sardard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.meow.sardard.Models.PDFData;
import com.meow.sardard.ViewHolderAdapter.SemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MaterialSem extends AppCompatActivity implements SelectListener{

    RecyclerView mrv;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_sem);

        list.add("Sem 2");
        list.add("Sem 3");
        list.add("Sem 4");
        list.add("Sem 5");
        list.add("Sem 6");
        list.add("Sem 7");
        list.add("Sem 8");

        mrv = findViewById(R.id.mrv);
        SemAdapter semAdapter = new SemAdapter(this, list, this);
        mrv.setHasFixedSize(true);
        mrv.setLayoutManager(new GridLayoutManager(this, 2));
        mrv.setAdapter(semAdapter);
    }

    @Override
    public void OnPDFClicked(PDFData data) {

    }

    @Override
    public void OnSemClicked(String s, int semname) {
        startActivity(new Intent(MaterialSem.this, MaterialSub.class).putExtra("sem", semname + ""));
    }
}