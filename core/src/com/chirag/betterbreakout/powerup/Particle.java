package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.chirag.betterbreakout.*;

import java.util.HashMap;
import java.util.Map;

public class Particle extends MovingGameElement {
    public enum ParticleType {
       EXPLOSION
    }

    private static Map<ParticleType, Texture> TEXTURES = new HashMap<ParticleType, Texture>();
    private static Map<ParticleType, Float> HEIGHTS = new HashMap<ParticleType, Float>();
    private static Map<ParticleType, Float> WIDTHS = new HashMap<ParticleType, Float>();

    public Particle(float x, float y, float XVel, float YVel, ParticleType type, Color color) {
        super(TEXTURES.get(type), x, y, WIDTHS.get(type), HEIGHTS.get(type), XVel, YVel);

        mSprite.setColor(color);
    }

    public void update() {
        mYVel -= 0.25f;
        super.update();

    }

    public static void loadAllTextures() {
        TEXTURES.put(ParticleType.EXPLOSION, new Texture(Gdx.files.internal("brick.png")));
        HEIGHTS.put(ParticleType.EXPLOSION, 10f);
        WIDTHS.put(ParticleType.EXPLOSION, 10f);
    }
}
