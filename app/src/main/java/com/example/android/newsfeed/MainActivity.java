package com.example.android.newsfeed;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.newsfeed.fragment.BusinessFragment;
import com.example.android.newsfeed.fragment.CultureFragment;
import com.example.android.newsfeed.fragment.EnvironmentFragment;
import com.example.android.newsfeed.fragment.FashionFragment;
import com.example.android.newsfeed.fragment.HomeFragment;
import com.example.android.newsfeed.fragment.ScienceFragment;
import com.example.android.newsfeed.fragment.SocietyFragment;
import com.example.android.newsfeed.fragment.SportFragment;
import com.example.android.newsfeed.fragment.WorldFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the default fragment when starting the app
        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_home) {
            // Handle the home action
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment())
                    .commit();
        } else if (id == R.id.nav_world) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new WorldFragment())
                    .commit();
        } else if (id == R.id.nav_science) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ScienceFragment())
                    .commit();
        } else if (id == R.id.nav_sport) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new SportFragment())
                    .commit();
        } else if (id == R.id.nav_environment) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new EnvironmentFragment())
                    .commit();
        } else if (id == R.id.nav_society) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new SocietyFragment())
                    .commit();
        } else if (id == R.id.nav_fashion) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FashionFragment())
                    .commit();
        } else if (id == R.id.nav_business) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new BusinessFragment())
                    .commit();
        } else if (id == R.id.nav_culture) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new CultureFragment())
                    .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
