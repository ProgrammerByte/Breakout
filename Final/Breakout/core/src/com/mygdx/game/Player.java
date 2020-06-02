package com.mygdx.game;

public class Player {
	protected int x, y, width, height, lives;
	
	public Player(GameWindow window) {
		this.width = 100;
		this.height = 15;
		this.x = (window.getXmax() / 2) - (this.width / 2);
		this.y = 25;
		this.lives = 3;
	}
	
	
	
	
	
	public void setLives(int value) {
		lives = value;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setX(int value) {
		x = value;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
