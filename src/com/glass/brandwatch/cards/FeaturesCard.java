package com.glass.brandwatch.cards;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.glass.brandwatch.R;
import com.glass.brandwatch.cards.data.FeaturesData;
import com.glass.brandwatch.cards.data.FeaturesData.Product;

public class FeaturesCard {
	
	public static View build(Context context, FeaturesData.Data data) {
		List<Product> products = data.results;
		
		View view = View.inflate(context, R.layout.features_card, null);
		
		TextView colour = (TextView)view.findViewById(R.id.features_color);
		TextView colourValue = (TextView)view.findViewById(R.id.features_value);
		
		colour.setText("Colour");
		colourValue.setText(products.get(0).color);
		
		TextView footer = (TextView)view.findViewById(R.id.features_footer);
		footer.setText(products.get(0).name + " Features");

	    return view;
	}
	
}
