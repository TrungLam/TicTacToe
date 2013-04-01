package com.github.TrungLam.TicTacToe;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class Square {
	
	private float x,y;
	private float w,h;
	private boolean marked;
	private TrueTypeFont ttf;
	private String c = null;
	
	public Square(float px, float py, float pw, float ph) {
		x = px;
		y = py;
		w = pw;
		h = ph;
		marked = false;
		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 50), false);
		
	}
	
	String getC() {
		return c;
	}
	
	public void draw() {
		if (c != null)
			ttf.drawString(x+30, y+10, c, Color.red);
		else
			ttf.drawString(x+30, y+10, "");
	}
	
	public void setC(String b) {
		c = b;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x,y,w,h);
	}
	
	public void setMark(boolean n) {
		marked = n;
	}
	
	public boolean getMark() {
		return marked;
	}
}
