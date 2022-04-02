package com.android.bathhack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.bathhack.models.LocationModel;
import com.android.bathhack.models.UserModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.android.bathhack.util.Constants.MAPVIEW_BUNDLE_KEY;

public class PlayActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Constants
    private static final String TAG = "PlayActivity";

    // UI Components
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private TextView popupImage, popupText, coins, hearts;
    private Chronometer mChronometer;

    // Variables
    private LatLngBounds mMapBoundary;
    private UserModel mUserPosition;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private GeoApiContext mGeoApiContext = null;
    private boolean cameraSet = false;

    private GoogleMap.OnMapLoadedCallback mMapLoadedCallback = new GoogleMap.OnMapLoadedCallback() {
        @Override
        public void onMapLoaded() {
            setCameraView();
        }
    };

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            // Save the location
            Location location = locationResult.getLastLocation();
            mUserPosition.setLongitude(location.getLongitude());
            mUserPosition.setLatitude(location.getLatitude());
            if (!cameraSet) {
                setCameraView();
                cameraSet = true;
            }

            Log.d(TAG, "onLocationResult: " + String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));
            if (location.getLatitude() - mDestination.getLatitude() < 0.001f && location.getLongitude() - mDestination.getLongitude() < 0.001f) {
                pauseChronometer();
                long duration = SystemClock.elapsedRealtime() - mChronometer.getBase();

                Log.d(TAG, "onLocationResult: Arrived at destination" + String.valueOf(duration / 1000) + "s");
                endGame();
            }
        }

    };
    private List<LocationModel> mLocationList = new ArrayList<>();
    private LocationModel mDestination;

    private void endGame() {
        Intent i = new Intent(PlayActivity.this, SummaryActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set to full screen (remove status bar)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initUI();
        initGoogleMap(savedInstanceState);
        insertDummyData();
        startChronometer();
        mUserPosition = new UserModel();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        createLocationRequest();
        startLocationUpdates();
    }

    private void startChronometer() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    private void pauseChronometer() {
        mChronometer.stop();
    }

    private void insertDummyData() {
        mLocationList.add(new LocationModel("Sports Training Village", 51.377688515670485, -2.324575367443366));
        mLocationList.add(new LocationModel("The Fresh Co-op", 51.38008470812914, -2.3296487592296486));
        mLocationList.add(new LocationModel("Polden", 51.38049929698658, -2.3326429905222605));
        mLocationList.add(new LocationModel("West Car Park", 51.379773368108445, -2.332843823219117));
        mLocationList.add(new LocationModel("Eastwood", 51.38118940103502, -2.3242811762256546));
        mLocationList.add(new LocationModel("Lake", 51.378361739039256, -2.3282803023977654));
//        mLocationList.add(new LocationModel("Westwood", 51.38130743609304,  -2.329858939953267));
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000 * 30);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    private void initUI() {
        mMapView = (MapView) findViewById(R.id.activity_play_mv_map);
        popupImage = findViewById(R.id.popup_image);
        popupText = findViewById(R.id.popup_text);
        coins = findViewById(R.id.coin_text);
        hearts = findViewById(R.id.heart_text);
        mChronometer = findViewById(R.id.chronometer);
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey("AIzaSyAuLOkf1BSbpkbqaJHvX_YL0HoW7NglxI0")
                    .build();
        }
    }

    private void setCameraView() {
        Log.d(TAG, "setCameraView: Set Camera");
        Log.d(TAG, "setCameraView: Lat " + String.valueOf(mUserPosition.getLatitude()));
        Log.d(TAG, "setCameraView: Long " + String.valueOf(mUserPosition.getLongitude()));
        // Overall map view window: 0.2*0.2 = 0.04
        double bottomBoundary = mUserPosition.getLatitude() - .1;
        double leftBoundary = mUserPosition.getLongitude() - .1;
        double topBoundary = mUserPosition.getLatitude() + .1;
        double rightBoundary = mUserPosition.getLongitude() + .1;

        mMapBoundary = new LatLngBounds(
                new LatLng(bottomBoundary, leftBoundary),
                new LatLng(topBoundary, rightBoundary)
        );

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Random random = new Random();
        int i = random.nextInt(mLocationList.size());
        Log.d(TAG, "onMapReady: random value: " + i);
        mDestination = mLocationList.get(i);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(mDestination.getLatitude(), mDestination.getLongitude()))
                .title(mDestination.getLocationName()));
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);
        mGoogleMap = map;

        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d(TAG, "onMapLoaded: Map loaded");
            }
        });

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}