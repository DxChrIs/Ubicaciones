package com.example.ubicaciones;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ubicaciones.modelo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    private static final String TAG = "FirebaseManager";

    Context context;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;

    public FirebaseManager(){
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Users");
    }

    public void WriteFirebase(User user){
        databaseReference.child(user.getId()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Se escribio correctamente");
                        } else {
                            Log.e(TAG, "No se pudo escribir");
                        }
                    }
                });
    }

    public void ReadFirebase(final OnUsersReadListener listener){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        users.add(user);
                    }
                }
                listener.onUsersRead(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "No se pudo leer: " + error.getMessage());
            }
        });
    }
    public interface OnUsersReadListener {
        void onUsersRead(List<User> users);
    }
}
