package com.example.android.googlemaptutorial2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    public static final String KEY = "925685a0f8f14cbe93d2265172f55140";
    public static final String URL = "https://developer.cumtd.com/api/v2.2/json/getstopsbylatlon";
    RequestQueue volleyQueue;
    TransportationStops ts;

    private View rootLayout;
    private static final String TAG = "Main Activity";

    //requesting permissions related
    private static final int REQUEST_LOCATION_PERMISSIONS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = (View) findViewById(R.id.root_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        volleyQueue = Volley.newRequestQueue(this);
    }

    public String formatURL(String key, double lat, double lon){
        return URL + "?key=" + key + "&lat=" + Double.toString(lat) + "&lon=" + Double.toString(lon);
    }

    private void startJsonRequest(String url){
        //create a request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ts = gson.fromJson(response.toString(), TransportationStops.class);
                        for(TransportationStops.StopInfo stopInfo: ts.getStops()){
                            for(TransportationStops.StopInfo.StopPointsData stopPointsData: stopInfo.getStop_points()){
                                LatLng stopLocation = new LatLng(stopPointsData.getStop_lat(), stopPointsData.getStop_lon());
                                map.addMarker(new MarkerOptions().position(stopLocation)
                                        .title(stopPointsData.getStop_id()));
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        //Add to volley queue, will handle everything in background
        volleyQueue.add(jsObjRequest);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.i(TAG,
                        "Displaying camera permission rationale to provide additional context.");
                Snackbar.make(rootLayout, "This is some information!!!!",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_LOCATION_PERMISSIONS);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSIONS);
            }
            return;
        }
        locationReceived();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission is granted.");
                    locationReceived();
                } else {
                    Log.i(TAG, "Permission is not granted.");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public void locationReceived(){
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (map!=null){
            setUpGoogleMap();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (mCurrentLocation != null) {
            setUpGoogleMap();
        }
    }

    public void setUpGoogleMap(){
        LatLng curLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        startJsonRequest(formatURL(KEY, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        map.addMarker(new MarkerOptions().position(curLocation)
                .title("You are here"));
        map.moveCamera(CameraUpdateFactory.newLatLng(curLocation));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 18));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng)
                        .title("Clicked Me"));
            }
        });

    }

    @Override
    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }


}
