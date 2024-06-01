package com.example.java_artbookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.java_artbookapp.databinding.ActivityMainBinding;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Art> arrayList;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        arrayList = new ArrayList<>();

        getData();
        adapter = new RecyclerAdapter(arrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    public void getData(){

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM arts",null);

            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String artisName = cursor.getString(2);
                String year = cursor.getString(3);

                byte[] imageByteArray = cursor.getBlob(4);
                Bitmap image = BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length);

                Art art = new Art(id,name,artisName,year,image);
                arrayList.add(art);
            }

            adapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addArt:
                Singleton.getInstance().setArt(null);
                Intent intent = new Intent(getApplicationContext(),ArtActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}