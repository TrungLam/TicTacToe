package com.github.TrungLam.TicTacToe;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

public class Piece {
	TrueTypeFont e;
	int x, y;
	public Piece(int pX, int pY, Font font) {
		e = new TrueTypeFont(font, false);
		x = pX;
		y = pY;
	}
	
	public TrueTypeFont getTTF() {
		return e;
	}
}
