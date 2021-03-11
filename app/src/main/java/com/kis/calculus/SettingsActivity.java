package com.kis.calculus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    Switch mSwitchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSwitchDarkMode = findViewById(R.id.switch_theme);

        int currentDarkMode = AppCompatDelegate.getDefaultNightMode();
        switch (currentDarkMode) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                mSwitchDarkMode.setChecked(true);
                break;

            case AppCompatDelegate.MODE_NIGHT_NO:
                mSwitchDarkMode.setChecked(false);
                break;

            default:
                int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        mSwitchDarkMode.setChecked(true);
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        mSwitchDarkMode.setChecked(false);
                        break;
                    case Configuration.UI_MODE_NIGHT_UNDEFINED:
                        mSwitchDarkMode.setChecked(false);
                        break;
                }
        }

        mSwitchDarkMode.setOnCheckedChangeListener((listener, isChecked) ->
                changeTheme(isChecked)
        );
    }
    protected void changeTheme(boolean isNight) {
        int nightMode = isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }
}