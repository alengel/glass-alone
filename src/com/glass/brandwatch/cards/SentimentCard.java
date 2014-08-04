package com.glass.brandwatch.cards;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.glass.brandwatch.R;
import com.glass.brandwatch.cards.data.SentimentData;
import com.glass.brandwatch.cards.data.SentimentData.Value;

public class SentimentCard {
	
	public static View build(Context context, SentimentData.Data data) {
		List<Value> results = data.results.get(0).values;
		Integer volumeCount = results.get(1).value + results.get(2).value;
		
		View view = View.inflate(context, R.layout.sentiment_card, null);
		
		TextView volume = (TextView)view.findViewById(R.id.volume);
		TextView volumeValue = (TextView)view.findViewById(R.id.volume_value);
		
		TextView positive = (TextView)view.findViewById(R.id.positive);
		TextView positiveValue = (TextView)view.findViewById(R.id.positive_value);
		
		TextView negative = (TextView)view.findViewById(R.id.negative);
		TextView negativeValue = (TextView)view.findViewById(R.id.negative_value);
		
		volume.setText("Volume");
		volumeValue.setText(volumeCount.toString());
		
		positive.setText(WordUtils.capitalize(results.get(2).name));
		positiveValue.setText(results.get(2).value.toString());
		
		negative.setText(WordUtils.capitalize(results.get(1).name));
		negativeValue.setText(results.get(1).value.toString());
		
		TextView footer = (TextView)view.findViewById(R.id.sentiment_footer);
		footer.setText("Brandwatch");

	    return view;
	}
	
}
