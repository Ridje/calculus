package com.kis.calculus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.CheckBox;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    SwitchMaterial mSwitchDarkMode;
    CheckBox mSystemSettingsDarkMode;
    int mUseForceDarkModeValue;
    boolean mUseSystemDarkModeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialzeButtons();
        getUsersSettings();
        setUpViews();
        initializeListeners();
    }

    private void initialzeButtons() {
        mSwitchDarkMode = findViewById(R.id.switch_theme);
        mSystemSettingsDarkMode = findViewById(R.id.checkBox_system_theme);
    }

    private void getUsersSettings() {
        SharedPreferences sharedPreferences = ThemeUtils.getSharedPreferences(this.getApplicationContext());
        mUseForceDarkModeValue = ThemeUtils.getDarkThemeForceUse(sharedPreferences);
        mUseSystemDarkModeValue = ThemeUtils.getDarkThemeUseSystem(sharedPreferences);
    }

    private void initializeListeners() {
        mSwitchDarkMode.setOnCheckedChangeListener((listener, isChecked) ->
                processDarkModeChanges());
        mSystemSettingsDarkMode.setOnCheckedChangeListener((listener, isChecked) ->
                processDarkModeChanges());
    }
    private void setUpViews() {

        setUpEnablenessOfViews();
        if (mSystemSettingsDarkMode.isChecked() != mUseSystemDarkModeValue) {
            mSystemSettingsDarkMode.setChecked(mUseSystemDarkModeValue);
        }

        switch (mUseForceDarkModeValue) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                mSwitchDarkMode.setChecked(true);
                break;

            case AppCompatDelegate.MODE_NIGHT_NO:
                mSwitchDarkMode.setChecked(false);
                break;

            default:
                int currentNightMode = getBaseContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
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
    }

    private void setUpEnablenessOfViews() {
        mSwitchDarkMode.setEnabled(!mUseSystemDarkModeValue);
    }

    private void changeSettingsValues() {
        mUseSystemDarkModeValue = mSystemSettingsDarkMode.isChecked();
        if (mUseSystemDarkModeValue) {
            mUseForceDarkModeValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        } else {
            mUseForceDarkModeValue = mSwitchDarkMode.isChecked() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        }
    }

    private void saveSettingsValues() {
        ThemeUtils.saveDayNightSettings(ThemeUtils.getSharedPreferences(this).edit(), mUseSystemDarkModeValue, mUseForceDarkModeValue);
    }

    private void applySettingsToApplication() {
        if (mUseSystemDarkModeValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            AppCompatDelegate.setDefaultNightMode(mUseForceDarkModeValue);
        }
    }

    protected void processDarkModeChanges() {
        changeSettingsValues();
        saveSettingsValues();
        setUpViews();
        applySettingsToApplication();
    }
}
