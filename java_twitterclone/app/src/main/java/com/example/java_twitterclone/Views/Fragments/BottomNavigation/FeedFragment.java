package com.example.java_twitterclone.Views.Fragments.BottomNavigation;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_twitterclone.Adapters.RecyclerTweetsAdapter;
import com.example.java_twitterclone.Models.Dtos.TweetDto;
import com.example.java_twitterclone.Models.Profile;
import com.example.java_twitterclone.Models.User;
import com.example.java_twitterclone.ViewModels.FeedViewModel;
import com.example.java_twitterclone.databinding.FragmentFeedBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    FragmentFeedBinding binding;
    FeedViewModel feedViewModel;
    FirebaseFirestore firebaseFirestore;
    List<TweetDto> tweetList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Profile> profileList = new ArrayList<>();
    RecyclerTweetsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedViewModel = new ViewModelProvider(requireActivity()).get(FeedViewModel.class);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedViewModel.setCheckVisibility(true);

                NavDirections action = FeedFragmentDirections.actionNavigationFeedToAddTweetFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
        getData();
        adapter = new RecyclerTweetsAdapter(tweetList);
        binding.tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.tweetsRecyclerView.setAdapter(adapter);
        return root;
    }

    private void getData(){
        firebaseFirestore.collection("tweets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskTweets) {
                        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> taskUser) {
                                for (DocumentSnapshot doc : taskUser.getResult()) {
                                    User user = new User(doc.getString("userId"),doc.getString("username"),doc.getString("name"),doc.getString("email"),doc.getString("dateOfBirth"));
                                    userList.add(user);
                                }
                                firebaseFirestore.collection("profiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> taskProfiles) {
                                        for (DocumentSnapshot doc : taskProfiles.getResult()) {
                                            Profile profile = new Profile(doc.getString("bio"),doc.getString("location"),doc.getString("username"),doc.getString("userId")
                                                    , Uri.parse(doc.getString("image")),Uri.parse(doc.getString("headerImage")));
                                            profileList.add(profile);
                                        }

                                        if (taskTweets.isSuccessful()) {
                                            for (DocumentSnapshot document : taskTweets.getResult()) {
                                                for (User user:userList) {
                                                    for (Profile profile : profileList){
                                                        if(user.getUserId().equals(document.get("userId")) && user.getUserId().equals(profile.getUserId())){
                                                            TweetDto tweet = new TweetDto(document.getId(),
                                                                    user.getUserName(),
                                                                    user.getName(),
                                                                    profile.getImage(),
                                                                    document.getString("date"),
                                                                    document.getString("description"),
                                                                    (ArrayList<String>) document.get("tweetImages"),
                                                                    (Long)  document.get("numberOfLike"),
                                                                    (Long)  document.get("numberOfComment"),
                                                                    (Long)  document.get("numberOfRetweet"),
                                                                    (Long)  document.get("numberOfView"));
                                                            tweetList.add(tweet);
                                                        }
                                                    }

                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}