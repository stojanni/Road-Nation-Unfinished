package com.vavylona.roadnation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DriverRegistrationInfo extends AppCompatActivity {

    ImageView img_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_info);
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    public void next(View view) {

        EditText driverName = (EditText)findViewById(R.id.driverName);
        EditText driverLastname = (EditText)findViewById(R.id.driverLastname);
        EditText driverEmail = (EditText)findViewById(R.id.driverEmail);
        EditText driverPhone = (EditText)findViewById(R.id.driverPhone);

        ImageView driverProfile = (ImageView)findViewById(R.id.driverProfile);
        ImageView driverIdentity = (ImageView)findViewById(R.id.driverIdentity);
        ImageView driverLicense = (ImageView)findViewById(R.id.driverLicense);


        if(!driverName.getText().toString().contains(" ") && driverName.getText().toString().matches("[A-Za-z]+") && driverName.getText().toString().length() >= 2) {

            if(!driverLastname.getText().toString().contains(" ") && driverLastname.getText().toString().matches("[A-Za-z]+") && driverLastname.getText().toString().length() >= 2) {

                if (!driverEmail.getText().toString().contains(" ") && driverEmail.getText().toString().matches("[A-Za-z0-9]+") && driverEmail.getText().toString().length() >= 10 && driverEmail.getText().toString().contains("@") && driverEmail.getText().toString().contains(".")) {

                    if (!driverPhone.getText().toString().contains(" ") && driverPhone.getText().toString().matches("[0-9]+") && driverPhone.getText().toString().length() >= 10) {

                        if (driverProfile.getDrawable() != null && driverIdentity.getDrawable() != null && driverLicense.getDrawable() != null) {

                            Data.driverName = driverName.getText().toString();
                            Data.driverLastname = driverLastname.getText().toString();
                            Data.driverEmail = driverEmail.getText().toString();
                            Data.driverPhone = driverPhone.getText().toString();

                            Intent intent;
                            intent = new Intent(this, DriverRegistrationBike.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Please select images", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Only spaceless numbers allowed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getApplicationContext(), "Only spaceless letters allowed", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Only spaceless letters allowed", Toast.LENGTH_SHORT).show();
        }

    }


    public void selectImage(View view){

        img_view = (ImageView) view;

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);

    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            try {

                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = Data.square(selectedImage);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), selectedImage);

                //setting radius
                roundedBitmapDrawable.setCornerRadius(Math.max(selectedImage.getWidth(), selectedImage.getHeight()));
                roundedBitmapDrawable.setAntiAlias(true);
                img_view.setImageDrawable(roundedBitmapDrawable);

                ImageView circle;

                if(img_view.getId() == R.id.driverProfile) {
                    circle = (ImageView) findViewById(R.id.driverProfileCircle);
                    Data.driverProfile = selectedImage;
                }else if(img_view.getId() == R.id.driverIdentity) {
                    circle = (ImageView) findViewById(R.id.driverIdentityCircle);
                    Data.driverIdentity = selectedImage;
                }else{
                    circle = (ImageView) findViewById(R.id.driverLicenseCircle);
                    Data.driverLicense = selectedImage;
                }

                circle.setImageResource(R.drawable.circle);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(this, "You didn't pick an image",Toast.LENGTH_LONG).show();
        }

    }

}
