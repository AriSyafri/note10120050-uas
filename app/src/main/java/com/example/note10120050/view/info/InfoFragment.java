package com.example.note10120050.view.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note10120050.R;
import com.example.note10120050.adapter.VPAdapter;
import com.example.note10120050.adapter.ViewPagerItem;
import com.example.note10120050.databinding.FragmentInfoBinding;
import com.example.note10120050.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

// NIM : 10120050
// Nama : Ari Syafri
// Kelas : IF2
public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private FirebaseAuth auth;

    ViewPager2 viewPager;

    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            binding.tvWelcome.setText("Hi! " + currentUser.getDisplayName());
            if (currentUser.getDisplayName() == null){
                binding.tvWelcome.setText("Hello Mate! ");
            }
        } else {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        }

        int[] images = {R.drawable.home, R.drawable.undraw_note_ilus};
        String[] headings = {"Aplikasi Note 10120050 adalah sebuah aplikasi pencatatan yang dibuat untuk memudahkan user dalam mencatat", "Silahkan pilih note untuk melakukan pencatatan yang ada pada aplikasi"};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], headings[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        VPAdapter vpAdapter = new VPAdapter(requireContext(), viewPagerItemArrayList);

        viewPager = binding.viewpager;
        viewPager.setAdapter(vpAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}