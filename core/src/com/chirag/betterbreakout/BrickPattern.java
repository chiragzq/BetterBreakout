package com.chirag.betterbreakout;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BrickPattern {
    List<Brick> bricks;
    Texture brickTexture;

    public BrickPattern(Texture brickTexture) {
        bricks = new ArrayList<Brick>();
        this.brickTexture = brickTexture;
    }

    public void add(int x, int y, int width, int height) {
        bricks.add(new Brick(brickTexture, x, y, width, height));
    }

    public void update() {

    }

    public void draw(SpriteBatch batch) {
        for(Brick b : bricks) {
            b.draw(batch);
        }
    }
}
