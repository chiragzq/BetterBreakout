package com.chirag.betterbreakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.chirag.betterbreakout.powerup.Bullet;
import com.chirag.betterbreakout.powerup.Explosion;
import com.chirag.betterbreakout.powerup.Laser;
import com.chirag.betterbreakout.powerup.Particle;
import com.chirag.betterbreakout.powerup.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public enum Side {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    private GlyphLayout glyphLayout;
    private int score;
    private boolean isReset;
    private Texture brickTexture;
    private Texture powerTexture;
    private Paddle paddle;
    private BrickGenerator bricks;
    private List<Ball> balls;
    private List<PowerUp> powerUps;
    private List<Laser> lasers;
    private List<Explosion> explosions;
    private List<Bullet> bullets;
    public static List<PowerUp.Power> activePowerups = new ArrayList<PowerUp.Power>();

    Game(Texture brickTexture, Texture powerTexture) {
        this.powerTexture = powerTexture;
        this.brickTexture = brickTexture;
        bricks = new BrickGenerator(brickTexture);

        balls = new ArrayList<Ball>();
        balls.add(new Ball(15, 960, 110, brickTexture));

        lasers = new ArrayList<Laser>();
        paddle = new Paddle(brickTexture, 120, 80);
        powerUps = new ArrayList<PowerUp>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        isReset = false;
        score = 0;
        glyphLayout = new GlyphLayout();
        activePowerups.add(PowerUp.Power.ADDBALL);
    }

    void update() {
        bricks.moveDown(0.1f);

        List<Brick> toDelBrick = new ArrayList<Brick>();
        List<Ball> toDelBall = new ArrayList<Ball>();
        List<PowerUp> toDelPowerUp = new ArrayList<PowerUp>();
        List<Laser> toDelLaser = new ArrayList<Laser>();
        List<Explosion> toDelExplosion = new ArrayList<Explosion>();
        List<Bullet> toDelBullet = new ArrayList<Bullet>();

        bricks.update();
        paddle.update();

        for(Laser r : lasers) {
            r.update();
        }

        for(Bullet b : bullets) {
            b.update();
        }

        for(Explosion explosion : explosions) {
            explosion.update(paddle);
            if(explosion.isDead()) {
                toDelExplosion.add(explosion);
            }
        }


        for(PowerUp p : powerUps) {
            p.update();
            if(isColliding(paddle.getSprite(), p.getSprite())) {
                onPowerUp(p);
                toDelPowerUp.add(p);
            }
        }

        for(Laser laser : lasers) {
            if(laser.isActive()) {
                for(Brick b : bricks.getBricks()) {
                    if(Math.abs(b.getX() - laser.getX()) < 51) {
                        b.setDead(true);
                    }
                }
                laser.setActive(false);
            }
            if(laser.isDead()) {
                toDelLaser.add(laser);
            }
        }
        bricks.removeAll(toDelBrick);
        toDelBrick = new ArrayList<Brick>();

        for(Brick b : bricks.getBricks()) {
            for(Bullet bullet : bullets) {
                if(isColliding(b.getSprite(), bullet.getSprite())) {
                    toDelBrick.add(b);
                    toDelBullet.add(bullet);
                    explosions.add(new Explosion(b.getX(),b.getY(),b.getColor(), Particle.ParticleType.BULLET));
                }
            }
            if(isColliding(paddle.getSprite(), b.getSprite())) {
                balls.clear();
            }
        }

        for(Ball ball : balls) {
            ball.update();
            if(ball.isDead()) {
                toDelBall.add(ball);
                activePowerups.remove(PowerUp.Power.ADDBALL);
                continue;
            }
            for(Brick brick : bricks.getBricks()) {
                if(brick.isDead()) {
                    explosions.add(new Explosion(brick.getX(), brick.getY(), brick.getColor(), Particle.ParticleType.EXPLOSION));
                    toDelBrick.add(brick);
                }
                doBallBrickCollision(ball, brick);
            }
            switch(getCollisionSide(ball, paddle)) {
                case LEFT:
                case RIGHT:
                    ball.reverseX();
                    ball.stepBack();
                    break;
                case BOTTOM:
                    ball.reverseY();
                    ball.stepBack();
                    break;
                case TOP:
                    float xdif = paddle.getX() + paddle.getWidth() / 2 - ball.getX() + ball.getWidth();
                    if(xdif < 0) {
                        xdif = 0;
                    }
                    if(xdif > paddle.getWidth()) {
                        xdif = paddle.getWidth();
                    }
                    float angle = (xdif * 150 / paddle.getWidth());
                    ball.stepBack();
                    ball.setDirection((int) angle, 10);

            }

        }

        bullets.removeAll(toDelBullet);
        bricks.removeAll(toDelBrick);
        balls.removeAll(toDelBall);
        powerUps.removeAll(toDelPowerUp);
        lasers.removeAll(toDelLaser);
        explosions.removeAll(toDelExplosion);
        if(balls.isEmpty() && !isReset) {
            lose();
        }
    }

    void draw(BitmapFont bitmapFont, SpriteBatch batch) {
        bricks.draw(batch);
        paddle.draw(batch);
        for(Laser r : lasers) {
            r.draw(batch);
        }
        for(Ball ball : balls) {
            ball.draw(batch);
        }
        for(PowerUp p : powerUps) {
            p.draw(batch);
        }
        for(Explosion explosion : explosions) {
            explosion.draw(batch);
        }
        for(Bullet b : bullets) {
            b.draw(batch);
        }
        if(isReset) {
            glyphLayout.setText(bitmapFont, "You Lose");
            bitmapFont.draw(
                    batch,
                    "You Lose",
                    (BetterBreakout.GAME_WIDTH - glyphLayout.width) / 2,
                    BetterBreakout.GAME_HEIGHT/2 + glyphLayout.height
            );
        } else {
            bitmapFont.draw(batch, "Score: " + score, 0, BetterBreakout.GAME_HEIGHT - 2);
            bitmapFont.draw(batch, "Fuel: " + paddle.getFuel(), 0, BetterBreakout.GAME_HEIGHT - 50);
        }
    }

    private boolean isColliding(Sprite s1, Sprite s2) {
        float xDis = Math.abs((s1.getX() + s1.getWidth() / 2) - (s2.getX() + s2.getWidth() / 2));
        float yDis = Math.abs((s1.getY() + s1.getHeight() / 2) - (s2.getY() + s2.getHeight() / 2));

        float avgWidth = (s1.getWidth() + s2.getWidth()) / 2;
        float avgHeight = (s1.getHeight() + s2.getHeight()) / 2;

        return xDis < avgWidth && yDis < avgHeight;
    }

    /**
     * Returns which side ball hit brick on
     *
     * @param ball the ball hitting rect2
     * @param rect the one getting hit
     * @return which side on brick was hit by ball
     */
    public static Side getCollisionSide(MovingGameElement ball, Rectangular rect) {
        float xDis = Math.abs((ball.getX()) - (rect.getX()));
        float yDis = Math.abs((ball.getY()) - (rect.getY()));

        float xDisOld = Math.abs((ball.getOldX()) - (rect.getX())); //ball center - vel

        float avgWidth = (ball.getWidth() + rect.getWidth()) / 2;
        float avgHeight = (ball.getHeight() + rect.getHeight()) / 2;

        if(xDis < avgWidth && yDis < avgHeight) {
            if(xDisOld < avgWidth) {
                //top or bottom
                if(ball.getYVel() > 0) {
                    return Side.BOTTOM;
                }
                return Side.TOP;
            } else {
                if(ball.getXVel() > 0) {
                    return Side.LEFT;
                }
                return Side.RIGHT;
            }
        }
        return Side.NONE;
    }

    private void onPowerUp(PowerUp powerUp) {
        switch(powerUp.getPower()) {
            case ADDBALL:
                Ball b = new Ball(15, 960, 110, brickTexture);
                balls.add(b);
                b.launch(Gdx.input.getX());
                activePowerups.add(PowerUp.Power.ADDBALL);
                break;
            case LARGEPADDLE:
                paddle.setState(Paddle.State.LARGE, 8000);
                activePowerups.add(PowerUp.Power.LARGEPADDLE);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.LARGEPADDLE);
                    }
                }, 8000);
                break;
            case SMALLPADDLE:
                paddle.setState(Paddle.State.SMALL, 8000);
                activePowerups.add(PowerUp.Power.SMALLPADDLE);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.SMALLPADDLE);
                    }
                }, 8000);
                break;
            case LASER:
                lasers.add(new Laser(brickTexture, powerUp.getX()));
                lasers.get(lasers.size() - 1).activate();
                break;
            case SHOTGUN:
                for(int i = 0; i < 8; i++) {
                    bullets.add(new Bullet(brickTexture, paddle.getX(), paddle.getY() + 5));
                }
                activePowerups.add(PowerUp.Power.SHOTGUN);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.SHOTGUN);
                    }
                }, 10000);
                break;
            case PADDLE_EFFICIENCY:
                switch((int)(Math.random() * 6)) {
                    case 0:case 4:
                        paddle.setEfficiency(Paddle.Efficiency.TERRIBLE);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        break;
                    case 1:case 5:
                        paddle.setEfficiency(Paddle.Efficiency.BAD);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        break;
                    case 2:
                        paddle.setEfficiency(Paddle.Efficiency.GOOD);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        break;
                    case 3:
                        paddle.setEfficiency(Paddle.Efficiency.AMAZING);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                }
                activePowerups.add(PowerUp.Power.PADDLE_EFFICIENCY);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.PADDLE_EFFICIENCY);
                    }
                }, 15100);
        }
    }

    private void doBallBrickCollision(Ball ball, Brick brick) {
        Side colSide = getCollisionSide(ball, brick);
        switch(colSide) {
            case LEFT:
            case RIGHT:
                ball.stepBack();
                ball.reverseX();
                brick.gotHit();
                if(Math.random() > 0.4) {
                    powerUps.add(new PowerUp(
                            PowerUp.Power.RANDOM,
                            powerTexture,
                            (int) brick.getX(),
                            (int) brick.getY(),
                            20,
                            20));
                }
                score++;
                ball.updateAngle();
                break;
            case TOP:
            case BOTTOM:
                ball.stepBack();
                ball.reverseY();
                brick.gotHit();
                if(Math.random() > 0.4) {
                    powerUps.add(new PowerUp(
                            PowerUp.Power.RANDOM,
                            powerTexture,
                            (int) brick.getX(),
                            (int) brick.getY(),
                            20,
                            20));
                }
                score++;
                ball.updateAngle();
                break;
        }
    }

    private void lose() {
        isReset = true;
        reset();
        TimeUtil.doLater(new Runnable() {
            @Override
            public void run() {
                isReset = false;
                start();
            }
        }, 8000);
    }

    private void reset() {
        score = 0;
        paddle = new Paddle(brickTexture, -2385, -21394);
        bricks.clearBricks();
        balls = new ArrayList<Ball>();
        powerUps = new ArrayList<PowerUp>();
        lasers = new ArrayList<Laser>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
    }

    private void start() {
        bricks = new BrickGenerator(brickTexture);
        paddle = new Paddle(brickTexture, 120, 80);
        balls.add(new Ball(15, 960, 110, brickTexture));
    }
}






































































































































































































































































