package google.try;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.util.Log;

public class GPSManager {

    @SuppressLint("MissingPermission")
    public static void updateCurrentLocation(Context context) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Log.d("GPSManager", "Location: " + latitude + ", " + longitude);
                        // Here we will sync with Firebase
                        FirebaseNativeSync.updateLocation(latitude, longitude);
                    } else {
                        Log.d("GPSManager", "Location is null");
                    }
                }
            });
    }
}
