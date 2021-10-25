package com.gmail.davidecoffaro.productscity.utilclass;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.gmail.davidecoffaro.productscity.BuildConfig;
import com.gmail.davidecoffaro.productscity.InsertCustomerDataActivity;
import com.gmail.davidecoffaro.productscity.MainActivity;
import com.gmail.davidecoffaro.productscity.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;

public class MyLocationClass {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = MainActivity.class.getSimpleName();
    protected Location mLastLocation;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    WeakReference<Activity> linkedActivity;

    public MyLocationClass(Activity linkedActivity){
        this.linkedActivity = new WeakReference<Activity>(linkedActivity);
    }


    public void createMethod(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(linkedActivity.get());
    }

    public void startMethod(){
        if (!checkPermissions()) {
            requestPermissions();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(linkedActivity.get(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            if(linkedActivity.get().getClass()==InsertCustomerDataActivity.class){
                                ((InsertCustomerDataActivity)linkedActivity.get()).startGeocoding(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            }

                            Log.d("Latitude", Double.toString(mLastLocation.getLatitude()));
                            Log.d("Longitude", Double.toString(mLastLocation.getLongitude()));

                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar("NoLocationDetected");
                        }
                    }
                });
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(linkedActivity.get(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(linkedActivity.get(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar("Permesso posizione non accettato", "Autorizza",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(linkedActivity.get(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getCurrentLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar("Permission_Denied", "Settings",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                linkedActivity.get().startActivity(intent);
                            }
                        });
            }
        }
    }

    private void showSnackbar(final String text) {
        View container = linkedActivity.get().findViewById(R.id.buttonLocate);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final String mainTextStringId, final String actionStringId,
                              View.OnClickListener listener) {
        View container = linkedActivity.get().findViewById(R.id.buttonLocate);
        if(container!= null){
            Snackbar.make(container,
                    //getString(mainTextStringId),
                    mainTextStringId,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(actionStringId, listener).show();
        }

    }

    @SuppressWarnings("MissingPermission")
    public void getCurrentLocation(){
        //permission ACCESS_FINE_LOCATION ok?
        if(checkPermissions()){
            //get current location using the google API fusedLocationClient, repeat it 2 times to get the correct location
            for(int i = 0; i<2; i++){

                locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                //get just one time location update
                locationRequest.setNumUpdates(1);

                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult != null) {
                            Location lastLocation = locationResult.getLastLocation();
                            if(linkedActivity.get().getClass()==InsertCustomerDataActivity.class){
                                //translate latitude and longitude to an address
                                ((InsertCustomerDataActivity)linkedActivity.get()).startGeocoding(lastLocation.getLatitude(), lastLocation.getLongitude());
                            }

                            Log.d("Latitude", Double.toString(lastLocation.getLatitude()));
                            Log.d("Longitude", Double.toString(lastLocation.getLongitude()));

                        }else{
                            Log.w(TAG, "getCurrentLocation error");
                            showSnackbar("NoLocationDetected");

                            getLastLocation();
                        }

                    }
                };

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }
        }else{
            requestPermissions();
        }
    }
}
