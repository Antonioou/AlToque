package com.altoque.delivery.view.oauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.ActivityRegisterBinding;
import com.altoque.delivery.model.CustomerModel;
import com.altoque.delivery.model.GeneroModel;
import com.altoque.delivery.view.direction.DirectionClientActivity;
import com.altoque.delivery.view.initial.InitialActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    Button btn_next;

    private TextInputLayout til_name, til_lastname, til_genero;
    private ProgressBar pb_load;

    public ApiInterface apiInterface;

    FirebaseAuth mAuth;

    /* genero */
    List<String> idgeneroList;
    List<String> namegeneroList;

    String guid = "";
    String posGenero = "";

    private void initView() {

        btn_next = findViewById(R.id.btn_next_register);
        til_name = findViewById(R.id.til_firstname_register);
        til_lastname = findViewById(R.id.til_lastname_register);
        til_genero = findViewById(R.id.til_genero_register);
        til_genero.setEnabled(false);


        pb_load = findViewById(R.id.pb_load_register);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    private void initResources() {
        idgeneroList = new ArrayList<>();
        namegeneroList = new ArrayList<>();
    }


    private void eventListener() {

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));
        }

        initView();
        initResources();
        eventListener();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        guid = user.getUid().toString();

        //Toast.makeText(this, ""+guid, Toast.LENGTH_SHORT).show();
        getGenero();
        //SessionSP.get(RegisterActivity.this).saveStateLogin("yes");
    }


    private void getGenero() {

        Call<List<GeneroModel>> call = apiInterface.getGenero("select_gender");
        ApiHelper.enqueueWithRetry(call, new Callback<List<GeneroModel>>() {
            @Override
            public void onResponse(Call<List<GeneroModel>> call, Response<List<GeneroModel>> response) {
                if (response.isSuccessful()) {


                    if (!idgeneroList.isEmpty() || !namegeneroList.isEmpty()) {
                        namegeneroList.removeAll(namegeneroList);
                        idgeneroList.removeAll(idgeneroList);
                    }
                    //Log.e("Reg_error", "Success " + response.body().toString());
                    if (response.body().get(0).getCode_server().equals("010")) {

                        Toast.makeText(RegisterActivity.this, "Hubo un problema al cargar los datos.", Toast.LENGTH_SHORT).show();

                    } else if (response.body().get(0).getCode_server().equals("221")) {

                        til_genero.setEnabled(true);

                        for (GeneroModel responseRes : response.body()) {
                            namegeneroList.add(responseRes.getNombre_genero());
                            idgeneroList.add(responseRes.getIdgenero());
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                                R.layout.dropdown_menu_popup_item, namegeneroList);
                        AutoCompleteTextView editTextFilledExposedDropdown =
                                findViewById(R.id.actv_genero_register);
                        editTextFilledExposedDropdown.setAdapter(arrayAdapter);
                        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                posGenero = idgeneroList.get(i);

                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<GeneroModel>> call, Throwable t) {
                Log.e("Reg_error", "Fail " + t.getMessage());
            }
        });
    }


    private void validateInput() {
        if (!guid.isEmpty()) {
            String name = til_name.getEditText().getText().toString();
            String lname = til_lastname.getEditText().getText().toString();

            if (name.isEmpty()) {
                til_name.setError("Ingrese su Nombre");
                return;
            } else if (lname.isEmpty()) {
                til_lastname.setError("Ingrese su Apellido");
                til_name.setError(null);
                til_genero.setError(null);
                return;
            } else if (posGenero.isEmpty()) {
                til_genero.setError("Seleccione su genero");
                til_name.setError(null);
                til_lastname.setError(null);
                return;
            } else {
                til_name.setError(null);
                til_lastname.setError(null);
                til_genero.setError(null);
                registerCustomer(name, lname);
                pb_load.setVisibility(View.VISIBLE);
                btn_next.setEnabled(false);
            }
        }
    }

    private void registerCustomer(String name, String lname) {

        til_genero.setEnabled(false);

        String token = "111";
        String phone = "";
        String photo = "foto.img";

        if (!SessionSP.get(RegisterActivity.this).getPhoneSessSp().isEmpty()) {
            phone = SessionSP.get(RegisterActivity.this).getPhoneSessSp();
        } else {
            Toast.makeText(this, "No se cargo el Número de celular", Toast.LENGTH_SHORT).show();
            return;
        }

        CustomerModel c = new CustomerModel();

        c.setNombrecli(name);
        c.setApellidos_cli(lname);
        c.setCel_cli(phone);
        c.setCodigoUID_cli(guid);
        c.setFoto_cli(photo);

        Call<List<CustomerModel>> call = apiInterface.
                ClientRegister("insert_select_user", "1", "2",
                        guid, phone, name, lname, photo, posGenero, token);
        ApiHelper.enqueueWithRetry(call, new Callback<List<CustomerModel>>() {
            @Override
            public void onResponse(Call<List<CustomerModel>> call, Response<List<CustomerModel>> response) {

                if (response.isSuccessful()) {
                    Log.e("Register_error", "uid: "+guid+"\nbody: "+response.body());
                    if (response.body().get(0).getCode_server().equals("221")) {
                        pb_load.setVisibility(View.GONE);
                        btn_next.setEnabled(true);
                        til_genero.setEnabled(true);

                        c.setIdcliente(response.body().get(0).getIdcliente());
                        SessionSP.get(RegisterActivity.this).saveDataCustomer(c);
                        SessionSP.get(RegisterActivity.this).saveStateLogin("dirclient");

                        final ConstraintLayout cl_sectionreg_register = findViewById(R.id.cl_sectionreg_register);

                        Intent intent = new Intent(RegisterActivity.this, DirectionClientActivity.class);
                        /*ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(RegisterActivity.this,
                                        cl_sectionreg_register,
                                        "go_dirclient_transition");
                        startActivity(intent, options.toBundle());*/
                        startActivity(intent);
                        finish();
                    } else if (response.body().get(0).getCode_server().equals("010")) {
                        til_genero.setEnabled(true);
                    } else if (response.body().get(0).getCode_server().equals("110")) {
                        Toast.makeText(RegisterActivity.this, "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    pb_load.setVisibility(View.GONE);
                    btn_next.setEnabled(true);
                    til_genero.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "No se logró registrar.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<CustomerModel>> call, Throwable t) {
                pb_load.setVisibility(View.GONE);
                btn_next.setEnabled(true);
                Log.e("Register_error", "error: " + t.getMessage().toString());
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Por favor finalice su registro.", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return super.onKeyDown(keyCode, event);
    }

}