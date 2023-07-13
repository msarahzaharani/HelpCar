package com.example.helpcarapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LokasiBengkel extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, info, kerosakankereta, lokasibengkel, jurnalkeretaku, logkeluar;
    RecyclerView recyclerView;
    ArrayList<Model> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_bengkel);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        info = findViewById(R.id.info);
        kerosakankereta = findViewById(R.id.kerosakankereta);
        lokasibengkel = findViewById(R.id.lokasibengkel);
        jurnalkeretaku = findViewById(R.id.jurnalkeretaku);
        logkeluar = findViewById(R.id.logout);
        recyclerView = findViewById(R.id.lokasiRecyclerView);


        ModelRecyclerView modelRecyclerView = new ModelRecyclerView(this,arrayList);
        recyclerView.setAdapter(modelRecyclerView);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(com.example.helpcarapps.LokasiBengkel.this, menu.class);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(com.example.helpcarapps.LokasiBengkel.this, Info.class);
            }
        });
        kerosakankereta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        lokasibengkel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        jurnalkeretaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(com.example.helpcarapps.LokasiBengkel.this, JurnalKeretaku.class);
            }
        });
        logkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(com.example.helpcarapps.LokasiBengkel.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList.add(new Model(R.drawable.servis,"Bengkel Kereta Bangi" , "03-89202047"));
        arrayList.add(new Model(R.drawable.servis,"G AutoTech Services" , "019-4430979"));
        arrayList.add(new Model(R.drawable.servis,"Norsam Auto Services" , "012-3794847"));
        arrayList.add(new Model(R.drawable.servis,"TAHARA SERVICES" , "03-89139488"));
        arrayList.add(new Model(R.drawable.servis,"Bengkel Kereta S.H.M" , "012-2951069"));
        arrayList.add(new Model(R.drawable.servis,"GoMechanic" , "03-92121789"));
        arrayList.add(new Model(R.drawable.servis,"Castrol Auto Service Workshop" , "03-89203093"));
        arrayList.add(new Model(R.drawable.servis,"RD Auto Care Service Center" , "03-89127473"));
        arrayList.add(new Model(R.drawable.servis,"Europe Auto Workshop" , "016-2132989"));
        arrayList.add(new Model(R.drawable.servis,"Along Auto Services" , "03-89202320"));

    }


    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }



}