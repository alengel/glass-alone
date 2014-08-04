package com.glass.brandwatch.asynctask;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.glass.brandwatch.cards.CardBundleActivity;
import com.glass.brandwatch.cards.data.SentimentData;
import com.glass.brandwatch.cards.data.TopicsData;
import com.glass.brandwatch.utils.HttpRequest;
import com.glass.brandwatch.utils.PropertiesManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RequestBrandDataTask extends AsyncTask<String, Void, ArrayList<String>> {

	private static final String TAG = "RequestBrandDataTask";
	private String url;
	private String query;
	private Context context;
    
	public RequestBrandDataTask(Context context) {
        this.context = context.getApplicationContext();
    }
	
	//Called by execute() - initializes this class
	protected ArrayList<String> doInBackground(String... parameters) {
		ArrayList <String> results = new ArrayList<String>();
		url = parameters[0];
		query = parameters[1];

		String queryId = getBrandwatchQueryId(url, query);
		results.add(getSentiment(queryId));
		//results.add(getFeatures(query));
		results.add(getTopics(queryId));
		//return true;
		
		return results;
	}

	//Get id for the requested query from Brandwatch
	private String getBrandwatchQueryId(String url, String query) {
		String queryId = "";
		Log.i(TAG, String.format("Requesting brand data from '%s' for '%s'", getQueryUrl(url), query));
		
		//Delegate the GET request to HttpRequest
		HttpResponse response = HttpRequest.doHttpGet(getQueryUrl(url), null);
		
		try {
			String responseString = EntityUtils.toString(response.getEntity());
			
			JsonObject results = getResults(responseString);
		    queryId = results.get("id").toString();
			
			Log.v(TAG, "Requesting Brandwatch queries " + queryId);

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return queryId;
	}
	
	private String getSentiment(String queryId) {
		return SentimentData.getData(url, queryId);
	}
	
	private String getFeatures(String queryId) {
		return "";
	}

	private String getTopics(String queryId) {
		return TopicsData.getData(url, queryId);
	}
	
	private String getQueryUrl(String url) {
		String brandwatchKey = PropertiesManager.getProperty("brandwatch_auth");
		return url + "queries/?" + brandwatchKey + "&nameContains=" + getQuery(query);
	}
	
	private String getQuery(String query) {
		Pattern whitespace = Pattern.compile("\\s");
		Matcher matcher = whitespace.matcher(query);
		return matcher.replaceAll("+");
	}
	
	private JsonObject getResults(String responseString) {
		JsonElement jElement = new JsonParser().parse(responseString);
		JsonObject jObject = jElement.getAsJsonObject();
		JsonArray jArray = jObject.getAsJsonArray("results");
	    jObject = jArray.get(0).getAsJsonObject();
	    
	    return jObject;
	}

	//Called after each HTTP request
	protected void onPostExecute(ArrayList<String> data) {
		if (data != null) {
			Log.v(TAG, String.format("Request for '%s' succedeed", query));
			showCardsActivity(data);
		} else {
			Log.v(TAG, String.format("Request for '%s' failed", query));
		}
	}
	
	private void showCardsActivity(ArrayList<String> data) {
		Intent intent = new Intent(context, CardBundleActivity.class);
		intent.putStringArrayListExtra("data", data);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
