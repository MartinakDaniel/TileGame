package com.lombicgames.tilegame.ui.anim;

import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lombicgames.tilegame.ui.views.Tile;

/**
 * Created by DANI on 2015.07.06..
 */
public class MyAnimationListener implements Animation.AnimationListener{
    private int mLeftMargin;
    private int mTopMargin;
    private Tile mTile;

    public MyAnimationListener(Tile tile, int leftMargin, int topMargin){
        this.mTile = tile;
        this.mLeftMargin = leftMargin;
        this.mTopMargin = topMargin;
    }
    @Override
    public void onAnimationStart(Animation animation) {
        mTile.setMoving(true);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mTile.clearAnimation();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTile.getWidth(), mTile.getHeight());
        params.setMargins(mLeftMargin, mTopMargin, 0 ,0);
        mTile.setLayoutParams(params);
        mTile.setMoving(false);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
