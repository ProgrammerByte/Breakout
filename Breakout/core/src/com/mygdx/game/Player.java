package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public class Player {
	protected int x, y, width, height, lives;
	
	public Player(Window window) {
		this.width = 100;
		this.height = 15;
		this.x = (window.getXmax() / 2) - (this.width / 2);
		this.y = 25;
		this.lives = 3;
	}
	
	public boolean input(Player player, Window window, Ball ball) {
		boolean ispressed = false;
		player.setX(((window.getXmax() * Gdx.input.getX() ) / window.getWidth()) - (player.getWidth() / 2)); //This line converts the mouse position into the coordinates used for shapes.
		
		if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.ESCAPE) || Gdx.input.isKeyPressed(Keys.END)) {
			window.setActive(false);
		}
		
		else if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (window.getIspressed() == false) {
				ball.setMoving(true);
				window.setIspressed(true);
				ispressed = true;
			}
		}
		else {
			window.setIspressed(false);
		}
		return ispressed;
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
