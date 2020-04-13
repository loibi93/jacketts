package com.loibi93.jacketts.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.ui.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }
}
