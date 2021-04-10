package com.example.xcargomobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import com.example.xcargomobile.login_signup.Login;
import com.example.xcargomobile.login_signup.Signup;
import com.example.xcargomobile.userscreen.Home;


public class MainActivity extends AppCompatActivity {

    Button login_pagebtn;
    Button signup_pagebtn;
    Button user_pagebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_pagebtn = (Button)findViewById(R.id.login_pagebtn);
        signup_pagebtn = (Button)findViewById(R.id.signup_pagebtn);

        user_pagebtn = (Button)findViewById(R.id.user_pagebtn);

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

        user_pagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
        });

    }

   /* public void onClick(View v){
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            System.out.println("tıkladın");
        }
*/
}


