package com.chirag.betterbreakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.powerup.Bullet;
import com.chirag.betterbreakout.powerup.Explosion;
import com.chirag.betterbreakout.powerup.Laser;
import com.chirag.betterbreakout.powerup.Particle;
import com.chirag.betterbreakout.powerup.PowerUp;
import com.chirag.betterbreakout.ui.FuelBar;
import com.chirag.betterbreakout.ui.PauseButton;
import com.chirag.betterbreakout.ui.PowerUpIcon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    public enum Side {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    public static int score;

    private PauseButton pauseButton;
    private GlyphLayout glyphLayout;
    private Texture brickTexture;
    private Texture powerTexture;
    private Paddle paddle;
    private BrickGenerator bricks;
    private FuelBar mFuelBar;
    private List<Ball> balls;
    private List<PowerUp> powerUps;
    private List<Laser> lasers;
    private List<Explosion> explosions;
    private List<Bullet> bullets;
    private List<PowerUp.Power> activePowerups = new ArrayList<PowerUp.Power>();
    private List<PowerUpIcon> powerIcons;

    Game(Texture brickTexture, Texture powerTexture) {
        this.powerTexture = powerTexture;
        this.brickTexture = brickTexture;
        pauseButton = new PauseButton(BetterBreakout.GAME_WIDTH + 115, 90, 125);
        bricks = new BrickGenerator(brickTexture);

        balls = new ArrayList<Ball>();
        paddle = new Paddle(brickTexture, 80);
        paddle.setMaxFuel();
        balls.add(new Ball(15, paddle.getX(), 110, brickTexture));
        mFuelBar = new FuelBar(brickTexture, 10000);

        lasers = new ArrayList<Laser>();
        powerUps = new ArrayList<PowerUp>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        score = 0;
        glyphLayout = new GlyphLayout();
        activePowerups.add(PowerUp.Power.ADDBALL);
        powerIcons = new ArrayList<PowerUpIcon>();
    }

    void update() {
        bricks.moveDown(0.1f);
        if(pauseButton.isClicked() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            BetterBreakout.pause2();
        }

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
                        toDelBrick.add(b);
                        explosions.add(new Explosion(b.getX(), b.getY(), b.getColor(), Particle.ParticleType.BULLET));
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
            if(b.getY() < -1 * Brick.HEIGHT/2) {
                b.setDead();
                score -= 5;
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
                } else if (!brick.isHit()){
                    doBallBrickCollision(ball, brick);
                }
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

        mFuelBar.setFuel(paddle.getFuel());

        bullets.removeAll(toDelBullet);
        bricks.removeAll(toDelBrick);
        balls.removeAll(toDelBall);
        powerUps.removeAll(toDelPowerUp);
        lasers.removeAll(toDelLaser);
        explosions.removeAll(toDelExplosion);
        if(balls.isEmpty()) {
            lose();
        }
    }

    void draw(BitmapFont smallFont, SpriteBatch batch) {
        batch.setColor(127 / 256f, 96 / 256f, 0, 1);
        batch.draw(new Texture("sidebarbackground.png"), BetterBreakout.GAME_WIDTH, 0, BetterBreakout.GAME_PADDING, BetterBreakout.GAME_HEIGHT);
        batch.setColor(Color.WHITE);
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
        mFuelBar.draw(batch);
        ArrayList<PowerUpIcon> toDelIcon = new ArrayList<PowerUpIcon>();
        for(PowerUpIcon icon : powerIcons) {
            if(icon.isExpired()) {
                toDelIcon.add(icon);
            }
        }
        powerIcons.removeAll(toDelIcon);
        for(int i = 0; i < powerIcons.size(); i++) {
            powerIcons.get(i).draw(batch, BetterBreakout.GAME_WIDTH + 50, 900 - (25 + PowerUpIcon.ICON_SIDE) * i);
        }

        glyphLayout.setText(smallFont, "Score: " + score);
        smallFont.draw(batch, "Score: " + score, 1790 - glyphLayout.width / 2, BetterBreakout.GAME_HEIGHT - 10);
        pauseButton.draw(batch);
    }

    private static boolean isColliding(Sprite s1, Sprite s2) {
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
    private static Side getCollisionSide(MovingGameElement ball, Rectangular rect) {
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
        List<PowerUp.Power> allowedPowerups = new ArrayList<PowerUp.Power>(Arrays.asList(PowerUp.POWERS));
        int extraBallCount = 0;
        for(PowerUp.Power p : activePowerups) {
            if(p == PowerUp.Power.ADDBALL) {
                extraBallCount++;
            } else if(p == PowerUp.Power.SMALLPADDLE || p == PowerUp.Power.LARGEPADDLE) {
                allowedPowerups.remove(PowerUp.Power.LARGEPADDLE);
                allowedPowerups.remove(PowerUp.Power.SMALLPADDLE);
            } else if(p == PowerUp.Power.SHOTGUN) {
                allowedPowerups.remove(PowerUp.Power.SHOTGUN);
            } else if(p == PowerUp.Power.LASER) {
                allowedPowerups.remove(PowerUp.Power.LASER);
            } else if(p == PowerUp.Power.PADDLE_EFFICIENCY) {
                allowedPowerups.remove(PowerUp.Power.PADDLE_EFFICIENCY);
            } else if(p == PowerUp.Power.PADDLE_SPEED) {
                allowedPowerups.remove(PowerUp.Power.PADDLE_SPEED);
            }
            if(extraBallCount == 3) {
                allowedPowerups.remove(PowerUp.Power.ADDBALL);
            }
        }

        PowerUp.Power p = allowedPowerups.get((int) (Math.random() * allowedPowerups.size()));
        switch(p) {
            case ADDBALL:
                Ball b = new Ball(15, paddle.getX(), 110, brickTexture);
                balls.add(b);
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
                }, 10000);
                powerIcons.add(new PowerUpIcon(p, 0, 8000));
                break;
            case SMALLPADDLE:
                paddle.setState(Paddle.State.SMALL, 8000);
                activePowerups.add(PowerUp.Power.SMALLPADDLE);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.SMALLPADDLE);
                    }
                }, 10000);
                powerIcons.add(new PowerUpIcon(p, 0, 8000));
                break;
            case LASER:
                lasers.add(new Laser(brickTexture, powerUp.getX()));
                lasers.get(lasers.size() - 1).activate();
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.LASER);
                    }
                }, 2000);
                powerIcons.add(new PowerUpIcon(p, 0, 2000));
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
                }, 5000);
                powerIcons.add(new PowerUpIcon(p, 0, 5000));
                break;
            case PADDLE_SPEED:
                if(Math.random() > 0.5) {
                    powerIcons.add(new PowerUpIcon(p, 1, 10000));
                    paddle.setSpeed(19.5f);
                } else {
                    powerIcons.add(new PowerUpIcon(p, 0, 10000));

                    paddle.setSpeed(10f);
                }
                activePowerups.add(PowerUp.Power.PADDLE_SPEED);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        paddle.setSpeed(10);
                        activePowerups.remove(PowerUp.Power.PADDLE_SPEED);
                    }
                }, 12000);
                break;
            case PADDLE_EFFICIENCY:
                switch((int)(Math.random() * 4)) {
                    case 0:
                        paddle.setEfficiency(Paddle.Efficiency.TERRIBLE);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        powerIcons.add(new PowerUpIcon(p, 0, 10000));

                        break;
                    case 1:
                        paddle.setEfficiency(Paddle.Efficiency.BAD);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        powerIcons.add(new PowerUpIcon(p, 1, 10000));

                        break;
                    case 2:
                        paddle.setEfficiency(Paddle.Efficiency.GOOD);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        powerIcons.add(new PowerUpIcon(p, 2, 10000));
                        break;
                    case 3:
                        paddle.setEfficiency(Paddle.Efficiency.AMAZING);
                        TimeUtil.doLater(new Runnable() {
                            @Override
                            public void run() {
                                paddle.setEfficiency(Paddle.Efficiency.NORMAL);
                            }
                        }, 15000);
                        powerIcons.add(new PowerUpIcon(p, 3, 10000));

                }
                activePowerups.add(PowerUp.Power.PADDLE_EFFICIENCY);
                TimeUtil.doLater(new Runnable() {
                    @Override
                    public void run() {
                        activePowerups.remove(PowerUp.Power.PADDLE_EFFICIENCY);
                    }
                }, 17000);
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
                if(Math.random() > 0.5) {
                    powerUps.add(new PowerUp(
                            powerTexture,
                            (int) brick.getX(),
                            (int) brick.getY(),
                            20,
                            20));
                }
                score += 3;
                ball.updateAngle();
                break;
            case TOP:
            case BOTTOM:
                ball.stepBack();
                ball.reverseY();
                brick.gotHit();
                if(Math.random() > 0.5) {
                    powerUps.add(new PowerUp(
                            powerTexture,
                            (int) brick.getX(),
                            (int) brick.getY(),
                            20,
                            20));
                }
                score += 3;
                ball.updateAngle();
                break;
        }
    }

    private void lose() {
        bricks = new BrickGenerator(brickTexture);

        balls = new ArrayList<Ball>();
        paddle = new Paddle(brickTexture, 80);
        paddle.setMaxFuel();
        balls.add(new Ball(15, paddle.getX(), 110, brickTexture));
        mFuelBar = new FuelBar(brickTexture, 10000);
        TimeUtil.clear();
        lasers = new ArrayList<Laser>();
        powerUps = new ArrayList<PowerUp>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        glyphLayout = new GlyphLayout();
        activePowerups.add(PowerUp.Power.ADDBALL);
        powerIcons = new ArrayList<PowerUpIcon>();
        BetterBreakout.setCurrentScreen(BetterBreakout.Screen.LOSE);
    }
}








