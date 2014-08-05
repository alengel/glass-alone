package com.glass.brandwatch.cards;

import java.util.Collections;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.glass.brandwatch.R;
import com.glass.brandwatch.cards.data.TopicsData;
import com.glass.brandwatch.cards.data.TopicsData.Sentiment;
import com.glass.brandwatch.cards.data.TopicsData.Topic;

public class TopicsCard {
	
	private static String TAG = "TopicsCard";
	
	public static View build(Context context, TopicsData.Data data) {
		Log.v(TAG, "Passed in topics data " + data.topics.size());
		
		View view = View.inflate(context, R.layout.topics_card, null);
		
//		Data sortedData = sortDataByVolume(data);
		
		int count = Math.min(data.topics.size(), 7);
		int startTextSize = 24;
		Collections.sort(data.topics, Collections.reverseOrder(new TopicVolumeComparer()));
		
		for(int i=1; i<=count; i++) {
			Topic topic = data.topics.get(i);
			String layoutId = "topics" + i;
			
			Log.v(TAG, "Building layout id " + layoutId);
			
			//Set the topic text in the view
			TextView topicView = (TextView)view.findViewById(context.getResources().getIdentifier(layoutId, "id", context.getPackageName()));
			topicView.setText(topic.label);
			
			//Set the text colour according to the most present sentiment
			Sentiment sentiment = topic.sentiment;
			setTextColour(sentiment, topicView);
			
			//Set the text size according to volume
			topicView.setTextSize(startTextSize--); 
		}
		
		TextView footer = (TextView)view.findViewById(R.id.topics_footer);
		footer.setText("Brandwatch");
		
		return view;
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
