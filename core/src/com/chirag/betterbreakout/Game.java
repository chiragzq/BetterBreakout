package com.chirag.betterbreakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.powerup.Explosion;
import com.chirag.betterbreakout.powerup.Laser;
import com.chirag.betterbreakout.powerup.PowerUp;

import java.util.ArrayList;
import java.util.List;

class Game {
    public enum Side {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    private int score;
    private Texture brickTexture;
    private Texture powerTexture;
    private Paddle paddle;
    private BrickPattern bricks;
    private List<Ball> balls;
    private List<PowerUp> powerUps;
    private List<Laser> lasers;
    private List<Explosion> explosions;

    Game(Texture brickTexture, Texture powerTexture) {
        this.powerTexture = powerTexture;
        this.brickTexture = brickTexture;
        bricks = new BrickPattern(brickTexture);
        bricks.generateGrid(15, 10, 10);

        balls = new ArrayList<Ball>();
        balls.add(new Ball(15, 960, 110, brickTexture));

        lasers = new ArrayList<Laser>();

        paddle = new Paddle(brickTexture, 120,80);

        powerUps = new ArrayList<PowerUp>();

        explosions = new ArrayList<Explosion>();

        score = 0;
    }

    void update() {
        List<Brick> toDelBrick = new ArrayList<Brick>();
        List<Ball> toDelBall = new ArrayList<Ball>();
        List<PowerUp> toDelPowerUp = new ArrayList<PowerUp>();
        List<Laser> toDelLaser = new ArrayList<Laser>();
        List<Explosion> toDelExplosion = new ArrayList<Explosion>();

        bricks.update();
        paddle.update();

        for(Laser r : lasers) {
            r.update();
        }

        for(Explosion explosion : explosions) {
            explosion.update();
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

        for(Ball ball : balls) {
            ball.update();
            if(ball.isDead()) {
                toDelBall.add(ball);
                continue;
            }
            for(Brick brick : bricks.getBricks()) {
                if(brick.isDead()) {
                    toDelBrick.add(brick);
                }
                doBallBrickCollision(ball, brick);
            }
            switch(getCollisionSide(ball, paddle)) {
                case LEFT: case RIGHT:
                    ball.reverseX();
                    ball.stepBack();
                    break;
                case BOTTOM:
                    ball.reverseY();
                    ball.stepBack();
                    break;
                case TOP:
                    float xdif = paddle.getX() + paddle.getWidth()/2 - ball.getX() + ball.getDiameter();
                    if(xdif < 0) {
                        xdif = 0;
                    }
                    if(xdif > paddle.getWidth()) {
                        xdif = paddle.getWidth();
                    }
                    float angle = (xdif * 120 / paddle.getWidth());
                    ball.stepBack();
                    ball.setDirection((int)angle, 10);

            }

        }

        bricks.removeAll(toDelBrick);
        balls.removeAll(toDelBall);
        powerUps.removeAll(toDelPowerUp);
        lasers.removeAll(toDelLaser);
        explosions.removeAll(toDelExplosion);
        if(balls.isEmpty()) {

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

        bitmapFont.draw(batch, "Score: " + score, 0, BetterBreakout.GAME_HEIGHT - 2);
    }

    private boolean isColliding(Sprite s1, Sprite s2) {
        float xDis = Math.abs((s1.getX() + s1.getWidth()/2) - (s2.getX() + s2.getWidth()/2));
        float yDis = Math.abs((s1.getY() + s1.getWidth()/2) - (s2.getY() + s2.getHeight()/2));

        float avgWidth = (s1.getWidth() + s2.getWidth())/2;
        float avgHeight = (s1.getHeight() + s2.getHeight())/2;

        return xDis < avgWidth && yDis < avgHeight;
    }

    /**
     * Returns which side ball hit brick on
     * @param ball the ball hitting rect2
     * @param rect the one getting hit
     * @return which side on brick was hit by ball
     */
    private Side getCollisionSide(Ball ball, Rectangular rect) {
        float xDis = Math.abs((ball.getX()) - (rect.getX()));
        float yDis = Math.abs((ball.getY()) - (rect.getY()));

        float xDisOld = Math.abs((ball.getOldX()) - (rect.getX())); //ball center - vel

        float avgWidth = (ball.getDiameter() + Brick.WIDTH)/2;
        float avgHeight = (ball.getDiameter() + Brick.HEIGHT)/2;

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
                break;
            case LARGEPADDLE:
                paddle.setState(Paddle.State.LARGE, 8000);
                break;
            case SMALLPADDLE:
                paddle.setState(Paddle.State.SMALL, 8000);
                break;
            case LASER:
                lasers.add(new Laser(brickTexture, paddle.getX()));
                lasers.get(lasers.size()-1).activate();
        }
    }

    private void doBallBrickCollision(Ball ball, Brick brick) {
        Side colSide = getCollisionSide(ball, brick);
        switch(colSide) {
            case LEFT: case RIGHT:
                ball.stepBack();
                ball.reverseX();
                brick.gotHit();
                explosions.add(new Explosion(brick.getX(), brick.getY()));
                if(Math.random() > 0.8) {
                    powerUps.add(new PowerUp(
                            PowerUp.Power.LASER,
                            powerTexture,
                            (int) brick.getX(),
                            (int) brick.getY(),
                            20,
                            20));
                }
                score++;
                ball.updateAngle();
                break;
            case TOP: case BOTTOM:
                ball.stepBack();
                ball.reverseY();
                brick.gotHit();
                explosions.add(new Explosion(brick.getX(), brick.getY()));
                if(Math.random() > 0.8) {
                    powerUps.add(new PowerUp(
                            PowerUp.Power.LASER,
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
        lose();
    }
}
