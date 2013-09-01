package com.ci.urlfetch;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Utils {
	public static final String GCM_ID = "gcm_id";

	public static void putValue(String chave, String item, Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = prefs.edit();
		edit.putString(chave, item);
		edit.commit();
	}

	public static String getValue(String chave, String defaultValue, Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(chave, defaultValue);
	}
}
