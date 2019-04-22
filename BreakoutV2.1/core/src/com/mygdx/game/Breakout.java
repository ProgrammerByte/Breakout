package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Breakout extends ApplicationAdapter {
	ShapeRenderer sr;
	SpriteBatch batch;
	BitmapFont font;
	GameWindow window, preview;
	Player player;
	Ball ball;
	Block[] blocks;
	Menu menu;
	LevelSelect levelselect;
	boolean playing = false, firsttime = true, anyleft;
	String state = "Menu", temp;
	String[] items, contents;
	int count, level;
	int[] currenttemp, currentdecompressed, size;
	int[][] nextcurrent;
	
	@Override
	public void create () {
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}
	
	public void Play() {
		if (playing == false && (firsttime == true || window.input(player, ball) == true)) {
			boolean haswon = false;
			if (firsttime == false) {
				if (window.getWinner() == true) {
					if (window.getLocked() == false) {
						if (window.getLevel() >= window.getBlockpos().length - 1) { 
							window.setLevel(0);
						}
						else {
							window.setLevel(window.getLevel() + 1);
						}
					}
					else {
						window.setLevel(level - 1);
					}
					
					haswon = true;
					window.setWinner(false);
					ball = new Ball(player);
				}
			}
			
			playing = true;
			
			if (haswon == false) {
				boolean temp = false;
				if (firsttime == false) {
					temp = window.getIspressed();
				}
				window = new GameWindow();
				if (level != -1) {
					window.setLevel(level - 1);
					window.setLocked(true);
				}
				if (firsttime == false) {
					window.setIspressed(temp);
				}
				player = new Player(window);
				ball = new Ball(player);
				firsttime = false;
			}
			currenttemp = window.getBlockpos()[window.getLevel()];
			if (level == -1) {
				level = window.getLevel() + 1;
				decompress();
				level = -1;
			}
			else {
				decompress();
			}
		}
			
		window.setHeight(Gdx.graphics.getHeight()); //USED FOR MOUSE
		window.setWidth(Gdx.graphics.getWidth()); //USED FOR MOUSE
		
		if (window.getFrame1() == false) {
			window.input(player, ball);
			ball.move(player, window, blocks);
		}
		else {
			window.setFrame1(false);
		}
		
		if (ball.getLost() == true && window.getWinner() == false) {
			player.setLives(player.getLives() - 1);
			if (player.getLives() <= 0) {
				window.setGameover(true);
			}
			ball = new Ball(player);
		}
		
		if (window.getGameover() == false && window.getWinner() == false) {
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.WHITE);
			sr.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		
			sr.circle(ball.getX(), ball.getY(), ball.getRad());
			
			for (int i = 0; i < player.getLives() - 1; i++) {
				sr.circle(((2 * i) * (2 * ball.getRad())) + ball.getRad(), ball.getRad(), ball.getRad());
			}
			
			rendergrid();
			
			if (anyleft == false) {
				window.setWinner(true);
			}
			
			sr.end();
			
			batch.begin();
			font.draw(batch, "FPS: " + (1 / Gdx.graphics.getDeltaTime()), window.getXmax() - 55, 100);
			font.draw(batch, "Score: " + window.getScore(), window.getXmax() - 75, window.getYmax() - 20);
			font.draw(batch, "Level: " + (window.getLevel() + 1), window.getXmax() - 200, window.getYmax() - 20);
			batch.end();
			
		}
		else if (window.getWinner() == true) {
			playing = false;
			batch.begin();
			font.draw(batch, "CONGRATULATIONS!", (window.getXmax() / 2) - 75, window.getYmax() / 2);
			batch.end();
		}
		
		
		
		else {
			playing = false;
			batch.begin();
			font.draw(batch, "GAME OVER!", (window.getXmax() / 2) - 50, window.getYmax() / 2);
			batch.end();
		}
		
		if (window.getActive() == false) {
			state = "Quit";
		}
	}
	
	public void decompress() {
		count = 0;
		for (int i = 0; i < currenttemp.length; i += 2) {
			count += currenttemp[i + 1];
		}
		
		if (level != -1) {
			window.setCurrentColumns(window.getColumns()[level - 1]);
			window.setCurrentRows(window.getRows()[level - 1]);
		}
		currentdecompressed = new int[count];
		nextcurrent = new int[count][3];
		count = 0;
		for (int i = 0; i < currenttemp.length; i += 2) {
			for (int x = 0; x < currenttemp[i + 1]; x++) {
				nextcurrent[count][2] = currenttemp[i]; //THRESHOLD
				nextcurrent[count][0] = (int)(count % window.getCurrentColumns()); //X COORD
				nextcurrent[count][1] = count / window.getCurrentColumns(); //Y COORD
				count += 1;
			}
		}
		
		
		
		window.setCurrent(nextcurrent);
		blocks = window.newBlock(window.getCurrent());
	}
	
	public void rendergrid() {
		anyleft = false;  //Has any breakable blocks been rendered?
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getBroken() == false && blocks[i].getThreshold() != -2) {
				if (blocks[i].getBreakable() == true) {
					anyleft = true;
				}
				
				if (blocks[i].getBreakable() == true) {
					if (blocks[i].getThreshold() == 0) {
						sr.setColor(Color.BROWN);
					}
					
					else {
						switch(blocks[i].getYcoord() % 7) {
							case 1:
								sr.setColor(Color.RED);
								break;
							case 2:
								sr.setColor(Color.ORANGE);
								break;
							case 3:
								sr.setColor(Color.YELLOW);
								break;
							case 4:
								sr.setColor(Color.GREEN);
								break;
							case 5:
								sr.setColor(Color.BLUE);
								break;
							case 6:
								sr.setColor(Color.MAROON);
								break;
							case 0:
								sr.setColor(Color.PURPLE);
								break;
						}
					}
				}
				else {
					sr.setColor(Color.LIGHT_GRAY);
				}
				sr.rect((float)blocks[i].getX(), (float)blocks[i].getY(), (float)blocks[i].getWidth(), (float)blocks[i].getHeight());
				if (blocks[i].getBreakable() == false || blocks[i].getThreshold() > 1) {
					sr.end();
					sr.begin(ShapeType.Line);
					sr.setColor(Color.WHITE);
					int thickness = blocks[i].getThreshold();
					if (thickness > 0) {
						for (int z = 0; z < thickness - 1; z++) {
							if (z * 4 <= blocks[i].getHeight() && z * 4 <= blocks[i].getWidth()) {
								sr.rect((float)(blocks[i].getX() + (z * 2)), (float)(blocks[i].getY() + (z * 2)), (float)(blocks[i].getWidth() - (z * 4)), (float)(blocks[i].getHeight() - (z * 4)));
							}
							else {
								break;
							}
						}
					}
					else {
						sr.rect((float)blocks[i].getX(), (float)blocks[i].getY(), (float)blocks[i].getWidth(), (float)blocks[i].getHeight());
						sr.line((float)blocks[i].getX(), (float)blocks[i].getY(), (float)(blocks[i].getX() + blocks[i].getWidth()), (float)(blocks[i].getY() + blocks[i].getHeight()));
						sr.line((float)blocks[i].getX(), (float)(blocks[i].getY() + blocks[i].getHeight()), (float)(blocks[i].getX() + blocks[i].getWidth()), (float)blocks[i].getY());
					}
					sr.end();
					sr.begin(ShapeType.Filled);
				}
			}
		}
	}
	
	public void Menu() {
		if (firsttime == true) {
			items = new String[]{"Play", "Create", "Controls", "Quit"};
			menu = new Menu("Linear", items);
			firsttime = false;
			level = -1; //A flag to indicate that there is no specified level
		}
		renderMenu("BREAKOUT!");
		
		state = menu.getOption();
		if (state != "Menu") {
			firsttime = true;
		}
	}
	
	public void renderMenu(String text) {
		menu.setHeight(Gdx.graphics.getHeight()); //USED FOR MOUSE
		menu.setWidth(Gdx.graphics.getWidth()); //USED FOR MOUSE
		
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		batch.begin();
		font.draw(batch, text, (menu.getXmax() / 2) - 50, menu.getYmax() - 20);
		
		for (int i = 0; i < menu.getItems().length; i++) {
			font.draw(batch, menu.getItems()[i], menu.getCoords()[i][0] + 20, menu.getCoords()[i][1] - 5);
			sr.rect(menu.getCoords()[i][0], menu.getCoords()[i][1], 100, -20);
		}
		
		batch.end();
		sr.end();
		
		menu.input(100, -20);
	}
	
	public void Create() {
		if (firsttime == true) {
			firsttime = false;
			preview = new GameWindow();
			levelselect = new LevelSelect("Level Select", items, preview);
		}
		levelselect.setHeight(Gdx.graphics.getHeight());
		levelselect.setWidth(Gdx.graphics.getWidth());
		
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		batch.begin();
		for (int i = 0; i < levelselect.getItems().length; i++) {
			sr.rect(levelselect.getCoords()[i][0], levelselect.getCoords()[i][1], 90, 90);
			font.draw(batch, levelselect.getItems()[i], levelselect.getCoords()[i][0] + 20, levelselect.getCoords()[i][1] + 20);
		}
		batch.end();
		sr.end();
		levelselect.input(90, 90);
		state = levelselect.getOption();
		if (state != "Create") {
			firsttime = true;
		}
	}
	
	public void Level() {
		if (state.contains("New")) {
			if (firsttime == true) {
				window = new GameWindow();
				firsttime = false;
			}
			batch.begin();
			font.draw(batch, " Use up and down arrow keys to change height and left and right keys to change width \n"
							+" Press enter to continue to the level editor \n"
							+" \n"
							+" Height: " + window.getCurrentRows() + "\n"
							+" Width:  " + window.getCurrentColumns(), 0, window.getYmax());
			batch.end();
			temp = window.initialInput();
			if (temp != "None") {
				state = temp;
				firsttime = true;
				size = new int[2];
				size[0] = window.getCurrentColumns();
				size[1] = window.getCurrentRows();
			}
		}
		
		
		else {
			if (firsttime == true) {
				items = new String[] {"Play", "Edit", "Delete"};
				menu = new Menu("Linear", items);
				menu.setOption(state);
				firsttime = false;
				level = Integer.parseInt(state.replaceAll("\\D", ""));
			}
			renderMenu(state);
			
			state = menu.getOption();
			if (state.contains("Level") == false) {
				firsttime = true;
			}
		}
	}
	
	public void Edit() {
		if (firsttime == true) {
			firsttime = false;
			window = new GameWindow();
			if (level == -1) { //DEFAULT HEIGHT AND WIDTH IS 6 AND 8 RESPECTIVELY
				window.setCurrentColumns(size[0]);
				window.setCurrentRows(size[1]);
				currenttemp = new int[]{-2, size[0] * size[1]};
			}
			else {
				currenttemp = window.getBlockpos()[level - 1];
			}
			decompress();
		}
		window.setHeight(Gdx.graphics.getHeight()); //USED FOR MOUSE
		window.setWidth(Gdx.graphics.getWidth()); //USED FOR MOUSE
		
		sr.begin(ShapeType.Line);
		sr.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < blocks.length; i++) {
			sr.rect((float)blocks[i].getX(), (float)blocks[i].getY(), (float)blocks[i].getWidth(), (float)blocks[i].getHeight());
		}
		sr.end();
		
		batch.begin();
		if (window.getTips() == true) {
			font.draw(batch, " Use Numkeys to select blocks: \n"
							+" 0 - Weak blocks \n"
							+" 1 to 8 - Normal blocks (number = threshold) \n"
							+" 9 - Unbreakable blocks \n"
							+" \n"
							+" Left Click to place block \n"
							+" Right Click to delete block \n"
							+" \n"
							+" Press S to save your work and return to the previous menu \n"
							+" \n"
							+" Press H to turn off these tips", 0, (window.getYmax() / 2) - 2);
		}
		font.setColor(Color.GREEN);
		if (window.getSelected() == -1) {
			temp = "Unbreakable";
		}
		else if (window.getSelected() == 0) {
			temp = "Weak";
		}
		else {
			temp = "Threshold " + window.getSelected();
		}
		
		font.draw(batch, "Current Selection: " + temp + " block", window.getXmax() / 2, (window.getYmax() / 2) - 2);
		font.setColor(Color.WHITE);
		batch.end();
		
		sr.begin(ShapeType.Filled);
		rendergrid();
		sr.end();
		
		if(window.createInput(blocks, level) == true) {
			state = "Create";
			firsttime = true;
		}
	}
	
	public void Delete() {
		try {
			File file = new File("Levels");
			Scanner scan = new Scanner(file);
			count = 0;
			while(scan.hasNextLine()) {
				count += 1;
				scan.nextLine();
			}
			scan.close();
			
			if (count > 3) {
				contents = new String[count];
				scan = new Scanner(file);
				int i = 0;
				while(scan.hasNextLine()) {
					contents[i] = scan.nextLine();
					i += 1;
				}
				scan.close();
				
				PrintWriter pw = new PrintWriter(file);
				pw.write("");
				for (i = 0; i < contents.length; i++) {
					if ((int)(i / 3) != level - 1) {
						pw.println(contents[i]);
					}
				}
				pw.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		firsttime = true;
		state = "Create";
	}
	
	public void Controls() {
		batch.begin();
		font.draw(batch, " Controls: \n \n"
				+        " Throw ball / Start  = Left Click \n"
				+        " Move paddle         = Move mouse \n"
				+        " Toggle Fullscreen = F \n"
				+        " Quit game             = Q / ESC / END \n"
				+        " Back / Menu         = B / BACKSPACE", 0, menu.getYmax() - 10);
		batch.end();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (state == "Play") {
			Play();
		}
		
		else if (state == "Menu") {
			Menu();
		}
		
		else if (state == "Create") {
			Create();
		}
		
		else if (state.contains("Level")) {
			Level();
		}
		
		else if (state == "Edit") {
			Edit();
		}
		
		else if (state == "Delete") {
			Delete();
		}
		
		else if (state == "Controls") {
			Controls();
		}
		
		else if (state == "Quit") {
			Gdx.app.exit();
		}
		
		
		
		temp = menu.universalInput(state);
		if (temp != "None") {
			state = temp;
			firsttime = true;
			playing = false;
		}
		
		
	}
	
	@Override
	public void dispose () {
	}
}
