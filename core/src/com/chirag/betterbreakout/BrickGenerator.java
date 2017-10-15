package com.chirag.betterbreakout;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BrickGenerator {
    private Texture mBrickTexture;
    private Set<Brick> mBricks;
    private int mCurrentRow;
    private float mBottomY;
    private Random mRandom;
    private boolean mIsStopped;

    private static final int TOP_PADDING = 120;
    private final Color[] RAINBOW = {Color.RED, Color.ORANGE, Color.GOLD, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PURPLE, Color.PINK, Color.WHITE};

    BrickGenerator(Texture brickTexture) {
        mRandom = new Random();
        mBricks = new HashSet<Brick>();
        mBottomY = BetterBreakout.GAME_HEIGHT - 435;
        mBrickTexture = brickTexture;
        mIsStopped = false;
    }

    Set<Brick> getBricks() {
        return mBricks;
    }

    void stop() {
        mIsStopped = true;
    }

    float getBottomY() {
        return mBottomY;
    }

    void clearBricks() {
        mBricks.clear();
    }

    void update() {
        for(Brick b : mBricks) {
            b.update();
        }

        int rowHeight = Brick.HEIGHT + 10;
        while(mCurrentRow * rowHeight + mBottomY < BetterBreakout.GAME_HEIGHT + Brick.HEIGHT && !mIsStopped) {
            addNewRow();
        }
    }

    void draw(SpriteBatch batch) {
        for(Brick b : mBricks) {
            b.draw(batch);
        }
    }

    void moveDown(float y) {
        mBottomY -= y;
    }

    private void add(int x, int y, Color color) {
        mBricks.add(new Brick(mBrickTexture, x, y, color, this));
    }

    public void remove(Brick b) {
        mBricks.remove(b);
    }

    void removeAll(List<Brick> b) {
        mBricks.removeAll(b);
    }

    void addNewRow() {
        int widthBricks = 20;
        int brickPadding = 10;
        int sidePadding = (BetterBreakout.GAME_WIDTH - Brick.WIDTH * widthBricks + (widthBricks-1) * brickPadding) / 2;
        int rowHeight = Brick.HEIGHT + brickPadding;
        int brickLossChance = Math.max(0, (int)(65 - mCurrentRow * 2.1));
        for(int i = sidePadding; i <= BetterBreakout.GAME_WIDTH - sidePadding; i += Brick.WIDTH + brickPadding) {
            if(mRandom.nextInt(100) < brickLossChance) continue;
            add(i, rowHeight * mCurrentRow, RAINBOW[RAINBOW.length - mCurrentRow % RAINBOW.length - 1]);
        }
        mCurrentRow++;
    }
}
