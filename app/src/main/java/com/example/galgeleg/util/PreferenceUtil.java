package com.example.galgeleg.util;



import android.content.Context;
import android.content.SharedPreferences;

/**
 * https://www.sundanesepeople.com/creating-onboarding-screen-android-studio/
 */

public class PreferenceUtil {
    private static final String PREFERENCES_FILE = "myPref";
    private Context mContext;

    public PreferenceUtil(Context context) {
        this.mContext = context;
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }
}
