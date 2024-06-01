package com.example.java_artbookapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.java_artbookapp.databinding.ActivityArtBinding;
import com.example.java_artbookapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class ArtActivity extends AppCompatActivity {

    ActivityArtBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap selectedImage;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        if(Singleton.getInstance().getArt() != null){
            Art art = Singleton.getInstance().getArt();
            binding.nameText.setText(art.name);
            binding.artisText.setText(art.artisName);
            binding.yearText.setText(art.year);
            binding.imageView.setImageBitmap(art.image);
            binding.btnAdd.setVisibility(View.INVISIBLE);
        } else {
            binding.btnAdd.setVisibility(View.VISIBLE);
        }

    }

    private void init(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null){
                        Uri uri = intentFromResult.getData();
                        try {
                            ImageDecoder.Source source = null;
                            if (android.os.Build.VERSION.SDK_INT >= 28) {
                                source = ImageDecoder.createSource(getContentResolver(),uri);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                binding.imageView.setImageBitmap(selectedImage);
                            } else {
                                // SDK Build Version < 28
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(ArtActivity.this,"Permission Denided",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void save(View view) {
        String name = binding.nameText.getText().toString();
        String artisName = binding.artisText.getText().toString();
        String year = binding.yearText.getText().toString();

        Bitmap smallImage = makeSmallerImage(selectedImage,300);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,stream);

        byte[] byteArray = stream.toByteArray();


        try {
            db = openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, year VARCHAR, image BLOB)");

            String sqlString = "INSERT INTO arts (artname,paintername,year,image) VALUES (?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sqlString);
            statement.bindString(1,name);
            statement.bindString(2,artisName);
            statement.bindString(3,year);
            statement.bindBlob(4,byteArray);
            statement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public Bitmap makeSmallerImage(Bitmap image,int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;

        if(bitmapRatio > 1){
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height / bitmapRatio);
        }


        return image.createScaledBitmap(image,width,height,true);
    }

    public void selectImage(View view) {
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) Android 33+ READ_MED
//        else Andorid 32- READ_EXTERNAL_STORAGE

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }).show();
            } else {
                // request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);



        }
    }
}