package com.davidru85.openweather;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class WeatherPreferences extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_activity);
	}
}
