package com.davidru85.openweather;

public class Values {
	public static final long ONE_MINUTE = 1000 * 60;
	public static final long FIFTEEN_MINUTES = 1000 * 60 * 15;
	public static final long ONE_HOUR = 1000 * 60 * 60;
	public static final long TEN_SECONDS = 1000 * 10;
	public static final long TEN_MINUTES = 1000 * 60 * 10;

	public static final long elapsed_time = ONE_MINUTE;

	private static final String weatherURL = "http://api.openweathermap.org/data/2.5/weather?";
	private static final String weatherURL2 = "http://api.openweathermap.org/data/2.5/find?cnt=1&";

	private static final String forecastURL = "http://api.openweathermap.org/data/2.5/forecast?";

	private static final String profileTwitter = "http://twitter.com/davidru85";
	private static final String profileFacebook = "http://facebook.com/davidru85";
	private static final String profileGoogle = "https://plus.google.com/115987355481602001725";
	private static final String profileLinkedin = "http://linkedin.com/in/davidru85/";
	private static final String profileGithub = "https://github.com/davidru85";

	private static final String PREFS = "com.davidru85.openweather_preferences";

	private static final String prev_notif = "PreviousNotification";
	private static final String active_notif = "ActiveNotifications";

	public static long getElapsedTime() {
		return elapsed_time;
	}

	public static String getWeatherURL() {
		return weatherURL;
	}

	public static String getWeatherURL2() {
		return weatherURL2;
	}

	public static String getForecastURL() {
		return forecastURL;
	}

	public static String getProfiletwitter() {
		return profileTwitter;
	}

	public static String getProfilefacebook() {
		return profileFacebook;
	}

	public static String getProfilegoogle() {
		return profileGoogle;
	}

	public static String getProfilelinkedin() {
		return profileLinkedin;
	}
	
	public static String getProfilegithub() {
		return profileGithub;
	}

	public static String getPrefs() {
		return PREFS;
	}

	public static String getPrevNotif() {
		return prev_notif;
	}

	public static String getActiveNotif() {
		return active_notif;
	}
}
