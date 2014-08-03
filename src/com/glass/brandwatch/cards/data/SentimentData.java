package com.glass.brandwatch.cards.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.glass.brandwatch.utils.HttpRequest;
import com.glass.brandwatch.utils.PropertiesManager;

public class SentimentData {
	private static String TAG = "SentimentData";
	
	public static String getData(String url, String queryId) {
		Log.i(TAG, String.format("Requesting sentiment data from '%s' for '%s'", getSentimentUrl(url, queryId), queryId));
		
		//Delegate the GET request to HttpRequest
		HttpResponse response = HttpRequest.doHttpGet(getSentimentUrl(url, queryId), null);
		
		try {
			String responseString = EntityUtils.toString(response.getEntity());
			
			Log.v(TAG, "Requesting Sentiment " + responseString);
			return responseString;

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return null;
	}
	
	private static String getSentimentUrl(String url, String queryId) {
		String brandwatchKey = PropertiesManager.getProperty("brandwatch_auth");
		String startDate = getDateSevenDaysAgo();
		String endDate = getDateFormat(new Date());
		String filters = "&endDate=" + endDate + "&startDate=" + startDate + "&queryId=";
		String sentimentUrl = "data/volume/months/sentiment/?";
		
		Log.v(TAG, url + sentimentUrl + filters + queryId);
		return url + sentimentUrl + brandwatchKey + filters + queryId;
	}
	
	private static String getDateSevenDaysAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		return getDateFormat(cal.getTime());
	}
	
	private static String getDateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return sdf.format(date);
	}
	
	public class Data{
		public List<Result> results;
	}
	
	public class Result
	{
		public List<Value> values;
	}
	
	public class Value
	{
		public String name;
		public Integer value;
	}
}
