package com.glass.brandwatch.cards;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.glass.brandwatch.cards.data.SentimentData;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.gson.Gson;

public class CardBundleActivity extends Activity{
	
	private List<View> mCards;
    private CardScrollView mCardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createCards();

        mCardScrollView = new CardScrollView(this);
        ScrollAdapter adapter = new ScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
    }

    private void createCards() {
        mCards = new ArrayList<View>();
        
        Intent intent = getIntent();
        String sentimentData = intent.getStringExtra("sentiment_data");
        mCards.add(SentimentCard.build(this, new Gson().fromJson(sentimentData, SentimentData.Data.class)));
        
//        mCards.add(FeaturesCard.build(this));
//        mCards.add(TopicsCard.build(this));
    }
    
    private class ScrollAdapter extends CardScrollAdapter {
    	
        @Override
        public int getPosition(Object item) {
            return mCards.indexOf(item);
        }
        
        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
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
            return  mCards.get(position); //.getView(convertView, parent);
        }
    }
}
