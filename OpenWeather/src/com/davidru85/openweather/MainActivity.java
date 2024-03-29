package com.davidru85.openweather;

import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String LogDavid = "OpenWeather";

	private String URL;
	private TextView textTemperature, textCity;
	private RelativeLayout rl;
	private ImageView iconWeather;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rl = (RelativeLayout) findViewById(R.id.main_relative);
		textCity = (TextView) findViewById(R.id.textCity);
		textTemperature = (TextView) findViewById(R.id.textTemperature);
		iconWeather = (ImageView) findViewById(R.id.iconWeather);

		prefs = getApplicationContext().getSharedPreferences(Values.getPrefs(),
				Context.MODE_PRIVATE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		preRefresh();
	}

	@Override
	protected void onResume() {
		super.onResume();
		preRefresh();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Localizator.stop();
	}

	private void preRefresh() {
		if (isNetworkAvailable()) {
			if (isLocationProviderEnabled()) {
				Location loc = Localizator.geoLocate(getApplicationContext());
				//loc.setLatitude(60.17332440000001);
				//loc.setLongitude(24.941024800000037);
				if (loc != null) {
					URL = Conversor.getUrlForecast(loc);
					if (!isMyServiceRunning()) {
						refresh();
					} else {
						show_info();
					}
				} else {
					// TODO LOC ES NULO
					noLocation();
				}
			} else {
				activeLocation();
			}
		} else {
			no_internet();
		}
	}

	private void noLocation() {
		textTemperature.setText(getResources().getString(R.string.no_location));
		textCity.setText("");
	}

	private boolean isLocationProviderEnabled() {
		return Localizator.isProviderEnabled(getApplicationContext());
	}

	private void refresh() {
		if (isNetworkAvailable()) {
			JsonParser jsonParser = new JsonParser(URL);
			WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask();
			try {
				Weather weather[] = weatherAsyncTask.execute(jsonParser).get();

				if (weather != null) {
					save_weather(weather[0]);
					Log.d(LogDavid, "SAVE");
					Toast toast2 = Toast.makeText(getApplicationContext(),
							R.string.success, Toast.LENGTH_SHORT);
					// toast2.show();
					show_info();
				} else {
					Toast toast2 = Toast.makeText(getApplicationContext(),
							R.string.no_weather, Toast.LENGTH_SHORT);
					toast2.show();
				}
			} catch (Exception e) {
				Log.e(LogDavid, "Error Refresh: " + e.toString());
			}
		} else {
			no_internet();
		}
		startServiceWeather();
	}

	private void startServiceWeather() {
		Intent i = new Intent(getApplicationContext(), ServiceWeather.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startService(i);
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (ServiceWeather.class.getName().equals(
					service.service.getClassName())) {
				Log.d(LogDavid, "RUNNING");
				return true;
			}
		}
		Log.d(LogDavid, "NOT RUNNING");
		return false;
	}

	private void save_weather(Weather weather) {
		Editor edit = prefs.edit();
		Conversor.saveWeather(edit, weather);
	}

	private void show_info() {
		Log.d(LogDavid, "SHOW");
		if (prefs.getBoolean("already_iniciated", false)) {
			Log.d(LogDavid, "IF");
			Weather weather = Conversor.getWeather(prefs);
			if (weather != null && weather.getData_receiving() != 0) {
				long weatherId = weather.getWeather_id();
				textCity.setText(weather.getCity_name());
				textTemperature.setText((int) Conversor
						.roundNoDecimals(Conversor.kelvinToCelsius(weather
								.getTemp()))
						+ " Cº");

				if (weatherId >= 200 && weatherId <= 232) {
					changeBackground(
							rl,
							getResources().getDrawable(
									R.drawable.img_thunderstorm));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_thunderstorm_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.white));
					textCity.setTextColor(getResources()
							.getColor(R.color.white));
				}
				if (weatherId >= 300 && weatherId <= 321) {
					changeBackground(rl,
							getResources().getDrawable(R.drawable.img_rain));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_rain_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.white));
					textCity.setTextColor(getResources()
							.getColor(R.color.white));
				}
				if (weatherId >= 500 && weatherId <= 522) {
					changeBackground(rl,
							getResources().getDrawable(R.drawable.img_rain));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_rain_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.white));
					textCity.setTextColor(getResources()
							.getColor(R.color.white));
				}
				if (weatherId >= 600 && weatherId <= 621) {
					// TODO Cambiar color texto
					changeBackground(rl,
							getResources().getDrawable(R.drawable.img_snow));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_snow_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.aqua));
					textCity.setTextColor(getResources().getColor(R.color.aqua));
				}
				if (weatherId >= 700 && weatherId <= 741) {
					changeBackground(rl,
							getResources().getDrawable(R.drawable.img_mist));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_mist_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.white));
					textCity.setTextColor(getResources()
							.getColor(R.color.white));

				}
				if (weatherId == 800) {
					changeBackground(rl,
							getResources().getDrawable(R.drawable.img_clear));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_clear_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.black));
					textCity.setTextColor(getResources()
							.getColor(R.color.black));

				}
				if (weatherId == 801) {
					changeBackground(
							rl,
							getResources().getDrawable(
									R.drawable.img_scattered_clouds));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_scattered_clouds_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.black));
					textCity.setTextColor(getResources()
							.getColor(R.color.black));

				}
				if (weatherId == 802) {
					changeBackground(
							rl,
							getResources().getDrawable(
									R.drawable.img_scattered_clouds));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_scattered_clouds_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.black));
					textCity.setTextColor(getResources()
							.getColor(R.color.black));

				}
				if (weatherId == 803) {
					changeBackground(
							rl,
							getResources().getDrawable(
									R.drawable.img_broken_clouds));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_broken_clouds_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.gray));
					textCity.setTextColor(getResources().getColor(R.color.gray));

				}
				if (weatherId == 804) {
					changeBackground(
							rl,
							getResources().getDrawable(
									R.drawable.img_broken_clouds));
					iconWeather.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_broken_clouds_day));
					textTemperature.setTextColor(getResources().getColor(
							R.color.gray));
					textCity.setTextColor(getResources().getColor(R.color.gray));

				}
			} else {
				textTemperature.setText("Unknown");
				textCity.setText("Unknown");
				rl.setBackgroundColor(getResources().getColor(R.color.white));
				iconWeather.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_launcher));
				textTemperature.setTextColor(getResources().getColor(
						R.color.white));
				textCity.setTextColor(getResources().getColor(R.color.white));

			}
		} else {
			prefs.edit().putBoolean("already_iniciated", true).commit();
			refresh();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void changeBackground(RelativeLayout rel, Drawable draw) {
		int sdkValue = Integer.valueOf(android.os.Build.VERSION.SDK);
		if (sdkValue < 16) {
			rel.setBackgroundDrawable(draw);
		} else {
			rel.setBackground(draw);
		}
	}

	private void no_internet() {
		Toast toast2 = Toast.makeText(getApplicationContext(),
				R.string.internet_error, Toast.LENGTH_SHORT);
		toast2.show();
	}

	private void settings() {
		Editor edit = prefs.edit();
		String versionName;
		try {
			versionName = getPackageManager().getPackageInfo(getPackageName(),
					0).versionName;
		} catch (NameNotFoundException e) {
			Log.d(LogDavid, e.toString());
			versionName = "Null";
		}
		edit.putString("appVersion", versionName);
		edit.commit();
		Intent settingsIntent = new Intent(MainActivity.this,
				WeatherPreferences.class);
		startActivity(settingsIntent);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_refresh) {
			refresh();
			return true;
		} else if (itemId == R.id.action_settings) {
			settings();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	public void activeLocation() {
		new AlertDialog.Builder(this)
				.setTitle(
						getResources().getString(
								R.string.location_provider_disabled_title))
				.setMessage(
						getResources().getString(
								R.string.location_provider_disabled_text))
				.setPositiveButton(getResources().getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(intent);
								finish();
							}
						})
				.setNegativeButton(getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).show();
	}
}