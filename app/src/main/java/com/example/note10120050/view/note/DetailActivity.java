package com.example.note10120050.view.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.note10120050.databinding.ActivityDetailBinding;
import com.example.note10120050.entity.DailyNote;
import com.example.note10120050.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
// NIM : 10120050
// Nama : Ari Syafri
// Kelas : IF2
public class DetailActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    private ActivityDetailBinding binding;
    private String noteKey; // Store the key of the selected DailyNote

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance();

        // Retrieve the DailyNote object and its key from the Intent extras
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("daily_note")) {
            DailyNote dailyNote = intent.getParcelableExtra("daily_note");
            noteKey = intent.getStringExtra("note_key");

            // Now you can use the dailyNote object to populate the detail activity
            binding.edtTitle.setText(dailyNote.getTitle());
            binding.edtCategory.setText(dailyNote.getCategory());
            binding.edtContent.setText(dailyNote.getContent());
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Note");

        setupUser();

        binding.btnSubmit.setOnClickListener(v -> updateData());

        binding.btnDelete.setOnClickListener(v -> deleteData());
    }

    private void updateData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null && noteKey != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNotesRef = databaseReference.child(userId);

            String newTitle = binding.edtTitle.getText().toString();
            String newCategory = binding.edtCategory.getText().toString();
            String newContent = binding.edtContent.getText().toString();

            DailyNote notes = new DailyNote(getCurrentDate(),newTitle, newCategory, newContent);

            // Update the specific item using the noteKey
            userNotesRef.child(noteKey).setValue(notes, (error, ref) -> {
                if (error != null) {
                    Toast.makeText(this, "Gagal update" + error.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "berhasil update", Toast.LENGTH_SHORT).show();

                    finish(); // Finish the AddActivity to prevent it from appearing in the back stack
                }
            });
        } else {
            // Handle the case when currentUser is null or noteKey is null
        }
    }

    private void deleteData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null && noteKey != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNotesRef = databaseReference.child(userId);

            // Delete the specific item using the noteKey
            userNotesRef.child(noteKey).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Data successfully deleted
                        Toast.makeText(DetailActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the DetailActivity after deleting the item
                    })
                    .addOnFailureListener(e -> {
                        // Error occurred while deleting data
                        Toast.makeText(DetailActivity.this, "Failed to delete data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Handle the case when currentUser is null or noteKey is null
        }
    }

    private void setupUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

    private String getCurrentDate() {
        // Create a Calendar instance and get the current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }
}