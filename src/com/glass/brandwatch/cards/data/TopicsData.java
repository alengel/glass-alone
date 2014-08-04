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

public class TopicsData {
	private static String TAG = "TopicsData";
	
	public static String getData(String url, String queryId) {
		Log.i(TAG, String.format("Requesting topics data from '%s' for '%s'", getTopicsUrl(url, queryId), queryId));
		
		//Delegate the GET request to HttpRequest
		HttpResponse response = HttpRequest.doHttpGet(getTopicsUrl(url, queryId), null);
		
		try {
			String responseString = EntityUtils.toString(response.getEntity());
			
			Log.v(TAG, "Requesting Topics " + responseString);
			return responseString;

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return null;
	}
	
	private static String getTopicsUrl(String url, String queryId) {
		String brandwatchKey = PropertiesManager.getProperty("brandwatch_auth");
		String startDate = getDateSevenDaysAgo();
		String endDate = getDateFormat(new Date());
		String filters = "&endDate=" + endDate + "&startDate=" + startDate + "&queryId=";
		String topicsUrl = "data/volume/topics/queries/?";
		
		Log.v(TAG, url + topicsUrl + filters + queryId);
		return url + topicsUrl + brandwatchKey + filters + queryId;
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
	
	//Create a Data class that is a list of topics
	public class Data {
		public List<Topic> topics;
	}
	
	//Create a topic class for each topic
	public class Topic {
		public String label;
		public Integer volume;
		public Sentiment sentiment;
	}
	
	//Create a sentiment class for the sentiment within each topic
	public class Sentiment {
		public int negative = 0;
		public int neutral = 0;
		public int positive = 0;
	}

}