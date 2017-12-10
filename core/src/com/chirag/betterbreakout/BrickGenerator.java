package com.chirag.betterbreakout;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    private final Color[] RAINBOW = {
            new Color(1, 0.1f, 0.1f, 1),
            new Color(1, 0.65f, 0.1f, 1),
            Color.YELLOW,
            Color.GREEN,
            new Color(0.1f, 1, 1, 1),
            new Color(0.1f, 0.1f, 1,1),
            Color.PURPLE,
            Color.PINK,
            Color.WHITE};

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

        int rowHeight = Brick.HEIGHT + 12;
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

    void removeAll(List<Brick> b) {
        mBricks.removeAll(b);
    }

    private void addNewRow() {
        int brickPadding = 13;
        int rowHeight = Brick.HEIGHT + brickPadding;
        int brickLossChance = Math.max(0, (int)(65 - mCurrentRow * 2.5));
        for(int i = Brick.WIDTH/2 + brickPadding; i <= BetterBreakout.GAME_WIDTH - Brick.WIDTH/2 - brickPadding; i += Brick.WIDTH + brickPadding) {
            if(mRandom.nextInt(100) < brickLossChance) continue;
            add(i, rowHeight * mCurrentRow, RAINBOW[RAINBOW.length - mCurrentRow % RAINBOW.length - 1]);
        }
        mCurrentRow++;
    }
}





































































































