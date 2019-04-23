package com.mygdx.game;

import java.util.Random;

public class Organism {
	protected float[] moves;
	protected int movenum, fitness;
	protected boolean alive;
	protected Player player;
	protected Ball ball;
	protected Block[] blocks;
	
	public Organism(int generation, float[] nextmoves, GameWindow window) {
		this.player = new Player(window);
		this.ball = new Ball(this.player);
		this.blocks = new Block[10]; //DEFINE THIS LATER AS A PART OF THE MAIN PROGRAM
		
		this.alive = true;
		this.movenum = 0;
		this.fitness = 0;
		this.moves = new float[7200]; //Either the left / right velocity or the left / right acceleration
		Random rand = new Random();
		if (generation == 0) {
			for (int i = 0; i < moves.length; i++) {
				this.moves[i] = rand.nextFloat() - rand.nextFloat();
			}
		}
		
		else {
			double mutationrate = 0.01;
			double choice;
			for (int i = 0; i < moves.length; i++) {
				choice = rand.nextDouble();
				if (choice > mutationrate) {
					this.moves[i] = nextmoves[i];
				}
				else {
					this.moves[i] = rand.nextFloat() - rand.nextFloat();
				}
			}
		}
	}
	
	public void update() {
		if (this.getMovenum() < this.getMoves().length) {
			float next = this.getMoves()[this.getMovenum()];
			this.player.setXvel(this.player.getXvel() + next);
			this.player.setX(this.player.getX() + this.player.getXvel());
			this.setMovenum(this.getMovenum() + 1);
		}
		else {
			this.setAlive(false);
		}
	}
	
	
	
	
	
	public void setAlive(boolean value) {
		alive = value;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public void setMoves(float[] value) {
		moves = value;
	}
	
	public float[] getMoves() {
		return moves;
	}
	
	public void setMovenum(int value) {
		movenum = value;
	}
	
	public int getMovenum() {
		return movenum;
	}
	
	public void setFitness(int value) {
		fitness = value;
	}
	
	public int getFitness() {
		return fitness;
	}
}
