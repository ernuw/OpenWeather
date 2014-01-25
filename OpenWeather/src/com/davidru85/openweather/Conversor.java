package com.davidru85.openweather;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.util.Log;

public class Conversor {
	private static final String LogDavid = "OpenWeather";

	private static String latitude = "lat";
	private static String longitude = "lon";

	private static String country = "country";
	private static String sunrise = "sunrise";
	private static String sunset = "sunset";

	private static String weather_main = "main";
	private static String weather_description = "description";
	private static String weather_icon = "icon";

	private static String temp = "temp";
	private static String temp_max = "temp_max";
	private static String temp_min = "temp_min";
	private static String pressure = "pressure";
	private static String sea_level = "sea_level";
	private static String grnd_level = "grnd_level";
	private static String humidity = "humidity";

	private static String wind_speed = "speed";
	private static String wind_degrees = "deg";

	private static String rain_threehours = "3h";

	private static String snow_threehours = "3h";

	private static String clouds_all = "all";

	private static String data_receiving = "dt";
	private static String date_update = "date_update";
	private static String city_id = "id";
	private static String city_name = "name";
	private static String code = "cod";

	public static double kelvinToCelsius(double temperature) {
		return (temperature - 273.15);
	}

	public static Weather jsonToWeather(JSONObject datos_object) {
		return OpenWeatherMapManager.jsonToWeather(datos_object);
	}

	public static Weather jsonToWeather2(JSONObject datos_object) {
		return OpenWeatherMapManager.jsonToWeather2(datos_object);
	}

	public static Weather[] jsonToForecast(JSONObject datos_object) {
		return OpenWeatherMapManager.jsonToForecast(datos_object);
	}

	public static String dateToString(long date) {
		String format, language;
		language = Locale.getDefault().getISO3Language();
		if (language.equals("spa")) {
			format = "dd/MM/yyyy HH:mm:ss";
		} else {
			format = "EEE, MMM d, yyyy HH:mm:ss";
		}
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		String fecha = sdf.format(date * 1000);
		return fecha;
	}

	public static boolean saveWeather(Editor edit, Weather weather) {
		edit.putLong(latitude, Double.doubleToLongBits(weather.getLatitude()));
		edit.putLong(longitude, Double.doubleToLongBits(weather.getLongitude()));
		edit.putString(country, weather.getCountry());
		edit.putLong(sunrise, weather.getSunrise());
		edit.putLong(sunset, weather.getSunset());
		edit.putInt("weather_id", weather.getWeather_id());
		edit.putString(weather_main, weather.getWeather_main());
		edit.putString(weather_description, weather.getWeather_description());
		edit.putString(weather_icon, weather.getWeather_icon());
		edit.putLong(temp, Double.doubleToLongBits(weather.getTemp()));
		edit.putLong(temp_min, Double.doubleToLongBits(weather.getTemp_min()));
		edit.putLong(temp_max, Double.doubleToLongBits(weather.getTemp_max()));
		edit.putLong(pressure, Double.doubleToLongBits(weather.getPreassure()));
		edit.putLong(sea_level, Double.doubleToLongBits(weather.getSea_level()));
		edit.putLong(grnd_level,
				Double.doubleToLongBits(weather.getGrnd_level()));
		edit.putLong(humidity, Double.doubleToLongBits(weather.getHumidity()));
		edit.putLong(wind_speed,
				Double.doubleToLongBits(weather.getWind_speed()));
		edit.putLong(wind_degrees,
				Double.doubleToLongBits(weather.getWind_degrees()));
		edit.putLong(rain_threehours,
				Double.doubleToLongBits(weather.getRain_threehours()));
		edit.putLong(snow_threehours,
				Double.doubleToLongBits(weather.getSnow_threehours()));
		edit.putLong(clouds_all,
				Double.doubleToLongBits(weather.getClouds_all()));
		edit.putLong(data_receiving, weather.getData_receiving());
		edit.putLong(date_update, System.currentTimeMillis() / 1000);
		edit.putString(city_id, weather.getCity_id());
		edit.putString(city_name, weather.getCity_name());
		edit.putString(code, weather.getCode());
		edit.putString("date_last_update",
				dateToString(System.currentTimeMillis() / 1000));
		edit.putString("last_date_server",
				dateToString(weather.getData_receiving()));
		return (edit.commit());
	}

