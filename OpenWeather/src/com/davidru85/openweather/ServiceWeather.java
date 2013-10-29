package com.davidru85.openweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceWeather extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Toast.makeText(this, "Servicio en Ejecucion", Toast.LENGTH_SHORT).show();

		Intent intentUpdate = new Intent(this, UpdateWeather.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intentUpdate,PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), Values.getElapsedTime(),pendingIntent);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
	}

}
