package com.altoque.delivery.deprecated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.CustomerModel;
import com.altoque.delivery.model.JoinResponseModel;
import com.altoque.delivery.view.initial.InitialActivity;
import com.altoque.delivery.view.oauth.RegisterActivity;
import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OAuthActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private MaterialButton btn_next_sstep;
    private EditText et_ncel;
    private TextInputLayout til_ncel;

    private ProgressBar pb_sstep;

    String name = "";
    String phone = "";

    private String verificationId;

    private PinView pinView;
    private MaterialButton btn_next_tstep;
    private ProgressBar pb_tstep;

    String type_oauth = "", pin_otp = "";

    ConstraintLayout layout_bottomsheet;
    BottomSheetBehavior bottomsheetbehavior;

    public ApiInterface apiInterface;


    private void initView() {

        btn_next_sstep = findViewById(R.id.btn_sign_sstep);

        et_ncel = findViewById(R.id.et_ncel_sstep);
        til_ncel = findViewById(R.id.til_ncel_sstep);

        pb_sstep = findViewById(R.id.pb_load_sstep);

        btn_next_tstep = findViewById(R.id.btn_activate_tstep);
        pb_tstep = findViewById(R.id.pb_load_tstep);
        pinView = findViewById(R.id.pinView);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

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
                            Toast.makeText(OAuthActivity.this, "Número de celular incorrecto", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        til_ncel.setError("Número erroneo");
                        Toast.makeText(OAuthActivity.this, "Número de celular incorrecto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    til_ncel.setError("Campo vacío");
                    Toast.makeText(OAuthActivity.this, "Por favor ingrese su número de celular", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_oauth);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(OAuthActivity.this, R.color.colorWhite));
            getWindow()
                    .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //BOTTOM SHEET
        //layout_bottomsheet = findViewById(R.id.bottomsheet_oauth);
        bottomsheetbehavior = BottomSheetBehavior.from(layout_bottomsheet);
        bottomsheetbehavior.setState(bottomsheetbehavior.STATE_HIDDEN);


        mAuth = FirebaseAuth.getInstance();

        initView();
        eventListener();
        setupStatesBS();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupStatesBS() {
        bottomsheetbehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull @NotNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull @NotNull View bottomSheet, float slideOffset) {

            }
        });


    }


    private void sendVerificationCode(String number) {

        try {
            PhoneAuthProvider.verifyPhoneNumber(
                    PhoneAuthOptions
                            .newBuilder(FirebaseAuth.getInstance())
                            .setActivity(this)
                            .setPhoneNumber(number)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setCallbacks(mCallBack)
                            .build());
        } catch (Exception e) {
            Log.e("Error OAuth", "ERROR VERIFICATION CODE: " + e.getMessage().toString());
        }

        /* RESEND CODE
        PhoneAuthProvider.verifyPhoneNumber(
         PhoneAuthOptions
                 .newBuilder(FirebaseAuth.getInstance())
                 .setActivity(this)
                 .setPhoneNumber(phoneNumber)
                 .setTimeout(60L, TimeUnit.SECONDS)
                 .setCallbacks(mCallbacks)
                 .setForceResendingToken(token)
                 .build());
         */
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;

            try {
                pin_otp = "" + s;
                pb_sstep.setVisibility(View.INVISIBLE);
                //pinView.setText(pin_otp);
                bottomsheetbehavior.setState(bottomsheetbehavior.STATE_EXPANDED);
                bottomsheetbehavior.setPeekHeight(5400);
                bottomsheetbehavior.setHideable(false);


            } catch (Exception e) {
                Toast.makeText(OAuthActivity.this, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                try {

                    Integer val = Integer.valueOf(code);
                    pin_otp = "" + val;
                    pinView.setText(pin_otp);

                } catch (Exception e) {
                    Toast.makeText(OAuthActivity.this, "Errror: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            btn_next_sstep.setEnabled(true);
            et_ncel.setEnabled(true);
            pb_sstep.setVisibility(View.INVISIBLE);

            Toast.makeText(OAuthActivity.this, "A intentado muchas veces. Pruebe en unos minutos.", Toast.LENGTH_LONG).show();
            Toast.makeText(OAuthActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    private void verifyCode() {
        String OTP = pinView.getText().toString().trim();
        if (!OTP.isEmpty()) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, OTP);
            signInWithCredential(credential);
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    pb_tstep.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        pinView.setLineColor(Color.GREEN);

                        valideteUser(user.getUid().toString());

                    } else {

                        btn_next_tstep.setEnabled(true);
                        pinView.setEnabled(true);
                        pinView.setLineColor(Color.RED);
                        Toast.makeText(OAuthActivity.this, "Codigo  OC FAIL", Toast.LENGTH_SHORT).show();
                        Toast.makeText(OAuthActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(e -> {

            btn_next_tstep.setEnabled(true);
            pinView.setEnabled(true);
        });
    }

    //SOLICITAR INFORMACIÓN DE LA EXISTENCIA DEL USUARIO
    private void valideteUser(String codeUID) {
        //Log.e("OAuth_res1", "validate user");

        Call<List<JoinResponseModel>> call = apiInterface.
                ClientLogin("valid_login_users", "1", "1", codeUID);
        ApiHelper.enqueueWithRetry(call, new Callback<List<JoinResponseModel>>() {
            @Override
            public void onResponse(Call<List<JoinResponseModel>> call, Response<List<JoinResponseModel>> response) {

                if (response.isSuccessful()) {
                    String code = response.body().get(0).getCode_server().toString();
                    String result = response.body().get(0).getRes_server().toString();
                    String msg = response.body().get(0).getMsg_server();

                    Log.e("OAuth_res", "OnFailure " + response.body().toString() + "\nUID: " + codeUID);

                    //ENVIA LA VARIABLE Y VALIDA CON BOOLEAN SI ES VACIO
                    if (validateEmpty(result) || validateEmpty(msg)) {
                        Toast.makeText(OAuthActivity.this, "Error de ingreso. Espere unos minutos.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //INVOCA METODO DE CAPTURA DE DATOS PRE GUARDADOS
                    if (code.equals("221")) {
                        getDataUser(codeUID);
                    }//BANEADO
                    else if (code.equals("322")) {
                        Toast.makeText(OAuthActivity.this, "Usuario baneado.", Toast.LENGTH_LONG).show();
                    } //REDIRECCIONA AL REGISTRO
                    else if (code.equals("010")) {
                        launchRegisterActivity();
                    } else if (code.equals("110")) {
                        Toast.makeText(OAuthActivity.this, "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<JoinResponseModel>> call, Throwable t) {
                Toast.makeText(OAuthActivity.this, "Error de conexión en el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show();
                Log.e("OAuth_res", "OnFailure " + t.getMessage());
            }
        });


    }

    //OBTENDRA LOS DATOS Y LO GUARDARA EN SP
    private void getDataUser(String codeUID) {

        Call<List<CustomerModel>> call = apiInterface.
                getClientData("insert_select_user", "1", "1", codeUID);
        ApiHelper.enqueueWithRetry(call, new Callback<List<CustomerModel>>() {
            @Override
            public void onResponse(Call<List<CustomerModel>> call, Response<List<CustomerModel>> response) {

                if (response.isSuccessful()) {
                    String code = response.body().get(0).getCode_server().toString();
                    String result = response.body().get(0).getRes_server().toString();
                    String msg = response.body().get(0).getMsg_server();

                    //Log.e("OAuth_res", "OnFailure " + response.body().toString());

                    //ENVIA LA VARIABLE Y VALIDA CON BOOLEAN SI ES VACIO
                    if (validateEmpty(result) || validateEmpty(msg)) {
                        Toast.makeText(OAuthActivity.this, "Error de ingreso. Espere unos minutos.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //INVOCA METODO DE CAPTURA DE DATOS PRE GUARDADOS
                    if (code.equals("221")) {
                        CustomerModel customerModel = response.body().get(0);
                        SessionSP.get(OAuthActivity.this).setPhoneSessSp(response.body().get(0).getCel_cli());
                        SessionSP.get(OAuthActivity.this).saveDataCustomer(customerModel);
                        SessionSP.get(OAuthActivity.this).saveStateLogin("yes");
                        startActivity(new Intent(OAuthActivity.this, InitialActivity.class));
                        finishAffinity();

                    }//REDIRECCIONA AL REGISTRO
                    else if (code.equals("010")) {

                    }
                }

            }

            @Override
            public void onFailure(Call<List<CustomerModel>> call, Throwable t) {
                Toast.makeText(OAuthActivity.this, "Error de conexión en el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show();
                Log.e("OAuth_res", "OnFailure " + t.getMessage());
            }
        });


    }

    //ENVIARA AL SIGT ACTIVITY PARA SU REGiSTRO
    private void launchRegisterActivity() {
        SessionSP.get(OAuthActivity.this).saveStateLogin("register");
        SessionSP.get(OAuthActivity.this).setPhoneSessSp(et_ncel.getText().toString().trim());
        Intent intent = new Intent(OAuthActivity.this, RegisterActivity.class);

        startActivity(intent);
        finishAffinity();
    }

    private boolean validateEmpty(String string) {
        string = string.trim();
        if (!string.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        //finishAffinity();
    }


}