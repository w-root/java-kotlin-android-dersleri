package com.example.java_twitterclone.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.java_twitterclone.Models.User;
import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public void init(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void signup(View view) {
        String email = binding.etSignupEmail.getText().toString();
        String username = binding.etSignupUsername.getText().toString();
        String dateOfBirth = binding.etSignupDateOfBirth.getText().toString();
        String password = binding.etSignupPassword.getText().toString();
        String name = binding.etSignupName.getText().toString();
        ProfileViewModel.getInstance().setName(name);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> user = new HashMap<>();
                user.put("email",email);
                user.put("username",username);
                user.put("dateOfBirth",dateOfBirth);
                user.put("name",name);
                user.put("userId",firebaseAuth.getCurrentUser().getUid());

                firebaseFirestore.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(SignupActivity.this, SetupProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }



}