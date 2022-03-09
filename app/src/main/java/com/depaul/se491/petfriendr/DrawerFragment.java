package com.depaul.se491.petfriendr;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DrawerFragment extends Fragment {

    private ListView listView;
    private DrawerLayout drawerLayout;
    private ConstraintLayout constraint;
    private ActionBarDrawerToggle toggle;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> options;

    public DrawerFragment() {
        super(R.layout.drawer_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.DrawerList);
        drawerLayout = view.findViewById(R.id.DrawerLayout);
        constraint = view.findViewById(R.id.LeftConstraint);

        options = new ArrayList<>();
        options.add("Profile");
        options.add("Swipe");
        options.add("Messages");
        options.add("Search");
        options.add("Settings");

        adapter = new ArrayAdapter<>(getActivity(), R.layout.drawer_item, options);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            selectOption(position);
            drawerLayout.closeDrawer(view.findViewById(R.id.LeftConstraint));
        });

        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
                R.string.drawer_open, R.string.drawer_closed);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void selectOption(int index) {
        String option = options.get(index);

        switch (option) {
            case "Profile":
                break;
            case "Swipe":
                break;
            case "Messages":
                break;
            case "Search":
                break;
            case "Settings":
                break;
            default:
                break;
        }
    }
}
