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
        setTexture(texture);
        isDead = false;
        if(power != Power.RANDOM) {
            this.power = power;
        } else {
            switch((int) (Math.random() * 3)) {
                case 0:
                    this.power = Power.ADDBALL;
                    break;
                case 1:
                    this.power = Power.LARGEPADDLE;
                    break;
                case 2:
                    this.power = Power.SMALLPADDLE;
                    break;
            }
        }

    }

    public void update() {
        if(!isDead) setY(getY() - 3);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public enum Power {
        ADDBALL, LARGEPADDLE, SMALLPADDLE, RANDOM
    }
}
