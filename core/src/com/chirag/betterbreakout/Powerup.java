package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Powerup extends Sprite {
    Power power;
    boolean isDead;

    public Powerup(Power power, Texture texture, int x, int y, int width, int height) {
        super(texture);
        setBounds(x, y, width, height);
        isDead = false;
        this.power = power;
    }

    public void update() {
        if(!isDead) setX(getX() - 1);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public enum Power {
        ADDBALL, LARGEPADDLE, SMALLPADDLE
    }
}
