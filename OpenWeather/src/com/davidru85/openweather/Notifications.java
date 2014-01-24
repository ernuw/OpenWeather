package com.davidru85.openweather;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
//import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Notifications {
	
	private static final String LogDavid = "OpenWeather";
	
	private NotificationManager mNotificationManager; 
	private Intent notIntent;
	private PendingIntent contIntent;
	private Context context;
	private NotificationCompat.Builder n;
	private SharedPreferences prefs;
	private SharedPreferences.Editor edit;
	
	public Notifications(Context appContext){
		context = appContext;
		
		prefs = context.getSharedPreferences(Values.getPrefs(), Context.MODE_PRIVATE);
		Log.d(LogDavid, "PREFERENCES: " + prefs.getAll());
		
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		notIntent = new Intent(context, MainActivity.class);
		contIntent = PendingIntent.getActivity(context, 0,
				notIntent, 0);
	}
	
	public void notify(Weather weather) {
		edit = prefs.edit();
		edit.putBoolean(Values.getPrevNotif(), true);
		edit.commit();
		
		long[] vibrate = { 0, 1000, 300, 1000, 100 };
		
		n = new NotificationCompat.Builder(context)
				.setContentTitle(weather.getCity_name())
				.setContentText(weather.getWeather_description())
				.setSmallIcon(getIcon(weather.getWeather_id()))
				.setAutoCancel(true)
				.setContentIntent(contIntent)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setVibrate(vibrate)
				.setLargeIcon(
						(((BitmapDrawable) context.getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()));

		mNotificationManager.notify(0, n.build());
	}
	
	private int getIcon(int weather_id){
		if (weather_id >= 200 && weather_id <= 232) {
			return(R.drawable.icon_thunderstorm_day);
		}
		if (weather_id >= 300 && weather_id <= 321) {
			return(R.drawable.icon_rain_day);
		}
		if (weather_id >= 500 && weather_id <= 522) {
			return(R.drawable.icon_rain_day);
		}
		if (weather_id >= 600 && weather_id <= 621) {
			return(R.drawable.icon_snow_day);
		}
		if (weather_id >= 700 && weather_id <= 741) {
			return(R.drawable.icon_mist_day);
		}
		if (weather_id == 800) {
			return(R.drawable.icon_clear_day);
		}
		if (weather_id == 801) {
			return(R.drawable.icon_scattered_clouds_day);
		}
		if (weather_id == 802) {
			return(R.drawable.icon_scattered_clouds_day);
		}
		if (weather_id == 803) {
			return(R.drawable.icon_broken_clouds_day);
		}
		if (weather_id == 804) {
			return(R.drawable.icon_broken_clouds_day);
		}
		return 800;
	}
}
