package com.vavylona.roadnation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class ClientLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    public void next(View view) {

        EditText username = (EditText)findViewById(R.id.clientUsername);
        EditText password = (EditText)findViewById(R.id.clientPassword);

        new AsyncTasks(this).execute(username.getText().toString(), password.getText().toString());

    }


    class AsyncTasks extends AsyncTask<String, Void, String> {

        Context mContext;

        public AsyncTasks (Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try{

                String link = "http://vavylona.000webhostapp.com/RoadNation/php/login.php?username="+strings[0]+"&password="+strings[1]+"&type=client";

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    Log.i("-", line);
                    break;
                }

                in.close();
                return sb.toString();

            } catch(Exception e){
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String result) {

            if(!result.contains("-")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }else{

                String[] info = result.split("-");

                //Set Profile

                Intent intent;
                intent = new Intent(getApplicationContext(), ClientProfile.class);
                intent.putExtra("LOGIN_OR_REGISTRATION", "LOGIN");
                startActivity(intent);

            }

        }

    }


}
