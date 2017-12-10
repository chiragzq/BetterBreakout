package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Color;
import com.chirag.betterbreakout.BetterBreakout;
import com.chirag.betterbreakout.DeleteableGameElement;

public class Particle implements DeleteableGameElement {
    public enum ParticleType {
       EXPLOSION, BULLET
    }

    private ParticleType mParticleType;

    private float mX;
    private float mY;
    private float mYVel;
    private float mXVel;
    private Color mColor;
    private boolean mIsDead;

    Particle(float x, float y, float XVel, float YVel, ParticleType type, Color color) {
        mX = x;
        mY = y;
        mXVel = XVel;
        mYVel = YVel;
        mColor = color;

        mIsDead = false;
        mParticleType = type;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public boolean isDead() {
        return mIsDead;
    }

    void setDead(boolean isDead) {
        mIsDead = isDead;
    }

    Color getColor() {
        return mColor;
    }

    public void update() {
        mYVel -= 0.25f;
        mX += mXVel;
        mY += mYVel;

        if(mX < 0 || mX > BetterBreakout.GAME_WIDTH || mY < 0 || mY > BetterBreakout.GAME_HEIGHT) {
            mIsDead = true;
        }
    }

    ParticleType getParticleType() {
        return mParticleType;
    }

    public static void loadAllTextures() {

    }
}
