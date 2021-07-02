package com.altoque.delivery.view.direction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.se.omapi.SEService;
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
import com.altoque.delivery.view.oauth.OAuthActivity;
import com.altoque.delivery.view.oauth.RegisterActivity;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionClientActivity extends AppCompatActivity implements OnMapReadyCallback {

    ExtendedFloatingActionButton feb_next;
    EditText et_name, et_reference, et_numberflat, et_distrito;


    private FirebaseAuth mAuth;
    public ApiInterface apiInterface;

    GoogleMap mMap;
    private Marker globalMarker;
    private double globalLat = 0.0;
    private double globalLng = 0.0;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private SupportMapFragment mapFragment;
    LatLng globalCoords;

    AlertDialog mat;

    List<Address> list;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private static final String TAG = MainActivity.class.getSimpleName();


    private void initView() {

        et_name = findViewById(R.id.et_namedir_dirclient);
        et_reference = findViewById(R.id.et_reference_dirclient);
        et_numberflat = findViewById(R.id.et_numberflat_dirclient);
        et_distrito = findViewById(R.id.et_distrito_dirclient);

        feb_next = findViewById(R.id.feb_next_dirclient);

    }

    private void eventListener() {

        feb_next.setOnClickListener(v -> validateRegister());

    }

    private void initResources() {

        mAuth = FirebaseAuth.getInstance();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_dirclient);
        mapFragment.getMapAsync(this::onMapReady);

        mat = new AlertDialog.Builder(DirectionClientActivity.this).create();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void compilateInit() {
        initView();
        initResources();
        eventListener();
        getLastLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_client);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(DirectionClientActivity.this,
                            R.color.colorWhite));
            getWindow()
                    .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        compilateInit();


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
                        Location location = task.getResult();
                        /*Integer retry = 0;
                        if (retry == 0) {
                            if (location == null) {
                                retry += 1;*/
                        requestNewLocationData();

                        if (ActivityCompat.checkSelfPermission(DirectionClientActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DirectionClientActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setCompassEnabled(false);

                        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).
                                getParent()).findViewById(Integer.parseInt("2"));

                        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                        rlp.setMargins(0, 20, 30, 0);


                            /*} else {
                                getNameDirection(location);
                            }
                        }*/
                    }
                });
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

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //Log.e("Dir_error", "Latitude: " + mLastLocation.getLatitude() + "");
            //Log.e("Dir_error", "Longitude: " + mLastLocation.getLongitude() + "");

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

                    TextView tv_dir = findViewById(R.id.tv_namedirection_dirclient);
                    tv_dir.setText(dir);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            agregarMarcador(mLastLocation.getLatitude(), mLastLocation.getLongitude());

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

    private void agregarMarcador(double lat, double lng) {
        globalCoords = new LatLng(lat, lng);

        if (globalMarker != null) globalMarker.remove();
        globalMarker = mMap.addMarker(new MarkerOptions()
                .position(globalCoords)
                .title("MI UBICACIÓN"));
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconomarkusuario)));
        CameraUpdate miU = CameraUpdateFactory.newLatLngZoom(globalCoords, 15);
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
        if (!globalMarker.getTitle().isEmpty()) {
            if (globalLat != 0.0 && globalLng != 0.0) {
                String name = et_name.getText().toString();
                String reference = et_reference.getText().toString();
                String distrito = et_distrito.getText().toString();
                String numberf = et_numberflat.getText().toString();

                String idcliente = SessionSP.get(DirectionClientActivity.this).
                        getIdClientSessSp();
                String lat = String.valueOf(globalLat);
                String lng = String.valueOf(globalLng);

                if (name.isEmpty()) {
                    et_name.setError("Ingrese un nombre");
                    return;
                } else if (reference.isEmpty()) {
                    et_reference.setError("Ingrese alguna referencia");
                    et_name.setError(null);
                    et_numberflat.setError(null);
                    return;
                } else if (numberf.isEmpty()) {
                    et_name.setError("Ingrese el número de pise");
                    et_reference.setError(null);
                    et_numberflat.setError(null);
                    return;
                } else {
                    et_name.setError(null);
                    et_reference.setError(null);
                    et_numberflat.setError(null);
                    et_distrito.setError(null);
                    registerDirection(name, numberf, reference, "1", lat, lng, idcliente);
                    disableInput();
                }
            }
            Toast.makeText(this, "Por favor espere que se cargue.", Toast.LENGTH_SHORT).show();
        }
    }

    private void disableInput() {
        feb_next.setEnabled(false);
        et_name.setEnabled(false);
        et_distrito.setEnabled(false);
        et_numberflat.setEnabled(false);
        et_reference.setEnabled(false);
    }

    private void enableInput() {
        feb_next.setEnabled(true);
        et_name.setEnabled(true);
        et_distrito.setEnabled(true);
        et_numberflat.setEnabled(true);
        et_reference.setEnabled(true);
    }


    private void registerDirection(String namedir, String numberflat, String reference, String iddistrito,
                                   String lat, String lng, String idcliente) {

        Call<List<DomicilioModel>> call = apiInterface.DirectionRegister("insert_select_adress",
                "2", namedir, numberflat, reference, lat, lng, iddistrito, idcliente);

        ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
            @Override
            public void onResponse(Call<List<DomicilioModel>> call, Response<List<DomicilioModel>> response) {
                if (response.isSuccessful()) {
                    Log.e("Register_error", "body: " + response.body());
                    if (response.body().get(0).getCode_server().equals("221")) {

                        SessionSP.get(DirectionClientActivity.this).saveStateLogin("yes");

                        Intent intent = new Intent(DirectionClientActivity.this, InitialActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.body().get(0).getCode_server().equals("110")) {
                        Toast.makeText(DirectionClientActivity.this, "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                        enableInput();
                    }

                } else {
                    Toast.makeText(DirectionClientActivity.this, "No se logró registrar.", Toast.LENGTH_SHORT).show();
                    enableInput();
                }
            }

            @Override
            public void onFailure(Call<List<DomicilioModel>> call, Throwable t) {
                Log.e("DirClient_error", "error: " + t.getMessage().toString());
                enableInput();
            }
        });
    }
}