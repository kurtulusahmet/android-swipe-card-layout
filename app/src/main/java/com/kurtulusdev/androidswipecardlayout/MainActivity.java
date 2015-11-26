package com.kurtulusdev.androidswipecardlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.kurtulusdev.androidswipecardlayout.library.CardObject;
import com.kurtulusdev.androidswipecardlayout.library.SwipeCardLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RelativeLayout scContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scContainer = (RelativeLayout) findViewById(R.id.swipeable_cards_container);

        SwipeCardLayout swipeCardLayout = new SwipeCardLayout(getApplicationContext(),scContainer);


        ArrayList<CardObject> cardList = new ArrayList<CardObject>();
        cardList.add(swipeCardLayout.createCardObject("Title #1", "http://lorempixel.com/300/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #2", "http://lorempixel.com/200/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #3", "http://lorempixel.com/300/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #4", "http://lorempixel.com/400/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #5", "http://lorempixel.com/400/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #6", "http://lorempixel.com/500/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #7", "http://lorempixel.com/600/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #8", "http://lorempixel.com/400/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #9", "http://lorempixel.com/400/300/"));
        cardList.add(swipeCardLayout.createCardObject("Title #10", "http://lorempixel.com/400/300/"));

        swipeCardLayout.addCards(cardList);
        swipeCardLayout.renderCards();
    }
}
