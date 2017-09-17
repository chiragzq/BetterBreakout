package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Game;
import com.chirag.betterbreakout.MovingGameElement;
import com.chirag.betterbreakout.Rectangular;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explosion implements DeleteableGameElement {
    private static final int PARTICLE_NUM = 85;

    private ArrayList<Particle> mParticles;

    public Explosion(float x, float y, Color color) {
        mParticles = new ArrayList<Particle>();
        Random random = new Random();

        for(int i = 0;i < PARTICLE_NUM;i ++) {
            float xVel = random.nextFloat() * 9 - 4;
            float yVel = random.nextFloat() * 9 - 4;

            mParticles.add(new Particle(x, y, xVel, yVel, Particle.ParticleType.EXPLOSION, color));
        }
    }

    @Override
    public boolean isDead() {
        return mParticles.isEmpty();
    }

    public void update(Rectangular rect) {
        List<Particle> toDelParticle = new ArrayList<Particle>();
        for(Particle particle : mParticles) {
            particle.update();
            doParticlePaddleCollision(particle, rect);
            if(particle.isDead()) {
                toDelParticle.add(particle);
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
             particle.stepBack();
             particle.reverseY();
         }
    }
}
