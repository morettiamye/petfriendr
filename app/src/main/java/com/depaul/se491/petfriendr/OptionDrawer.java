package com.depaul.se491.petfriendr;

import android.content.res.Configuration;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class OptionDrawer {

    private final static int ITEM_DRAWER = 0;
    private final static int STRING_OPEN = 0;
    private final static int STRING_CLOSED = 0;
    private final static int STRING_PROFILE = 0;
    private final static int STRING_SWIPE = 0;
    private final static int STRING_MESSAGE = 0;
    private final static int STRING_SEARCH = 0;
    private final static int STRING_SETTINGS = 0;

    private ListView list;
    private DrawerLayout drawer;
    private ConstraintLayout constraint;
    private ActionBarDrawerToggle toggle;
    private ArrayAdapter<Integer> adapter;
    private ArrayList<Integer> options;
    private AppCompatActivity context;

    public OptionDrawer(AppCompatActivity context, ListView list,
                        DrawerLayout drawer, ConstraintLayout constraint) {
        this.list = list;
        this.drawer = drawer;
        this.constraint = constraint;
        this.context = context;

        addOptionsToList();

        list.setOnItemClickListener((parent, view, position, id) -> {
            onSelectOption(position);
            drawer.closeDrawer(constraint);
        });

        toggle = new ActionBarDrawerToggle(context, drawer,
                STRING_OPEN, STRING_CLOSED);

        adapter = new ArrayAdapter<>(context, ITEM_DRAWER, options);

        list.setAdapter(adapter);
    }

    private void addOptionsToList() {
        options.add(STRING_PROFILE);
        options.add(STRING_SWIPE);
        options.add(STRING_MESSAGE);
        options.add(STRING_SEARCH);
        options.add(STRING_SETTINGS);
    }

    private void onSelectOption(int index) {
        // TODO: Process on select menu option
    }

    public void syncStateToggle() {
        toggle.syncState();
    }

    public void onConfigChanged(Configuration config) {
        toggle.onConfigurationChanged(config);
    }
}
