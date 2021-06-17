package com.altoque.delivery.view.initial.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.logout;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionSP.get(requireContext()).saveStateLogin("no");
                startActivity(new Intent(requireContext(), MainActivity.class));
                getActivity().finish();

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}