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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class User_Add_Complain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String unm;

    EditText collecID, complain, contactNo;
    Button send, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_complain);

        Intent i = getIntent();
        unm = i.getStringExtra("uname");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        send = (Button)findViewById(R.id.btnSendUAC);
        cancel = (Button)findViewById(R.id.btnCancelUAC);
        collecID = (EditText)findViewById(R.id.txtCollectorID );
        complain = (EditText)findViewById(R.id.txtComplainUAC );
        contactNo = (EditText)findViewById(R.id.txtContactUAC);


        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collecID.getText().toString().isEmpty() || complain.getText().toString().isEmpty() || contactNo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }else{

                    final String colID = collecID.getText().toString().trim();
                    final String comp = complain.getText().toString().trim();
                    final String conNo = contactNo.getText().toString().trim();

                    insertData(colID, comp, conNo);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

    }

    private void clear(){
        collecID.setText(null);
        collecID.setHint("Enter Collector ID");
        complain.setText(null);
        complain.setHint("Enter Complain");
        contactNo.setText(null);
        contactNo.setHint("Enter Contact Number");
    }

    private void insertData(String collectorID, String complain, String contactNo){

        HashMap<String,String> item=new HashMap<>();

        item.put("Collector ID", collectorID);
        item.put("Complain", complain);
        item.put("Contact", contactNo);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tasksRef = rootRef.child("Complains").child(unm).child(currentDateandTime);
        tasksRef.setValue(item);
        clear();
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
                Intent intent2 = new Intent(getApplicationContext(), User_Home.class);
                intent2.putExtra("uname", unm);
                startActivity(intent2);
                break;
            case R.id.nav_additem:
                Intent intent = new Intent(getApplicationContext(), User_Add_Item.class);
                intent.putExtra("uname", unm);
                startActivity(intent);
                break;
            case R.id.nav_addcomplain:
                Toast.makeText(this, "Add Complain", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Intent intent3 = new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent3);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
