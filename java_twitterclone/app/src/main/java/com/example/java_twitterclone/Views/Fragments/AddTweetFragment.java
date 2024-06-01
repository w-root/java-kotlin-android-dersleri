package com.example.java_twitterclone.Views.Fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.java_twitterclone.Models.Tweet;
import com.example.java_twitterclone.R;
import com.example.java_twitterclone.ViewModels.FeedViewModel;
import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.databinding.FragmentAddTweetBinding;
import com.example.java_twitterclone.databinding.FragmentFeedBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


public class AddTweetFragment extends Fragment {

    FeedViewModel feedViewModel;
    FragmentAddTweetBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    ArrayList<Integer> imageViews = new ArrayList<>();
    ArrayList<Uri> imageUries = new ArrayList<>();
    ArrayList<String> tweetImageUries = new ArrayList<>();
    int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedViewModel = new ViewModelProvider(requireActivity()).get(FeedViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        registerLaunchers();
    }

    public void registerLaunchers(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                feedViewModel.setCheckVisibility(true);
                Intent intentFromResult = result.getData();
                if(intentFromResult.getData() != null){
                    imageUries.add(intentFromResult.getData());
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setImageURI(intentFromResult.getData());
                    imageView.setId(View.generateViewId());
                    imageViews.add(imageView.getId());

                    ConstraintSet constraintSet = new ConstraintSet();
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(300,300);

                    if (count > 0) {
                        layoutParams.setMargins(10, 0, 0, 0);
                        imageView.setLayoutParams(layoutParams);
                        binding.constraintLayout.addView(imageView);
                        constraintSet.clone(binding.constraintLayout);
                        constraintSet.connect(imageView.getId(), ConstraintSet.START, imageViews.get(count -1), ConstraintSet.END);
                        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, imageViews.get(count -1), ConstraintSet.TOP);
                    } else {
                        layoutParams.setMargins(10, 20, 10, 0);
                        imageView.setLayoutParams(layoutParams);
                        binding.constraintLayout.addView(imageView);
                        constraintSet.clone(binding.constraintLayout);
                        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, R.id.etDescription, ConstraintSet.BOTTOM);
                    }
                    count++;
                    constraintSet.applyTo(binding.constraintLayout);
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean bool) {
                if(bool){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(getContext(), "Bu işlem için izin gereklidir.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTweetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedViewModel.setCheckVisibility(false);
                NavDirections action = AddTweetFragmentDirections.actionAddTweetFragmentToNavigationFeed();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CountDownLatch latch = new CountDownLatch(imageUries.size());
                for (Uri uri : imageUries) {
                    addTweetImagesToFirebase(uri, latch);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            latch.await();

                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            String description = binding.etDescription.getText().toString();
                            String userId = firebaseAuth.getUid();

                            Tweet tweet = new Tweet(description, date, userId, tweetImageUries, 0L, 0L, 0L, 0L);

                            firebaseFirestore.collection("tweets").add(tweet)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            NavDirections action = AddTweetFragmentDirections.actionAddTweetFragmentToNavigationFeed();
                                            Navigation.findNavController(view).navigate(action);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        binding.addTweetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Snackbar.make(view,"Galeri için izin zorunludur!",Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                            }
                        }).show();
                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                } else {
                    if(count == 4){
                        Toast.makeText(getContext(), "Daha fazla resim ekleyemezsiniz.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activityResultLauncher.launch(intent);
                    }

                }
            }
        });
        return root;
    }

    public void addTweetImagesToFirebase(Uri uri, final CountDownLatch latch) {
        String imageName = "tweetImages/" + UUID.randomUUID() + ".jpg";
        firebaseStorage.getReference().child(imageName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference ref = firebaseStorage.getReference(imageName);
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        tweetImageUries.add(String.valueOf(uri));
                        latch.countDown();
                    }
                });
            }
        });
    }

    @Override
    public void onStop() {
        feedViewModel.setCheckVisibility(false);
        onDestroy();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        feedViewModel.setCheckVisibility(false);
        super.onDestroy();
    }
}