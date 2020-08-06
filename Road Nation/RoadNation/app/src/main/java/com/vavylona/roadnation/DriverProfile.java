package com.vavylona.roadnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DriverProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        if(getIntent().getExtras().getString("LOGIN_OR_REGISTRATION") == "REGISTRATION") {

            ImageView driverProfile = (ImageView) findViewById(R.id.driverProfile);
            driverProfile.setImageBitmap(Data.driverProfile);

            ImageView driverID = (ImageView) findViewById(R.id.driverIdentity);
            driverID.setImageBitmap(Data.driverIdentity);

            EditText driverName = (EditText) findViewById(R.id.driverName);
            EditText driverEmail = (EditText) findViewById(R.id.driverEmail);
            EditText driverPhone = (EditText) findViewById(R.id.driverPhone);
            TextView driverRating = (TextView) findViewById(R.id.driverRating);

        }else{



        }

    }

}
