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

public class UploadPDFMaterial extends AppCompatActivity {

    EditText semno;
    EditText enterpdfname;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        firebaseDBManager.uploadpdf(UploadPDFMaterial.this, uri, enterpdfname.getText().toString());
                        FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/all", enterpdfname.getText().toString(), "Study material uploaded", UploadPDFMaterial.this, UploadPDFMaterial.this);
                        fcmNotificationsSender.SendNotifications();
                    }
                }
            }
    );
    //    String what = getIntent().getStringExtra("what");
    FirebaseDBManager firebaseDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf_material);

        semno = findViewById(R.id.semno);
        enterpdfname = findViewById(R.id.enterpdfname);
        Button uploadbtn = findViewById(R.id.uploadbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDBManager = new FirebaseDBManager(UploadPDFMaterial.this, "material/Sem " + semno.getText().toString());
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