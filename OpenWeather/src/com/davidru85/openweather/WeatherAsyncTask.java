package com.davidru85.openweather;

import org.json.JSONObject;

import android.os.AsyncTask;

public class WeatherAsyncTask extends AsyncTask<JsonParser, Integer, Weather> {

	//private static final String LogDavid = "OpenWeather";

	@Override
	protected Weather doInBackground(JsonParser... parser) {
		Weather weather = new Weather();
		JSONObject jsonObject = parser[0].getJSONFromUrl();
		weather = Conversor.jsonToWeather(jsonObject);
        return weather;
	}

}
