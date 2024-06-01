package com.example.java_instagramclone.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.java_instagramclone.R;

import com.example.java_instagramclone.adapter.RecyclerAdapter;
import com.example.java_instagramclone.databinding.ActivityFeedBinding;
import com.example.java_instagramclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    ActivityFeedBinding binding;
    List<Post> postList;
    RecyclerAdapter adapter;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        postList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getData();

        adapter = new RecyclerAdapter(postList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
        binding.recyclerView.setAdapter(adapter);

    }

    private void getData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getApplicationContext(),error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else  {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String,Object> data = snapshot.getData();
                        String email = (String) data.get("email");
                        String comment = (String) data.get("comment");
                        String url = (String) data.get("url");
                        Post post = new Post(comment,email,url);
                        postList.add(post);
                    }
                    adapter.notifyDataSetChanged();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.uploadPage:
                Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                auth.signOut();
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}