package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    int waveNum;
    int lives;
    int score;
    Texture brickTexture;
    Paddle paddle;
    BrickPattern bricks;
    List<Ball> balls;

    public Game(Texture brickTexture) {
        this.brickTexture = brickTexture;
        bricks = new BrickPattern(brickTexture);
        bricks.generateGrid(15, 10, 10);
        balls = new ArrayList<Ball>();
        balls.add(new Ball(15, 960, 110, brickTexture));
        paddle = new Paddle(120,20, 100, brickTexture);
        score = 0;
    }

    public void update() {
        bricks.update();
        paddle.update();
        List<Brick> toDelBrick = new ArrayList<Brick>();
        List<Ball> toDelBall = new ArrayList<Ball>();
        for(Ball ball : balls) {
            ball.update();
            if(ball.isDead) toDelBall.add(ball);
            for(Brick brick: bricks.bricks) {
                if(brick.isDead) toDelBrick.add(brick);
                Side colSide = getCollisionSide(ball, brick.getBoundingRectangle());
                switch(colSide) {
                    case LEFT: case RIGHT:
                        ball.y -= ball.yVel;
                        ball.x -= ball.xVel;
                        ball.xVel *= -1;
                        brick.gotHit();
                        score++;
                        break;
                    case TOP: case BOTTOM:
                        ball.x -= ball.xVel;
                        ball.y -= ball.yVel;
                        ball.yVel *= -1;
                        brick.gotHit();
                        score++;
                        break;
                }
            }
            switch(getCollisionSide(ball, paddle.getBoundingRectangle())) {
                case LEFT: case RIGHT:
                    ball.xVel *= -1;
                    ball.x -= ball.xVel;
                    ball.y -= ball.yVel;
                    break;
                case BOTTOM:
                    ball.yVel *= -1;
                    ball.x -= ball.xVel;
                    ball.y -= ball.yVel;
                    break;
                case TOP:
                    float xdif = paddle.getX() + paddle.getWidth()/2 - ball.getX() + ball.radius*2;
                    if(xdif < 0)xdif = 0;
                    if(xdif > paddle.getWidth()) xdif = paddle.getWidth();
                    float angle = (xdif * 160 / paddle.getWidth()) + 10;
                    System.out.println(xdif);
                    ball.x -= ball.xVel;
                    ball.y -= ball.yVel;
                    ball.setDirection((int)angle, 10);

            }

        }
        bricks.removeAll(toDelBrick);
        balls.removeAll(toDelBall);
        if(balls.isEmpty()) {
            int[] i = new int[3];
            System.out.println(i[4]);
        }
    }

    public void draw(BitmapFont bitmapFont, SpriteBatch batch) {
        bricks.draw(batch);
        paddle.draw(batch);
        for(Ball ball : balls) {
            ball.draw(batch);
        }
        bitmapFont.draw(batch, "Score: " + score, 0, BetterBreakout.GAME_HEIGHT);
    }
    public void reset() {
        waveNum = 0;
        balls.clear();
        balls.add(new Ball(10, 960, 110, brickTexture));
        lives = 3;
        paddle = new Paddle(120, 20, 100, new Texture("brick.png"));
    }

    public enum Side {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    /**
     * Returns which side ball hit rect 2 on
     * @param ball the ball hitting rect2
     * @param rect2 the one getting hit
     * @return which side on rect2 was hit by rect1
     */
    public Side getCollisionSide(Ball ball, Rectangle rect2) {
        float xDis = Math.abs((ball.getX() + ball.radius) - (rect2.getX() + rect2.getWidth()/2));
        float yDis = Math.abs((ball.getY() + ball.radius) - (rect2.getY() + rect2.getHeight()/2));

        float xDisOld = Math.abs((ball.getX()-ball.xVel + ball.radius) - (rect2.getX() + rect2.getWidth()/2)); //ball center - vel
        float yDisOld = Math.abs((ball.getY()-ball.yVel + ball.radius) - (rect2.getY() + rect2.getHeight()/2)); //ball center - vel


        float avgWidth = (ball.radius*2 + rect2.width)/2;
        float avgHeight = (ball.radius*2 + rect2.height)/2;

        if(xDis < avgWidth && yDis < avgHeight) {
            if(xDisOld < avgWidth) {
                //top or bottom
                if(ball.yVel > 0) {
                    return Side.BOTTOM;
                }
                return Side.TOP;
            } else {
               if(ball.xVel > 0) {
                   return Side.LEFT;
               }
               return Side.RIGHT;
            }
        }
        return Side.NONE;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
