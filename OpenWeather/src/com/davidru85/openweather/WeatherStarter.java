package com.davidru85.openweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class WeatherStarter extends BroadcastReceiver {

	private static final String LogDavid = "OpenWeather";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, ServiceWeather.class);  // aqui llama a la clase que comienza la app
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		SharedPreferences prefs = context.getSharedPreferences(Values.getPrefs(), Context.MODE_PRIVATE);
		
		Editor edit = prefs.edit();
		edit.putBoolean(Values.getPrevNotif(), false);
		edit.commit();
		Log.d(LogDavid, "Prev -> False");
		
		context.startService(i);
	}

}
