package com.glass.brandwatch.cards;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.glass.brandwatch.cards.data.SentimentData;
import com.glass.brandwatch.cards.data.TopicsData;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.gson.Gson;

public class CardBundleActivity extends Activity{
	
	private List<View> cardsBundle;
    private CardScrollView cardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createCards();

        cardScrollView = new CardScrollView(this);
        ScrollAdapter adapter = new ScrollAdapter();
        cardScrollView.setAdapter(adapter);
        cardScrollView.activate();
        setContentView(cardScrollView);
    }

    private void createCards() {
    	cardsBundle = new ArrayList<View>();
        
        Intent intent = getIntent();
        String sentimentData = intent.getStringArrayListExtra("data").get(0);
        String topicsData = intent.getStringArrayListExtra("data").get(1);
        
        cardsBundle.add(SentimentCard.build(this, new Gson().fromJson(sentimentData, SentimentData.Data.class)));
        cardsBundle.add(TopicsCard.build(this, new Gson().fromJson(topicsData, TopicsData.Data.class))); 
//        mCards.add(FeaturesCard.build(this));
       
    }
    
    private class ScrollAdapter extends CardScrollAdapter {
    	
        @Override
        public int getPosition(Object item) {
            return cardsBundle.indexOf(item);
        }
        
        @Override
        public int getCount() {
            return cardsBundle.size();
        }

        @Override
        public Object getItem(int position) {
            return cardsBundle.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return Card.getViewTypeCount();
        }

//        @Override
//        public int getItemViewType(int position){
//            return mCards.get(position).getItemViewType();
//        }

        @Override
        public View getView(int position, View convertView,
                ViewGroup parent) {
            return  cardsBundle.get(position); //.getView(convertView, parent);
        }
    }
}
