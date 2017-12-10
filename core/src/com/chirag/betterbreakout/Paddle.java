package com.chirag.betterbreakout;

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

    private static Map<State, Integer> WIDTHS = new HashMap<State, Integer>();
    private static Map<Efficiency, Float> FUEL_MULTIPLIER = new HashMap<Efficiency, Float>();
    private static Map<Efficiency, Color> EFFICIENCY_COLOR = new HashMap<Efficiency, Color>();
    private static Map<State, Float> SIZE_MULTIPLIER = new HashMap<State, Float>();
    private static final int HEIGHT = 30;

    static {
        WIDTHS.put(State.NORMAL, 150);
        WIDTHS.put(State.SMALL, 75);
        WIDTHS.put(State.LARGE, 225);

        FUEL_MULTIPLIER.put(Efficiency.TERRIBLE, 1.5f);
        FUEL_MULTIPLIER.put(Efficiency.BAD, 1.2f);
        FUEL_MULTIPLIER.put(Efficiency.NORMAL, 0.7f);
        FUEL_MULTIPLIER.put(Efficiency.GOOD, 0.6f);
        FUEL_MULTIPLIER.put(Efficiency.AMAZING, 0.4f);

        EFFICIENCY_COLOR.put(Efficiency.TERRIBLE, Color.RED);
        EFFICIENCY_COLOR.put(Efficiency.BAD, Color.ORANGE);
        EFFICIENCY_COLOR.put(Efficiency.NORMAL, Color.WHITE);
        EFFICIENCY_COLOR.put(Efficiency.GOOD, Color.YELLOW);
        EFFICIENCY_COLOR.put(Efficiency.AMAZING, Color.GREEN);

        SIZE_MULTIPLIER.put(State.SMALL, 0.5f);
        SIZE_MULTIPLIER.put(State.NORMAL, 1f);
        SIZE_MULTIPLIER.put(State.LARGE, 1.5f);
    }

    private float mMaxFuel;
    private float mSpeed;
    private float mFuel;
    private Efficiency mEfficiency;
    private float mX;
    private long mPowerUpEndTime;
    private State mState;
    private Sprite mSprite;
    private float mY;
    private float mWidth;

    Paddle(Texture paddleTexture, float y) {
        mX = BetterBreakout.GAME_WIDTH/2;
        mY = y;
        mWidth = WIDTHS.get(State.NORMAL);
        mFuel = 5000;
        mMaxFuel = 10000;
        mSpeed = 15;

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

    float getFuel() {
        return mFuel;
    }

    public void addFuel(int value) {
        mFuel += value;
        mFuel = Math.min(mFuel, mMaxFuel);
    }

    Sprite getSprite() {
        mSprite.setCenter(mX, mY);
        return mSprite;
    }

    void setEfficiency(Efficiency efficiency) {
        mEfficiency = efficiency;
        mSprite.setColor(EFFICIENCY_COLOR.get(efficiency));
    }

    void setSpeed(float speed) {
        mSpeed = speed;
    }

    void setMaxFuel() {
        mMaxFuel = (float) 10000;
    }

    void update() {
        if(mFuel > mSpeed && ControlsUtil.direction != 0) {
            if(mX < BetterBreakout.GAME_WIDTH - mWidth / 2 && ControlsUtil.direction == 1) {
                mFuel -= mSpeed * FUEL_MULTIPLIER.get(mEfficiency) * SIZE_MULTIPLIER.get(mState);
                mX += ControlsUtil.direction * mSpeed;
            } else if(mX > mWidth / 2 && ControlsUtil.direction == -1) {
                mFuel -= mSpeed * FUEL_MULTIPLIER.get(mEfficiency) * SIZE_MULTIPLIER.get(mState);
                mX += ControlsUtil.direction * mSpeed;
            }
        }

        if(mState != State.NORMAL) {
            if(mPowerUpEndTime < System.currentTimeMillis()) {
                mState = State.NORMAL;
                setState(State.NORMAL, 0);
                mSprite.setCenter(mX, mY);
            }
        }
        mFuel = Math.max(0, mFuel);
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
        mSprite.setSize(mWidth, HEIGHT);
        mSprite.setCenterX(mX);
    }
}
