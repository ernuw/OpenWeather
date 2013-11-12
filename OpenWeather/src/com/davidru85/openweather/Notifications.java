package com.davidru85.openweather;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Notifications {
	
	private static final String LogDavid = "OpenWeather";
	
	private NotificationManager mNotificationManager; 
	private Intent notIntent;
	private PendingIntent contIntent;
	private Context context;
	private NotificationCompat.Builder n;
	
	public Notifications(Context appContext){
		context = appContext;
		
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		notIntent = new Intent(context, MainActivity.class);
		contIntent = PendingIntent.getActivity(context, 0,
				notIntent, 0);
	}

	public void notifyBoth() {
		Log.d(LogDavid, "SNOW & RAIN");

		n = new NotificationCompat.Builder(context)
				.setContentTitle("RAIN AND SNOW")
				.setContentText("Rain & Snow")
				.setSmallIcon(R.drawable.icon_snow_day)
				.setAutoCancel(true)
				.setContentIntent(contIntent)
				.setLargeIcon(
						(((BitmapDrawable) context.getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()));

		mNotificationManager.notify(0, n.build());
	}
	
	public void notifySnow() {
		Log.d(LogDavid, "SNOW");

		n = new NotificationCompat.Builder(context)
				.setContentTitle("SNOW")
				.setContentText("Snow")
				.setSmallIcon(R.drawable.icon_snow_day)
				.setAutoCancel(true)
				.setContentIntent(contIntent)
				.setLargeIcon(
						(((BitmapDrawable) context.getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()));

		mNotificationManager.notify(0, n.build());
	}
	
	public void notifyRain() {
		Log.d(LogDavid, "RAIN");

		n = new NotificationCompat.Builder(context)
				.setContentTitle("RAIN")
				.setContentText("Rain")
				.setSmallIcon(R.drawable.icon_rain_day)
				.setAutoCancel(true)
				.setContentIntent(contIntent)
				.setLargeIcon(
						(((BitmapDrawable) context.getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()));

		mNotificationManager.notify(0, n.build());
	}
}
