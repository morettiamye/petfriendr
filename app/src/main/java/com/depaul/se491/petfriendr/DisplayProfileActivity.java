/*
    This screen populates the selected profile to display:
        - Name
        - Profile Picture
        - Profile Description
        - Messages left
        - Leave message
 */

package com.depaul.se491.petfriendr;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);
    }
}