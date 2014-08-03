package com.glass.brandwatch.cards;

import android.content.Context;

import com.google.android.glass.app.Card;

public class TopicsCard {
	
	public static Card build(Context context) {
		Card card = new Card(context);
        card.setText("This card has a mosaic of puppies.");
        card.setFootnote("Aren't they precious?");
        card.setImageLayout(Card.ImageLayout.LEFT);
//        card.addImage(R.drawable.puppy_small_1);
        return card;
	}
	
}
