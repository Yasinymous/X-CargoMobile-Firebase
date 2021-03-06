package com.example.xcargomobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.xcargomobile.fragment.Home;
import com.example.xcargomobile.login_signup.Login;
import com.example.xcargomobile.login_signup.Signup;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button login_pagebtn;
    Button signup_pagebtn;
    Button user_pagebtn;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_pagebtn = (Button)findViewById(R.id.login_pagebtn);
        signup_pagebtn = (Button)findViewById(R.id.signup_pagebtn);

        fAuth = FirebaseAuth.getInstance();
       //user_pagebtn = (Button)findViewById(R.id.user_pagebtn);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Home.class));
            finish();
        }

        login_pagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        signup_pagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

    }

}


