package fr.android.simplevolumesetter;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPref {
	private final static String STRING_ERROR = "";
	private final static int INT_ERROR = -1;


	public static void saveString(String key, String value) {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(GlobalApp.getInstance().getContext()).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String loadString(String key) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(GlobalApp.getInstance().getContext());
		return sp.getString(key, STRING_ERROR);
	}

	public static void saveInt(String key, int value) {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(GlobalApp.getInstance().getContext()).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int loadInt(String key) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(GlobalApp.getInstance().getContext());
		return sp.getInt(key, INT_ERROR);
	}
}
