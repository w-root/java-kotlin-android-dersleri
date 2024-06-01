package com.example.java_twitterclone.Views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.java_twitterclone.R;
import com.example.java_twitterclone.ViewModels.FeedViewModel;
import com.example.java_twitterclone.ViewModels.ProfileViewModel;
import com.example.java_twitterclone.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    FeedViewModel feedViewModel;
    int lastNavBottomMenuItem = R.id.navigation_feed;
    NavHostFragment navHostFragment;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore.collection("users").whereEqualTo("userId",firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String,Object> data = snapshot.getData();
                    ProfileViewModel.getInstance().setUsername((String) data.get("username"));
                    ProfileViewModel.getInstance().setName((String) data.get("name"));
                }
            }
        });

        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_baseline_person_pin_24);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_feed, R.id.navigation_search, R.id.navigation_communities, R.id.navigation_notifications, R.id.navigation_message)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                updateNavBottomMenuIcons();
                switch (item.getItemId()) {

                    case R.id.navigation_feed:
                        lastNavBottomMenuItem = R.id.navigation_feed;
                        item.setIcon(R.drawable.ic_baseline_home_24);
                        break;
                    case R.id.navigation_search:
                        lastNavBottomMenuItem = R.id.navigation_search;
                        item.setIcon(R.drawable.ic_baseline_search_24);
                        break;
                    case R.id.navigation_message:
                        lastNavBottomMenuItem = R.id.navigation_message;
                        item.setIcon(R.drawable.ic_baseline_email_24);
                        break;
                    case R.id.navigation_communities:
                        lastNavBottomMenuItem = R.id.navigation_communities;
                        item.setIcon(R.drawable.ic_baseline_people_24);
                        break;
                    case R.id.navigation_notifications:
                        lastNavBottomMenuItem = R.id.navigation_notifications;
                        item.setIcon(R.drawable.ic_baseline_notifications_24);
                        break;
                }
                navController.navigate(item.getItemId());
                return false;
            }
        });

        feedViewModel.getCheckVisibility().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                if (bool){
                    binding.navView.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                }
                else{
                    binding.navView.setVisibility(View.VISIBLE);
                    binding.toolbar.setVisibility(View.VISIBLE);
                }
            }
        });


        NavigationView navigationViewDrawer = findViewById(R.id.nav_view_drawer);
        navigationViewDrawer.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    break;
                case R.id.nav_premium:
                    // Ayarlar ekranına yönlendirme işlemleri
                    // Örneğin: startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                    break;
            }


            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateNavBottomMenuIcons() {
        Menu menu = binding.navView.getMenu();
        switch (lastNavBottomMenuItem) {
            case R.id.navigation_feed:
                menu.getItem(0).setIcon(R.drawable.ic_outline_home_24);
                break;
            case R.id.navigation_search:
                menu.getItem(1).setIcon(R.drawable.ic_baseline_search_24);
                break;
            case R.id.navigation_communities:
                menu.getItem(2).setIcon(R.drawable.ic_baseline_people_outline_24);
                break;
            case R.id.navigation_notifications:
                menu.getItem(3).setIcon(R.drawable.ic_outline_notifications_24);
                break;
            case R.id.navigation_message:
                menu.getItem(4).setIcon(R.drawable.ic_outline_email_24);
                break;
        }
    }

    public void showBottomSheetDialog(View view) {

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

            Button btnCreateNewAccount = bottomSheetDialog.findViewById(R.id.btnCreateNewAccount);
            Button btnSignout = bottomSheetDialog.findViewById(R.id.btnSignout);


            bottomSheetDialog.show();


    }
}
