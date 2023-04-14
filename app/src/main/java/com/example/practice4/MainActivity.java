package com.example.practice4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Fields for the navigation drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.main_nav_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.navigation_drawer_open, // String to open
                R.string.navigation_drawer_close // String to close
        );
        /** Step 6: Include the mActionBarDrawerToggle as the listener to the DrawerLayout.
         *  The synchState() method is used to synchronize the state of the navigation drawer */
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        /** Step 7:Set the default fragment to the HomeFragment */
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProductListFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {// Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_price_change:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductListFragment()).commit();
                break;
            case R.id.nav_update:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductListFragment()).commit();
                break;
            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductListFragment()).commit();
                break;
        }

        // Close the navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}