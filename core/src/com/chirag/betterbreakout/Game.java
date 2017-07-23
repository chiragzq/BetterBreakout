package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    int waveNum;
    int lives;
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
    }

    public void update() {
        bricks.update();
        paddle.update();
        List<Brick> toDel = new ArrayList<Brick>();
        for(Ball ball : balls) {
            ball.update();
            for(Brick brick: bricks.bricks) {
                Side colSide = getCollisionSide(ball, brick.getBoundingRectangle());
                switch(colSide) {
                    case LEFT: case RIGHT:
                        ball.xVel *= -1;
                        toDel.add(brick);
                        break;
                    case TOP: case BOTTOM:
                        ball.yVel *= -1;
                        toDel.add(brick);
                        break;
                }
            }
            switch(getCollisionSide(ball, paddle.getBoundingRectangle())) {
                case LEFT: case RIGHT:
                    ball.xVel *= -1;
                    break;
                case TOP: case BOTTOM:
                    ball.yVel *= -1;
                    break;
            }
        }
        for(Brick b : toDel) {
            bricks.remove(b);
        }
    }

    public void draw(SpriteBatch batch) {
        bricks.draw(batch);
        paddle.draw(batch);
        for(Ball ball : balls) {
            ball.draw(batch);
        }
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
        float xDis = Math.abs(ball.getX() + ball.getWidth()/2 - rect2.getX() + rect2.getWidth()/2);
        float yDis = Math.abs(ball.getY() + ball.getHeight()/2 - rect2.getY() + rect2.getHeight()/2);

        float xDisOld = Math.abs(ball.getX()-ball.xVel + ball.getWidth()/2 - rect2.getX() + rect2.getWidth()/2); //ball center - vel

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
}
