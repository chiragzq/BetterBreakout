package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Brick extends Sprite {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 25;
    long time;
    boolean isDead;

    public void update() {
        if(System.currentTimeMillis() > time) isDead = true;
    }

    public void gotHit() {
        time = System.currentTimeMillis() + 200;
        setColor(getColor().mul(0.75f));
    }

    public Brick(Texture brickTexture, int x, int y, int width, int height, Color color) {
        super(brickTexture);
        this.setBounds(x, y, width, height);
        this.setColor(color);
        time = Long.MAX_VALUE;
        isDead = false;
    }

}
