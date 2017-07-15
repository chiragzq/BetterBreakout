package com.chirag.betterbreakout;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle extends Sprite {
    public Paddle(int width, int height, int y,Texture paddleTexture) {
        super(paddleTexture);
        setBounds(0, y, width, height);
    }

    public void update() {
        setCenterX(Gdx.input.getX());
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
