package com.altoque.delivery.view.oauth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.FragmentOauthInitialBinding;
import com.altoque.delivery.databinding.FragmentOauthVerifyBinding;
import com.altoque.delivery.model.CustomerModel;
import com.altoque.delivery.model.JoinResponseModel;
import com.altoque.delivery.view.initial.InitialActivity;
import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OAuthVerifyFragment extends Fragment {

   View root;

   private FragmentOauthVerifyBinding binding;

    String name = "";
    String phone = "";

    private String verificationId;
    private String numberPhone;

    private PinView pinView;
    private MaterialButton btn_next_tstep;
    private ProgressBar pb_tstep;

    String type_oauth = "", pin_otp = "";

    public ApiInterface apiInterface;

    private FirebaseAuth mAuth;


    public OAuthVerifyFragment() {
        // Required empty public constructor
    }

    private void initView() {


        btn_next_tstep = binding.btnActivateTstep;
        pb_tstep = binding.pbLoadTstep;
        pinView = binding.pinView;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    }

    private void eventListener() {

        btn_next_tstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = Objects.requireNonNull(pinView.getText()).toString().trim();

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            verificationId = getArguments().getString("verificationId");
            numberPhone = getArguments().getString("numberPhone");

        }else{
            Toast.makeText(requireContext(), "Nulo arg", Toast.LENGTH_SHORT).show();}
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOauthVerifyBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        //root = inflater.inflate(R.layout.fragment_oauth_verify, container, false);

        mAuth = FirebaseAuth.getInstance();
        initView();
        eventListener();



        return root;
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
                .addOnCompleteListener(task -> {

                    pb_tstep.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        pinView.setLineColor(Color.GREEN);

                        assert user != null;
                        valideteUser(user.getUid().toString());

                    } else {

                        btn_next_tstep.setEnabled(true);
                        pinView.setEnabled(true);
                        pinView.setLineColor(Color.RED);
                        Toast.makeText(requireContext(), "Codigo  OC FAIL", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
            public void onResponse(@NotNull Call<List<JoinResponseModel>> call, Response<List<JoinResponseModel>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String code = response.body().get(0).getCode_server().toString();
                    String result = response.body().get(0).getRes_server().toString();
                    String msg = response.body().get(0).getMsg_server();

                    Log.e("OAuth_res", "OnFailure " + response.body().toString() + "\nUID: " + codeUID);

                    //ENVIA LA VARIABLE Y VALIDA CON BOOLEAN SI ES VACIO
                    if (validateEmpty(result) || validateEmpty(msg)) {
                        Toast.makeText(requireContext(), "Error de ingreso. Espere unos minutos.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //INVOCA METODO DE CAPTURA DE DATOS PRE GUARDADOS
                    if (code.equals("221")) {
                        getDataUser(codeUID);
                    }//BANEADO
                    else if (code.equals("322")) {
                        Toast.makeText(requireContext(), "Usuario baneado.", Toast.LENGTH_LONG).show();
                    } //REDIRECCIONA AL REGISTRO
                    else if (code.equals("010")) {
                        launchRegisterActivity();
                    } else if (code.equals("110")) {
                        Toast.makeText(requireContext(), "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<JoinResponseModel>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión en el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show();
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
            public void onResponse(@NotNull Call<List<CustomerModel>> call, Response<List<CustomerModel>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String code = response.body().get(0).getCode_server().toString();
                    String result = response.body().get(0).getRes_server().toString();
                    String msg = response.body().get(0).getMsg_server();

                    //Log.e("OAuth_res", "OnFailure " + response.body().toString());

                    //ENVIA LA VARIABLE Y VALIDA CON BOOLEAN SI ES VACIO
                    if (validateEmpty(result) || validateEmpty(msg)) {
                        Toast.makeText(requireContext(), "Error de ingreso. Espere unos minutos.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //INVOCA METODO DE CAPTURA DE DATOS PRE GUARDADOS
                    if (code.equals("221")) {
                        CustomerModel customerModel = response.body().get(0);
                        SessionSP.get(requireContext()).setPhoneSessSp(response.body().get(0).getCel_cli());
                        SessionSP.get(requireContext()).saveDataCustomer(customerModel);
                        SessionSP.get(requireContext()).saveStateLogin("yes");
                        startActivity(new Intent(requireContext(), InitialActivity.class));

                        requireActivity().finishAffinity();

                    }//REDIRECCIONA AL REGISTRO
                    else if (code.equals("010")) {

                    }
                }

            }

            @Override
            public void onFailure(Call<List<CustomerModel>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión en el servidor. Intente mas tarde.", Toast.LENGTH_LONG).show();
                Log.e("OAuth_res", "OnFailure " + t.getMessage());
            }
        });


    }

    //ENVIARA AL SIGT ACTIVITY PARA SU REGiSTRO
    private void launchRegisterActivity() {


        SessionSP.get(requireContext()).saveStateLogin("register");
        SessionSP.get(requireContext()).setPhoneSessSp(numberPhone);
        Intent intent = new Intent(requireContext(), RegisterActivity.class);

        startActivity(intent);
        requireActivity().finishAffinity();
    }

    private boolean validateEmpty(String string) {
        string = string.trim();
        if (!string.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}