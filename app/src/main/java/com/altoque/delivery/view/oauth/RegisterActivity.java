package com.altoque.delivery.view.oauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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

    /**
     * FOTO
     **/
    CircularImageView civ_picprofile;
    MaterialButton btn_setphoto;

    private StorageReference mStorageRef;
    Uri selectedImage;
    private static final int REQUEST_TAKE_GALLERY_PHOTO = 2;
    StorageReference firebaseRef;

    ProgressBar pb_uploadfoto;

    String imageURL = "";
    String keyProd = "";
    String keyNode = "";

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

        pb_load = findViewById(R.id.pb_load_register);
        pb_uploadfoto = binding.pbUploadfotoRegister;

        civ_picprofile = binding.civPicprofileRegister;
        btn_setphoto = binding.btnSetphotoRegister;


    }

    private void initResources() {
        idgeneroList = new ArrayList<>();
        namegeneroList = new ArrayList<>();
        til_genero.setEnabled(false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mStorageRef = FirebaseStorage.getInstance().getReference();
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
        getWindow()
                .setStatusBarColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));

        initView();
        initResources();
        eventListener();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        guid = user.getUid().toString();

        //Toast.makeText(this, ""+guid, Toast.LENGTH_SHORT).show();
        getGenero();
        //SessionSP.get(RegisterActivity.this).saveStateLogin("yes");
    }


    private void getGenero() {

        Call<List<GeneroModel>> call = apiInterface.getGenero("select_gender");
        ApiHelper.enqueueWithRetry(call, new Callback<List<GeneroModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GeneroModel>> call, @NonNull Response<List<GeneroModel>> response) {
                if (response.isSuccessful()) {


                    if (!idgeneroList.isEmpty() || !namegeneroList.isEmpty()) {
                        namegeneroList.removeAll(namegeneroList);
                        idgeneroList.removeAll(idgeneroList);
                    }
                    //Log.e("Reg_error", "Success " + response.body().toString());
                    assert response.body() != null;
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
            public void onFailure(@NotNull Call<List<GeneroModel>> call, Throwable t) {
                Log.e("Reg_error", "Fail " + t.getMessage());
            }
        });
    }


    public void openGallery(View view) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                && !Environment.getExternalStorageState().equals(
                Environment.MEDIA_CHECKING)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(RegisterActivity.this, "Galeria no encontrada.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Bitmap originBitmap = null;
                selectedImage = data.getData();
                InputStream imageStream;
                try {
                    //pbbar.setVisibility(View.VISIBLE);
                    imageStream = getContentResolver().openInputStream(selectedImage);
                    originBitmap = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (originBitmap != null) {
                    {
                        this.civ_picprofile.setImageBitmap(originBitmap);

                    }
                } else
                    selectedImage = null;
            }

        }
    }

    public String GetDate() {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MMddHHmm");
        return df.format(Calendar.getInstance().getTime());
    }

    public void UploadImages() {
        try {
            pb_uploadfoto.setVisibility(View.VISIBLE);
            btn_setphoto.setEnabled(false);
            til_genero.setEnabled(false);

            btn_next.setEnabled(false);

            String strFileName = "picprof" + Objects.requireNonNull(til_name.getEditText()).getText().toString() + "-" + GetDate() + ".jpg";

            Uri file = null;
            file = selectedImage;

            if (file != null) {

                firebaseRef = mStorageRef.child("assets" + "/" + "profile" + "/" + "customer" + "/" + strFileName);

                UploadTask uploadTask = firebaseRef.putFile(file);
                Log.e("Fire Path", firebaseRef.toString());
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }
                        return firebaseRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        pb_uploadfoto.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            selectedImage = null;
                            imageURL = downloadUri.toString();
                            String name = til_name.getEditText().getText().toString();
                            String lname = Objects.requireNonNull(til_lastname.getEditText()).getText().toString();
                            registerCustomer(name, lname, imageURL);

                        } else {
                            //simpleProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "No se logró subir la foto."
                                    , Toast.LENGTH_LONG).show();
                            pb_load.setVisibility(View.GONE);
                            btn_next.setEnabled(true);
                            btn_setphoto.setEnabled(true);
                            til_genero.setEnabled(true);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Por favor seleccione una imagen.", Toast.LENGTH_SHORT).show();

                pb_uploadfoto.setVisibility(View.GONE);
                pb_load.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Toast.makeText(RegisterActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            pb_uploadfoto.setVisibility(View.GONE);
        }


    }

    private void validateInput() {
        if (!guid.isEmpty()) {
            String name = Objects.requireNonNull(til_name.getEditText()).getText().toString();
            String lname = Objects.requireNonNull(til_lastname.getEditText()).getText().toString();
            if (name.isEmpty()) {
                til_name.setError("Ingrese su Nombre");

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
                UploadImages();

                pb_load.setVisibility(View.VISIBLE);
                btn_next.setEnabled(false);
            }
        }
    }

    private void registerCustomer(String name, String lname, String photo) {

        String token = "111";
        String phone = "";

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
            public void onResponse(@NotNull Call<List<CustomerModel>> call, @NotNull Response<List<CustomerModel>> response) {

                if (response.isSuccessful()) {
                    //Log.e("Register_error", "uid: "+guid+"\nbody: "+response.body());
                    assert response.body() != null;
                    switch (response.body().get(0).getCode_server()) {
                        case "221":
                            pb_load.setVisibility(View.GONE);
                            btn_next.setEnabled(true);
                            til_genero.setEnabled(true);

                            c.setIdcliente(response.body().get(0).getIdcliente());
                            SessionSP.get(RegisterActivity.this).saveDataCustomer(c);
                            SessionSP.get(RegisterActivity.this).saveStateLogin("dirclient");

                            Intent intent = new Intent(RegisterActivity.this, DirectionClientActivity.class);
                            intent.putExtra("action", "register_data");
                            intent.putExtra("state_use", "1");
                            intent.putExtra("value_source", "actv_register_client");
                            startActivity(intent);
                            finish();
                            break;
                        case "010":
                            til_genero.setEnabled(true);
                            btn_next.setEnabled(true);
                            pb_load.setVisibility(View.GONE);
                            break;
                        case "110":
                            Toast.makeText(RegisterActivity.this, "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                            break;
                    }

                } else {
                    pb_load.setVisibility(View.GONE);
                    btn_next.setEnabled(true);
                    til_genero.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "No se logró registrar.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<CustomerModel>> call, @NotNull Throwable t) {
                pb_load.setVisibility(View.GONE);
                btn_next.setEnabled(true);
                Log.e("Register_error", "error: " + t.getMessage().toString());
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Por favor finalice su registro.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}