package com.vavylona.roadnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationCredentials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_credentials);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void next(View view) {

        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        if(!username.getText().toString().contains(" ") && username.getText().toString().matches("[A-Za-z0-9]+") && username.getText().toString().length() >= 6) {

            if(!password.getText().toString().contains(" ") && password.getText().toString().matches("[A-Za-z0-9]+") && password.getText().toString().length() >= 6) {

                Data.username = username.getText().toString().toLowerCase();
                Data.password = password.getText().toString();

                Intent intent;

                if(getIntent().getExtras().getInt("DRIVER_OR_CLIENT") == R.id.driverRegistrationBtn) {
                    intent = new Intent(this, DriverRegistrationInfo.class);
                }else{
                    intent = new Intent(this, ClientRegistrationInfo.class);
                }

                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), "Only spaceless slpharithmetics allowed", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Only spaceless alpharithmetics allowed", Toast.LENGTH_SHORT).show();
        }

    }

}
