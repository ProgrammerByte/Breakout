package com.mygdx.game;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public class Frame { //All in game screens will be based upon this class
	protected int height, width, xmax, ymax;
	boolean ispressed;
	
	Frame() {
		this.xmax = 640; //MAX X-COORDINATE FOR RENDERED SHAPES
		this.ymax = 480; //MAX Y-COORDINATE FOR RENDERED SHAPES
		this.ispressed = true;
	}
	
	public String universalInput(String state) {
		String newstate = "None";
		if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.ESCAPE) || Gdx.input.isKeyPressed(Keys.END)) {
			newstate = "Quit";
		}
		else if (Gdx.input.isKeyPressed(Keys.B) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) { //The "Going back keys"
			newstate = "Menu";
		}
		
		if (Gdx.input.isKeyPressed(Keys.F)) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(640, 480);
			}
			else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		}
		
		return newstate;
	}
	
	
	
	
	
	public int getYmax() {
		return ymax;
	}
	
	public int getXmax() {
		return xmax;
	}
	
	public void setHeight(int value) {
		height = value;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int value) {
		width = value;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setIspressed(boolean value) {
		ispressed = value;
	}
	
	public boolean getIspressed() {
		return ispressed;
	}
}
