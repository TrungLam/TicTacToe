package com.github.TrungLam.TicTacToe;

import org.newdawn.slick.geom.Rectangle;

public class MouseBlock {
	private int x,y;
	public MouseBlock(int px, int py) {
		x = px;
		y = py;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,5,5);
	}
	
	public void setX(int px) {
		x =px;
	}
	
	public void setY(int py) {
		y = py;
	}
}
