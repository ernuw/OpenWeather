package com.davidru85.openweather;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.util.Log;

public class Conversor {
	private static final String LogDavid = "OpenWeather";

	private static String coordinates = "coord";
	private static String system = "sys";
	private static String weather = "weather";
	private static String main = "main";
	private static String wind = "wind";
	private static String rain = "rain";
	private static String snow = "snow";
	private static String clouds = "clouds";

	private static String latitude = "lat";
	private static String longitude = "lon";

	private static String country = "country";
	private static String sunrise = "sunrise";
	private static String sunset = "sunset";

	private static String weather_id = "id";
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

	public static Weather[] jsonToForecast(JSONObject datos_object) {
		// Weather[] forecast = new Weather[];
		return null;
	}

	public static Weather jsonToWeather2(JSONObject datos_object) {
		try {
			Weather weather = new Weather();
			
			weather.setCode(datos_object.getString(code));
			
			if(datos_object.has("list")){
				JSONArray listAr = datos_object.getJSONArray("list");
				JSONObject listOb = listAr.getJSONObject(0);
				
				weather.setCity_name(listOb.getString(city_name));
				
				JSONObject coordOb = listOb.getJSONObject(coordinates);
				weather.setLatitude(coordOb.getDouble(latitude));
				weather.setLongitude(coordOb.getDouble(longitude));
				
				JSONObject mainOb = listOb.getJSONObject(main);
				weather.setTemp(mainOb.getDouble(temp));
				weather.setPreassure(mainOb.getDouble(pressure));
				weather.setHumidity(mainOb.getDouble(humidity));

				if (mainOb.has(temp_max))
					weather.setTemp_max(mainOb.getDouble(temp_max));
				else
					weather.setTemp_max(0);
				if (mainOb.has(temp_min))
					weather.setTemp_min(mainOb.getDouble(temp_min));
				else
					weather.setTemp_min(0);
				if (mainOb.has(sea_level))
					weather.setSea_level(mainOb.getDouble(sea_level));
				else
					weather.setSea_level(0);
				if (mainOb.has(grnd_level))
					weather.setGrnd_level(mainOb.getDouble(grnd_level));
				else
					weather.setGrnd_level(0);
				
				weather.setData_receiving(listOb.getLong(data_receiving));
				
				JSONObject windOb = listOb.getJSONObject(wind);
				weather.setWind_speed(windOb.getDouble(wind_speed));
				weather.setWind_degrees(windOb.getDouble(wind_degrees));
				
				JSONObject cloudsOb = listOb.getJSONObject(clouds);
				weather.setClouds_all(cloudsOb.getDouble(clouds_all));
				
				if (listOb.has(Conversor.weather)) {
					JSONArray weatherAr = listOb.getJSONArray(Conversor.weather);
					JSONObject weatherOb = weatherAr.getJSONObject(0);
					weather.setWeather_id(weatherOb.getInt(weather_id));
					weather.setWeather_main(weatherOb.getString(weather_main));
					weather.setWeather_description(weatherOb.getString(weather_description));
					weather.setWeather_icon(weatherOb.getString(weather_icon));
				} else {
					Log.d(LogDavid, "jsonToWeather2: NoWeather");
				}
			}
			return weather;
		} catch (Exception e) {
			return null;
		}
	}

	public static Weather jsonToWeather(JSONObject datos_object) {
		try {
			Weather weather = new Weather();
			
			JSONObject coordOb = datos_object.getJSONObject(coordinates);
			weather.setLatitude(coordOb.getDouble(latitude));
			weather.setLongitude(coordOb.getDouble(longitude));

			JSONObject sysOb = datos_object.getJSONObject(system);
			weather.setCountry(sysOb.getString(country));
			weather.setSunrise(sysOb.getLong(sunrise));
			weather.setSunset(sysOb.getLong(sunset));

			if (datos_object.has(Conversor.weather)) {
				JSONArray weatherAr = datos_object.getJSONArray(Conversor.weather);
				JSONObject weatherOb = weatherAr.getJSONObject(0);
				weather.setWeather_id(weatherOb.getInt(weather_id));
				weather.setWeather_main(weatherOb.getString(weather_main));
				weather.setWeather_description(weatherOb.getString(weather_description));
				weather.setWeather_icon(weatherOb.getString(weather_icon));
			} else {
				Log.d(LogDavid, "jsonToWeather: NoWeather");
			}

			JSONObject mainOb = datos_object.getJSONObject(main);
			weather.setTemp(mainOb.getDouble(temp));
			weather.setPreassure(mainOb.getDouble(pressure));
			weather.setHumidity(mainOb.getDouble(humidity));

			if (mainOb.has(temp_max))
				weather.setTemp_max(mainOb.getDouble(temp_max));
			else
				weather.setTemp_max(0);
			if (mainOb.has(temp_min))
				weather.setTemp_min(mainOb.getDouble(temp_min));
			else
				weather.setTemp_min(0);
			if (mainOb.has(sea_level))
				weather.setSea_level(mainOb.getDouble(sea_level));
			else
				weather.setSea_level(0);
			if (mainOb.has(grnd_level))
				weather.setGrnd_level(mainOb.getDouble(grnd_level));
			else
				weather.setGrnd_level(0);

			JSONObject windOb = datos_object.getJSONObject(wind);
			weather.setWind_speed(windOb.getDouble(wind_speed));
			weather.setWind_degrees(windOb.getDouble(wind_degrees));

			if (datos_object.has(rain)) {
				JSONObject rainOb = datos_object.getJSONObject(rain);
				weather.setRain_threehours(rainOb.getDouble(rain_threehours));
				Log.d(LogDavid, "RAIN: " + rainOb.getDouble(rain_threehours));
			} else {
				Log.d(LogDavid, "jsonToWeather: NoRain");
			}

			if (datos_object.has(snow)) {
				JSONObject rainOb = datos_object.getJSONObject(snow);
				weather.setRain_threehours(rainOb.getDouble(snow_threehours));
			} else {
				Log.d(LogDavid, "jsonToWeather: NoSnow");
			}

			JSONObject cloudsOb = datos_object.getJSONObject(clouds);
			weather.setClouds_all(cloudsOb.getDouble(clouds_all));

			weather.setData_receiving(datos_object.getLong(data_receiving));
			weather.setCity_id(datos_object.getString(city_id));
			weather.setCity_name(datos_object.getString(city_name));
			weather.setCode(datos_object.getString(code));

			return weather;

		} catch (Exception e) {
			Log.e(LogDavid, "jsonToWeather: " + e.toString());
			return null;
		}
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
		URL = URL + "lat=" + roundTwoDecimals(loc.getLatitude());
		URL = URL + "&";
		URL = URL + "lon=" + roundTwoDecimals(loc.getLongitude());
		URL = URL + "&mode=json";
		return URL;
	}
	
	public static String getUrlWeather2(Location loc) {
		// TODO
		String URL = Values.getWeatherURL2();
		//URL = URL + "lat=" + roundTwoDecimals(loc.getLatitude());
		URL = URL + "lat=" + loc.getLatitude();
		URL = URL + "&";
		//URL = URL + "lon=" + roundTwoDecimals(loc.getLongitude());
		URL = URL + "lon=" + loc.getLongitude();
		URL = URL + "&mode=json";
		return URL;
	}

	public static String getUrlForecast(Location loc) {
		String URL = Values.getForecastURL();
		URL = URL + "lat=" + roundTwoDecimals(loc.getLatitude());
		URL = URL + "&";
		URL = URL + "lon=" + roundTwoDecimals(loc.getLongitude());
		URL = URL + "&mode=json";
		return URL;
	}
}