package com.meow.sardard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.meow.sardard.Models.AnnData;
import com.meow.sardard.Models.PDFData;
import com.meow.sardard.ViewHolderAdapter.AnnAdapter;
import com.meow.sardard.ViewHolderAdapter.PDFAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseDBManager {

    Context context;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String folder;
    SelectListener listener;
    List<PDFData> list = new ArrayList<PDFData>();
    List<AnnData> listann = new ArrayList<AnnData>();
    List<String> listad = new ArrayList<String>();
    PDFAdapter pdfAdapter = new PDFAdapter(context, list, listener);
    AnnAdapter annAdapter = new AnnAdapter(context, listann);
    boolean bool;

    public FirebaseDBManager(Context context, String folder) {
        this.context = context;
        this.folder = folder;
    }
    public FirebaseDBManager(Context context, String folder, SelectListener listener) {
        this.context = context;
        this.folder = folder;
        this.listener = listener;
    }

    public void viewpdf(RecyclerView rv) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting PDFs...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PDFData pdfData = dataSnapshot.getValue(PDFData.class);
                    list.add(pdfData);
                }
                Collections.reverse(list);
                pdfAdapter = new PDFAdapter(context, list, listener);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(context, 1));
                rv.setAdapter(pdfAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void viewann(RecyclerView rv) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Announcements...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    AnnData annData = dataSnapshot.getValue(AnnData.class);
                    listann.add(annData);
                }
                Collections.reverse(listann);
                annAdapter = new AnnAdapter(context, listann);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(context, 1));
                rv.setAdapter(annAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadpdf(Context context, Uri data, String uploadpdfname) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        StorageReference reference = storageReference.child(folder + "/" + uploadpdfname + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url = uri.getResult();
                        PDFData pdfData = new PDFData(uploadpdfname, url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(pdfData);
                        Toast.makeText(context, "Uploaded!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded: " + (int) progress + "%");
                    }
                });
    }

    public Task<Void> pushannouncement(AnnData annData) {
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        return databaseReference.push().setValue(annData);
    }

    public void checkAdmin(String email) {
        bool = false;
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (email.equals(dataSnapshot.getValue().toString())) {
                        context.startActivity(new Intent(context, AdminLogin.class));
                        bool = true;
                        break;
                    }
                }
                if (!bool) Toast.makeText(context, "You're not an Admin", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Task<Void> addAdmin(String email) {
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        return databaseReference.push().setValue(email);
    }
}
