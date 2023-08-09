package com.example.note10120050.view.note;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note10120050.adapter.NoteAdapter;
import com.example.note10120050.databinding.FragmentDailyNotesBinding;
import com.example.note10120050.entity.DailyNote;
import com.example.note10120050.view.login.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// NIM : 10120050
// Nama : Ari Syafri
// Kelas : IF2
public class DailyNotesFragment extends Fragment {

    private FragmentDailyNotesBinding binding;
    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private NoteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailyNotesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Note");

        setupUser();
        loadData();

        binding.btnAdd.setOnClickListener(v -> gotoAdd());
    }

    private void loadData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNotesRef = databaseReference.child(userId);

            LinearLayoutManager manager = new LinearLayoutManager(requireContext());
            binding.rvNote.setLayoutManager(manager);

            FirebaseRecyclerOptions<DailyNote> options =
                    new FirebaseRecyclerOptions.Builder<DailyNote>()
                            .setQuery(userNotesRef, DailyNote.class)
                            .build();
            adapter = new NoteAdapter(options, (item, noteKey) -> {
                // Handle item click here
                showDetailActivity(item, noteKey); // Pass the noteKey to the showDetailActivity method
            });
            binding.rvNote.setAdapter(adapter);
        } else {
            // Handle the case when currentUser is null
        }
    }

    private void showDetailActivity(DailyNote item, String noteKey) {
        // Create an Intent to start the DetailActivity and pass the data
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("daily_note",item);
        intent.putExtra("note_key", noteKey); // Pass the noteKey to the DetailActivity
        startActivity(intent);
    }

    private void gotoAdd() {
        Intent intent = new Intent(requireContext(), AddActivity.class);
        startActivity(intent);
    }


    private void setupUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}