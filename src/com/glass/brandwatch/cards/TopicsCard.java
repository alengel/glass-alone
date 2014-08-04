package com.glass.brandwatch.cards;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.glass.brandwatch.R;
import com.glass.brandwatch.cards.data.TopicsData;
import com.glass.brandwatch.cards.data.TopicsData.Data;
import com.glass.brandwatch.cards.data.TopicsData.Sentiment;
import com.glass.brandwatch.cards.data.TopicsData.Topic;

public class TopicsCard {
	
	private static String TAG = "TopicsCard";
	
	public static View build(Context context, TopicsData.Data data) {
		Log.v(TAG, "Passed in topics data " + data.topics.size());
		
		View view = View.inflate(context, R.layout.topics_card, null);
		
//		Data sortedData = sortDataByVolume(data);
		
		int count = Math.min(data.topics.size(), 7);
		for(int i=1; i<=count; i++) {
			Topic topic = data.topics.get(i);
			String layoutId = "topics" + i;
			Log.v(TAG, "Building layout id " + layoutId);
			
			TextView topics1 = (TextView)view.findViewById(context.getResources().getIdentifier(layoutId, "id", context.getPackageName()));
			topics1.setText(topic.label);
			
			Sentiment sentiment = topic.sentiment;
			setTextColour(sentiment, topics1);
			
			//Set the text size according to volume
//			Integer volume = topic.volume;
//			topics1.setTextSize(volume/50); 
		}
		
		TextView footer = (TextView)view.findViewById(R.id.topics_footer);
		footer.setText("Brandwatch");
		
		return view;
	}
	
	private static Data sortDataByVolume(Data data) {
		//TODO: Sort the data by volume here
		return data;
	}
	
	private static void setTextColour(Sentiment sentiment, TextView topics1) {
		Log.v(TAG, "neutral " + sentiment.neutral);
		Log.v(TAG, "negative " + sentiment.negative);
		Log.v(TAG, "positive " + sentiment.positive);
		
		//If negative sentiment is greater than positive or neutral, return red
		if(sentiment.negative != 0  &&
	        sentiment.negative > sentiment.positive || 
	        sentiment.negative > sentiment.neutral) {
	    	topics1.setTextColor(Color.parseColor("#FF0000"));
	    }

	    //If positive sentiment is greater than negative or neutral, return green
	    if(sentiment.positive != 0 &&
	        sentiment.positive > sentiment.negative ||
	        sentiment.positive > sentiment.neutral) {
	    	topics1.setTextColor(Color.parseColor("#00FF00"));
	    }
	}
	
}
