package com.vavylona.roadnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ClientProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        if(getIntent().getExtras().getString("LOGIN_OR_REGISTRATION") == "REGISTRATION") {

            ImageView clientProfile = (ImageView) findViewById(R.id.clientProfile);
            clientProfile.setImageBitmap(Data.clientProfile);

            ImageView clientID = (ImageView) findViewById(R.id.clientIdentity);
            clientID.setImageBitmap(Data.clientID);

            EditText clietName = (EditText) findViewById(R.id.clientName);
            EditText clientEmail = (EditText) findViewById(R.id.clientEmail);
            EditText clientPhone = (EditText) findViewById(R.id.clientPhone);
            TextView clientRating = (TextView) findViewById(R.id.clientRating);

        }else{



        }

    }

}
