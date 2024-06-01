package com.example.java_twitterclone.Views.Fragments.SetupProfile;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.databinding.FragmentSetHeaderImageBinding;
import com.example.java_twitterclone.databinding.FragmentSetProfileImageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class SetHeaderImageFragment extends Fragment {

    FragmentSetHeaderImageBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;


    public void registerLaunchers(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intentFromResult = result.getData();
                if(intentFromResult.getData() != null){

                    ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(),intentFromResult.getData());
                    try {
                        int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;


                        Bitmap selectedImage = ImageDecoder.decodeBitmap(source);
                        selectedImage.createScaledBitmap(selectedImage,widthPixels,175,true);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), selectedImage, "Title", null);
                        Uri u = Uri.parse(path);

                        binding.setHeaderImageView.setImageURI(u);
                        ProfileViewModel.getInstance().setHeaderImageUri(u);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerLaunchers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetHeaderImageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnNextFromHeaderImageToBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = SetHeaderImageFragmentDirections.actionSetHeaderImageFragment2ToSetBioFragment2();
                Navigation.findNavController(view).navigate(action);

            }
        });

        binding.setHeaderImageView.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
            }
        });

        return root;

    }



}

