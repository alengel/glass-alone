package com.glass.brandwatch.application;

import android.app.Application;

import com.glass.brandwatch.R;
import com.glass.brandwatch_shared.utils.PropertiesManager;

public class GlassApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the configuration file once, so that the properties are
		// accessible throughout the lifetime of the application
		PropertiesManager.init(getApplicationContext(), R.raw.config);
	}
}