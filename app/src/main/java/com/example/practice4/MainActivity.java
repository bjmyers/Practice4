package com.example.practice4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
        // Add listener and synchronize the nav drawer
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        // Start on the ProductListFragment, which is out main view
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProductListFragment()).commit();
    }

    // Handle navigation view item clicks here.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_full_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductListFragment()).commit();
                break;
            case R.id.nav_price_change:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PriceChangeFragment()).commit();
                break;
            case R.id.nav_update:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductUpdateFragment()).commit();
                break;
            case R.id.nav_logout:
                // Sign out and go back to the login activity
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }

        // Close the navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}