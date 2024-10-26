package com.example.ubicaciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ubicaciones.modelo.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationHelper {
    private static final String TAG = "LocationHelper";
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final Context context;
    private User user;

    public LocationHelper(Context c, User user){
        this.context = c;
        this.user = user;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            double latitud = location.getLatitude();
                            double longitud = location.getLongitude();
                            Toast.makeText(context, "Latitud: " + latitud + " Longitud: " + longitud, Toast.LENGTH_SHORT).show();
                            user.setLatitud(latitud);
                            user.setLongitud(longitud);
                            Log.d(TAG, "Se obtuvo la ubicacion correctamente");

                            FirebaseManager firebaseManager = new FirebaseManager();
                            firebaseManager.WriteFirebase(user);
                        } else {
                            Toast.makeText(context, "No se detecto la ubicacion actual",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "No se pudo obtener la ubicacion" + e.getMessage());
                        Toast.makeText(context, "No se pudo obtener la ubicacion",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
