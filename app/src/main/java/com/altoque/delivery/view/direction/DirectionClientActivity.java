package com.altoque.delivery.view.direction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.view.initial.InitialActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionClientActivity extends AppCompatActivity implements OnMapReadyCallback {

    ExtendedFloatingActionButton feb_next, feb_update;
    EditText et_name, et_reference, et_numberflat;
    //, et_distrito;

    String value_action = "";
    String value_iddom = "";
    String value_use = "";
    String value_source = "";


    private FirebaseAuth mAuth;
    public ApiInterface apiInterface;

    GoogleMap mMap;
    private Marker globalMarker;
    private double globalLat = 0.0;
    private double globalLng = 0.0;
    private Marker globalMarkerOld;
    private double globalLatOld = 0.0;
    private double globalLngOld = 0.0;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private SupportMapFragment mapFragment;
    LatLng globalCoords;
    LatLng globalCoordsOld;

    AlertDialog mat;

    List<Address> list;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private static final String TAG = MainActivity.class.getSimpleName();

    Boolean value_state = false;

    private void initView() {

        et_name = findViewById(R.id.et_namedir_dirclient);
        et_reference = findViewById(R.id.et_reference_dirclient);
        et_numberflat = findViewById(R.id.et_numberflat_dirclient);
        //et_distrito = findViewById(R.id.et_distrito_dirclient);

        feb_next = findViewById(R.id.feb_next_dirclient);
        feb_update = findViewById(R.id.feb_update_dirclient);

    }


    private void initResources() {

        mAuth = FirebaseAuth.getInstance();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_dirclient);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        mat = new AlertDialog.Builder(DirectionClientActivity.this).create();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void compilateInit() {
        initView();
        initResources();
        eventListener();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_client);

        getWindow()
                .setStatusBarColor(ContextCompat.getColor(DirectionClientActivity.this,
                        R.color.colorWhite));
        getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        compilateInit();

        if (getIntent().getExtras() != null) {
            String action = getIntent().getExtras().getString("action");
            String iddom = getIntent().getExtras().getString("value_id");
            value_use = getIntent().getExtras().getString("state_use");
            value_source = getIntent().getExtras().getString("value_source");

            //Toast.makeText(this, "" + value_use, Toast.LENGTH_SHORT).show();
            //Log.e("DirectionClient_log", "intent "+iddom);
            //Log.e("DirectionClient_log", "action "+action);

            if (!action.isEmpty()) {

                if (action.equals("edit_data")) {
                    if (!iddom.isEmpty()) {
                        value_action = action;
                        value_iddom = iddom;
                        et_name.setEnabled(false);
                        et_numberflat.setEnabled(false);
                        et_reference.setEnabled(false);
                        feb_update.setVisibility(View.VISIBLE);
                        TextView tv_dir = findViewById(R.id.tv_directionOld_dirclient);
                        tv_dir.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, "No se cargaron los datos correctamente.", Toast.LENGTH_SHORT).show();
                        mapFragment.onDestroy();
                        finish();
                    }

                } else if (action.equals("register_data")) {
                    value_action = action;
                    feb_next.setVisibility(View.VISIBLE);
                }
            }
        }


        getLastLocation();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFragment.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
    }


    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        requestNewLocationData();

                        if (ActivityCompat.checkSelfPermission(DirectionClientActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DirectionClientActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setCompassEnabled(false);

                        View locationButton = ((View) mapFragment.requireView().findViewById(Integer.parseInt("1")).
                                getParent()).findViewById(Integer.parseInt("2"));

                        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                        rlp.setMargins(0, 500, 30, 0);

                    }
                });
                //Log.e("DirectionClient_log", "getlast "+value_action);
                if (value_action.equals("edit_data")) {
                    //Log.e("DirectionClient_log", " validete getlast "+value_action);
                    getDataDefaultDirection(value_iddom);
                }


            } else {
                Toast.makeText(this, "Por favor active su GPS.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            dialogRequestLocation();
        }
    }

    private void dialogRequestLocation() {

        final View customLayout = getLayoutInflater()
                .inflate(R.layout.layout_requestlocation_dialog, null);

        mat.setView(customLayout);

        mat.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                MaterialButton done = customLayout.findViewById(R.id.btn_requestdialog);

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissions();
                    }
                });
            }
        });

        mat.setCancelable(false);
        mat.show();
    }

    private void requestNewLocationData() {

        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(60)
                .setFastestInterval(0)
                .setNumUpdates(3);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            globalLat = mLastLocation.getLatitude();
            globalLng = mLastLocation.getLongitude();

            if (mLastLocation.getLatitude() != 0.0 && mLastLocation.getLongitude() != 0.0) {
                try {

                    Geocoder geocoder = new Geocoder(DirectionClientActivity.this, Locale.getDefault());
                    list = geocoder.getFromLocation(
                            mLastLocation.getLatitude(),
                            mLastLocation.getLongitude(),
                            1);
                    String dir = String.valueOf(list.get(0).getAddressLine(0));

                    TextView tv_dir = findViewById(R.id.tv_directionnew_dirclient);

                    tv_dir.setText("Dirección actual: " + dir);

                    //Log.e("DirectionClient_log", ""+list.get(0).toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            agregarMarcadorActual(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_ID);
        mat.dismiss();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void agregarMarcadorActual(double lat, double lng) {
        globalCoords = new LatLng(lat, lng);

        if (globalMarker != null) globalMarker.remove();
        globalMarker = mMap.addMarker(new MarkerOptions()
                .position(globalCoords)
                .title("Ubicación actual"));
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconomarkusuario)));
        CameraUpdate miU = CameraUpdateFactory.newLatLngZoom(globalCoords, 15);
        mMap.moveCamera(miU);
    }

    private void getDataDefaultDirection(String iddomicilio) {

        String idclient = SessionSP.get(this).getIdClientSessSp();
        try {
            Call<List<DomicilioModel>> call = apiInterface.
                    getDataByIdDirection("insert_select_adress", "6", iddomicilio, idclient);
            ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<DomicilioModel>> call,
                                       @NotNull Response<List<DomicilioModel>> response) {
                    //pb_load_direction.setVisibility(View.GONE);
                    //Log.e("DirectionClient_log", "body: " + response.body());
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String code = response.body().get(0).getCode_server().toString();
                        //Log.e("DirectionClient_log", "body: " + response.body());

                        if (code.equals("221")) {
                            String namedir = response.body().get(0).getDireccion_dom().toString();
                            String ref = response.body().get(0).getReferencia_dom().toString();
                            String numberf = response.body().get(0).getPiso_dom().toString();
                            String lat = response.body().get(0).getLat_dom().toString();
                            String lng = response.body().get(0).getLong_dom().toString();

                            et_name.setText(namedir);
                            et_reference.setText(ref);
                            et_numberflat.setText(numberf);
                            agregarMarcadorGuardado(Double.parseDouble(lat), Double.parseDouble(lng));
                            getDirectionDefault(Double.parseDouble(lat), Double.parseDouble(lng));

                        } else if (code.equals("010")) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<DomicilioModel>> call, @NotNull Throwable t) {
                    //pb_load_direction.setVisibility(View.GONE);

                }
            });
        } catch (Exception e) {
        }
    }

    @SuppressLint("SetTextI18n")
    private void getDirectionDefault(double lat, double lng) {
        try {

            Geocoder geocoder = new Geocoder(DirectionClientActivity.this, Locale.getDefault());
            list = geocoder.getFromLocation(
                    lat,
                    lng,
                    1);
            String dir = String.valueOf(list.get(0).getAddressLine(0));

            TextView tv_dir = findViewById(R.id.tv_directionOld_dirclient);
            tv_dir.setText("Dirección guardada: " + dir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void agregarMarcadorGuardado(double lat, double lng) {
        globalCoordsOld = new LatLng(lat, lng);

        if (globalMarkerOld != null) globalMarkerOld.remove();
        globalMarkerOld = mMap.addMarker(new MarkerOptions()
                .position(globalCoordsOld)
                .title("Ubicación guardada"));
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconomarkusuario)));
        CameraUpdate miU = CameraUpdateFactory.newLatLngZoom(globalCoordsOld, 15);
        mMap.moveCamera(miU);
    }


    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng cord = new LatLng(-11.108078, -77.606723);
        CameraUpdate miU = CameraUpdateFactory.newLatLngZoom(cord, 15);
        mMap.moveCamera(miU);
    }

    private void validateRegister() {

        try {


            //if (!Objects.requireNonNull(globalMarker.getTitle()).isEmpty()) {
            //if (globalLat != 0.0 && globalLng != 0.0) {

            String name = et_name.getText().toString();
            String reference = et_reference.getText().toString();
            Integer numberf = Integer.parseInt(et_numberflat.getText().toString());


            String idcliente = SessionSP.get(DirectionClientActivity.this).
                    getIdClientSessSp();
            String lat = String.valueOf(globalLat);
            String lng = String.valueOf(globalLng);

            if (name.isEmpty()) {
                et_name.setError("Ingrese un nombre a la dirección");
                return;
            } else if (reference.isEmpty()) {
                et_reference.setError("Ingrese alguna referencia");
                et_name.setError(null);
                et_numberflat.setError(null);
                return;
            } else if (numberf < 1 || numberf > 99) {
                et_numberflat.setError("Número incorrecto. Máximo hasta 99.");
                et_reference.setError(null);
                et_name.setError(null);
                return;
            } else {
                Log.e("DirectionClient_log", "reg 0" + idcliente);
                disableInput();
                et_name.setError(null);
                et_reference.setError(null);
                et_numberflat.setError(null);

                if (value_action.equals("edit_data")) {
                    updateDirection(name, String.valueOf(numberf), reference, lat, lng, idcliente, value_iddom);
                    //Toast.makeText(this, "to Update", Toast.LENGTH_SHORT).show();
                } else if (value_action.equals("register_data")) {
                    Log.e("DirectionClient_log", "reg 1" + idcliente);
                    registerDirection(name, String.valueOf(numberf), reference, lat, lng, idcliente);
                }

                //disableInput();
            }
            /*}*/

        /*} else {
            Toast.makeText(this, "Por favor espere que se cargue.", Toast.LENGTH_SHORT).show();
        }*/

        } catch (Exception e) {
            Log.e("DirectionClient_log", "TRY " + e.getMessage().toString());
        }
    }

    private void disableInput() {
        et_name.setEnabled(false);
        et_numberflat.setEnabled(false);
        et_reference.setEnabled(false);
    }

    private void enableInput() {
        feb_next.setEnabled(true);
        feb_update.setEnabled(true);
        et_name.setEnabled(true);
        et_numberflat.setEnabled(true);
        et_reference.setEnabled(true);
    }


    private void registerDirection(String namedir, String numberflat, String reference,
                                   String lat, String lng, String idcliente) {

        String estado_uso = value_use;
        if (!estado_uso.isEmpty()) {

            Call<List<DomicilioModel>> call = apiInterface.setDirectionRegister("insert_select_adress",
                    "2", namedir, numberflat, reference, lat, lng, idcliente, estado_uso);

            ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<DomicilioModel>> call,
                                       @NotNull Response<List<DomicilioModel>> response) {
                    if (response.isSuccessful()) {
                        Log.e("DirectionClient_log", "body: " + response.body());
                        assert response.body() != null;
                        switch (response.body().get(0).getCode_server()) {
                            case "221":
                                SessionSP.get(DirectionClientActivity.this).saveStateLogin("yes");

                                if (value_source.equals("actv_register_client")) {
                                    Intent intent = new Intent(DirectionClientActivity.this, InitialActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (value_action.equals("bts_list_direction")) {
                                    Toast.makeText(DirectionClientActivity.this,
                                            "Registro exitoso.", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                break;
                            case "110":
                                Toast.makeText(DirectionClientActivity.this,
                                        "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                                enableInput();
                                break;
                            case "010":
                                Toast.makeText(DirectionClientActivity.this,
                                        "Máxima cantidad de direcciones excedidas.", Toast.LENGTH_LONG).show();
                                //disableInput();
                                break;
                        }

                    } else {
                        Toast.makeText(DirectionClientActivity.this, "No se logró registrar.", Toast.LENGTH_SHORT).show();
                        enableInput();
                    }
                }

                @Override
                public void onFailure(Call<List<DomicilioModel>> call, Throwable t) {
                    Log.e("DirClient_error", "error: " + t.getMessage());
                    enableInput();
                }
            });

        }
    }

    private void updateDirection(String namedir, String numberflat, String reference,
                                 String lat, String lng, String idcliente, String iddom) {

        Call<List<DomicilioModel>> call = apiInterface.setDirectionUpdate("insert_select_adress",
                "5", namedir, numberflat, reference, lat, lng, idcliente, iddom);

        ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<DomicilioModel>> call,
                                   @NotNull Response<List<DomicilioModel>> response) {
                if (response.isSuccessful()) {
                    //Log.e("DirectionClient_log", "body: " + response.body());
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        switch (response.body().get(0).getCode_server()) {
                            case "221":
                                Toast.makeText(DirectionClientActivity.this, "Actualización completa.", Toast.LENGTH_SHORT).show();
                                disableInput();
                                feb_update.setText("Actualizar dirección");
                                value_state = false;
                                break;
                            case "110":
                                Toast.makeText(DirectionClientActivity.this,
                                        "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                                enableInput();
                                break;
                            case "010":

                                break;
                        }
                    }

                } else {
                    Toast.makeText(DirectionClientActivity.this, "No se logró registrar.", Toast.LENGTH_SHORT).show();
                    enableInput();
                }
            }

            @Override
            public void onFailure(Call<List<DomicilioModel>> call, Throwable t) {
                Log.e("DirClient_error", "error: " + t.getMessage());
                enableInput();
            }
        });
    }


    private void eventListener() {

        feb_next.setOnClickListener(v -> {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
            validateRegister();
        });

        feb_update.setOnClickListener(v -> {
            //feb_update.
            //Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
            if (value_state == false) {
                //Toast.makeText(this, "VALUE GUARDAR" + value_state, Toast.LENGTH_SHORT).show();

                feb_update.setText("Guardar dirección");
                value_state = true;
                et_name.setEnabled(true);
                et_numberflat.setEnabled(true);
                et_reference.setEnabled(true);

            } else {
                //Toast.makeText(this, "VALUE " + value_state, Toast.LENGTH_SHORT).show();
                validateRegister();
            }
        });

    }
}