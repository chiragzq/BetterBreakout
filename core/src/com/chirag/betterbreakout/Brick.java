package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Brick extends Sprite {

    public Brick(Texture brickTexture, int x, int y, int width, int height) {
        super(brickTexture);
        this.setBounds(x, y, width, height);
    }

}
