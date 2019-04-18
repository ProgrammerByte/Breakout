package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameWindow extends Frame {
	protected int level, score, selected, currentRows, currentColumns;
	protected boolean gameover, active, winner, frame1, locked, editing, tips, isSpressed; //Whether the level is locked and cannot change
	protected int[] columns, rows;
	protected int[][] blockpos, current;
	
	
	public GameWindow() {
		this.isSpressed = false;
		this.currentRows = 6;
		this.currentColumns = 8;
		this.tips = true;
		this.selected = 1; //Only used for editing
		this.score = 0;
		this.level = 0;
		this.gameover = false;
		this.active = true;
		this.winner = false;
		this.frame1 = true;
		this.locked = false;
		this.editing = false;
		
		try {
			File file = new File("Levels");
			
			Scanner scan = new Scanner(file);
			int count = 0;
			int maxlength = 0;
			int lengthtemp;
			
			while(scan.hasNextLine()) {
				count += 1;
				if ((lengthtemp = scan.nextLine().split(" ").length) > maxlength) {
					maxlength = lengthtemp;
				}
			}
			
			count = count / 3;
			this.columns = new int[count];
			this.rows = new int[count];
			this.blockpos = new int[count][];
			
			scan.close();
			String[] temp;
			scan = new Scanner(file);
			for (int i = 0; i < count; i ++) {
				this.rows[i] = Integer.parseInt(scan.nextLine());
				this.columns[i] = Integer.parseInt(scan.nextLine());
				temp = scan.nextLine().split(" ");
				this.blockpos[i] = new int[temp.length];
				for (int x = 0; x < temp.length; x++) {
					this.blockpos[i][x] = Integer.parseInt(temp[x]);
				}
			}
			scan.close();
		
			
		} catch (Exception FileNotFoundException) {
			FileNotFoundException.printStackTrace();
		}
	}
	
	public boolean input(Player player, Ball ball) { //Only used in game
		boolean ispressed = false;
		
		player.setX(((this.getXmax() * Gdx.input.getX() ) / this.getWidth()) - (player.getWidth() / 2)); //This line converts the mouse position into the coordinates used for shapes.
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (this.getIspressed() == false) {
				ball.setMoving(true);
				this.setIspressed(true);
				ispressed = true;
			}
		}
		else {
			this.setIspressed(false);
		}
		
		
		return ispressed;
	}
	
	public String initialInput() { //Changes the height and width before a new level is created
		String state = "None";
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (this.getIspressed() == false) {
				this.setIspressed(true);
				
				if (Gdx.input.isKeyPressed(Keys.UP) && this.getCurrentRows() < 20) {
					this.setCurrentRows(this.getCurrentRows() + 1);
				}
				else if (Gdx.input.isKeyPressed(Keys.DOWN) && this.getCurrentRows() > 1) {
					this.setCurrentRows(this.getCurrentRows() - 1);
				}
				else if (Gdx.input.isKeyPressed(Keys.RIGHT) && this.getCurrentColumns() < 40) {
					this.setCurrentColumns(this.getCurrentColumns() + 1);
				}
				else if (Gdx.input.isKeyPressed(Keys.LEFT) && this.getCurrentColumns() > 1) {
					this.setCurrentColumns(this.getCurrentColumns() - 1);
				}
				
			}
		}
		else {
			this.setIspressed(false);
		}
		
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			state = "Edit";
		}
		return state;
	}
	
	public boolean createInput(Block[] blocks, int level) { //Only used for editing
		boolean finished = false;
		if (Gdx.input.isKeyPressed(Keys.H)) {
			if (this.getIspressed() == false) {
				this.setIspressed(true);
				if (this.getTips() == true) {
					this.setTips(false);
				}
				else {
					this.setTips(true);
				}
			}
		}
		else {
			this.setIspressed(false);
		}
		
		
		if (Gdx.input.isKeyPressed(Keys.NUM_0)) {
			this.setSelected(0); //Weak block
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			this.setSelected(1); //Normal block
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			this.setSelected(2); //Stronger blocks
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			this.setSelected(3);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			this.setSelected(4);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
			this.setSelected(5);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
			this.setSelected(6);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_7)) {
			this.setSelected(7);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
			this.setSelected(8);
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_9)) {
			this.setSelected(-1); //Unbreakable block
		}
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT) || Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			int current = -1;
			float xclick = (Gdx.input.getX()  * this.getXmax()) / this.getWidth();
			float yclick = ((this.getHeight() - Gdx.input.getY()) * this.getYmax()) / this.getHeight(); //Converts mouse position to coordinate
			
			for (int i = 0; i < blocks.length; i++) {
				if (blocks[i].getX() <= xclick && (blocks[i].getX() + blocks[i].getWidth() >= xclick)  && (blocks[i].getY() <= yclick && blocks[i].getY() + blocks[i].getHeight() >= yclick)) {
					current = i;
					break;
				}
			}
			if (current != -1) {
				if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
					if (this.getFrame1() == false) {
						blocks[current].setThreshold(this.getSelected());
						if (this.getSelected() == -1) {
							blocks[current].setBreakable(false);
						}
						else {
							blocks[current].setBreakable(true);
						}
					}
				}
				else {
					blocks[current].setThreshold(-2);
					blocks[current].setBreakable(false);
				}
			}
		}
		else if (this.getFrame1() == true){
			this.setFrame1(false);
		}
		
		if (Gdx.input.isKeyPressed(Keys.S)) {
			finished = true;
			if (this.getIsSpressed() == false) {
				this.setIsSpressed(true);
				try {
					boolean New = false;
					if (level == -1) {
						New = true;
					}
					File file = new File("Levels");
					Scanner scan = new Scanner(file);
					int count = 0;
					while (scan.hasNextLine()) {
						count += 1;
						scan.nextLine();
					}
					if (New == true) {
						count += 3;
					}
					scan.close();
					scan = new Scanner(file);
					String[] contents = new String[count];
					
					int i = 0;
					while (scan.hasNextLine()) {
						contents[i] = scan.nextLine();
						i += 1;
					}
					scan.close();
					
					String[] temp = new String[0];
					String[] temptemp;
					boolean ended;
					int recurring = 1;
					for (i = 0; i < blocks.length; i++) {
						ended = false;
						if (i < blocks.length - 1) {
							if (blocks[i].getThreshold() == blocks[i + 1].getThreshold()) {
								recurring += 1;
							}
							else {
								ended = true;
							}
						}
						else {
							ended = true;
						}
						
						if (ended == true) {
							temptemp = temp.clone();
							temp = new String[temp.length + 2];
							for (int x = 0; x < temptemp.length; x++) {
								temp[x] = temptemp[x];
							}
							temp[temp.length - 1] = Integer.toString(recurring);
							recurring = 1;
							temp[temp.length - 2] = Integer.toString(blocks[i].getThreshold());
						}
					}
					
					String tempString = new String();
					for (i = 0; i < temp.length; i++) {
						if (i == 0) {
							tempString = temp[i];
						}
						else {
							tempString = tempString + " " + temp[i];
						}
					}
					
					if (New == true) {
						contents[contents.length - 3] = Integer.toString(this.getCurrentRows());
						contents[contents.length - 2] = Integer.toString(this.getCurrentColumns());
						contents[contents.length - 1] = tempString;
					}
					else {
						int index = (level - 1) * 3;
						contents[index] = Integer.toString(this.getCurrentRows());
						contents[index + 1] = Integer.toString(this.getCurrentColumns());
						contents[index + 2] = tempString;
					}
					
					
					PrintWriter pw = new PrintWriter("Levels");
					pw.write("");
					for (i = 0; i < contents.length; i++) {
						pw.println(contents[i]);
					}
					
					pw.close();
					
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			this.setIsSpressed(false);
		}
		return finished;
	}
	
	
	public Block[] newBlock(int[][] current) {
		Block[] newlist = new Block[current.length];
		for (int i = 0; i < current.length; i++) {
			boolean breakable = true;
			if (current[i][2] == -1) {
				breakable = false;
			}
			newlist[i] = new Block(current[i][0], current[i][1] + 1, current[i][2], breakable, this);
		}
		return newlist;
	}
	
	
	
	
	
	public void setIsSpressed(boolean value) {
		isSpressed = value;
	}
	
	public boolean getIsSpressed() {
		return isSpressed;
	}
	
	public void setCurrentColumns(int value) {
		currentColumns = value;
	}
	
	public int getCurrentColumns() {
		return currentColumns;
	}
	
	public void setCurrentRows(int value) {
		currentRows = value;
	}
	
	public int getCurrentRows() {
		return currentRows;
	}
	
	public void setTips(boolean value) {
		tips = value;
	}
	
	public boolean getTips() {
		return tips;
	}
	
	public void setSelected(int value) {
		selected = value;
	}
	
	public int getSelected() {
		return selected;
	}
	
	public void setEditing(boolean value) {
		editing = value;
	}
	
	public boolean getEditing() {
		return editing;
	}
	
	public void setLocked(boolean value) {
		locked = value;
	}
	
	public boolean getLocked() {
		return locked;
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
	
	
	public int[][] getBlockpos() {
		return blockpos;
	}
	
	public void setIspressed(boolean value) {
		ispressed = value;
	}
	
	public boolean getIspressed() {
		return ispressed;
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
