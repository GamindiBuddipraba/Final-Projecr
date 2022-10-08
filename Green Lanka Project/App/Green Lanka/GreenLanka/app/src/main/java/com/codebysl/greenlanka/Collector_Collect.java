package com.codebysl.greenlanka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Collector_Collect extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String cid;

    ListView itemList;

    private ArrayList<String> type;
    private ArrayList<String> desc;
    private ArrayList<String> contact;
    private ArrayList<String> address;
    private ArrayList<String> lat;
    private ArrayList<String> longti;
    private ArrayList<String> status;
    private ArrayList<String> key1;
    private ArrayList<String> key2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_collect);

        Intent i = getIntent();
        cid = i.getStringExtra("cid");

        itemList = findViewById(R.id.idLVItemsCC);

        type = new ArrayList<String>();
        desc = new ArrayList<String>();
        contact = new ArrayList<String>();
        address = new ArrayList<String>();
        lat = new ArrayList<String>();
        longti = new ArrayList<String>();
        status = new ArrayList<String>();
        key1 = new ArrayList<String>();
        key2 = new ArrayList<String>();

        loadDatainListview();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String code_lat = ((TextView) view.findViewById(R.id.et_lat)).getText().toString();
                String code_longti = ((TextView) view.findViewById(R.id.et_long)).getText().toString();
                String value1 = ((TextView) view.findViewById(R.id.et_key1)).getText().toString();
                String value2 = ((TextView) view.findViewById(R.id.et_key2)).getText().toString();

                // update Status
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference errandsRef = rootRef.child("Items");
                errandsRef.child(value1).child(value2).child("Status").setValue("1").toString();



                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("lat",code_lat);
                intent.putExtra("longti",code_longti);
                startActivity(intent);
            }
        });

    }


    private void loadDatainListview() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference errandsRef = rootRef.child("Items");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String sKey1 = ds.getKey();

                    DatabaseReference keyRef1 = errandsRef.child(sKey1);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String sKey2 = ds.getKey();

                                DatabaseReference keyRef2 = keyRef1.child(sKey2);
                                ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        type.add(dataSnapshot.child("Type").getValue().toString());
                                        desc.add(dataSnapshot.child("Description").getValue().toString());
                                        contact.add(dataSnapshot.child("Contact").getValue().toString());
                                        address.add(dataSnapshot.child("Address").getValue().toString());
                                        lat.add(dataSnapshot.child("Latitude").getValue().toString());
                                        longti.add(dataSnapshot.child("Longitude").getValue().toString());
                                        status.add(dataSnapshot.child("Status").getValue().toString());
                                        key1.add(sKey1);
                                        key2.add(sKey2);

                                        ItemAdapter item_adapter = new ItemAdapter(getApplicationContext(), type, desc, contact, address, lat, longti,status,key1,key2);
                                        itemList.setAdapter(item_adapter);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                };
                                keyRef2.addListenerForSingleValueEvent(valueEventListener);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
                    keyRef1.addListenerForSingleValueEvent(valueEventListener);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        errandsRef.addListenerForSingleValueEvent(eventListener);

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent2 = new Intent(getApplicationContext(), Collector_Home.class);
                intent2.putExtra("cid", cid);
                startActivity(intent2);
                break;
            case R.id.nav_collect:
                Toast.makeText(this, "Collect", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                Intent intent3 = new Intent(getApplicationContext(), Collector_Login.class);
                startActivity(intent3);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
