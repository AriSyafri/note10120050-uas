package com.example.note10120050;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.note10120050.view.info.InfoFragment;
import com.example.note10120050.view.login.LoginActivity;
import com.example.note10120050.view.note.DailyNotesFragment;
import com.example.note10120050.view.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// NIM : 10120050
// Nama : Ari Syafri
// Kelas : IF2
public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private InfoFragment fragment_Info = new InfoFragment();
    private ProfileFragment fragment_Profile = new ProfileFragment();
    private DailyNotesFragment fragment_Notes = new DailyNotesFragment();

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);
        // mengatur masuk langsung di awal
        bottomNavigationView.setSelectedItemId(R.id.infoApp);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // Not signed in, launch the Login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_Info).commit();
                return true;
            case R.id.noteApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_Notes).commit();
                return true;
            case R.id.profileAPP:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_Profile).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}