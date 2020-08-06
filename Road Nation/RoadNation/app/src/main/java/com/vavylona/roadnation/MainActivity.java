package com.vavylona.roadnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void registrationCredentials(View view){
        Intent intent = new Intent(this, RegistrationCredentials.class);
        intent.putExtra("DRIVER_OR_CLIENT", view.getId());
        startActivity(intent);
    }


    public void clientLogin(View view){
        Intent intent = new Intent(this, ClientLogin.class);
        startActivity(intent);
    }


    public void driverLogin(View view){
        Intent intent = new Intent(this, DriverLogin.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        //super.onBackPressed(); //Βγαινει απο την εφαρμογη
    }

}
