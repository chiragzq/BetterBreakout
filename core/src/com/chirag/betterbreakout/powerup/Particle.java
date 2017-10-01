package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.chirag.betterbreakout.*;

import java.util.HashMap;
import java.util.Map;

public class Particle extends MovingGameElement {
    public enum ParticleType {
       EXPLOSION, BULLET
    }

    private ParticleType mParticleType;
    private static Map<ParticleType, Texture> TEXTURES = new HashMap<ParticleType, Texture>();
    private static Map<ParticleType, Float> HEIGHTS = new HashMap<ParticleType, Float>();
    private static Map<ParticleType, Float> WIDTHS = new HashMap<ParticleType, Float>();

    Particle(float x, float y, float XVel, float YVel, ParticleType type, Color color) {
        super(TEXTURES.get(type), x, y, WIDTHS.get(type), HEIGHTS.get(type), XVel, YVel);

        mParticleType = type;
        mSprite.setColor(color);
    }

    public void update() {
        mYVel -= 0.25f;
        super.update();

    }

    public ParticleType getParticleType() {
        return mParticleType;
    }

    public static void loadAllTextures() {
        TEXTURES.put(ParticleType.EXPLOSION, new Texture(Gdx.files.internal("brick.png")));
        HEIGHTS.put(ParticleType.EXPLOSION, 12f);
        WIDTHS.put(ParticleType.EXPLOSION, 12f);
        TEXTURES.put(ParticleType.BULLET, new Texture(Gdx.files.internal("brick.png")));
        HEIGHTS.put(ParticleType.BULLET, 5f);
        WIDTHS.put(ParticleType.BULLET, 5f);
    }
}
