package com.chirag.betterbreakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;


public class Paddle implements Rectangular{
    public enum State {
        NORMAL, LARGE, SMALL
    }

    public enum Efficiency {
        TERRIBLE, BAD, NORMAL, GOOD, AMAZING
    }

    private static Map<State, Integer> WIDTHS;
    private static Map<Efficiency, Float> FUEL_MULTIPLIER;
    private static Map<Efficiency, Color> EFFICIENCY_COLOR;
    private static final int HEIGHT = 30;

    static {
        WIDTHS = new HashMap<State, Integer>();
        WIDTHS.put(State.NORMAL, 100);
        WIDTHS.put(State.SMALL, 50);
        WIDTHS.put(State.LARGE, 150);

        FUEL_MULTIPLIER = new HashMap<Efficiency, Float>();
        FUEL_MULTIPLIER.put(Efficiency.TERRIBLE, 1.8f);
        FUEL_MULTIPLIER.put(Efficiency.BAD, 1.4f);
        FUEL_MULTIPLIER.put(Efficiency.NORMAL, 1.0f);
        FUEL_MULTIPLIER.put(Efficiency.GOOD, 0.8f);
        FUEL_MULTIPLIER.put(Efficiency.AMAZING, 0.6f);

        EFFICIENCY_COLOR = new HashMap<Efficiency, Color>();
        EFFICIENCY_COLOR.put(Efficiency.TERRIBLE, Color.RED);
        EFFICIENCY_COLOR.put(Efficiency.BAD, Color.ORANGE);
        EFFICIENCY_COLOR.put(Efficiency.NORMAL, Color.WHITE);
        EFFICIENCY_COLOR.put(Efficiency.GOOD, Color.YELLOW);
        EFFICIENCY_COLOR.put(Efficiency.AMAZING, Color.GREEN);
    }

    private int mFuel;
    private Efficiency mEfficiency;
    private float mX;
    private long mPowerUpEndTime;
    private State mState;
    private Sprite mSprite;
    private float mY;
    private float mWidth;

    Paddle(Texture paddleTexture, float x, float y) {
        mX = Gdx.input.getX();
        mY = y;
        mWidth = WIDTHS.get(State.NORMAL);
        mFuel = 5000;

        mEfficiency = Efficiency.NORMAL;
        mState = State.NORMAL;

        mSprite = new Sprite(paddleTexture);
        mSprite.setBounds(0, y, WIDTHS.get(State.NORMAL), 20);
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public float getWidth() {
        return mWidth;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    int getFuel() {
        return mFuel;
    }

    public void addFuel(int value) {
        mFuel += value;
    }

    Sprite getSprite() {
        mSprite.setCenter(mX, mY);
        return mSprite;
    }

    public void setEfficiency(Efficiency efficiency) {
        mEfficiency = efficiency;
        mSprite.setColor(EFFICIENCY_COLOR.get(efficiency));
    }

    void update() {
        if(Math.abs(Gdx.input.getX() - mX) < mFuel) {
            mFuel -= Math.abs(Gdx.input.getX() - mX) * FUEL_MULTIPLIER.get(mEfficiency);
            mX = Gdx.input.getX();
        }

        if(mState != State.NORMAL) {
            if(mPowerUpEndTime < System.currentTimeMillis()) {
                mState = State.NORMAL;
                setState(State.NORMAL, 0);
                mSprite.setCenter(mX, mY);
            }
        }
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.setSize(mWidth, HEIGHT);
        mSprite.draw(batch);
    }

    /**
     * Changes with width of the paddle for a duration
     * @param state THE STATE
     * @param duration the duration (in millis) that it should stay
     */
    void setState(State state, int duration) {
        mState = state;

        mWidth = WIDTHS.get(state);
        mPowerUpEndTime = System.currentTimeMillis() + duration;
        mX = Gdx.input.getX();
        mSprite.setBounds(mX, mY, mWidth, HEIGHT);

    }
}
