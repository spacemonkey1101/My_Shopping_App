package com.example.MyShoppingApp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.MyShoppingApp.model.LocationPOJO;
import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.interfaces.JsonPlaceHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class LocationService extends AppCompatActivity {

    LocationListener locationListener;
    LocationManager locationManager;
    private JsonPlaceHolder jsonPlaceHolder;
    private static final String TAG = "LocationServicexxx";

    ///*****///

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        locationTrack = new LocationTrack(LocationService.this);


        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            setUpApiCall(latitude,longitude);
        } else {

            locationTrack.showSettingsAlert();
        }

    }
 public void setUpApiCall(double latitude, double longitude){
        Intent intent =getIntent();
        final String token = intent.getStringExtra("auth_token");
     HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
     httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

     OkHttpClient okHttpClient = new OkHttpClient
             .Builder()
             .addInterceptor(httpLoggingInterceptor)
             .authenticator(new Authenticator() {
                 @Nullable
                 @Override
                 public Request authenticate(@Nullable Route route, @NotNull okhttp3.Response response) throws IOException {
                     if (response.request().header("Authorization") != null)
                         return null;  //if you've tried to authorize and failed, give up

                     return response.request().newBuilder().header("Authorization","Bearer " +token).build();
                     }
             })
             .build();
     Retrofit retrofit = new Retrofit.Builder()
             .baseUrl("https://wofo24.herokuapp.com/api/")
             .addConverterFactory(GsonConverterFactory.create())
             .client(okHttpClient)
             .build();

     jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
     Call<LocationPOJO> call = jsonPlaceHolder.sendUserLocation("Bearer " +token ,new LocationPOJO(latitude,longitude , locationTrack.getAddress()));
     call.enqueue(new Callback<LocationPOJO>() {

         @Override
         public void onResponse(Call<LocationPOJO> call, Response<LocationPOJO> response) {
             if(!response.isSuccessful()){

                 Toast.makeText(LocationService.this, response.toString(), Toast.LENGTH_SHORT).show();
                 return;
             }
             LocationPOJO postResponse = response.body();
             Toast.makeText(LocationService.this, postResponse.getLatitude() +" xxxx" + postResponse.getLongitude(), Toast.LENGTH_SHORT).show();

         }

         @Override
         public void onFailure(Call<LocationPOJO> call, Throwable t) {
             Toast.makeText(LocationService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });

    Intent intent1 = new Intent(getApplicationContext(), CategorySubcategoryActivity.class);
    startActivity(intent1);

    }




    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) { result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LocationService.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");

        setContentView(R.layout.activity_location_service);*/


       /* locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc!= null)
                updateLocation(loc);

        }*/
/*
       okhttp*/
    }
   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }

    }
    public  void updateLocation(Location location){

    }*/

