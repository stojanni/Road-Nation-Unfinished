package com.vavylona.roadnation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class ClientRegistrationInfo extends AppCompatActivity {

    ImageView img_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration_info);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void next(View view) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        EditText clientName = (EditText)findViewById(R.id.clientName);
        EditText clientEmail = (EditText)findViewById(R.id.clientEmail);
        EditText clientPhone = (EditText)findViewById(R.id.clientPhone);

        ImageView clientProfile = (ImageView)findViewById(R.id.clientProfile);
        ImageView clientID = (ImageView)findViewById(R.id.clientIdentity);


        if(!clientName.getText().toString().contains(" ") && clientName.getText().toString().matches("[A-Za-z]+") && clientName.getText().toString().length() >= 2) {

            if(!clientEmail.getText().toString().contains(" ") /*&& clientEmail.getText().toString().matches("[A-Za-z0-9]+") */&& clientEmail.getText().toString().length() >= 10 && clientEmail.getText().toString().contains("@") && clientEmail.getText().toString().contains(".")) {

                if(!clientPhone.getText().toString().contains(" ") && clientPhone.getText().toString().matches("[0-9]+") && clientPhone.getText().toString().length() >= 10) {

                    if (clientProfile.getDrawable() != null && clientID.getDrawable() != null) {

                        Data.clientName = clientName.getText().toString();
                        Data.clientEmail = clientName.getText().toString();
                        Data.clientPhone = clientName.getText().toString();

                        new AsyncTasks(this).execute(Data.username, Data.password, Data.clientName, Data.clientEmail, Data.clientPhone);

                    }else{
                        Toast.makeText(getApplicationContext(), "Please select images", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Phone: Only spaceless numbers allowed", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Name: only spaceless letters allowed", Toast.LENGTH_SHORT).show();
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

        //super.onActivityResult(reqCode, resultCode, data);

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

                if(img_view.getId() == R.id.clientProfile) {
                    circle = (ImageView) findViewById(R.id.clientProfileCircle);
                    Data.clientProfile = selectedImage;
                }else{
                    circle = (ImageView) findViewById(R.id.clientIdentityCircle);
                    Data.clientIdentity = selectedImage;
                }

                circle.setImageResource(R.drawable.circle);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

                String clientUsername = strings[0];
                String clientPassword = strings[1];
                String clientName = strings[2];
                String clientEmail = strings[3];
                String clientPhone = strings[4];

                String link = "http://vavylona.000webhostapp.com/RoadNation/php/register.php";

                String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(clientUsername, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(clientPassword, "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(clientName, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(clientEmail, "UTF-8");
                data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(clientPhone, "UTF-8");
                data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("client", "UTF-8");

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

                ImageView profile = (ImageView)findViewById(R.id.clientProfile);
                ImageView identity = (ImageView)findViewById(R.id.clientIdentity);

                Bitmap profileBmp = ((BitmapDrawable)profile.getDrawable()).getBitmap();
                Bitmap identityBmp = ((BitmapDrawable)identity.getDrawable()).getBitmap();

                new UploadTask().execute(profileBmp, identityBmp);

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

            Bitmap profile = params[0];
            String profileString = Data.getStringImage(profile);

            Bitmap identity = params[1];
            String identityString = Data.getStringImage(identity);

            HashMap<String,String> data = new HashMap<>();

            data.put("clientProfile", profileString);
            data.put("clientIdentity", identityString);

            data.put("username", Data.username);

            String result = rh.postRequest("http://vavylona.000webhostapp.com/RoadNation/php/uploadImage.php",data);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            //super.onPostExecute(s);

            if(!result.contains("Upload success")) {

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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
