package com.example.chani.logini;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Clase Multimedia
AppCompatActivity
Actividad que maneja la multimedia.
Video, foto, y musica.
 */
public class Multimedia extends AppCompatActivity {
    //Variables globales
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_TAKE_VIDEO = 2;
    private static final int MEDIA_TYPE_VIDEO = 200;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
    }


    public void tomarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_TAKE_PHOTO:
                Date date = new Date();
                Bitmap picture = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                picture.compress(Bitmap.CompressFormat.PNG, 0, arrayOutputStream);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"nombre"+date.getTime()+date.getHours()+date.getMinutes()+date.getSeconds()+".png");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(arrayOutputStream.toByteArray());
                    Toast.makeText(getApplicationContext(), "OKS", Toast.LENGTH_LONG).show();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    Log.d("CAMARAAA", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("CAMARAAA", e.getMessage());
                    e.printStackTrace();
                }
            break;
            case REQUEST_TAKE_VIDEO:
                if(fileUri != null) {
                    Toast.makeText(this, "Video guardado", Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void playMusic(View view) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        startActivity(intent);
    }

    public void tomarVideo(View view){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFile(MEDIA_TYPE_VIDEO);  // create a file to save the video in specific folder (this works for video only)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        // start the Video Capture Intent
        startActivityForResult(intent, REQUEST_TAKE_VIDEO);
    }

    public Uri getOutputMediaFile(int type)
    {
        if(Environment.getExternalStorageState() != null) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SMW_VIDEO");

            if (! mediaStorageDir.exists()) {
                if (! mediaStorageDir.mkdirs()) {
                    
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            if(type == MEDIA_TYPE_VIDEO) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "VID_"+ timeStamp + ".mp4");
            } else {
                return null;
            }

            return Uri.fromFile(mediaFile);
        }

        return null;
    }
}
