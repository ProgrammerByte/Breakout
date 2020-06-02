package com.mygdx.game;

public class LevelSelect extends Menu {
	public LevelSelect(String type, String[] items, GameWindow preview) {
		super(type, items);
		this.setOption("Create");
		String[] temp = new String[preview.getBlockpos().length + 1];
		
		for (int i = 0; i < temp.length - 1; i++) {
			temp[i] = "Level " + (int)(i + 1);
		}
		
		temp[temp.length - 1] = "New Level";
		this.setItems(temp);
		
		int[][] coordstemp = new int[temp.length][2];
		for (int i = 0; i < temp.length; i++) {
			coordstemp[i][0] = ((i * (preview.getXmax() / 5)) % (preview.getXmax())) + 20;
			coordstemp[i][1] = preview.getYmax() - ((int)(((i * (preview.getXmax() / 5)) + 20) / preview.getXmax()) * 120) - 100;
		}
		this.setCoords(coordstemp);
	}
}
