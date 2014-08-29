package com.glass.brandwatch.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.glass.brandwatch.R;
import com.glass.brandwatch.asynctask.RequestBrandDataTask;
import com.glass.brandwatch_shared.utils.PropertiesManager;

public class WaitActivity extends Activity {
	static final private String TAG = WaitActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wait);

		makeRequests();
	}

	private void makeRequests() {
		Log.i(TAG, "Initialising Http requests.");

		Intent intent = getIntent();
		String query = intent.getStringExtra("query");

		// Pass the URL and product name to the Brandwatch server requests
		new RequestBrandDataTask(getApplicationContext()).execute(
				PropertiesManager.getProperty("brandwatch_url"), query);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}
