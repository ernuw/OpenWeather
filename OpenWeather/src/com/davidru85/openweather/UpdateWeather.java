package com.davidru85.openweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class UpdateWeather extends BroadcastReceiver {
	private static final String LogDavid = "OpenWeather";

	private Context context;
	private SharedPreferences prefs;
	private Location loc;
	private String URLForecast;

	@Override
	public void onReceive(Context appContext, Intent intent) {
		context = appContext;
		loc = Localizator.geoLocate(context);
		if (loc != null) {
			URLForecast = Conversor.getUrlForecast(loc);

			/*
			 * Toast.makeText(context,
			 * "¡Vibration Mode On porque el tiempo se ha terminado!",
			 * Toast.LENGTH_LONG).show();
			 */

			// Define la vibracion del telefono

			/*
			 * Vibrator vibrator = (Vibrator) context
			 * .getSystemService(Context.VIBRATOR_SERVICE);
			 * vibrator.vibrate(1000);
			 */
			prefs = context.getSharedPreferences(Values.getPrefs(),
					Context.MODE_PRIVATE);
			prefs.edit().putBoolean("active", true).commit();
			check_server();
		}
	}

	private void check_server() {
		JsonParser jsonParser = new JsonParser(URLForecast);
		WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask();
		Editor edit;
		try {
			if (necesaryUpdate()) {
				Log.d(LogDavid, "NECESITO ACTUALIZAR");
				Weather weathers[] = weatherAsyncTask.execute(jsonParser).get();
				int n = Conversor.getNextWeather(weathers);
				Log.e(LogDavid, "N = " + n);
				Weather weather = weathers[n + 1];

				save_weather(weathers[n]);

				/*
				 * Toast toast2 = Toast.makeText(context, R.string.success,
				 * Toast.LENGTH_SHORT); toast2.show();
				 */

				if (weather.getRain_threehours() > 0
						|| weather.getSnow_threehours() > 0) {
					Log.d(LogDavid, "NECESITO AVISAR");
					if (Conversor.ifNotificationAllowed(prefs)) {
						Log.d(LogDavid, "PUEDO AVISAR");
						Notifications notif = new Notifications(context);
						notif.notify(weather);
					} else {
						Log.d(LogDavid, "NO PUEDO AVISAR");
					}
				} else {
					Log.d(LogDavid, "NO NECESITO AVISAR - PREVIOUS FALSE");
					edit = prefs.edit();
					edit.putBoolean(Values.getPrevNotif(), false);
					edit.commit();
				}
			} else {
				Log.d(LogDavid, "NO ACTUALIZO");
			}
		} catch (Exception e) {
			Log.e(LogDavid, "Error Refresh: " + e.toString());
			e.printStackTrace();
		}
	}

	private boolean necesaryUpdate() {
		prefs = context.getSharedPreferences(Values.getPrefs(),
				Context.MODE_PRIVATE);
		long date_last_update = prefs.getLong("date_update", 0) * 1000;
		if ((System.currentTimeMillis() - date_last_update) < Values.elapsed_time / 2) {
			return false;
		}
		Log.d(LogDavid, "UPDATE_NECESARY");
		return true;
	}

	private void save_weather(Weather weather) {
		Editor edit = prefs.edit();
		Conversor.saveWeather(edit, weather);
	}
}
