package com.lombicgames.tilegame.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by DANI on 2015.06.29..
 */
public class Tile extends View {

    private int mType;
    private int mSideSize;
    private int mX;
    private int mY;
    private int mTableX;
    private int mTableY;
    private int mTableMaxX;
    private int mTableMaxY;
    private boolean mIsMoving = false;

    private Canvas mCanvas;

    private Paint mPaint;

    public Tile(Context context) {
        super(context);
        init();
    }

    public Tile(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Tile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Random random = new Random();

        mType = random.nextInt(4);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        drawType();
        drawId();
    }

    private void drawType() {
        switch (mType) {
            case 0:
                mPaint.setColor(Color.GREEN);
                mCanvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 4, mPaint);
                break;
            case 1:
                mPaint.setColor(Color.RED);
                mCanvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 4, mPaint);
                break;
            case 2:
                mPaint.setColor(Color.BLUE);
                mCanvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 4, mPaint);
                break;
            case 3:
                mPaint.setColor(Color.YELLOW);
                mCanvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 4, mPaint);
                break;
        }
    }

    private void drawId(){
        mCanvas.drawText("id:"+getId() + " x:"+mTableX+" y:"+mTableY, 5,mCanvas.getHeight()/2,mPaint);
    }

    public int getType() {
        return mType;
    }

    public int getTableX() {
        return mTableX;
    }

    public void setTableX(int tableX) {
        this.mTableX = tableX;
    }

    public int getTableY() {
        return mTableY;
    }

    public void setTableY(int tableY) {
        this.mTableY = tableY;
    }

    public int getId() {
        return mTableMaxY * mTableY + mTableX;
    }

    public void setTableMaxX(int mTableMaxX) {
        this.mTableMaxX = mTableMaxX;
    }

    public void setTableMaxY(int mTableMaxY) {
        this.mTableMaxY = mTableMaxY;
    }

    public void setMoving(boolean moving){
        this.mIsMoving = moving;
    }

    public boolean isMoving(){
        return mIsMoving;
    }
    public boolean isNeighbour(Tile tile) {
        if (mTableX - 1 == tile.getTableX() && mTableY == tile.getTableY()) {
            return true;
        } else if (mTableX + 1 == tile.getTableX() && mTableY == tile.getTableY()) {
            return true;
        } else if (mTableX == tile.getTableX() && mTableY - 1 == tile.getTableY()) {
            return true;
        } else if (mTableX == tile.getTableX() && mTableY + 1 == tile.getTableY()) {
            return true;
        } else {
            return false;
        }
    }
}
