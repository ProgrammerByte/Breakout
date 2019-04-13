package com.mygdx.game;

public class Ball {
	protected int rad;
	protected float x, y, xvel, yvel, maxvel; //maxvel is the total velocity that the ball can experience (Vector sum of velx and vely)
	protected boolean moving, lost;
	
	public Ball(Player player) {
		this.lost = false;
		this.moving = false;
		this.maxvel = 10;
		this.rad = 10;
		this.xvel = 0;
		this.yvel = this.maxvel;
		this.y = player.getY() + player.getHeight() + this.rad;
		this.x = player.getX() + (player.getWidth() / 2);
	}
	
	public void move(Ball ball, Player player, Window window, Block[] blocks) {
		if (ball.getMoving() == false) {
			ball.setX(player.getX() + (player.getWidth() / 2));
		}
		else {
			if (((ball.getX() + ball.getRad() + ball.getXvel() >= window.getXmax()) && ball.getXvel() > 0) || ((ball.getX() - ball.getRad() + ball.getXvel() <= 0) && ball.getXvel() < 0)) {
				ball.setXvel(-ball.getXvel());
			}
			if ((ball.getY() + ball.getRad() + ball.getYvel() >= window.getYmax()) && ball.getYvel() > 0) {
				ball.setYvel(-ball.getYvel());
			}
			else if (ball.getYvel() < 0 && (ball.getY() - ball.getRad() <= player.getY() + player.getHeight()) && (ball.getY() - ball.getRad() >= player.getY()) && ((ball.getX() + ball.getRad() >= player.getX()) && (ball.getX() - ball.getRad() <= player.getX() + player.getWidth()))) {
				//ball.setYvel(-ball.getYvel()); //THIS IS JUST A PLACEHOLDER FOR THE TRIGONOMETRY YET TO COME TO WORK OUT THE DIRECTION OF THE BALL'S MOVEMENT
				float xdistancemax = (player.getWidth() / 2) + ball.getRad(); //The furthest distance the ball could be from the center of the paddle and still bounce off
				float xdistance = ball.getX() - (player.getX() + (player.getWidth() / 2));
				ball.setXvel((xdistance * ball.getMaxvel()) / xdistancemax);
				double yvel = Math.sqrt((ball.getMaxvel() * ball.getMaxvel()) - (ball.getXvel() * ball.getXvel()));
				if (yvel < 1) {
					yvel = 1;
				}
				ball.setYvel((float)yvel);
				//System.out.println(ball.getYvel() + " " + ball.getXvel());
				
			}
			
			for (int i = 0; i < blocks.length; i++) {
				
				if (blocks[i].getBroken() == false) {
					
					if ((ball.getY() - ball.getRad() + ball.getYvel() <= blocks[i].getY() + blocks[i].getHeight()) && (ball.getY() + ball.getRad() + ball.getYvel() >= blocks[i].getY()) && (ball.getX() - ball.getRad() + ball.getXvel() <= blocks[i].getX() + blocks[i].getWidth()) && (ball.getX() + ball.getRad() + ball.getXvel() >= blocks[i].getX())) {
						if (blocks[i].getBreakable() == true) {
							blocks[i].setThreshold(blocks[i].getThreshold() - 1);
							if (blocks[i].getThreshold() <= 0) {
								blocks[i].setBroken(true);
								window.setScore(window.getScore() + 1);
							}
						}
						
						if(blocks[i].getThreshold() > -1 || blocks[i].getBreakable() == false) {
							if ((ball.getY() - ball.getRad() > blocks[i].getY() + blocks[i].getHeight()) && (ball.getY() - ball.getRad() + ball.getYvel() <= blocks[i].getY() + blocks[i].getHeight())) {
								ball.setYvel(-ball.getYvel());
							}
							else if ((ball.getY() + ball.getRad() < blocks[i].getY()) && (ball.getY() + ball.getRad() + ball.getYvel() >= blocks[i].getY())) {
								ball.setYvel(-ball.getYvel());
							}
							
							if ((ball.getX() - ball.getRad() > blocks[i].getX() + blocks[i].getWidth()) && (ball.getX() - ball.getRad() + ball.getXvel() <= blocks[i].getX() + blocks[i].getWidth())) {
								ball.setXvel(-ball.getXvel());
							}
							else if ((ball.getX() + ball.getRad() < blocks[i].getX()) && (ball.getX() + ball.getRad() + ball.getXvel() >= blocks[i].getX())) {
								ball.setXvel(-ball.getXvel());
							}
						}
						
					
						//break;
					}
				}
			}
			
			ball.setX(ball.getX() + ball.getXvel());
			ball.setY(ball.getY() + ball.getYvel());
			
			if (ball.getY() - ball.getRad() <= 0) {
				ball.setLost(true);
			}
			
			
			
		}
	}
	
	
	
	public void setLost(boolean value) {
		lost = value;
	}
	
	public boolean getLost() {
		return lost;
	}
	
	public void setMoving(boolean value) {
		moving = value;
	}
	
	public boolean getMoving() {
		return moving;
	}
	
	public float getMaxvel() {
		return maxvel;
	}
	
	public int getRad() {
		return rad;
	}
	
	public void setXvel(float value) {
		xvel = value;
	}
	
	public float getXvel() {
		return xvel;
	}
	
	public void setYvel(float value) {
		yvel = value;
	}
	
	public float getYvel() {
		return yvel;
	}
	
	public void setY(float value) {
		y = value;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float value) {
		x = value;
	}
	
	public float getX() {
		return x;
	}
	
}
