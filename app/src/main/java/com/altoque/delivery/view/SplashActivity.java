package com.altoque.delivery.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.AccessResponseModel;
import com.altoque.delivery.model.GeneroModel;
import com.altoque.delivery.utils.ConnectivityReceiver;
import com.altoque.delivery.utils.initClass;
import com.altoque.delivery.view.direction.DirectionClientActivity;
import com.altoque.delivery.view.oauth.RegisterActivity;
import com.altoque.delivery.view.initial.InitialActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReciverListener {

    private TextView tv_state;

    AlertDialog mat;

    ApiInterface api;

    List<Address> list;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //requestWindowFeature(1);
        /*Button crashButton = new Button(this);
        crashButton.setText("Test Crash");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });
        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

*/
        getWindow()
                .setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.colorPrimary));
        tv_state = findViewById(R.id.tv_load_splash);

        api = ApiClient.getApiClient().create(ApiInterface.class);

        mat = new AlertDialog.Builder(SplashActivity.this).create();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void getLastLocation() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkPermissionsQ()) {
                if (isLocationEnabled()) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            int retry = 0;
                            if (retry == 0) {
                                if (location == null) {
                                    retry += 1;
                                    requestNewLocationData();
                                } else {
                                    getDataDirection(location);
                                }
                            }
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
        } else {*/
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        int retry = 0;
                        if (retry == 0) {
                            if (location == null) {
                                retry += 1;
                                requestNewLocationData();
                            } else {
                                getDataDirection(location);
                            }
                        }
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
        //}
    }

    private void dialogRequestLocation() {

        final View customLayout = getLayoutInflater()
                .inflate(R.layout.layout_requestlocation_dialog, null);

        mat.setView(customLayout);
        mat.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
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
                .setInterval(10 * 60 * 1000)
                .setFastestInterval(2 * 60 * 1000)
                .setNumUpdates(1)
                .setMaxWaitTime(60 * 60 * 1000);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            /*latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");*/
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermissionsQ() {

        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
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


    private void getDataDirection(Location location) {
        if (location != null) {
            if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                try {
                    double lat = 0.0;
                    double lng = 0.0;

                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    Geocoder geocoder = new Geocoder(SplashActivity.this, Locale.getDefault());
                    list = geocoder.getFromLocation(
                            location.getLatitude(),
                            location.getLongitude(),
                            1);
                    tv_state.setText("Validando ubicación...");
                    verifyStateLocation(list.get(0).getPostalCode().toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /********* NETWORK*/

    @Override
    public void onNetworkConnectionChanger(boolean isConnected) {

        new Handler().postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (!isConnected) {
                    Toast.makeText(SplashActivity.this, "Sin conexión a internet.", Toast.LENGTH_SHORT).show();
                    tv_state.setText("Esperando conexión a internet...");
                } else {
                    getLastLocation();
                    tv_state.setText("Obteniendo su ubicación...");
                }
            }
        }, 100);
    }

    @SuppressLint("SetTextI18n")
    private void executeVerifyNetwork() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        initClass.getInstance().setConnectivityListener(this);
        tv_state.setText("Verificando la conexión a internet...");
    }

    private void verifyStateLocation(String PostCode) {

        Call<List<AccessResponseModel>> call =
                api.getResponseAccess("list_district_by_code_postal", PostCode);
        ApiHelper.enqueueWithRetry(call, new Callback<List<AccessResponseModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<AccessResponseModel>> call,
                                   @NonNull Response<List<AccessResponseModel>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    switch (response.body().get(0).getCode_server()) {
                        case "010":
                            if (response.body().get(0).getHabilitado_distrito().equals("1")) {

                                Toast.makeText(SplashActivity.this, "Se hailitará pronto.",
                                        Toast.LENGTH_SHORT).show();
                                nextActivity("block");

                            } else if (response.body().get(0).getHabilitado_distrito().equals("0")) {

                                Toast.makeText(SplashActivity.this, "Distrito no habilitado.",
                                        Toast.LENGTH_SHORT).show();
                                nextActivity("block");

                            }
                            break;
                        case "221":
                            if (response.body().get(0).getHabilitado_distrito().equals("1")) {

                                /*Toast.makeText(SplashActivity.this, "Distrito  habilitado.",
                                        Toast.LENGTH_SHORT).show();*/
                                tv_state.setText("Ubicación valida...");

                                nextActivity("allowed");

                            } else if (response.body().get(0).getHabilitado_distrito().equals("0")) {

                                Toast.makeText(SplashActivity.this, "Distrito no habilitado.",
                                        Toast.LENGTH_SHORT).show();
                                nextActivity("block");

                            }
                            break;
                        default:

                            break;
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AccessResponseModel>> call, @NonNull Throwable t) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                executeVerifyNetwork();
            }
        }, 100);
    }

    ;

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void nextActivity(String state) {
        if (state.equals("block")) {

            Intent intent = new Intent(SplashActivity.this, AccessRestrictActivity.class);
            startActivity(intent);
            finish();

        } else if (state.equals("allowed")) {
            switch (SessionSP.get(SplashActivity.this).getStateLogin()) {
                case "yes":
                    startActivity(new Intent(SplashActivity.this, InitialActivity.class));
                    break;
                case "register":
                case "dirclient": {
                    Intent intent = new Intent(SplashActivity.this, DirectionClientActivity.class);
                    intent.putExtra("action", "register_data");
                    intent.putExtra("state_use", "1");
                    startActivity(intent);
                    break;
                }
                default: {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            finish();
        }
    }
}