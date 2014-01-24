package com.davidru85.openweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Localizator {

	/**
	 * @param args
	 */

	private static LocationManager mLocationManager;

	private static final int TEN_SECONDS = 10000;
	private static final int TEN_METERS = 10;
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	private static final LocationListener listener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	};

	private static Location getBetterLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}

	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	public static Location geoLocate(Context myContext) {
		mLocationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
		//Location gpsLocation = null;
		Location networkLocation = null;
		mLocationManager.removeUpdates(listener);
		//gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER,R.string.not_support_gps);
		networkLocation = requestUpdatesFromProvider(LocationManager.NETWORK_PROVIDER, R.string.not_support_network);
		/*if (gpsLocation != null && networkLocation != null) {
			return (getBetterLocation(gpsLocation, networkLocation));
		} else if (gpsLocation != null) {
			return (gpsLocation);
		} else */if (networkLocation != null) {
			return (networkLocation);
		} else {
			return (null);
		}
	}

	public static boolean isProviderEnabled(Context myContext) {
		boolean enabled;
		LocationManager lm = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
		enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		return enabled;
	}

	private static Location requestUpdatesFromProvider(final String provider,
			final int errorResId) {
		Location location = null;
		if (mLocationManager.isProviderEnabled(provider)) {
			mLocationManager.requestLocationUpdates(provider, TEN_SECONDS,TEN_METERS, listener);
			location = mLocationManager.getLastKnownLocation(provider);
		} else {
			// Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
		}
		return location;
	}

	public static void stop() {
		if (mLocationManager != null)
			mLocationManager.removeUpdates(listener);
	}
}