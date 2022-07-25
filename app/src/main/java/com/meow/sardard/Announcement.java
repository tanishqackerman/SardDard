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
import com.google.firebase.messaging.FirebaseMessaging;
import com.meow.sardard.Models.AnnData;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Announcement extends AppCompatActivity {

    EditText anntitle, annmsg;

    FirebaseDBManager firebaseDBManager = new FirebaseDBManager(this, "announcement");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        FirebaseMessaging.getInstance().subscribeToTopic("all");;

        anntitle = findViewById(R.id.anntitle);
        annmsg = findViewById(R.id.annmsg);
        Button uploadbtn = findViewById(R.id.uploadbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                AnnData annData = new AnnData(anntitle.getText().toString(), annmsg.getText().toString(), formatter.format(date));
                firebaseDBManager.pushannouncement(annData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Announcement.this, "Announcement Pushed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Announcement.this, "Announcement Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
                if (!anntitle.getText().toString().isEmpty() && !annmsg.getText().toString().isEmpty()) {
                    FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/all", anntitle.getText().toString(), annmsg.getText().toString(), Announcement.this, Announcement.this);
                    fcmNotificationsSender.SendNotifications();
                } else {
                    Toast.makeText(Announcement.this, "Enter Title and Message!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}