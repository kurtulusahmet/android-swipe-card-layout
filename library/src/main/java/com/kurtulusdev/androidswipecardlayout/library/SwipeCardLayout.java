package com.kurtulusdev.androidswipecardlayout.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by kurtulusahmet on 26.11.2015.
 */
public class SwipeCardLayout extends FrameLayout {

    Context context;
    RelativeLayout scContainer;

    View currentTopView;

    private ViewConfiguration viewConfig;
    private float mScreenWidth;
    private float mScreenHeight;

    private final int INVALID_POINTER_ID = -1337;
    private int mActivePointerId = INVALID_POINTER_ID;

    private Integer MAX_RENDERED_COUNT = 3;

    // CUSTOM GESTURE TRACKING FIELDS - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private float MAX_ROTATION_AMOUNT = 20f;
    private final float TOUCH_SLOP_SCALE_FACTOR_X = 5.0f;

    ArrayList<CardObject> cardList;

    public SwipeCardLayout(Context context, RelativeLayout scContainer) {
        super(context);
        this.context = context;
        this.scContainer = scContainer;
        viewConfig = ViewConfiguration.get(context);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    //String image url
    public CardObject createCardObject(String title, String imageurl){
        CardObject newCard = new CardObject();

        newCard.setTitle(title);
        newCard.setImage(imageurl);
        return newCard;
    }

    //Resource id
    public CardObject createCardObject(String title, int resource_id){
        CardObject newCard = new CardObject();

        newCard.setTitle(title);
        newCard.setImageResourceId(resource_id);

        return newCard;
    }

    public void addCards(ArrayList<CardObject> cardlist){
        int index = 0;
        this.cardList = cardlist;
        for(int i = 0; i < cardlist.size(); i++){
            RelativeLayout newCardViewToAdd = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.card_item, scContainer, false);
            scContainer.addView(newCardViewToAdd, index + i);

            newCardViewToAdd.setTranslationY(mScreenHeight + 200);
            newCardViewToAdd.setScaleX(0.5f);
            newCardViewToAdd.setScaleY(0.5f);
            newCardViewToAdd.setAlpha(1f);

            CardViewHolder holder = new CardViewHolder(newCardViewToAdd);
            newCardViewToAdd.setTag(holder);
        }
    }

    static class CardViewHolder {
        public CardObject data;
        public ImageView imageView;
        public TextView textView;

        public CardViewHolder(View cardView){
            imageView = (ImageView) cardView.findViewById(R.id.card_image);
            textView = (TextView) cardView.findViewById(R.id.card_text);
        }
    }

    public void renderCards(){
        int renderedCount = -1;
        int i = 0;

        for(int x = i; x <= scContainer.getChildCount(); x++){
            renderedCount++;
            if(renderedCount >= MAX_RENDERED_COUNT){
                return;
            }
            if (renderedCount == cardList.size()){
                return;
            }

            final View view;

            view = scContainer.getChildAt((scContainer.getChildCount() - x - 1));
            CardViewHolder holder = (CardViewHolder) view.getTag();

            if (renderedCount == 0){
                currentTopView = view;
                view.setOnTouchListener(new SwipeViewOnTouchListener());
            }

            if (renderedCount == 0) {
                view.setTranslationY(150f);
                view.setAlpha(1f);
                view.animate().translationY(0f).scaleX(1.0f).scaleY(1.0f).setInterpolator(new OvershootInterpolator());
            }
            else if (renderedCount == 1) {
                view.setTranslationY(1000f);
                view.setAlpha(1f);
                view.animate().translationY(150f).scaleX(0.9f).scaleY(0.9f).setInterpolator(new OvershootInterpolator());
            }

            if(holder.data == null){
                holder.data = cardList.get(x);
                holder.textView.setText(holder.data.getTitle());
                if (holder.data.getImage() == null){
                    holder.imageView.setImageResource(holder.data.getImageResourceId());
                }else{
                    Glide.with(context).load(holder.data.getImage()).into(holder.imageView);
                }
            }
        }
    }

    private void removeTopCard(){
        currentTopView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scContainer.removeView(currentTopView);
                cardList.remove(0);

                /*if(cardList.size() <= MAX_RENDERED_COUNT){
                    loadMoreCards();
                }*/

                renderCards();
            }
        }, 100);
    }

    public void topCardClicked() {
        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
    }

    private void topCardLeft() {
        currentTopView.animate().translationX(-mScreenWidth).setInterpolator(new OvershootInterpolator());
        removeTopCard();
        //Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();
    }

    private void topCardRight() {
        currentTopView.animate().translationX(mScreenWidth).setInterpolator(new OvershootInterpolator());
        removeTopCard();
        //Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();
    }

    private void snapback(View v){
        if(v != null){
            v.animate().translationX(0f).translationY(0f).rotation(0).setInterpolator(new OvershootInterpolator());
        }
    }

    class SwipeViewOnTouchListener implements View.OnTouchListener {

        private float mLastTouchX = 0;
        private float mLastTouchY = 0;
        private float dx = 0;
        private float dy = 0;
        private boolean isClick = true;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();

            switch (action) {
                case MotionEvent.ACTION_DOWN: {

                    final float x = event.getRawX();
                    final float y = event.getRawY();

                    mLastTouchX = x;
                    mLastTouchY = y;

                    final int pointerIndex = event.getActionIndex();
                    mActivePointerId = event.getPointerId(pointerIndex);
                    isClick = true;

                    break;
                }
                case MotionEvent.ACTION_MOVE: {

                    final float x = event.getRawX();
                    final float y = event.getRawY();

                    dx += (x - mLastTouchX);
                    dy += (y - mLastTouchY);

                    if (Math.abs(dx) > viewConfig.getScaledTouchSlop() || Math.abs(dy) > viewConfig.getScaledTouchSlop()) {
                        isClick = false;
                    }

                    v.setTranslationX(dx);
                    v.setTranslationY(dy);

                    float percentScreenWidthX = (Math.abs(dx) / mScreenWidth);
                    float rotationDegrees = percentScreenWidthX * MAX_ROTATION_AMOUNT;

                    if (dx < 0) {
                        rotationDegrees *= -1;
                    }

                    v.setRotation(rotationDegrees);

                    mLastTouchX = x;
                    mLastTouchY = y;

                    break;
                }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {

                    if (Math.abs(dx) <= viewConfig.getScaledTouchSlop() && Math.abs(dy) <= viewConfig.getScaledTouchSlop() && isClick) {
                        topCardClicked();
                    } else if (Math.abs(dx) <= viewConfig.getScaledTouchSlop() * TOUCH_SLOP_SCALE_FACTOR_X) {
                        snapback(v);
                    } else {
                        if (dx < 0) topCardLeft();
                        else topCardRight();
                    }

                    dx = 0;
                    dy = 0;
                    mLastTouchX = 0;
                    mLastTouchY = 0;
                    mActivePointerId = INVALID_POINTER_ID;

                    break;
                }
            }

            return true;
        }

    }
}
