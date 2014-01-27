package com.davidru85.openweather;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
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
	private boolean alert;
	private long[] vibrate = { 0, 1000, 300, 1000, 100 };

	private int icon = 0;
	private String description = "", nameCity = "";

	public Notifications(Context appContext) {
		context = appContext;

		prefs = context.getSharedPreferences(Values.getPrefs(),
				Context.MODE_PRIVATE);
		Log.d(LogDavid, "PREFERENCES: " + prefs.getAll());

		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		notIntent = new Intent(context, MainActivity.class);
		contIntent = PendingIntent.getActivity(context, 0, notIntent, 0);
	}

	public void notify(Weather weather) {
		edit = prefs.edit();
		edit.putBoolean(Values.getPrevNotif(), true);
		edit.commit();

		getValuesNotification(weather);

		n = new NotificationCompat.Builder(context)
				.setContentTitle(nameCity)
				.setContentText(description)
				.setSmallIcon(icon)
				.setAutoCancel(true)
				.setContentIntent(contIntent)
				.setLargeIcon(
						(((BitmapDrawable) context.getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()));

		if (alert) {
			n.setSound(RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			n.setVibrate(vibrate);
		}

		mNotificationManager.notify(0, n.build());
	}

	private void getValuesNotification(Weather weather) {
		int weather_id = weather.getWeather_id();
		nameCity = weather.getCity_name();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.clear();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		alert = Conversor.soundVibrationAvailable(hour, prefs);

		if (weather_id >= 200 && weather_id <= 232) {
			if (weather_id == 200) {
				description = context.getResources().getString(
						R.string.description_200);
			} else if (weather_id == 201) {
				description = context.getResources().getString(
						R.string.description_201);
			} else if (weather_id == 202) {
				description = context.getResources().getString(
						R.string.description_202);
			} else if (weather_id == 210) {
				description = context.getResources().getString(
						R.string.description_210);
			} else if (weather_id == 211) {
				description = context.getResources().getString(
						R.string.description_211);
			} else if (weather_id == 212) {
				description = context.getResources().getString(
						R.string.description_212);
			} else if (weather_id == 221) {
				description = context.getResources().getString(
						R.string.description_221);
			} else if (weather_id == 230) {
				description = context.getResources().getString(
						R.string.description_230);
			} else if (weather_id == 231) {
				description = context.getResources().getString(
						R.string.description_231);
			} else if (weather_id == 232) {
				description = context.getResources().getString(
						R.string.description_232);
			} else {
				description = context.getResources().getString(
						R.string.description_211);
			}
			icon = R.drawable.icon_thunderstorm_day;
		} else if (weather_id >= 300 && weather_id <= 321) {
			if (weather_id == 300) {
				description = context.getResources().getString(
						R.string.description_300);
			} else if (weather_id == 301) {
				description = context.getResources().getString(
						R.string.description_301);
			} else if (weather_id == 302) {
				description = context.getResources().getString(
						R.string.description_302);
			} else if (weather_id == 310) {
				description = context.getResources().getString(
						R.string.description_310);
			} else if (weather_id == 311) {
				description = context.getResources().getString(
						R.string.description_311);
			} else if (weather_id == 312) {
				description = context.getResources().getString(
						R.string.description_312);
			} else if (weather_id == 313) {
				description = context.getResources().getString(
						R.string.description_313);
			} else if (weather_id == 314) {
				description = context.getResources().getString(
						R.string.description_314);
			} else if (weather_id == 321) {
				description = context.getResources().getString(
						R.string.description_321);
			} else {
				description = context.getResources().getString(
						R.string.description_311);
			}
			icon = R.drawable.icon_rain_day;
		} else if (weather_id >= 500 && weather_id <= 522) {
			if (weather_id == 500) {
				description = context.getResources().getString(
						R.string.description_500);
			} else if (weather_id == 501) {
				description = context.getResources().getString(
						R.string.description_501);
			} else if (weather_id == 502) {
				description = context.getResources().getString(
						R.string.description_502);
			} else if (weather_id == 503) {
				description = context.getResources().getString(
						R.string.description_503);
			} else if (weather_id == 504) {
				description = context.getResources().getString(
						R.string.description_504);
			} else if (weather_id == 511) {
				description = context.getResources().getString(
						R.string.description_511);
			} else if (weather_id == 520) {
				description = context.getResources().getString(
						R.string.description_520);
			} else if (weather_id == 521) {
				description = context.getResources().getString(
						R.string.description_521);
			} else if (weather_id == 522) {
				description = context.getResources().getString(
						R.string.description_522);
			} else if (weather_id == 531) {
				description = context.getResources().getString(
						R.string.description_531);
			} else {
				description = context.getResources().getString(
						R.string.description_501);
			}
			icon = R.drawable.icon_rain_day;
		} else if (weather_id >= 600 && weather_id <= 621) {
			if (weather_id == 600) {
				description = context.getResources().getString(
						R.string.description_600);
			} else if (weather_id == 601) {
				description = context.getResources().getString(
						R.string.description_601);
			} else if (weather_id == 602) {
				description = context.getResources().getString(
						R.string.description_602);
			} else if (weather_id == 611) {
				description = context.getResources().getString(
						R.string.description_611);
			} else if (weather_id == 612) {
				description = context.getResources().getString(
						R.string.description_612);
			} else if (weather_id == 615) {
				description = context.getResources().getString(
						R.string.description_615);
			} else if (weather_id == 616) {
				description = context.getResources().getString(
						R.string.description_616);
			} else if (weather_id == 620) {
				description = context.getResources().getString(
						R.string.description_620);
			} else if (weather_id == 621) {
				description = context.getResources().getString(
						R.string.description_621);
			} else if (weather_id == 622) {
				description = context.getResources().getString(
						R.string.description_622);
			} else {
				description = context.getResources().getString(
						R.string.description_601);
			}
			icon = R.drawable.icon_snow_day;
		} else if (weather_id >= 700 && weather_id <= 741) {
			if (weather_id == 701) {
				description = context.getResources().getString(
						R.string.description_701);
			} else if (weather_id == 711) {
				description = context.getResources().getString(
						R.string.description_711);
			} else if (weather_id == 721) {
				description = context.getResources().getString(
						R.string.description_721);
			} else if (weather_id == 731) {
				description = context.getResources().getString(
						R.string.description_731);
			} else if (weather_id == 741) {
				description = context.getResources().getString(
						R.string.description_741);
			} else if (weather_id == 751) {
				description = context.getResources().getString(
						R.string.description_751);
			} else if (weather_id == 761) {
				description = context.getResources().getString(
						R.string.description_761);
			} else if (weather_id == 762) {
				description = context.getResources().getString(
						R.string.description_762);
			} else if (weather_id == 771) {
				description = context.getResources().getString(
						R.string.description_771);
			} else if (weather_id == 781) {
				description = context.getResources().getString(
						R.string.description_781);
			} else {
				description = context.getResources().getString(
						R.string.description_701);
			}
			icon = R.drawable.icon_mist_day;
		} else if (weather_id >= 800 && weather_id <= 804) {
			if (weather_id == 800) {
				description = context.getResources().getString(
						R.string.description_800);
				icon = R.drawable.icon_clear_day;
			} else if (weather_id == 801) {
				description = context.getResources().getString(
						R.string.description_801);
				icon = R.drawable.icon_scattered_clouds_day;
			} else if (weather_id == 802) {
				description = context.getResources().getString(
						R.string.description_802);
				icon = R.drawable.icon_scattered_clouds_day;
			} else if (weather_id == 803) {
				description = context.getResources().getString(
						R.string.description_803);
				icon = R.drawable.icon_broken_clouds_day;
			} else {
				description = context.getResources().getString(
						R.string.description_804);
				icon = R.drawable.icon_broken_clouds_day;
			}
		}
	}
}
