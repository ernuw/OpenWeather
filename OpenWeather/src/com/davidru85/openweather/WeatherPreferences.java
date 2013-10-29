package com.davidru85.openweather;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class WeatherPreferences extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_activity);
		/*EditTextPreference edit = (EditTextPreference) findViewById(R.xml.preferences_activity.
		Preference last_update_update = findPreference("last_update_update");
		last_update_update.setSummary("asd");*/
	}
}
