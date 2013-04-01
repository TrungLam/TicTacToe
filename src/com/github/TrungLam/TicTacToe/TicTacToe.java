package com.github.TrungLam.TicTacToe;



import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;


public class TicTacToe extends BasicGame{
	
	Line line, line2, line3, line4;
	static int screenWidth, screenHeight;
	int mousex, mousey;
	String mouseXY = "";
	int counter;
	ArrayList<Square> squares = new ArrayList<Square>();
	MouseBlock mb;
	int squareX, squareY;
	float squareWidth, squareHeight;
	


	public TicTacToe() {
		super("Tic Tac Toe");
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		arg1.draw(line);
		arg1.draw(line2);
		arg1.draw(line3);
		arg1.draw(line4);
		arg1.drawString(mouseXY, 0, 100);
		

		for (Square square : squares) {
			square.draw();
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		line = new Line(screenWidth * .33f,0.f, screenWidth *.33f,screenHeight);
		line2 = new Line(screenWidth *.66f, 0, screenWidth *.66f, screenHeight);
		line3 = new Line(0, screenHeight * .33f, screenWidth, screenHeight * .33f);
		line4 = new Line(0, screenHeight * .66f, screenWidth, screenHeight * .66f);
		mousex = Mouse.getX();
		mousey = screenHeight - Mouse.getY();
		squareX = 0;
		squareY = 0;
		squareWidth = (float)screenWidth - screenWidth*.66f;
		squareHeight = (float)screenHeight - screenHeight*.66f;
		mb = new MouseBlock(mousex, mousey);
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				squares.add(new Square(squareX, squareY, squareWidth, squareHeight));				
				squareX += squareWidth;
			}
			squareX = 0;
			squareY += squareHeight;
		}
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		mousex = Mouse.getX();
		mousey = screenHeight -Mouse.getY();
		mouseXY = "x: " + mousex + "\ty: " + mousey;
		mb.setX(mousex);
		mb.setY(mousey);
		Input input = arg0.getInput();
		
		if (input.isMousePressed(0)) {
			Square clickedSquare = null;
			int found = 0;
			
			for (Square square : squares) {
				if (mb.getRect().intersects(square.getRectangle()) && !square.getMark()) {
					if (clickedSquare == null )
						clickedSquare = square;
					found++;
				}
			}
			
			if (found == 1) {
				clickedSquare.setC("X");
				clickedSquare.setMark(true);
			}
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TicTacToe());
		screenHeight = 225;
		screenWidth = 300;
		
		app.setDisplayMode(screenWidth, screenHeight, false);
		app.start();
	}
	

}
