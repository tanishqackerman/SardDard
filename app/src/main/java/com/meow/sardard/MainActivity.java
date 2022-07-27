package com.meow.sardard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private CardView wtt, ann, dt, material, admin, signout;
    TextView greet, namebla;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
    Date meow = new Date();
    String day = meow.toString().substring(0, 3);
    LocalDateTime now = LocalDateTime.now();
    int time = Integer.parseInt(dtf.format(now));
    String gr = "";
    String cls = "No";
    String email = "";
    FirebaseDBManager firebaseDBManager = new FirebaseDBManager(this, "admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wtt = findViewById(R.id.wtt);
        ann = findViewById(R.id.ann);
        dt = findViewById(R.id.dt);
        material = findViewById(R.id.material);
        admin = findViewById(R.id.admin);
        signout = findViewById(R.id.signout);
        greet = findViewById(R.id.greet);
        namebla = findViewById(R.id.namebla);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            if (time >= 2 && time <= 12) {
                gr = "Good Morning! ";
            } else if (time >= 12 && time <= 16) {
                gr = "Good Afternoon! ";
            } else if (time >= 16 && time <= 20) {
                gr = "Good Evening! ";
            } else  {
                gr = "Good Night! ";
            }
            namebla.setText(gr + googleSignInAccount.getDisplayName());
        }

        switch (day) {
            case "Mon" :
                if (time >= 9 && time <= 13) cls = "Phy Lab (G2) / PPS Lab (G1)";
                else cls = "No";
                break;
            case "Tue" :
                if (time >= 9 && time <= 11) cls = "PPS";
                else if (time >= 11 && time <= 13) cls = "Phy";
                else cls = "No";
                break;
            case "Wed" :
                if (time >= 10 && time <= 12) cls = "Maths";
                else if (time >= 12 && time <= 13) cls = "PPS";
                else if (time >= 14 && time <= 17) cls = "EGD";
                else cls = "No";
                break;
            case "Thu" :
                if (time >= 10 && time <= 11) cls = "Maths";
                else if (time >= 11 && time <= 13) cls = "Phy";
                else cls = "No";
                break;
            case "Fri" :
                if (time >= 9 && time <= 13) cls = "Workshop";
                else if (time >= 14 && time <= 15) cls = "Maths";
                else cls = "No";
                break;
            default:
                cls = "No";
        }

        greet.setText("Right Now You Have " + cls + " Class!");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                        .build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String name = account.getDisplayName();
        }

        wtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeeklyTimeTable.class));
            }
        });

        ann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewAnnouncements.class));
            }
        });

        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DateSheet.class));
            }
        });

        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MaterialSem.class));
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDBManager.checkAdmin(googleSignInAccount.getEmail());
//                if (bool) startActivity(new Intent(MainActivity.this, AdminLogin.class));
//                else Toast.makeText(MainActivity.this, "You're not an Admin!", Toast.LENGTH_SHORT).show();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, GoogleLogin.class));
            }
        });
    }
}