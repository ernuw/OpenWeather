package com.davidru85.openweather;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class UpdateWeather extends BroadcastReceiver {
	private static final String LogDavid = "OpenWeather";

	private Context context;
	private SharedPreferences prefs;
	private Location loc;
	private String URL;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		loc = Localizator.geoLocate(context);
		if (loc != null) {
			URL = Conversor.getUrlWeather(loc);

			/*
			 * Toast.makeText(context,
			 * "¡Vibration Mode On porque el tiempo se ha terminado!",
			 * Toast.LENGTH_LONG).show();
			 */
			// Define la vibracion del telefono
			/*
			 * Vibrator vibrator = (Vibrator)
			 * context.getSystemService(Context.VIBRATOR_SERVICE);
			 * vibrator.vibrate(2000);
			 */
			prefs = PreferenceManager.getDefaultSharedPreferences(context);
			prefs.edit().putBoolean("active", true).commit();
			check_server();
		}
	}

	private void check_server() {
		JsonParser jsonParser = new JsonParser(URL);
		WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask();
		try {
			if (necesaryUpdate()) {
				Weather weather = weatherAsyncTask.execute(jsonParser).get();
				if (weather != null) {
					save_weather(weather);

					Toast toast2 = Toast.makeText(context, R.string.success,
							Toast.LENGTH_SHORT);
					toast2.show();

					if (weather.getRain_threehours() > 0
							|| weather.getSnow_threehours() > 0) {
						// TODO NOTIFICACIÓN Probabilidad de lluvia o nieve
						notifyWeather();						
					}
				} else {
					Toast toast2 = Toast.makeText(context, R.string.no_weather,
							Toast.LENGTH_SHORT);
					toast2.show();
					Log.e(LogDavid, "No Weather");
				}
			}
		} catch (Exception e) {
			Log.e(LogDavid, "Error Refresh: " + e.toString());
			e.printStackTrace();
		}

	}

	private void notifyWeather() {
		// TODO Auto-generated method stub
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification n  = new NotificationCompat.Builder(context)
        .setContentTitle("RAIN OR SNOW")
        .setContentText("Rain/Snow")
        .setSmallIcon(R.drawable.icon_rain_day)/*
        .setContentIntent(pIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.icon, "Call", pIntent)
        .addAction(R.drawable.icon, "More", pIntent)
        .addAction(R.drawable.icon, "And more", pIntent)*/.build();
    

		mNotificationManager.notify(0, n);
	}

	private boolean necesaryUpdate() {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		long date_last_update = prefs.getLong("date_update", 0) * 1000;
		if ((System.currentTimeMillis() - date_last_update) < Values.FIFTEEN_MINUTES) {
			return false;
		}
		return true;
	}

	private void save_weather(Weather weather) {
		Editor edit = prefs.edit();
		Conversor.saveWeather(edit, weather);

	}

}
