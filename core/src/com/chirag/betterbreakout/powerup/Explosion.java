package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Paddle;
import com.chirag.betterbreakout.Rectangular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Explosion implements DeleteableGameElement {
    private static Map<Particle.ParticleType, Sprite> particleSprites = new HashMap<Particle.ParticleType, Sprite>();
    private static Map<Particle.ParticleType, Integer> particleNums = new HashMap<Particle.ParticleType, Integer>();
    private static Map<Particle.ParticleType, Integer> fuelAmounts = new HashMap<Particle.ParticleType, Integer>();

    static {
        particleNums.put(Particle.ParticleType.EXPLOSION, 75);
        particleNums.put(Particle.ParticleType.BULLET, 50);
        fuelAmounts.put(Particle.ParticleType.EXPLOSION, 50);
        fuelAmounts.put(Particle.ParticleType.BULLET, 30);

        Sprite explosionSprite = new Sprite(new Texture(Gdx.files.internal("brick.png")));
        explosionSprite.setSize(12f + 6 * BetterBreakout.deviceType, 12f + 6 * BetterBreakout.deviceType);
        Sprite bulletSprite = new Sprite(new Texture(Gdx.files.internal("brick.png")));
        bulletSprite.setSize(5f + 3 * BetterBreakout.deviceType, 5f + 3 * BetterBreakout.deviceType);

        particleSprites.put(Particle.ParticleType.EXPLOSION, explosionSprite);
        particleSprites.put(Particle.ParticleType.BULLET, bulletSprite);
    }

    private ArrayList<Particle> mParticles;
    private Particle.ParticleType mParticleType;

    public Explosion(float x, float y, Color color, Particle.ParticleType particleType) {
        mParticles = new ArrayList<Particle>();
        mParticleType = particleType;
        Random random = new Random();
        for(int i = 0; i < particleNums.get(particleType) * (-1 / 5.0 * BetterBreakout.deviceType + 1); i ++) {
            float xVel = random.nextFloat() * 7 - 4;
            float yVel = random.nextFloat() * 7 - 4;

            mParticles.add(new Particle(x, y, xVel, yVel, particleType, color));
        }
    }

    @Override
    public boolean isDead() {
        return mParticles.isEmpty();
    }

    public void update(Paddle paddle) {
        List<Particle> toDelParticle = new ArrayList<Particle>();
        for(Particle particle : mParticles) {
            particle.update();
            doParticlePaddleCollision(particle, paddle);
            if(particle.isDead()) {
                toDelParticle.add(particle);
                paddle.addFuel((int)(fuelAmounts.get(mParticleType) * (1 / 4.0 * BetterBreakout.deviceType + 1)));
            }
        }
        mParticles.removeAll(toDelParticle);
    }

    public void draw(SpriteBatch batch) {
        for(Particle particle : mParticles) {
           Sprite sprite = particleSprites.get(particle.getParticleType());
           sprite.setCenter(particle.getX(), particle.getY());
           sprite.setColor(particle.getColor());
           sprite.draw(batch);
        }
    }

    private void doParticlePaddleCollision(Particle particle, Rectangular paddle) {
        Sprite sprite = particleSprites.get(particle.getParticleType());

        float xDis = Math.abs((particle.getX()) - (paddle.getX()));
        float yDis = Math.abs((particle.getY()) - (paddle.getY()));

        float avgWidth = (sprite.getWidth() + paddle.getWidth()) / 2;
        float avgHeight = (sprite.getHeight() + paddle.getHeight()) / 2;

        particle.setDead(xDis < avgWidth && yDis < avgHeight);
    }
}
