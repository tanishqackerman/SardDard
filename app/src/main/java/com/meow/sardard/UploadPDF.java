package com.meow.sardard;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UploadPDF extends AppCompatActivity {

    EditText enterpdfname;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        firebaseDBManager.uploadpdf(UploadPDF.this, uri, enterpdfname.getText().toString());
                        FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/all", enterpdfname.getText().toString(), "Datesheet uploaded", UploadPDF.this, UploadPDF.this);
                        fcmNotificationsSender.SendNotifications();
                    }
                }
            }
    );
//    String what = getIntent().getStringExtra("what");
    FirebaseDBManager firebaseDBManager = new FirebaseDBManager(this, "datesheet");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        enterpdfname = findViewById(R.id.enterpdfname);
        Button uploadbtn = findViewById(R.id.uploadbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filedialog();
            }
        });
    }

    public void filedialog() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent = Intent.createChooser(intent, "Select The PDF File");
        mGetContent.launch(intent);
    }
}