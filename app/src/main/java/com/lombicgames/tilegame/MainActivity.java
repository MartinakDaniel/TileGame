package com.lombicgames.tilegame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lombicgames.tilegame.ui.anim.MyAnimationListener;
import com.lombicgames.tilegame.ui.views.Tile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
    private static final int MOVE_RATE = 20;
    private static final int ANIMATION_TIME = 300;

    private int mTileNumInARow = 10;
    private int mTileSize;

    private List<Tile> mTileList = new ArrayList<>();
    private Tile[][] mTileArray;

    private Point mTouchOld;

    @ViewById(R.id.container)
    protected LinearLayout mContainer;

    private FrameLayout mTileContainer;

    @AfterViews
    protected void afterViews() {
        mTileSize = getTileSize();
        initTiles();
        setTouchEvent();
    }

    private void initTiles() {
        mTileArray = new Tile[mTileNumInARow][mTileNumInARow];

        mTileContainer = new FrameLayout(this);
        mTileContainer.setLayoutParams(new FrameLayout.LayoutParams(new LinearLayout.LayoutParams(
                mTileNumInARow * mTileSize, mTileNumInARow * mTileSize
        )));
        mTileContainer.setBackgroundColor(Color.GRAY);
        for (int y = 0; y < mTileNumInARow; y++) {

            for (int x = 0; x < mTileNumInARow; x++) {
                if (y % 2 == 0) {
                    if (x % 2 == 0) {
                        Tile tile = new Tile(this);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize, mTileSize);
                        params.setMargins(mTileSize * x, mTileSize * y, 0, 0);
                        tile.setLayoutParams(params);
                        mTileContainer.addView(tile);
                        tile.setTableX(x);
                        tile.setTableY(y);
                        tile.setTableMaxX(mTileNumInARow);
                        tile.setTableMaxY(mTileNumInARow);
                        mTileArray[y][x] = tile;
                    }
                    else{
                        mTileArray[y][x] = null;
                    }
                } else {
                    if (x % 2 == 1) {
                        Tile tile = new Tile(this);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize, mTileSize);
                        params.setMargins(mTileSize * x, mTileSize * y, 0, 0);
                        tile.setLayoutParams(params);
                        mTileContainer.addView(tile);
                        tile.setTableX(x);
                        tile.setTableY(y);
                        tile.setTableMaxX(mTileNumInARow);
                        tile.setTableMaxY(mTileNumInARow);
                        mTileArray[y][x] = tile;
                    }
                    else{
                        mTileArray[y][x] = null;
                    }
                }
            }

        }
        mContainer.addView(mTileContainer);
    }


    private int getTileSize() {
        Display display = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            return size.x / mTileNumInARow;
        } else {
            return display.getWidth() / mTileNumInARow;
        }
    }

    private void setTouchEvent() {
        mContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchOld = new Point((int) event.getX(),(int) event.getY());
                        return true;
                    case MotionEvent.ACTION_UP:
                        checkMotion(mTouchOld, new Point((int) event.getX(),(int) event.getY()));
                        return true;
                }
                return false;
            }
        });
    }

    private void checkMotion(Point downPoint, Point upPoint){
        int difX = downPoint.x - upPoint.x;
        int difY = downPoint.y - upPoint.y;

        if(Math.abs(difX) > Math.abs(difY)){
            if(difX > 0){
                moveLeft();
//                fillEmptySlots();
//                removeBricks();
            }
            else{
                moveRight();
//                fillEmptySlots();
//                removeBricks();
            }
        }
        else{
            if(difY < 0){
                moveDown();
//                fillEmptySlots();
//                removeBricks();
            }
            else{
                moveUp();
//                fillEmptySlots();
//                removeBricks();
            }
        }
    }

    private void moveLeft() {
        Log.d("Move", "Left");
        int tempX = -1;
        int tempY = -1;

        for(int y = 0; y < mTileNumInARow; y++){
            tempX = -1;
            tempY = -1;
            for(int x = 0; x < mTileNumInARow; x++){
                if(mTileArray[y][x] == null){
                    if(tempX == -1){
                        tempX = x;
                        tempY = y;
                    }
                }
                else{
                    if(tempX != -1 && tempY == y){
//                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize,mTileSize);
//                        params.setMargins(mTileSize*tempX, mTileSize*tempY, 0, 0);
//                        mTileArray[y][x].setLayoutParams(params);
//                        TranslateAnimation animation = new TranslateAnimation(
//                                mTileArray[y][x].getTableX()*mTileSize,
//                                mTileSize*tempX,
//                                mTileArray[y][x].getTableY()*mTileSize,
//                                mTileSize*tempY);
                        TranslateAnimation animation = new TranslateAnimation(
                                0,
                                mTileSize*tempX,
                                0,
                                0);
                        animation.setDuration(ANIMATION_TIME);
                        animation.setFillAfter(false);
                        animation.setAnimationListener(new MyAnimationListener(mTileArray[y][x],mTileSize*tempX, mTileSize*tempY));

                        mTileArray[y][x].startAnimation(animation);
                        mTileArray[y][x].setTableY(tempY);
                        mTileArray[y][x].setTableX(tempX);
                        mTileArray[tempY][tempX] = mTileArray[y][x];
                        mTileArray[y][x] = null;
                        tempX = -1;
                        tempY = -1;
                        x = 0;

                    }
                }
            }
        }
    }

    private void moveRight() {
        Log.d("Move", "Right");
        int tempX = -1;
        int tempY = -1;

        for(int y = 0; y < mTileNumInARow; y++){
            tempX = -1;
            tempY = -1;
            for(int x = mTileNumInARow-1; x > -1; x--){
                if(mTileArray[y][x] == null){
                    if(tempX == -1){
                        tempX = x;
                        tempY = y;
                    }
                }
                else{
                    if(tempX != -1 && tempY == y){
//                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize,mTileSize);
//                        params.setMargins(mTileSize*tempX, mTileSize*tempY, 0, 0);
//                        mTileArray[y][x].setLayoutParams(params);
//                        TranslateAnimation animation = new TranslateAnimation(
//                                mTileArray[y][x].getTableX()*mTileSize,
//                                mTileSize*tempX,
//                                mTileArray[y][x].getTableY()*mTileSize,
//                                mTileSize*tempY);
                        TranslateAnimation animation = new TranslateAnimation(
                                0,
                                mTileSize*tempX,
                                0,
                                0);
                        animation.setDuration(ANIMATION_TIME);
                        animation.setFillAfter(false);
                        animation.setAnimationListener(new MyAnimationListener(mTileArray[y][x],mTileSize*tempX, mTileSize*tempY));

                        mTileArray[y][x].startAnimation(animation);
                        mTileArray[y][x].setTableY(tempY);
                        mTileArray[y][x].setTableX(tempX);
                        mTileArray[tempY][tempX] = mTileArray[y][x];
                        mTileArray[y][x] = null;
                        tempX = -1;
                        tempY = -1;
                        x = mTileNumInARow-1;
                    }
                }
            }
        }
    }

    private void moveDown() {
        Log.d("Move", "Down");
        int tempX = -1;
        int tempY = -1;

        for(int x = 0; x < mTileNumInARow; x++){
            tempX = -1;
            tempY = -1;
            for(int y = mTileNumInARow-1; y > -1; y--){
                if(mTileArray[y][x] == null){
                    if(tempX == -1){
                        tempX = x;
                        tempY = y;
                    }
                }
                else{
                    if(tempX != -1 && tempX == x){
//                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize,mTileSize);
//                        params.setMargins(mTileSize*tempX, mTileSize*tempY, 0, 0);
//                        mTileArray[y][x].setLayoutParams(params);
//                        TranslateAnimation animation = new TranslateAnimation(
//                                mTileArray[y][x].getTableX()*mTileSize,
//                                mTileSize*tempX,
//                                mTileArray[y][x].getTableY()*mTileSize,
//                                mTileSize*tempY);
                        TranslateAnimation animation = new TranslateAnimation(
                                0,
                                0,
                                0,
                                mTileSize*tempY);
                        animation.setDuration(ANIMATION_TIME);
                        animation.setFillAfter(false);
                        animation.setAnimationListener(new MyAnimationListener(mTileArray[y][x],mTileSize*tempX, mTileSize*tempY));

                        mTileArray[y][x].startAnimation(animation);
                        mTileArray[y][x].setTableY(tempY);
                        mTileArray[y][x].setTableX(tempX);
                        mTileArray[tempY][tempX] = mTileArray[y][x];
                        mTileArray[y][x] = null;
                        tempX = -1;
                        tempY = -1;
                        y = mTileNumInARow -1;
                    }
                }
            }
        }
    }

    private void moveUp() {
        Log.d("Move", "Up");
        int tempX = -1;
        int tempY = -1;

        for(int x = 0; x < mTileNumInARow; x++){
            tempX = -1;
            tempY = -1;
            for(int y = 0; y < mTileNumInARow; y++){
                if(mTileArray[y][x] == null){
                    if(tempX == -1){
                        tempX = x;
                        tempY = y;
                    }
                }
                else{
                    if(tempX != -1 && tempX == x){
//                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize,mTileSize);
//                        params.setMargins(mTileSize*tempX, mTileSize*tempY, 0, 0);
//                        mTileArray[y][x].setLayoutParams(params);
//                        TranslateAnimation animation = new TranslateAnimation(
//                                mTileArray[y][x].getTableX()*mTileSize,
//                                mTileSize*tempX,
//                                mTileArray[y][x].getTableY()*mTileSize,
//                                mTileSize*tempY);
                        TranslateAnimation animation = new TranslateAnimation(
                                0,
                                0,
                                0,
                                mTileSize*tempY);
                        animation.setDuration(ANIMATION_TIME);
                        animation.setFillAfter(false);
                        animation.setAnimationListener(new MyAnimationListener(mTileArray[y][x],mTileSize*tempX, mTileSize*tempY));

                        mTileArray[y][x].startAnimation(animation);
                        mTileArray[y][x].setTableY(tempY);
                        mTileArray[y][x].setTableX(tempX);
                        mTileArray[tempY][tempX] = mTileArray[y][x];
                        mTileArray[y][x] = null;
                        tempX = -1;
                        tempY = -1;
                        y = 0;
                    }
                }
            }
        }
    }

    private void removeBricks(){
        List<Tile> tileType1 = new ArrayList<>();
        List<Tile> tileType2 = new ArrayList<>();
        List<Tile> tileType3 = new ArrayList<>();
        List<Tile> tileType0 = new ArrayList<>();

        for(int outY = 0; outY < mTileNumInARow; outY++){
            for(int outX = 0; outX < mTileNumInARow; outX++){
                Tile tile = mTileArray[outY][outX];
                if(tile != null){
                    switch(tile.getType()){
                        case 0:
                            tileType0.add(tile);
                            break;
                        case 1:
                            tileType1.add(tile);
                            break;
                        case 2:
                            tileType2.add(tile);
                            break;
                        case 3:
                            tileType3.add(tile);
                            break;
                    }

                }
            }
        }

        checkBricks(tileType0);
        checkBricks(tileType1);
        checkBricks(tileType2);
        checkBricks(tileType3);
    }

    private void checkBricks(List<Tile> tileList){
        List<Tile> deleteList = new ArrayList<>();
        List<Tile> twoNeighbourList = new ArrayList<>();

        if(!tileList.isEmpty() && tileList.size() > 2){
            int counter = 0;
            for(Tile tempTile : tileList) {
                counter = 0;
                for (int i = 0; i < tileList.size(); i++) {
                    if (tempTile.isNeighbour(tileList.get(i))) {
                        counter++;
                    }
                }
                if (counter > 1) {
                    twoNeighbourList.add(tempTile);
                }
            }

            for(Tile tempTile : tileList){
                for(Tile tile : twoNeighbourList){
                    if(tile.isNeighbour(tempTile)){
                        deleteList.add(tempTile);
                    }
                }
            }
            deleteList.addAll(twoNeighbourList);
        }

        /*For debug*/
        for(Tile tile : deleteList){
            Log.d("Tiles", "TilesID:"+tile.getId());
        }

        deleteBricks(deleteList);
    }

    private void deleteBricks(List<Tile> tileList){
        for(Tile tile : tileList){
            mTileContainer.removeView(mTileArray[tile.getTableY()][tile.getTableX()]);
            mTileArray[tile.getTableY()][tile.getTableX()] = null;
        }
    }

    private void fillEmptySlots(){
        for(int y = 0; y < mTileNumInARow; y++){
            for(int x = 0; x <mTileNumInARow; x++){
                if(mTileArray[y][x] == null){
                    Tile tile = new Tile(this);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mTileSize, mTileSize);
                    params.setMargins(mTileSize * x, mTileSize * y, 0, 0);
                    tile.setLayoutParams(params);
                    mTileContainer.addView(tile);
                    tile.setTableX(x);
                    tile.setTableY(y);
                    tile.setTableMaxX(mTileNumInARow);
                    tile.setTableMaxY(mTileNumInARow);
                    mTileArray[y][x] = tile;
                }
            }
        }
    }
}
