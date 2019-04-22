package com.mygdx.game;

public class Ball {
	protected int rad;
	protected float x, y, xvel, yvel, maxvel; //maxvel is the total velocity that the this.can experience (Vector sum of velx and vely)
	protected boolean moving, lost;
	
	public Ball(Player player) {
		this.lost = false;
		this.moving = false;
		this.maxvel = 10;
		this.rad = 10;
		this.xvel = 0;
		this.yvel = this.maxvel;
		this.y = player.getY() + player.getHeight() + this.rad;
		this.x = player.getX() + (player.getWidth() / 2);
	}
	
	public void move(Player player, GameWindow window, Block[] blocks) {
		if (this.getMoving() == false) {
			this.setX(player.getX() + (player.getWidth() / 2));
		}
		else {
			if (((this.getX() + this.getRad() + this.getXvel() >= window.getXmax()) && this.getXvel() > 0) || ((this.getX() - this.getRad() + this.getXvel() <= 0) && this.getXvel() < 0)) {
				this.setXvel(-this.getXvel());
			}
			if ((this.getY() + this.getRad() + this.getYvel() >= window.getYmax()) && this.getYvel() > 0) {
				this.setYvel(-this.getYvel());
			}
			else if (this.getYvel() < 0 && (this.getY() - this.getRad() <= player.getY() + player.getHeight()) && (this.getY() - this.getRad() >= player.getY()) && ((this.getX() + this.getRad() >= player.getX()) && (this.getX() - this.getRad() <= player.getX() + player.getWidth()))) {
				float xdistancemax = (player.getWidth() / 2) + this.getRad(); //The furthest distance the this.could be from the center of the paddle and still bounce off
				float xdistance = this.getX() - (player.getX() + (player.getWidth() / 2));
				this.setXvel((xdistance * this.getMaxvel()) / xdistancemax);
				double yvel = Math.sqrt((this.getMaxvel() * this.getMaxvel()) - (this.getXvel() * this.getXvel()));
				if (yvel < 1) {
					yvel = 1;
				}
				this.setYvel((float)yvel);
				
			}
			
			boolean xchange = false;
			boolean ychange = false;
			
			for (int i = 0; i < blocks.length; i++) {
				
				if (blocks[i].getBroken() == false && blocks[i].getThreshold() != -2) {
					
					if ((this.getY() - this.getRad() + this.getYvel() <= blocks[i].getY() + blocks[i].getHeight()) && (this.getY() + this.getRad() + this.getYvel() >= blocks[i].getY()) && (this.getX() - this.getRad() + this.getXvel() <= blocks[i].getX() + blocks[i].getWidth()) && (this.getX() + this.getRad() + this.getXvel() >= blocks[i].getX())) {
						if (blocks[i].getBreakable() == true) {
							blocks[i].setThreshold(blocks[i].getThreshold() - 1);
							if (blocks[i].getThreshold() <= 0) {
								blocks[i].setBroken(true);
								window.setScore(window.getScore() + 1);
							}
						}
						
						if(blocks[i].getThreshold() > -1 || blocks[i].getBreakable() == false) {
							if ((this.getY() - this.getRad() > blocks[i].getY() + blocks[i].getHeight()) && (this.getY() - this.getRad() + this.getYvel() <= blocks[i].getY() + blocks[i].getHeight())) {
								if (i - window.getColumns()[window.getLevel()] >= 0) {
									if (blocks[i - window.getColumns()[window.getLevel()]].getBroken() == true || blocks[i - window.getColumns()[window.getLevel()]].getThreshold() == -2 || blocks[i - window.getColumns()[window.getLevel()]].getThreshold() == 0) {
										//this.setYvel(-this.getYvel()); //TOP EDGE OF BLOCK    VERIFICATION SEEMINGLY IGNORED HERE
										ychange = true;
									}
								}
								else {
									ychange = true;
								}
							}
							
							else if ((this.getY() + this.getRad() < blocks[i].getY()) && (this.getY() + this.getRad() + this.getYvel() >= blocks[i].getY())) {
								if (i + window.getColumns()[window.getLevel()] < blocks.length) {
									if (blocks[i + window.getColumns()[window.getLevel()]].getBroken() == true || blocks[i + window.getColumns()[window.getLevel()]].getThreshold() == -2 || blocks[i + window.getColumns()[window.getLevel()]].getThreshold() == 0) {
										//this.setYvel(-this.getYvel()); //BOTTOM EDGE OF BLOCK
										ychange = true;
									}
								}
								else {
									ychange = true;
								}
							}
							
							if ((this.getX() - this.getRad() > blocks[i].getX() + blocks[i].getWidth()) && (this.getX() - this.getRad() + this.getXvel() <= blocks[i].getX() + blocks[i].getWidth())) {
								if ((i + 1) % window.getColumns()[window.getLevel()] != 0 && i + 1 < blocks.length) {
									if (blocks[i + 1].getBroken() == true || blocks[i + 1].getThreshold() == -2 || blocks[i + 1].getThreshold() == 0) {
										//this.setXvel(-this.getXvel()); //RIGHT EDGE OF BLOCK
										xchange = true;
									}
								}
								else {
									xchange = true;
								}
							}
							else if ((this.getX() + this.getRad() < blocks[i].getX()) && (this.getX() + this.getRad() + this.getXvel() >= blocks[i].getX())) {
								if ((i - 1) % window.getColumns()[window.getLevel()] != 0 && i - 1 >= 0) {
									if (blocks[i - 1].getBroken() == true || blocks[i - 1].getThreshold() == -2 || blocks[i - 1].getThreshold() == 0) {
										//this.setXvel(-this.getXvel()); //LEFT EDGE OF BLOCK   VERIFICATION SEEMINGLY IGNORED HERE
										xchange = true;
									}
								}
								else {
									xchange = true;
								}
							}
						}
					}
				}
			}
			
			if (xchange == true) {
				this.setXvel(-this.getXvel());
			}
			if (ychange == true) {
				this.setYvel(-this.getYvel());
			}
			
			this.setX(this.getX() + this.getXvel());
			this.setY(this.getY() + this.getYvel());
			
			if (this.getY() - this.getRad() <= 0) {
				this.setLost(true);
			}
			
			
			
		}
	}
	
	
	
	public void setLost(boolean value) {
		lost = value;
	}
	
	public boolean getLost() {
		return lost;
	}
	
	public void setMoving(boolean value) {
		moving = value;
	}
	
	public boolean getMoving() {
		return moving;
	}
	
	public float getMaxvel() {
		return maxvel;
	}
	
	public int getRad() {
		return rad;
	}
	
	public void setXvel(float value) {
		xvel = value;
	}
	
	public float getXvel() {
		return xvel;
	}
	
	public void setYvel(float value) {
		yvel = value;
	}
	
	public float getYvel() {
		return yvel;
	}
	
	public void setY(float value) {
		y = value;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float value) {
		x = value;
	}
	
	public float getX() {
		return x;
	}
	
}
