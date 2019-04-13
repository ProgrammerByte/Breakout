package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input.Buttons;

public class Breakout extends ApplicationAdapter {
	ShapeRenderer sr;
	SpriteBatch batch;
	BitmapFont font;
	Window window;
	Player player;
	Ball ball;
	Block[] blocks;
	boolean playing = false, firsttime = true, anyleft;
	
	@Override
	public void create () {
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (playing == false && (firsttime == true || player.input(player, window, ball) == true)) {
			boolean haswon = false;
			if (firsttime == false) {
				if (window.getWinner() == true) {
					if (window.getLevel() >= window.getBlockpos().length - 1) { 
						window.setLevel(0);
					}
					else {
						window.setLevel(window.getLevel() + 1);
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
				window = new Window();
				if (firsttime == false) {
					window.setIspressed(temp);
				}
				player = new Player(window);
				ball = new Ball(player);
				firsttime = false;
			}
			
			window.setCurrent(window.getBlockpos()[window.getLevel()]);
			blocks = window.newBlock(window.getCurrent(), window);
		}
		
		window.setHeight(Gdx.graphics.getHeight()); //USED FOR MOUSE
		window.setWidth(Gdx.graphics.getWidth()); //USED FOR MOUSE
		
		if (window.getFrame1() == false) {
			player.input(player, window, ball);
			ball.move(ball, player, window, blocks);
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
			
			anyleft = false;  //Has any breakable blocks been rendered?
			for (int i = 0; i < blocks.length; i++) {
				if (blocks[i].getBroken() == false) {
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
					sr.rect(blocks[i].getX(), blocks[i].getY(), blocks[i].getWidth(), blocks[i].getHeight());
					if (blocks[i].getBreakable() == false || blocks[i].getThreshold() > 1) {
						sr.end();
						sr.begin(ShapeType.Line);
						sr.setColor(Color.WHITE);
						int thickness = blocks[i].getThreshold();
						if (thickness > 0) {
							for (int z = 0; z < thickness - 1; z++) {
								sr.rect(blocks[i].getX() + (z * 2), blocks[i].getY() + (z * 2), blocks[i].getWidth() - (z * 4), blocks[i].getHeight() - (z * 4));
							}
						}
						else {
							sr.rect(blocks[i].getX(), blocks[i].getY(), blocks[i].getWidth(), blocks[i].getHeight());
							sr.line(blocks[i].getX(), blocks[i].getY(), blocks[i].getX() + blocks[i].getWidth(), blocks[i].getY() + blocks[i].getHeight());
							sr.line(blocks[i].getX(), blocks[i].getY() + blocks[i].getHeight(), blocks[i].getX() + blocks[i].getWidth(), blocks[i].getY());
						}
						sr.end();
						sr.begin(ShapeType.Filled);
					}
					//System.out.println(blocks[i].getX() + " " + blocks[i].getY() + " " + blocks[i].getWidth() + " " + blocks[i].getHeight());
				}
			}
			
			if (anyleft == false) {
				window.setWinner(true);
			}
			
			sr.end();
			
			batch.begin();
			//font.setColor(Color.WHITE());
			font.draw(batch, "FPS: " + (1 / Gdx.graphics.getDeltaTime()), window.getXmax() - 55, 100);
			font.draw(batch, "Score: " + window.getScore(), window.getXmax() - 75, window.getYmax() - 20);
			font.draw(batch, "Level: " + (window.getLevel() + 1), window.getXmax() - 200, window.getYmax() - 20);
			batch.end();
			
		}
		else if (window.getWinner() == true) {
			playing = false;
			batch.begin();
			//font.setColor(Color.WHITE);
			font.draw(batch, "CONGRATULATIONS!", (window.getXmax() / 2) - 75, window.getYmax() / 2);
			batch.end();
		}
		
		
		
		else {
			playing = false;
			batch.begin();
			//font.setColor(Color.WHITE);
			font.draw(batch, "GAME OVER!", (window.getXmax() / 2) - 50, window.getYmax() / 2);
			batch.end();
		}
		
		
		
		
		
		
		//System.out.println(1 / Gdx.graphics.getDeltaTime()); //Framerate
		
		if (window.getActive() == false) {
			Gdx.app.exit();
		}
		
		
	}
	
	@Override
	public void dispose () {
	}
}
