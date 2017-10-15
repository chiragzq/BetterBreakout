package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Game;
import com.chirag.betterbreakout.MovingGameElement;
import com.chirag.betterbreakout.Paddle;
import com.chirag.betterbreakout.Rectangular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Explosion implements DeleteableGameElement {
    private static Map<Particle.ParticleType, Integer> particleNums = new HashMap<Particle.ParticleType, Integer>();
    private static Map<Particle.ParticleType, Integer> fuelAmounts = new HashMap<Particle.ParticleType, Integer>();

    static {
        particleNums.put(Particle.ParticleType.EXPLOSION, 75);
        particleNums.put(Particle.ParticleType.BULLET, 50);
        fuelAmounts.put(Particle.ParticleType.EXPLOSION, 6);
        fuelAmounts.put(Particle.ParticleType.BULLET, 4);
    }

    private ArrayList<Particle> mParticles;
    private Particle.ParticleType mParticleType;

    public Explosion(float x, float y, Color color, Particle.ParticleType particleType) {
        mParticles = new ArrayList<Particle>();
        mParticleType = particleType;
        Random random = new Random();
        for(int i = 0; i < particleNums.get(particleType) * (-3 / 5.0 * BetterBreakout.deviceType + 1); i ++) {
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
                paddle.addFuel((int)(fuelAmounts.get(mParticleType) * (3 / 2.0 * BetterBreakout.deviceType + 1)));
            }
        }
        mParticles.removeAll(toDelParticle);
    }

    public void draw(SpriteBatch batch) {
        for(Particle particle : mParticles) {
            particle.draw(batch);
        }
    }

    private void doParticlePaddleCollision(MovingGameElement particle, Rectangular paddle) {
         if(Game.getCollisionSide(particle, paddle) == Game.Side.TOP) {
             particle.setDead(true);
         }
    }
}
