package com.example.saravanamurali.farmersgen.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final void storeString(Context context, String key, String value) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static final String getString(Context context, String key) {
        String data;
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        data = preferences.getString(key, "");
        editor.commit();
        return data;
    }

    public static boolean validatePassword(final String password){

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z]).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static final boolean isValidMobile(String mobile) {
        //String regexStr = "^[0-9]{10}$";
        String regexStr = "^[+]?[0-9]{10,12}$";
        if (mobile.matches(regexStr)) {
            return true;
        }
        return false;
    }
}
