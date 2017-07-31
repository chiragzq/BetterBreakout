package com.chirag.betterbreakout;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BrickPattern {
    List<Brick> bricks;
    Texture brickTexture;
    public static final int TOP_PADDING = 120;
    final Color[] rainbow = {Color.RED, Color.ORANGE, Color.GOLD, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PURPLE, Color.PINK, Color.WHITE};

    public BrickPattern(Texture brickTexture) {
        bricks = new ArrayList<Brick>();
        this.brickTexture = brickTexture;
    }

    public void generateGrid(int widthBricks, int heightBricks, int padding) {
        int sidePadding = (BetterBreakout.GAME_WIDTH - Brick.WIDTH * widthBricks + (widthBricks-1) * padding) / 2;
        int height = Brick.HEIGHT * heightBricks + (heightBricks-1) * padding;

        for(int i = sidePadding;
            i <= BetterBreakout.GAME_WIDTH - sidePadding;
            i += Brick.WIDTH + padding) {
            for(int j = BetterBreakout.GAME_HEIGHT - TOP_PADDING;
                j >= BetterBreakout.GAME_HEIGHT - TOP_PADDING - height;
                j -= Brick.HEIGHT + padding) {
                Color c = rainbow[(BetterBreakout.GAME_HEIGHT - TOP_PADDING - j)/(Brick.HEIGHT+padding)];
                add(i, j , Brick.WIDTH, Brick.HEIGHT, c);
            }
        }

    }

    public void add(int x, int y, int width, int height, Color color) {
        bricks.add(new Brick(brickTexture, x, y, width, height, color));
    }

    public void remove(Brick b) {
        bricks.remove(b);
    }

    public void removeAll(List<Brick> b) {
        bricks.removeAll(b);
    }

    public void update() {
        for(Brick b : bricks)
            b.update();
    }

    public void draw(SpriteBatch batch) {
        for(Brick b : bricks) {
            b.draw(batch);
        }
    }
}
