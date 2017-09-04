package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.chirag.betterbreakout.BetterBreakout;

import java.util.HashMap;
import java.util.Map;

public class Particle extends BaseGameElement {
    public enum ParticleType {
       EXPLOSION
    }

    private static Map<ParticleType, Texture> TEXTURES = new HashMap<ParticleType, Texture>();
    private static Map<ParticleType, Float> HEIGHTS = new HashMap<ParticleType, Float>();
    private static Map<ParticleType, Float> WIDTHS = new HashMap<ParticleType, Float>();

    private float mXVel;
    private float mYVel;

    public Particle(float x, float y, float XVel, float YVel, ParticleType type) {
        super(TEXTURES.get(type), x, y, WIDTHS.get(type), HEIGHTS.get(type));

        mXVel = XVel;
        mYVel = YVel;
    }

    public void update() {
        mX += mXVel;
        mY += mYVel;

        if(mX < 0 || mX > BetterBreakout.GAME_WIDTH || mY < 0 || mY > BetterBreakout.GAME_HEIGHT) {
            mIsDead = true;
        }
    }

    public static void loadAllTextures() {
        TEXTURES.put(ParticleType.EXPLOSION, new Texture(Gdx.files.internal("brick.png")));
        HEIGHTS.put(ParticleType.EXPLOSION, 10f);
        WIDTHS.put(ParticleType.EXPLOSION, 10f);
    }
}
