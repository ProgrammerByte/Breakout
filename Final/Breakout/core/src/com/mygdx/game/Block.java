package com.mygdx.game;

public class Block {
	protected double x, y, width, height;
	protected int threshold, xcoord, ycoord; //xcoord and ycoord are copies of the ones from window
	protected boolean breakable, broken;
	
	public Block(int xcoord, int ycoord, int threshold, boolean breakable, GameWindow window) {
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.x = xcoord * (double)window.getXmax() / window.getCurrentColumns();
		this.y = window.getYmax() - (ycoord * ((double)window.getYmax() / (2 * window.getCurrentRows())));
		this.width = (double)window.getXmax() / window.getCurrentColumns();
		this.height = (double)window.getYmax() / (2 * window.getCurrentRows());
		this.threshold = threshold; //How many hits it takes until the block breaks
		this.breakable = breakable;
		this.broken = false;
	}
	
	
	
	
	
	public int getXcoord() {
		return xcoord;
	}
	
	public int getYcoord() {
		return ycoord;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setThreshold(int value) {
		threshold = value;
	}
	
	public int getThreshold() {
		return threshold;
	}
	
	public void setBreakable(boolean value) {
		breakable = value;
	}
	
	public boolean getBreakable() {
		return breakable;
	}
	
	public void setBroken(boolean value) {
		broken = value;
	}
	
	public boolean getBroken() {
		return broken;
	}
}
