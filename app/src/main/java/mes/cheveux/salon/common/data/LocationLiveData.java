package mes.cheveux.salon.common.data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class LocationLiveData extends MutableLiveData<LocationModel> {

    private FusedLocationProviderClient fusedLocationClient;
    private static LocationLiveData instance;
    private LocationRequest locationRequest;
    private Context mContext;
    private SessionManager sessionManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 4000;

    @SuppressLint("MissingPermission")
    public LocationLiveData(Context context) {
        mContext = context.getApplicationContext();
        sessionManager = new SessionManager(context);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(PRIORITY_HIGH_ACCURACY);
        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                setLocationData(task.getResult());
            } else {
                fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
            }
        });
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    setLocationData(location);
                }

            }
        }
    };

    private void setLocationData(Location location) {
        LocationModel locationModel = new LocationModel();
        if (location != null) {
            locationModel.setLatitude(location.getLatitude());
            locationModel.setLongitude(location.getLongitude());
            Geocoder geocoder = new Geocoder(mContext);
            try {
                List<Address> addressList = geocoder
                        .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addressList != null && addressList.size() >= 1) {
                    locationModel.setPlaceName(addressList.get(0).getLocality());
                } else {
                    locationModel.setPlaceName("No location data");
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            locationModel.setPlaceName("");
            locationModel.setLongitude(0.000000);
            locationModel.setLatitude(0.000000);
        }


        sessionManager.saveLastLocation(
                locationModel.getLatitude(), locationModel.getLongitude(), locationModel.getPlaceName());

        setValue(locationModel);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        super.onActive();
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this::setLocationData);
        startLocationUpdates();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mLocationCallback != null) {
            fusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

}
