package com.depaul.se491.petfriendr;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private ListView listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        listView = findViewById(R.id.DrawerList);
        drawerLayout = findViewById(R.id.DrawerLayout);

        options = new ArrayList<>();
        options.add("Edit Profile");
        options.add("Feed");
        options.add("Log Out");

        adapter = new ArrayAdapter<>(this, R.layout.drawer_item, options);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            onSelectOption(position);
            drawerLayout.closeDrawer(findViewById(R.id.LeftConstraint));
        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_closed);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void onSelectOption(int index) {
        String option = options.get(index);
        switch (option) {
            case "Edit Profile":
                startActivity(new Intent(this, EditProfileActivity.class));
            case "Feed":
                startActivity(new Intent(this, SeePetsActivity.class));
                break;
            case "Log Out":
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
