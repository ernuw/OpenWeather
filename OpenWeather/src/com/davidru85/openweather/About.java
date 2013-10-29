package com.davidru85.openweather;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class About extends Activity {
	private ImageButton facebook, twitter, google, linkedin;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		facebook = (ImageButton) findViewById(R.id.profile_facebook);
		google = (ImageButton) findViewById(R.id.profile_google);
		twitter = (ImageButton) findViewById(R.id.profile_twitter);
		linkedin = (ImageButton) findViewById(R.id.profile_linkedin);
	}

	public void profileTwitter(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(Values.getProfiletwitter()));
		startActivity(browserIntent);
	}

	public void profileFacebook(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(Values.getProfilefacebook()));
		startActivity(browserIntent);
	}

	public void profileGoogle(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(Values.getProfilegoogle()));
		startActivity(browserIntent);
	}

	public void profileLinkedin(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(Values.getProfilelinkedin()));
		startActivity(browserIntent);
	}
}
