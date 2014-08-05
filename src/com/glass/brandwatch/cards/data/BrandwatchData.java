package com.glass.brandwatch.cards.data;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.glass.brandwatch.utils.DateHelper;
import com.glass.brandwatch.utils.HttpRequest;
import com.glass.brandwatch.utils.PropertiesManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BrandwatchData {
	private static String TAG = "BrandwatchData";

	public static String getBrandwatchQueryId(String url, String query) {
		Log.i(TAG, String.format("Requesting queryId for query '%s'", query));
		String queryUrl = buildQueryUrl(url, query);
		String data = getData(queryUrl);
		JsonObject results = getResults(data);
		return results.get("id").toString();
	}

	public static String getSentimentData(String url, String queryId) {
		Log.i(TAG, String.format("Requesting sentiment data for queryId '%s'", queryId));
		String sentimentUrl = buildSentimentUrl(url, queryId);
		return getData(sentimentUrl);
	}

	public static String getTopicsData(String url, String queryId) {
		Log.i(TAG, String.format("Requesting topics data for queryId '%s'", queryId));
		String topicstUrl = buildTopicsUrl(url, queryId);
		return getData(topicstUrl);
	}

	private static String getData(String url) {
		// Delegate the GET request to HttpRequest
		HttpResponse response = HttpRequest.doHttpGet(url, null);

		try {
			return EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return null;
	}

	private static String buildSentimentUrl(String url, String queryId) {
		String sentimentUrl = "data/volume/months/sentiment/?";
		return url + sentimentUrl + buildFilters() + queryId;
	}

	private static String buildTopicsUrl(String url, String queryId) {
		String topicsUrl = "data/volume/topics/queries/?";
		return url + topicsUrl + buildFilters() + queryId;
	}

	private static String buildFilters() {
		String brandwatchKey = PropertiesManager.getProperty("brandwatch_auth");
		String startDate = DateHelper.getDateSevenDaysAgo();
		String endDate = DateHelper.getDateFormat(new Date());
		String filters = "&endDate=" + endDate + "&startDate=" + startDate + "&queryId=";

		return brandwatchKey + filters;
	}

	private static String buildQueryUrl(String url, String query) {
		String brandwatchKey = PropertiesManager.getProperty("brandwatch_auth");
		return url + "queries/?" + brandwatchKey + "&nameContains=" + query.replace(' ', '+');
	}

	private static JsonObject getResults(String responseString) {
		JsonElement jElement = new JsonParser().parse(responseString);
		JsonObject jObject = jElement.getAsJsonObject();
		JsonArray jArray = jObject.getAsJsonArray("results");
		jObject = jArray.get(0).getAsJsonObject();

		return jObject;
	}
}
