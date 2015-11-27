package com.kurtulusdev.androidswipecardlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        cardList.add(swipeCardLayout.createCardObject("Title #0", R.mipmap.basket5));
        cardList.add(swipeCardLayout.createCardObject("Title #1", "http://lorempixel.com/400/300/sports/1/"));
        cardList.add(swipeCardLayout.createCardObject("Title #2", R.mipmap.basket1));
        cardList.add(swipeCardLayout.createCardObject("Title #3", "http://lorempixel.com/400/300/sports/2/"));
        cardList.add(swipeCardLayout.createCardObject("Title #4", R.mipmap.basket2));
        cardList.add(swipeCardLayout.createCardObject("Title #5", "http://lorempixel.com/400/300/sports/3/"));
        cardList.add(swipeCardLayout.createCardObject("Title #6", R.mipmap.basket3));
        cardList.add(swipeCardLayout.createCardObject("Title #7", "http://lorempixel.com/400/300/sports/4/"));
        cardList.add(swipeCardLayout.createCardObject("Title #8", R.mipmap.basket4));
        cardList.add(swipeCardLayout.createCardObject("Title #9", "http://lorempixel.com/400/300/sports/5/"));


        swipeCardLayout.addCards(cardList);
        swipeCardLayout.renderCards();

    }


}
