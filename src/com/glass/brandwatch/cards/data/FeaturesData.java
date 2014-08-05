package com.glass.brandwatch.cards.data;

import java.math.BigDecimal;
import java.util.List;

import android.util.Log;

import com.glass.brandwatch.utils.PropertiesManager;
import com.semantics3.api.Products;


public class FeaturesData {
	private static String TAG = "FeaturesData";
	
	public static String getData(String query) {
		
		Log.i(TAG, String.format("Requesting features data for '%s'", query));
		
		Products products = new Products(
			PropertiesManager.getProperty("semantics3_key"),
			PropertiesManager.getProperty("semantics3_secret")
		);

		products
		    .productsField( "search", query );
		    
		try {
			String resultsString = products.getProducts().toString();
			Log.v(TAG, "product results " + resultsString);
			
			return resultsString;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return "";
	}
	
	//Create a Data class that is a list of the retrieved products
	public class Data {
		public List<Product> results;
	}
	
	//Create a product class for each product
	public class Product {
		public String name;
		public BigDecimal price;
		public String color;
	}
}