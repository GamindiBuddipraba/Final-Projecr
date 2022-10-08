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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.Objects;

public class Collector_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String cid;
    TextView contactUS;

    Button collectItem, viewComplains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_home);

        Intent i = getIntent();
        cid = i.getStringExtra("cid");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        collectItem = (Button)findViewById(R.id.btnCollectItemsCH);
        
        contactUS = (TextView)findViewById(R.id.contactUSCH);

        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        collectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Collector_Collect.class);
                i.putExtra("cid", cid);
                startActivity(i);
            }
        });



        contactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactUS.class);
                i.putExtra("user", cid);
                startActivity(i);
            }
        });

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
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_collect:
                Intent intent = new Intent(getApplicationContext(), Collector_Collect.class);
                startActivity(intent);
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
