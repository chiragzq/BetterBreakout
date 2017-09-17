package com.chirag.betterbreakout;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrickPattern {
    private Texture mBrickTexture;
    private List<Brick> mBricks;
    private boolean[] brickStatus;

    private static final int TOP_PADDING = 120;
    private final Color[] RAINBOW = {Color.RED, Color.ORANGE, Color.GOLD, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PURPLE, Color.PINK, Color.WHITE};

    BrickPattern(Texture brickTexture) {
        mBricks = new ArrayList<Brick>();
        this.mBrickTexture = brickTexture;
    }

    List<Brick> getBricks() {
        return mBricks;
    }

    void update() {
        for(Brick b : mBricks)
            b.update();
    }

    void draw(SpriteBatch batch) {
        for(Brick b : mBricks) {
            b.draw(batch);
        }
    }

    private void add(int x, int y, int width, int height, Color color) {
        mBricks.add(new Brick(mBrickTexture, x, y, width, height, color));
    }

    public void remove(Brick b) {
        mBricks.remove(b);
    }

    void removeAll(List<Brick> b) {
        mBricks.removeAll(b);
    }

    void generateGrid(int widthBricks, int heightBricks, int padding) {
        brickStatus = new boolean[widthBricks * heightBricks];

        int sidePadding = (BetterBreakout.GAME_WIDTH - Brick.WIDTH * widthBricks + (widthBricks-1) * padding) / 2;
        int height = Brick.HEIGHT * heightBricks + (heightBricks-1) * padding;

        for(int i = sidePadding;
            i <= BetterBreakout.GAME_WIDTH - sidePadding;
            i += Brick.WIDTH + padding) {
            for(int j = BetterBreakout.GAME_HEIGHT - TOP_PADDING;
                j >= BetterBreakout.GAME_HEIGHT - TOP_PADDING - height;
                j -= Brick.HEIGHT + padding) {
                Color c = RAINBOW[(BetterBreakout.GAME_HEIGHT - TOP_PADDING - j)/(Brick.HEIGHT+padding)];
                add(i, j , Brick.WIDTH, Brick.HEIGHT, c);
            }
        }

    }
}
