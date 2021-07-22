package com.altoque.delivery.view.direction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.DomicilioModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aaa extends BottomSheetDialogFragment implements OnMapReadyCallback {

    View view;

    private BottomSheetBehavior mBehavior;
    private ApiInterface api;
    SessionSP sessionSP;

    TextView tv_name, tv_reference, tv_numberflat;

    GoogleMap mMap;
    private Marker globalMarker;
    private double globalLat = 0.0;
    private double globalLng = 0.0;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private SupportMapFragment mapFragment;
    LatLng globalCoords;

    AlertDialog mat;

    List<Address> list;
    int PERMISSION_ID = 44;
    private static final String TAG = MainActivity.class.getSimpleName();


    private void initView() {

        tv_name = view.findViewById(R.id.tv_namedir_dirstatic);
        tv_numberflat = view.findViewById(R.id.tv_numberflat_dirstatic);
        tv_reference = view.findViewById(R.id.tv_reference_dirstatic);
    }

    private void eventListener() {

    }

    private void initResources() {

        api = ApiClient.getApiClient().create(ApiInterface.class);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_dirstatic);

        mapFragment.getMapAsync(this);

        mat = new AlertDialog.Builder(requireContext()).create();

    }

    private void compilateInit() {
        initView();
        initResources();
        eventListener();
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

        view = View.inflate(getContext(), R.layout.aaa, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());


        setupStatesBS();
        compilateInit();

        getLocation();

        return dialog;
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng cord = new LatLng(-11.108078, -77.606723);
        CameraUpdate miU = CameraUpdateFactory.newLatLngZoom(cord, 15);
        mMap.moveCamera(miU);
    }

    private void getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                /*********  **/

                getDataDirections();

            } else {
                Toast.makeText(requireContext(), "Por favor active su GPS.", Toast.LENGTH_LONG).show();
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

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_ID);
        mat.dismiss();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    private void getDataDirections(){

        String idclient = SessionSP.get(requireContext()).getIdClientSessSp();
        //Log.e("Home_log", "OnFailure " + idclient);

        try {
            Call<List<DomicilioModel>> call = api.
                    getDataDirection("insert_select_adress", "3", idclient);
            ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<DomicilioModel>> call,
                                       @NotNull Response<List<DomicilioModel>> response) {
                    //pb_load_direction.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String code = response.body().get(0).getCode_server().toString();


                        if (code.equals("221")) {
                            String namedir = response.body().get(0).getDireccion_dom().toString();
                            String ref = response.body().get(0).getReferencia_dom().toString();
                            String numberf = response.body().get(0).getPiso_dom().toString();
                            String lat = response.body().get(0).getLat_dom().toString();
                            String lng = response.body().get(0).getLong_dom().toString();

                            tv_name.setText(namedir);
                            tv_reference.setText(ref);
                            tv_numberflat.setText(numberf);
                            agregarMarcador(Double.parseDouble(lat), Double.parseDouble(lng));

                        }
                        else if (code.equals("010")) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<DomicilioModel>> call, @NotNull Throwable t) {
                    //pb_load_direction.setVisibility(View.GONE);

                }
            });
        }catch (Exception e){
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
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapFragment.onDestroyView();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mapFragment.onDetach();
    }
}
