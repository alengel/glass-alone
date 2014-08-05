package com.glass.brandwatch.asynctask;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.glass.brandwatch.cards.CardBundleActivity;
import com.glass.brandwatch.cards.data.BrandwatchData;
import com.glass.brandwatch.cards.data.Semantics3Data;

public class RequestBrandDataTask extends AsyncTask<String, Void, ArrayList<String>> {

	private static final String TAG = "RequestBrandDataTask";
	private String url;
	private String query;
	private Context context;

	public RequestBrandDataTask(Context context) {
		this.context = context.getApplicationContext();
	}

	// Called by execute() - initializes this class
	protected ArrayList<String> doInBackground(String... parameters) {
		ArrayList<String> results = new ArrayList<String>();
		url = parameters[0];
		query = parameters[1];

		String queryId = BrandwatchData.getBrandwatchQueryId(url, query);
		results.add(Semantics3Data.getData(query));
		results.add(BrandwatchData.getSentimentData(url, queryId));
		results.add(BrandwatchData.getTopicsData(url, queryId));

		return results;
	}

	// Called after each HTTP request
	protected void onPostExecute(ArrayList<String> data) {
		if (data != null) {
			Log.v(TAG, String.format("Request for query '%s' succedeed", query));
			showCardsActivity(data);
		} else {
			Log.v(TAG, String.format("Request for query '%s' failed", query));
		}
	}

	private void showCardsActivity(ArrayList<String> data) {
		Intent intent = new Intent(context, CardBundleActivity.class);
		intent.putStringArrayListExtra("data", data);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
