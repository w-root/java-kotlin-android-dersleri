package com.example.java_twitterclone.Views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.java_twitterclone.Views.Fragments.ProfileFragments.HighlightsFragment;
import com.example.java_twitterclone.Views.Fragments.ProfileFragments.LikesFragment;
import com.example.java_twitterclone.Views.Fragments.ProfileFragments.MediaFragment;
import com.example.java_twitterclone.Views.Fragments.ProfileFragments.PostsFragment;
import com.example.java_twitterclone.Views.Fragments.ProfileFragments.RepliesFragment;
import com.example.java_twitterclone.databinding.ActivityProfileBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String[] tabTitle = {"Posts", "Replies", "Highlights", "Media", "Likes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        binding.vpager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tabs, binding.vpager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabTitle[position]);

                    }
                }).attach();


        binding.btnSetUpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,SetupProfileActivity.class);
                startActivity(intent);
            }
        });

        firebaseFirestore.collection("users").whereEqualTo("userId",firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            String name;
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String,Object> data = snapshot.getData();
                    name = (String) data.get("name");

                }
                firebaseFirestore.collection("profiles").whereEqualTo("userId",firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Map<String,Object> data = snapshot.getData();
                            Uri imageUri = Uri.parse((String) data.get("image"));
                            Uri headerImage = Uri.parse((String) data.get("headerImage"));
                            String username = (String) data.get("username");

                            Picasso.get().load(imageUri).into(binding.profileImageView);
                            Picasso.get().load(headerImage).into(binding.profileHeaderImageView);

                            long creationDate = firebaseAuth.getCurrentUser().getMetadata().getCreationTimestamp();
                            Date date = new Date(creationDate);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                            binding.tvCreatedDate.setText("Joinded " + monthName + " " + calendar.get(Calendar.YEAR));
                            binding.tvName.setText(name);
                            binding.tvUsername.setText("@"+username);
                        }
                    }
                });
            }
        });

    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new PostsFragment();
            } else if(position == 1){
                return new RepliesFragment();
            }
            else if(position == 2){
                return new HighlightsFragment();
            }
            else if(position == 3){
                return new MediaFragment();
            }
            else if(position == 4){
                return new LikesFragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}