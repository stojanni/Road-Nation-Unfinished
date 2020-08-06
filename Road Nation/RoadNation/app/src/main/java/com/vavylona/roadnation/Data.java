package com.vavylona.roadnation;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;


public class Data {

    public static String username, password;

    public static String clientName;
    public static String clientEmail;
    public static String clientPhone;

    public static Bitmap clientProfile;
    public static Bitmap clientIdentity;

    public static String driverName;
    public static String driverLastname;
    public static String driverEmail;
    public static String driverPhone;

    public static Bitmap driverProfile;
    public static Bitmap driverIdentity;
    public static Bitmap driverLicense;

    public static Bitmap bikeProfile;
    public static Bitmap bikeLicense;
    public static Bitmap bikeInsurance;

    public static String engineCC;


    /*public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap) {

        int size;

        if(bitmap.getWidth() < bitmap.getHeight()){
            size = bitmap.getWidth();
        }else{
            size = bitmap.getHeight();
        }

        int widthLight = size;
        int heightLight = size;

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paintColor = new Paint();
        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

        canvas.drawRoundRect(rectF, widthLight / 2 ,heightLight / 2,paintColor);

        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        if(bitmap.getWidth() < bitmap.getHeight()) {
            canvas.drawBitmap(bitmap, 0, -bitmap.getHeight() / 6, paintImage);
        }else{
            canvas.drawBitmap(bitmap, -bitmap.getHeight() / 3, 0, paintImage);
        }

        return output;

    }


    public static Bitmap getCroppedBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;

    }*/


    public Bitmap saveImage(Bitmap bmp, String filename, Context appContext) {

        try {

            // Assume block needs to be inside a Try/Catch block.
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut;
            File file = new File(path, filename); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            fOut = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream

            MediaStore.Images.Media.insertImage(appContext.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

        }catch (Exception e){
            Toast.makeText(appContext, "Image saving error", Toast.LENGTH_SHORT).show();
        }

        return bmp;

    }


    public static Bitmap square(Bitmap bmp){

        if (bmp.getWidth() >= bmp.getHeight()){
            bmp = Bitmap.createBitmap(bmp,bmp.getWidth()/2 - bmp.getHeight()/2,0, bmp.getHeight(), bmp.getHeight());
        }else{
            bmp = Bitmap.createBitmap(bmp,0,bmp.getHeight()/2 - bmp.getWidth()/2, bmp.getWidth(), bmp.getWidth());
        }

        return bmp;

    }


    public static String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

}
