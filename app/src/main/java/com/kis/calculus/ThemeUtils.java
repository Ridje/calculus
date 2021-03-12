package com.kis.calculus;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;
import static android.content.Context.MODE_PRIVATE;

public class ThemeUtils {

    public static String SHARED_PREFERENCES_NAMESPACE = "the_calculus_app";
    public static String USE_SYSTEM_DARK_THEME_SETTING_NAME = "dark_theme_use_system";
    public static String FORCE_USE_DARK_THEME_SETTING_NAME = "dark_theme_force_use";

    public static void setDayNightModeFromSettings(SharedPreferences sharedPreferences) {
        setDayNightMode(getDarkThemeUseSystem(sharedPreferences), getDarkThemeForceUse(sharedPreferences));
    }

    public static void setDayNightMode(boolean darkThemeUseSystem, int darkThemeForceUse) {
        if (darkThemeUseSystem) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            AppCompatDelegate.setDefaultNightMode(darkThemeForceUse);
        }
    }

    public static boolean getDarkThemeUseSystem(SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(USE_SYSTEM_DARK_THEME_SETTING_NAME, true);
    }

    public static int getDarkThemeForceUse(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(FORCE_USE_DARK_THEME_SETTING_NAME, AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
    }

    public static void saveDayNightSettings(SharedPreferences.Editor editor, boolean darkThemeUseSystem, int darkThemeForceUse) {
        editor.putBoolean(USE_SYSTEM_DARK_THEME_SETTING_NAME, darkThemeUseSystem);
        editor.putInt(FORCE_USE_DARK_THEME_SETTING_NAME, darkThemeForceUse);
        editor.commit();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAMESPACE, MODE_PRIVATE);
    }


}
