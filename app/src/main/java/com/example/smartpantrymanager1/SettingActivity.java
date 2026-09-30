package com.example.smartpantrymanager1;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Switch switchAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchAlerts = findViewById(R.id.switchAlerts);

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Expiring soon alerts enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Expiring soon alerts disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}