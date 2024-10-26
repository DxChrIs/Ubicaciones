package com.example.ubicaciones.modelo;

import android.content.Context;
import android.util.Log;

import com.example.ubicaciones.LocationHelper;

public class User {
    Context context;
    private String id;
    private String name;
    private String email;
    private double latitud;
    private double longitud;
    private LocationHelper locationHelper;

    public User(){

    }

    public User(String id, String name, String email, double latitud, double longitud) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.latitud = latitud;
        this.longitud = longitud;
        this.locationHelper = new LocationHelper(context, this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setCurrentLocation(LocationHelper locationHelper){
        this.locationHelper = locationHelper;
    }

    public void getCurrentLocation(){
        if (locationHelper != null){
            locationHelper.getCurrentLocation();
        } else {
            Log.e("User", "No se pudo obtener la ubicacion");
        }
    }
}
