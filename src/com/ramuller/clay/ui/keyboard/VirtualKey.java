package com.ramuller.clay.ui.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;

public class VirtualKey {
	private float weight;
	private int width;
	private int x;
	private static int margin=2;
	private static int radius=16;
	private char label;
	
	public VirtualKey(char label){
		this.label = label;
		this.weight = 1;
		if(label==' ') this.weight = 3;
	}

	public int getWidth() {
		return width;
	}

	protected void setWidth(float width) {
		this.width = (int)width;
	}

	public int getX() {
		return x;
	}

	protected void setX(float x) {
		this.x = (int)x;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public void paint(Graphics2D g, int y, int height, boolean invert){
		g.setColor(invert?Color.BLACK:Color.WHITE);
		g.fillRoundRect(x+margin, y+margin, width-2*margin, height-2*margin, radius, radius);
		g.setColor(invert?Color.WHITE:Color.BLACK);
		if(!invert)g.drawRoundRect(x+margin, y+margin, width-2*margin, height-2*margin, radius, radius);
		g.drawString(""+label, x+3, y+(int)(height*0.8f));
	}
}