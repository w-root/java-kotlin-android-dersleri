package com.example.java_twitterclone.Views.Fragments.ProfileFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_twitterclone.Adapters.RecyclerTweetsAdapter;
import com.example.java_twitterclone.Models.Dtos.TweetDto;
import com.example.java_twitterclone.Models.Profile;
import com.example.java_twitterclone.Models.User;
import com.example.java_twitterclone.R;
import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.databinding.FragmentPostsBinding;
import com.example.java_twitterclone.databinding.FragmentSetHeaderImageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class PostsFragment extends Fragment {

    FragmentPostsBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    List<TweetDto> tweetList = new ArrayList<>();
    RecyclerTweetsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getData();


        return root;
    }

    private void getData(){
        firebaseFirestore.collection("tweets").whereEqualTo("userId",firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Map<String,Object> data = snapshot.getData();
                            TweetDto tweet = new TweetDto(snapshot.getId(),
                                    ProfileViewModel.profileViewModel.getUsername(),
                                    ProfileViewModel.profileViewModel.getName(),
                                    ProfileViewModel.profileViewModel.getImageUri(),
                                    (String) data.get("date"),
                                    (String) data.get("description"),
                                    (ArrayList<String>) data.get("tweetImages"),
                                    (Long)  data.get("numberOfLike"),
                                    (Long)  data.get("numberOfComment"),
                                    (Long)  data.get("numberOfRetweet"),
                                    (Long)  data.get("numberOfView"));
                            tweetList.add(tweet);
                        }

                        for (TweetDto tweet : tweetList){
                            System.out.println(tweet.getUserName());
                            System.out.println(tweet.getTweetDescription());
                        }
                        adapter = new RecyclerTweetsAdapter(tweetList);
                        binding.userTweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.userTweetsRecyclerView.setAdapter(adapter);

                    }
                });

    }
}