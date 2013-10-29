package com.davidru85.openweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WeatherStarter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, ServiceWeather.class);  // aqui llama a la clase que comienza la app
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
	}

}