	public static Weather getWeather(SharedPreferences prefs) {
		Weather weather = new Weather();
		weather.setLatitude(Double.longBitsToDouble(prefs.getLong(latitude, 0)));
		weather.setLongitude(Double.longBitsToDouble(prefs
				.getLong(longitude, 0)));
		weather.setCountry(prefs.getString(country, ""));
		weather.setSunrise(prefs.getLong(sunrise, 0));
		weather.setSunset(prefs.getLong(sunset, 0));
		weather.setWeather_id(prefs.getInt("weather_id", 0));
		weather.setWeather_main(prefs.getString(weather_main, ""));
		weather.setWeather_description(prefs.getString(weather_description, ""));
		weather.setWeather_icon(prefs.getString(weather_icon, ""));
		weather.setTemp(Double.longBitsToDouble(prefs.getLong(temp, 0)));
		weather.setTemp_min(Double.longBitsToDouble(prefs.getLong(temp_min, 0)));
		weather.setTemp_max(Double.longBitsToDouble(prefs.getLong(temp_max, 0)));
		weather.setPreassure(Double.longBitsToDouble(prefs.getLong(pressure, 0)));
		weather.setSea_level(Double.longBitsToDouble(prefs
				.getLong(sea_level, 0)));
		weather.setGrnd_level(Double.longBitsToDouble(prefs.getLong(grnd_level,
				0)));
		weather.setHumidity(Double.longBitsToDouble(prefs.getLong(humidity, 0)));
		weather.setWind_speed(Double.longBitsToDouble(prefs.getLong(wind_speed,
				0)));
		weather.setWind_degrees(Double.longBitsToDouble(prefs.getLong(
				wind_degrees, 0)));
		weather.setRain_threehours(Double.longBitsToDouble(prefs.getLong(
				rain_threehours, 0)));
		weather.setSnow_threehours(Double.longBitsToDouble(prefs.getLong(
				snow_threehours, 0)));
		weather.setClouds_all(Double.longBitsToDouble(prefs.getLong(clouds_all,
				0)));
		weather.setData_receiving(prefs.getLong(data_receiving, 0));
		weather.setCity_id(prefs.getString(city_id, ""));
		weather.setCity_name(prefs.getString(city_name, ""));
		weather.setCode(prefs.getString(code, ""));

		return weather;
	}

	public static double roundTwoDecimals(double d) {

		return (Math.floor(d * 100) / 100);
	}

	public static double roundNoDecimals(double d) {

		DecimalFormat twoDForm = new DecimalFormat("#");
		return Double.valueOf(twoDForm.format(d));
	}

	public static String getUrlWeather(Location loc) {
		// TODO
		String URL = Values.getWeatherURL();
		URL = URL + "lat=" + loc.getLatitude();
		URL = URL + "&";
		URL = URL + "lon=" + loc.getLongitude();
		URL = URL + "&mode=json";
		return URL;
	}

	public static String getUrlWeather2(Location loc) {
		// TODO
		String URL = Values.getWeatherURL2();
		URL = URL + "lat=" + loc.getLatitude();
		URL = URL + "&";
		URL = URL + "lon=" + loc.getLongitude();
		URL = URL + "&mode=json";
		return URL;
	}

	public static String getUrlForecast(Location loc) {
		String URL = Values.getForecastURL();
		URL = URL + "lat=" + loc.getLatitude();
		URL = URL + "&";
		URL = URL + "lon=" + loc.getLongitude();
		URL = URL + "&mode=json";
		return URL;
	}

	public static boolean ifNotificationAllowed(SharedPreferences prefs) {
		boolean notify = prefs.getBoolean("ActiveNotifications", true);
		String Sprevious = Values.getPrevNotif();
		boolean previous = prefs.getBoolean(Sprevious, false);
		Log.d(LogDavid, "PREV: " + previous);
		if (notify == true) {
			if (previous == true) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public static int getNextWeather(Weather[] weather) {
		long actual_time = System.currentTimeMillis();
		for (int n = 0; n < weather.length; n++) {
			if (weather[n].getData_receiving() > actual_time) {
				if (n == 0)
					return 1;
				else
					return n;
			}
		}
		return 1;
	}

	public static boolean soundVibrationAvailable(int hour, SharedPreferences prefs) {
		if (hour > 0 && hour < 8)
			return false;
		else
			return true;
	}
}