package com.glass.brandwatch.cards;

import android.content.Context;

import com.google.android.glass.app.Card;

public class FeaturesCard {
	
	public static Card build(Context context) {
		Card card = new Card(context);
        card.setText("This card has a puppy background image.");
        card.setFootnote("How can you resist?");
        card.setImageLayout(Card.ImageLayout.FULL);
//        card.addImage(R.drawable.puppy_bg);
        return card;
	}
	
}
