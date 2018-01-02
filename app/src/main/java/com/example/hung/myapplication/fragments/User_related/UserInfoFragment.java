package com.example.hung.myapplication.fragments.User_related;

import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.activities.ViewModel;
import com.example.hung.myapplication.data.object_define.User;
import com.example.hung.myapplication.interfaces.UserActivityInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hung on 11/16/2017.
 */

public class UserInfoFragment extends android.support.v4.app.Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView mUsername, mLocation, mSubscribed, mtitle;
    private Button mSubscribe, mNotelist, mLogout;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final String TAG = "UserInfo_Fragment";
    private User user;
    private ViewModel mViewModel;
    private String city;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_userinfo, container, false);
        String user_email = getArguments().getString("USER_EMAIL");
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        user=mViewModel.getCurrentuser(user_email);

        mUsername=(TextView) v.findViewById(R.id.info_username);
        mtitle=(TextView) v.findViewById(R.id.info_title);
        mtitle.setText(R.string.title_Userinfo);
        mUsername.setText(user.getEmail());
        mLocation=(TextView) v.findViewById(R.id.info_location);
        mSubscribed=(TextView) v.findViewById(R.id.info_subscribed);

        mViewModel.addTag("l-"+city,user);                          //automatic subscribe to current city
        mSubscribed.setText("Subscribed to: "+user.getTagstring());
        mSubscribe=(Button) v.findViewById(R.id.info_toSubscribe);
        mNotelist=(Button) v.findViewById(R.id.info_toNoteList);
        mLogout=(Button) v.findViewById(R.id.info_toLogOut);

        mSubscribe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((UserActivityInterface)getActivity()).GetInfoFromInfo(true, false,false);
            }
        });
        mNotelist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((UserActivityInterface)getActivity()).GetInfoFromInfo(false, true,false);
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((UserActivityInterface)getActivity()).GetInfoFromInfo(false, false,true);
            }
        });
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    200);
        }
        else {
            mLastLocation = fusedLocationProviderApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                try {
                    handleNewLocation(mLastLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                    .setInterval(30 * 1000)        // 30 seconds, in milliseconds
                    .setFastestInterval(15 * 1000); // 15 second, in milliseconds
            fusedLocationProviderApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected())
            stopLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            mLastLocation = location;
            try {
                handleNewLocation(mLastLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleNewLocation(Location location) throws IOException {
        String address = null,country= null;
        Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());;
        List<Address> addresses = geocoder.getFromLocation(
                location.getLatitude(), location.getLongitude(), 1);
        if (addresses != null) {
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                city = city.replaceAll("\\s", "");//remove white space
                country = addresses.get(0).getCountryName();
            }
        }
        mLocation.setText("Address: "+ address + ", "+ country);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }
    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            fusedLocationProviderApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }
}