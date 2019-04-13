package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;

public class Window {
	protected int height, width, xmax, ymax, level, score;
	protected boolean gameover, active, ispressed, winner, frame1;
	protected int[] columns = {8, 8},
					rows =    {6, 6};
	protected int[][] current;
	protected int[][][] blockpos = { //Is a 3 dimensional array as there is an array for each level and inside are multiple arrays containing coordinates for where the blocks are going to be
									{{0, 2, 1}, {1, 2, 1}, {2, 2, 1}, {3, 2, 1}, {4, 2, 1}, {5, 2, 1}, {6, 2, 1}, {7, 2, 1}, {0, 5, 1}, {1, 5, 1}, {2, 5, 1}, {3, 5, 1}, {4, 5, 1}, {5, 5, 1}, {6, 5, 1}, {7, 5, 1}, {0, 3, 1}, {1, 3, 1}, {2, 3, 1}, {3, 3, 1}, {4, 3, 1}, {5, 3, 1}, {6, 3, 1}, {7, 3, 1}, {0, 4, 1}, {1, 4, 1}, {2, 4, 1}, {3, 4, 1}, {4, 4, 1}, {5, 4, 1}, {6, 4, 1}, {7, 4, 1}},  //Each row is a different level and contains a different set of coordinates positive y is downwards, positive x is towards the right
									{{0, 0, -1}, {1, 0, -1}, {2, 0, -1}, {3, 0, -1}, {4, 0, -1}, {5, 0, -1}, {6, 0, -1}, {7, 0, -1}, {0, 1, -1}, {1, 1, 5}, {2, 1, 5}, {3, 1, 5}, {4, 1, 5}, {5, 1, 5}, {6, 1, 5}, {7, 1, -1}, {0, 2, 1}, {1, 2, 0}, {2, 2, 0}, {3, 2, 0}, {4, 2, 0}, {5, 2, 0}, {6, 2, 0}, {7, 2, 1}}
									}; // {{x, y, threshold}}
	
	
	public Window() {
		this.score = 0;
		this.level = 0;
		this.xmax = 640; //MAX X-COORDINATE FOR RENDERED SHAPES
		this.ymax = 480; //MAX Y-COORDINATE FOR RENDERED SHAPES
		this.gameover = false;
		this.active = true;
		this.ispressed = false;
		this.winner = false;
		this.frame1 = true;
	}
	
	
	public Block[] newBlock(int[][] current, Window window) {
		Block[] newlist = new Block[current.length];
		for (int i = 0; i < current.length; i++) {
			boolean breakable = true;
			if (current[i][2] < 0) {
				breakable = false;
			}
			newlist[i] = new Block(current[i][0], current[i][1] + 1, current[i][2], breakable, window);
		}
		return newlist;
	}
	
	
	
	
	
	public int[] getRows() {
		return rows;
	}
	
	public int[] getColumns() {
		return columns;
	}
	
	
	public void setScore(int value) {
		score = value;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setFrame1(boolean value) {
		frame1 = value;
	}
	
	public boolean getFrame1() {
		return frame1;
	}
	
	public void setWinner(boolean value) {
		winner = value;
	}
	
	public boolean getWinner() {
		return winner;
	}
	
	public void setCurrent(int[][] value) {
		current = value;
	}
	
	public int[][] getCurrent() {
		return current;
	}
	
	public void setLevel(int value) {
		level = value;
	}
	
	public int getLevel() {
		return level;
	}
	
	
	public int[][][] getBlockpos() {
		return blockpos;
	}
	
	public void setIspressed(boolean value) {
		ispressed = value;
	}
	
	public boolean getIspressed() {
		return ispressed;
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
	
	public void setGameover(boolean value) {
		gameover = value;
	}
	
	public boolean getGameover() {
		return gameover;
	}
	
	public void setActive(boolean value) {
		active = value;
	}
	
	public boolean getActive() {
		return active;
	}
}
