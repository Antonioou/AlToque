package com.altoque.delivery.view.oauth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.databinding.FragmentHomeBinding;
import com.altoque.delivery.databinding.FragmentOauthInitialBinding;
import com.altoque.delivery.utils.ReverseTimer;
import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class OAuthInitialFragment extends Fragment {

    View root;
    View rootView;

    private FragmentOauthInitialBinding binding;

    private FirebaseAuth mAuth;

    private MaterialButton btn_next_sstep;
    private EditText et_ncel;
    private TextInputLayout til_ncel;

    private ProgressBar pb_sstep;

    String name = "";
    String phone = "";

    private String verificationId;

    public ApiInterface apiInterface;


    public OAuthInitialFragment() {
        // Required empty public constructor
    }


    private void initView() {

        btn_next_sstep = binding.btnSignSstep;

        et_ncel = binding.etNcelSstep;
        til_ncel = binding.tilNcelSstep;

        pb_sstep = binding.pbLoadSstep;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //root = inflater.inflate(R.layout.fragment_oauth_initial, container, false);
        binding = FragmentOauthInitialBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        initView();
        eventListener();


        return root;
    }

    private void eventListener() {
        btn_next_sstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_ncel.getText().toString().trim();

                if (!TextUtils.isEmpty(phone)) {
                    if (phone.length() == 9) {

                        String valPhone = phone.substring(0, 1);

                        if (valPhone.equals("9")) {
                            pb_sstep.setVisibility(View.VISIBLE);
                            btn_next_sstep.setEnabled(false);
                            et_ncel.setEnabled(false);
                            til_ncel.setError(null);

                            sendVerificationCode("+51" + phone);
                        } else {
                            til_ncel.setError("Número erroneo");
                            Toast.makeText(requireContext(), "Número de celular incorrecto", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        til_ncel.setError("Número erroneo");
                        Toast.makeText(requireContext(), "Número de celular incorrecto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    til_ncel.setError("Campo vacío");
                    Toast.makeText(requireContext(), "Por favor ingrese su número de celular", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void sendVerificationCode(String number) {

        try {
            PhoneAuthProvider.verifyPhoneNumber(
                    PhoneAuthOptions
                            .newBuilder(FirebaseAuth.getInstance())
                            .setActivity(requireActivity())
                            .setPhoneNumber(number)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setCallbacks(mCallBack)
                            .build());
            countReverse();

        } catch (Exception e) {
            Log.e("Error OAuth", "ERROR VERIFICATION CODE: " + e.getMessage());
        }
    }

    private void countReverse() {
        TextView tv_count = binding.tvCountSstep;
        new CountDownTimer(59 * 1000 + 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                //int minutes = seconds / 60;
                seconds = seconds % 60;
                tv_count.setVisibility(View.VISIBLE);
                tv_count.setText("Tiempo para intentar nuevamente: " + String.format("%02d", seconds) + " segundos.");
            }

            public void onFinish() {
                tv_count.setVisibility(View.INVISIBLE);
                pb_sstep.setVisibility(View.INVISIBLE);
                btn_next_sstep.setEnabled(true);
                et_ncel.setEnabled(true);

            }
        }.start();
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
            fragmentNavigation();

            //ReverseTimer reverseTimer = new ReverseTimer();

            //tv_count.setVisibility(View.VISIBLE);
            //reverseTimer.reverseTimer( 59, binding.tvCountSstep);

            try {

                //Toast.makeText(requireContext(), ""+verificationId, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(requireContext(), "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                try {
                    Integer val = Integer.valueOf(code);
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Errror: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            btn_next_sstep.setEnabled(true);
            et_ncel.setEnabled(true);
            pb_sstep.setVisibility(View.INVISIBLE);
            et_ncel.setText("");
            Toast.makeText(requireContext(), "A intentado muchas veces. Pruebe en unos minutos.", Toast.LENGTH_LONG).show();
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    public void fragmentNavigation() {

        Bundle bundle = new Bundle();
        bundle.putString("verificationId", verificationId);
        bundle.putString("numberPhone", et_ncel.getText().toString().trim());
        Navigation.findNavController(rootView).navigate(R.id.action_OAuthInitialFragment_to_OAuthVerifyFragment, bundle);

    }


}