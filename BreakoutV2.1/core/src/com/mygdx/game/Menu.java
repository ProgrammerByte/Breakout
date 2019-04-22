package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;


public class Menu extends Frame{
	protected String option;
	protected String[] items;
	protected int[][] coords; //The bottom left corner of the "button";
	
	public Menu(String type, String[] items) {
		if (type == "Linear") { //A way of ignoring the constructor if the menu created is not linear
			this.option = "Menu";
			this.items = items; //new String[]{"Play", "Create", "Controls", "Quit"};
			this.coords = new int[this.items.length][2];
			for (int i = 0; i < this.items.length; i++) {
				this.coords[i][0] = (this.getXmax() / 2) - 50;
				this.coords[i][1] = (this.getYmax() - 200 - (i * 30));
			}
		}
	}
	
	public void input(int x, int y) {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (this.getIspressed() == false) {
				this.setIspressed(true);
				float xclick = (Gdx.input.getX()  * this.getXmax()) / this.getWidth();
				float yclick = ((this.getHeight() - Gdx.input.getY()) * this.getYmax()) / this.getHeight();
				for (int i = 0; i < this.getCoords().length; i++) {
					if (this.getCoords()[i][0] <= xclick && (this.getCoords()[i][0] + x >= xclick)) {
						if ((y <= 0 && this.getCoords()[i][1] + y <= yclick && this.getCoords()[i][1] >= yclick) || (y >= 0 && this.getCoords()[i][1] + y >= yclick && this.getCoords()[i][1] <= yclick)) {
							this.setOption(this.getItems()[i]);
						}
					}
				}
			}
		}
		else {
			this.setIspressed(false);
		}
	}
	
	
	
	
	public void setOption(String value) {
		option = value;
	}
	
	public String getOption() {
		return option;
	}
	
	public void setItems(String[] value) {
		items = value;
	}
	
	public String[] getItems() {
		return items;
	}
	
	public void setCoords(int[][] value) {
		coords = value;
	}
	
	public int[][] getCoords() {
		return coords;
	}
}
