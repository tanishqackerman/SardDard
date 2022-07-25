package com.meow.sardard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class WeeklyTimeTable extends AppCompatActivity {
    StorageReference storageReference;
    ProgressDialog progressDialog;
    ImageView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_time_table);

        tt = findViewById(R.id.tt);

        progressDialog = new ProgressDialog(WeeklyTimeTable.this);
        progressDialog.setMessage("Getting Time Table...");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference().child("tt.png");

        try {
            File localfile = File.createTempFile("tempfile", ".png");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if (progressDialog.isShowing()) progressDialog.dismiss();

                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    tt.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    Toast.makeText(WeeklyTimeTable.this, "Failed to get Time Table!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}