package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Brick extends Sprite {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 25;

    public Brick(Texture brickTexture, int x, int y, int width, int height, Color color) {
        super(brickTexture);
        this.setBounds(x, y, width, height);
        this.setColor(color);
    }

}
