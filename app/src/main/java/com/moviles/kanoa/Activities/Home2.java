package com.moviles.kanoa.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.moviles.kanoa.Fragments.HomeFragment;
import com.moviles.kanoa.Fragments.MuseumFragment;
import com.moviles.kanoa.Fragments.PlaceFragment;
import com.moviles.kanoa.Fragments.RestaurantFragment;
import com.moviles.kanoa.Model.Museum;
import com.moviles.kanoa.R;

public class Home2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private TextView nameCurrentUser;
    private TextView emailCurrentUser;
    private ImageView imgCurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setTitle(R.string.Home_Explore);
        //Default
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft. replace(R.id.flMain, new HomeFragment());
        ft.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle(R.string.Home_Explore);
        navigationView.setCheckedItem(R.id.nav_home);


        View hView =  navigationView.getHeaderView(0);
        nameCurrentUser = (TextView)hView.findViewById(R.id.text_name_header_home);
        emailCurrentUser = (TextView) hView.findViewById(R.id.text_email_header_home);
        imgCurrentUser = (ImageView) hView.findViewById(R.id.image_header_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null){
            Toast.makeText(this, "No hay usuarios logueados", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Hay un usuario logueado", Toast.LENGTH_SHORT).show();
            updateUI(user);
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("¿Are you sure you want to exit and logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        closetabs();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void closetabs(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle(R.string.Home_Explore);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft. replace(R.id.flMain, new HomeFragment());
            ft.commit();

        } else if (id == R.id.nav_museos) {
            setTitle(R.string.Near_Museums);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft. replace(R.id.flMain, new MuseumFragment());
            ft.commit();
        } else if (id == R.id.nav_restaurantes) {
            setTitle(R.string.Near_Restaurants);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft. replace(R.id.flMain, new RestaurantFragment());
            ft.commit();
        }else if (id == R.id.nav_map) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }else if (id == R.id.nav_BLE) {
            Intent i = new Intent(this, BLEActivity.class);
            startActivity(i);
        }else if (id == R.id.nav_account) {
            Intent i = new Intent(this, EditProfileActivity.class);
            startActivity(i);
        }else if (id == R.id.nav_logout) {
           //Log Out
            mAuth.signOut();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_Qr) {
            setTitle(R.string.qr);
            Intent i = new Intent(this, QrActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doFragmentUnity(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft. replace(R.id.flMain, new PlaceFragment());
        ft.commit();
    }

    public void backFragmentUnity(String type){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (type.equals("Museums")){
            ft. replace(R.id.flMain, new MuseumFragment());
        }
        ft.commit();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (nameCurrentUser == null) {
                Toast.makeText(this, "es nulo el text view", Toast.LENGTH_SHORT).show();

            }else {
                if (user == null) {
                    Toast.makeText(this, "el usuario es el nulo", Toast.LENGTH_SHORT).show();
                }else {
                    nameCurrentUser.setText(user.getDisplayName());
                    emailCurrentUser.setText(user.getEmail());
                }

            }
        }else {

        }
    }
}
