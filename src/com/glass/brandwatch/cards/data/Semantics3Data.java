package com.glass.brandwatch.cards.data;

import android.util.Log;

import com.glass.brandwatch.utils.PropertiesManager;
import com.semantics3.api.Products;

public class Semantics3Data {
	private static String TAG = "Semantics3Data";

	public static String getData(String query) {

		Log.i(TAG, String.format("Requesting features data for query '%s'", query));

		Products products = new Products(PropertiesManager.getProperty("semantics3_key"),
				PropertiesManager.getProperty("semantics3_secret"));

		products.productsField("search", query);

		try {
			return products.getProducts().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}