package com.vavylona.roadnation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;


public class DriverRegistrationBike extends AppCompatActivity {

    ImageView img_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_bike);
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    public void next(View view) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        EditText engineCC = (EditText) findViewById(R.id.engineCC);

        ImageView bikeProfile = (ImageView) findViewById(R.id.bikeProfile);
        ImageView bikeLicense = (ImageView) findViewById(R.id.bikeLicense);
        ImageView bikeInsurance = (ImageView) findViewById(R.id.bikeInsurance);


        if (!engineCC.getText().toString().contains(" ") && engineCC.getText().toString().matches("[0-9]+") && engineCC.getText().toString().length() <= 2000) {

            if (bikeProfile.getDrawable() != null && bikeLicense.getDrawable() != null && bikeInsurance.getDrawable() != null) {

                Data.engineCC = engineCC.getText().toString();

                new AsyncTasks(this).execute(Data.username, Data.password, Data.driverName, Data.driverLastname, Data.driverEmail, Data.driverPhone);

            } else {
                Toast.makeText(getApplicationContext(), "Please select images", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Only spaceless numbers allowed", Toast.LENGTH_SHORT).show();
        }

    }



    // SELECT IMAGES

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

                if(img_view.getId() == R.id.bikeProfile) {
                    circle = (ImageView) findViewById(R.id.bikeProfileCircle);
                    Data.bikeProfile = selectedImage;
                }else if(img_view.getId() == R.id.bikeLicense) {
                    circle = (ImageView) findViewById(R.id.bikeLicenseCircle);
                    Data.bikeLicense = selectedImage;
                }else{
                    circle = (ImageView) findViewById(R.id.bikeInsuranceCircle);
                    Data.bikeInsurance = selectedImage;
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



    // UPLOAD INFO

    class AsyncTasks extends AsyncTask<String, Void, String> {

        Context mContext;

        public AsyncTasks (Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try{

                String driverUsername = strings[0];
                String driverPassword = strings[1];
                String driverName = strings[2];
                String driverLastname = strings[3];
                String driverEmail = strings[4];
                String driverPhone = strings[5];
                String enginecc = strings[6];
                String helmet = strings[7];

                String link = "http://vavylona.000webhostapp.com/RoadNation/php/register.php";

                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(driverUsername, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(driverPassword, "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(driverName, "UTF-8");
                data += "&" + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(driverLastname, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(driverEmail, "UTF-8");
                data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(driverPhone, "UTF-8");
                data += "&" + URLEncoder.encode("enginecc", "UTF-8") + "=" + URLEncoder.encode(enginecc, "UTF-8");
                data += "&" + URLEncoder.encode("helmet", "UTF-8") + "=" + URLEncoder.encode(helmet, "UTF-8");
                data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("driver", "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();

            } catch(Exception e){
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String result) {

            if(!result.contains("Registration success")){

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }else{

                //Set Profile

                Intent intent;
                intent = new Intent(getApplicationContext(), DriverProfile.class);
                intent.putExtra("LOGIN_OR_REGISTRATION", "REGISTRATION");
                startActivity(intent);

            }

        }

    }



    // UPLOAD IMAGES

    String getFileName(Uri uri){

        String result = null;

        if (uri.getScheme().equals("content")) {

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }

        if (result == null) {

            result = uri.getPath();
            int cut = result.lastIndexOf('/');

            if (cut != -1) {
                result = result.substring(cut + 1);
            }

        }

        return result;

    }


    class UploadTask extends AsyncTask<Bitmap,Void,String> {

        RequestHandler rh = new RequestHandler();

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Toast.makeText(getApplicationContext(), "Uploading images...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Bitmap... params) {

            Bitmap driverProfile = params[0];
            String driverProfileString = Data.getStringImage(driverProfile);

            Bitmap driverIdentity = params[1];
            String driverIdentityString = Data.getStringImage(driverIdentity);

            Bitmap driverLicense = params[2];
            String driverLicenseString = Data.getStringImage(driverLicense);

            Bitmap bikeProfile = params[3];
            String bikeProfileString = Data.getStringImage(bikeProfile);

            Bitmap bikeLicense = params[4];
            String bikeLicenseString = Data.getStringImage(bikeLicense);

            Bitmap bikeInsurance = params[5];
            String bikeInsuranceString = Data.getStringImage(bikeInsurance);

            HashMap<String,String> data = new HashMap<>();

            data.put("driverProfile", driverProfileString);
            data.put("driverIdentity", driverIdentityString);
            data.put("driverLicense", driverLicenseString);
            data.put("bikeProfile", bikeProfileString);
            data.put("bikeLicense", bikeLicenseString);
            data.put("bikeInsurance", bikeInsuranceString);

            data.put("username", Data.username);

            String result = rh.postRequest("http://vavylona.000webhostapp.com/RoadNation/php/uploadImage.php",data);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            //super.onPostExecute(s);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            if(!result.contains("Upload success")) {

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                finish();

            }else {

                //Set Profile

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Intent intent;
                intent = new Intent(getApplicationContext(), ClientProfile.class);
                intent.putExtra("LOGIN_OR_REGISTRATION", "REGISTRATION");
                startActivity(intent);

            }

        }

    }

}
