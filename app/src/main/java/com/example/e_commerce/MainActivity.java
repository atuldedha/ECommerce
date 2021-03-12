package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login_and_register.LoginActivity;
import com.example.e_commerce.main_screen.StarterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

               FirebaseUser currentUser=firebaseAuth.getCurrentUser();

               if(currentUser==null){

                   Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                   startActivity(intent);
                   finish();

               }else{

                   Intent intent=new Intent(getApplicationContext(), StarterActivity.class);
                   startActivity(intent);
                   finish();

               }

           }
       },3000);

    }

}
