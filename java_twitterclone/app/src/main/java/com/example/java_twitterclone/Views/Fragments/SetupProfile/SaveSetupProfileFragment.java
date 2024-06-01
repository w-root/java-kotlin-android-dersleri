package com.example.java_twitterclone.Views.Fragments.SetupProfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_twitterclone.Models.Profile;
import com.example.java_twitterclone.R;
import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.Views.Activities.ProfileActivity;
import com.example.java_twitterclone.databinding.FragmentSaveSetupProfileBinding;
import com.example.java_twitterclone.databinding.FragmentSetHeaderImageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SaveSetupProfileFragment extends Fragment {

    FragmentSaveSetupProfileBinding binding;

    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSaveSetupProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String profileImageName = "images/profileimages/"+firebaseAuth.getCurrentUser().getUid() + ".jpg";
        String headerImageName = "images/headerimages/"+firebaseAuth.getCurrentUser().getUid() + ".jpg";

        binding.btnSaveSetupProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageReference.child(profileImageName)
                        .putFile(ProfileViewModel.getInstance().getImageUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.child(headerImageName)
                                        .putFile(ProfileViewModel.getInstance().getHeaderImageUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                StorageReference ref = firebaseStorage.getReference(profileImageName);
                                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        StorageReference ref2 = firebaseStorage.getReference(headerImageName);
                                                        ref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri2) {
                                                                String bio = ProfileViewModel.getInstance().getBio();
                                                                Uri profileImageUri = uri;
                                                                Uri headerImageUri = uri2;
                                                                String location = ProfileViewModel.getInstance().getLocation();
                                                                String username = ProfileViewModel.getInstance().getUsername();

                                                                Profile profile = new Profile(bio,location,username,firebaseAuth.getCurrentUser().getUid(),profileImageUri,headerImageUri);
                                                                firebaseFirestore.collection("profiles").add(profile).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(intent);
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                    }
                                                                });
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        });

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        return root;
    }
}