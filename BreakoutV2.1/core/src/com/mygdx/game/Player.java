package com.mygdx.game;

public class Player {
	protected int width, height, lives;
	protected float x, y, xvel;
	
	public Player(GameWindow window) {
		this.width = 100;
		this.height = 15;
		this.x = (window.getXmax() / 2) - (this.width / 2);
		this.y = 25;
		this.xvel = 0;
		this.lives = 3;
	}
	
	
	
	
	public void setXvel(float value) {
		xvel = value;
	}
	
	public float getXvel() {
		return xvel;
	}
	
	public void setLives(int value) {
		lives = value;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setX(float value) {
		x = value;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
