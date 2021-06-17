package com.altoque.delivery.view.OAuth;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.altoque.delivery.R;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.view.initial.InitialActivity;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;

public class OAuthBottomSheet extends BottomSheetDialogFragment {

    View root;

    private BottomSheetBehavior mBehavior;
    /*private ApiInterface api;
    SessionSP sessionSP;*/

    private FirebaseAuth mAuth;

    private PinView pinView;

    private MaterialButton btn_next_tstep;

    private ProgressBar pb_tstep;

    private String verificationId;

    String phone = "", type_oauth = "", pin_otp = "";


    private void bindView() {

        btn_next_tstep = root.findViewById(R.id.btn_activate_tstep);
        pb_tstep = root.findViewById(R.id.pb_load_tstep);
        pinView = root.findViewById(R.id.pinView);

    }

    private void initResources() {
        /*api = ApiClient.getApiClient().create(ApiInterface.class);
        sessionSP = SessionSP.get(requireContext());*/

        mAuth = FirebaseAuth.getInstance();
    }

    private void eventListener() {


    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBehavior.setHideable(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireActivity().getWindow()
                    .setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.colorWhite));
            requireActivity().getWindow()
                    .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //root = View.inflate(getContext(), R.layout.bottomsheet_oauth, null);
        dialog.setContentView(root);
        mBehavior = BottomSheetBehavior.from((View) root.getParent());
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bindView();
        initResources();
        eventListener();
        setupStatesBS();

        if (getArguments() != null) {

            phone = getArguments().getString("phone", "");
            pin_otp = getArguments().getString("pin_otp", "");
            type_oauth = getArguments().getString("type_oauth", "");

            if (!phone.isEmpty() && !type_oauth.isEmpty()) {

                /*if (type == 1) {
                    String pred_type = "menu";
                    getDetailMenu(id, pred_type, id_customer);
                    ll_blank_marca.setVisibility(View.GONE);

                } else if (type == 2) {
                    String pred_type = "product";
                    getDetailProduct(id, pred_type, id_customer);
                    //stockCheck(Integer.valueOf(id));
                }*/

                if (!pin_otp.isEmpty()) {
                    //pinView.setText("" + pin_otp);
                }

            } else {
                mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                String textToast = "¡Ups! No se logró cargar correctamente.";
                showToast(textToast);
            }

        }

        btn_next_tstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = pinView.getText().toString().trim();

                if (!OTP.isEmpty()) {
                    pb_tstep.setVisibility(View.VISIBLE);
                    btn_next_tstep.setEnabled(false);
                    pinView.setEnabled(false);
                    verifyCode();

                } else {
                    pinView.setLineColor(Color.RED);
                }

            }
        });


        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showLongToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }


    private void setupStatesBS() {
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    private void verifyCode() {
        String OTP = pinView.getText().toString().trim();
        if (!OTP.isEmpty()) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, OTP);
            signInWithCredential(credential);
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb_tstep.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e("LogUser", "" + user.getUid().toString());

                            showToast(user.getUid().toString());
                            SessionSP.get(requireContext()).saveStateLogin("yes");
                            pinView.setLineColor(Color.GREEN);
                            startActivity(new Intent(requireContext(), InitialActivity.class));
                            getActivity().finishAffinity();

                        } else {

                            btn_next_tstep.setEnabled(true);
                            pinView.setEnabled(true);

                            Toast.makeText(requireContext(), "Codigo  OC FAIL", Toast.LENGTH_SHORT).show();
                            Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
