package com.altoque.delivery.view.initial.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    TextView tv_username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv_username = binding.logout;

        tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionSP.get(requireContext()).saveStateLogin("no");

                FirebaseAuth mAuthent = FirebaseAuth.getInstance();
                mAuthent.signOut();

                SessionSP.get(requireContext()).logout();
                startActivity(new Intent(requireContext(), MainActivity.class));
                getActivity().finish();



            }
        });

        setUserName();

        return root;
    }

    private void setUserName(){
        tv_username.setText("Hola, "+SessionSP.get(requireContext()).getNameSessSp().toString().trim());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}