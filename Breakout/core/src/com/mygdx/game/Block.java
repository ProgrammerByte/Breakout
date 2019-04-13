package com.mygdx.game;

public class Block {
	protected float x, y, width, height;
	protected int threshold, xcoord, ycoord; //xcoord and ycoord are copies of the ones from window
	protected boolean breakable, broken;
	
	public Block(int xcoord, int ycoord, int threshold, boolean breakable, Window window) {
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.x = xcoord * (window.getXmax() / window.getColumns()[window.getLevel()]); //8 is the total width of the game board which may later be changed so that the width can be any natural number
		this.y = window.getYmax() - (ycoord * (window.getYmax() / window.getRows()[window.getLevel()]) / 2);
		this.width = window.getXmax() / window.getColumns()[window.getLevel()];
		this.height = (window.getYmax() / window.getRows()[window.getLevel()]) / 2;
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
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setThreshold(int value) {
		threshold = value;
	}
	
	public int getThreshold() {
		return threshold;
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
