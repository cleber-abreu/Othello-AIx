package com.qualityautomacao.webposto.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Wiliam on 20/09/2016.
 */
public class UtilsPreferences {

    public static final String KEY_LOGIN = "pref_login";
    public static final String KEY_SENHA = "pref_senha";

    private final SharedPreferences sharedPref;

    public UtilsPreferences(Context ctx) {
        this.sharedPref = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public void setPreferences(String key, String valor) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(key, valor);
        editor.apply();
    }

    public String getPreferences(String key) {
        return getPreferences(key, "");
    }

    public String getPreferences(String key, String valueDefault) {
        return this.sharedPref.getString(key, valueDefault);
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}

