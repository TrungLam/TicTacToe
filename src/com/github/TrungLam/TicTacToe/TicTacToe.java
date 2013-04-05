package com.github.TrungLam.TicTacToe;

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
	int squareX, squareY;
	Square squares[][] = new Square[3][3];
	MouseBlock playerCursor;
	float squareWidth, squareHeight;
	String playerMarker[] = {"X", "O"};
	int playerIndex;
	boolean win;

	public TicTacToe() {
		super("Tic Tac Toe");
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		//draws game grid
		arg1.draw(line);
		arg1.draw(line2);
		arg1.draw(line3);
		arg1.draw(line4);
		
		//draws the current status of the game by iterating over each game square
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++)	{
				squares[i][k].draw();
			}
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		//initilize the positions and length of each line
		line = new Line(screenWidth * .33f,0.f, screenWidth *.33f,screenHeight);
		line2 = new Line(screenWidth *.66f, 0, screenWidth *.66f, screenHeight);
		line3 = new Line(0, screenHeight * .33f, screenWidth, screenHeight * .33f);
		line4 = new Line(0, screenHeight * .66f, screenWidth, screenHeight * .66f);
		
		//initilize mouse location for collision detection
		mousex = Mouse.getX();
		mousey = screenHeight - Mouse.getY();
		playerCursor = new MouseBlock(mousex, mousey);
		
		//player 0 (human) goes first
		playerIndex = 0;

		//initialize game squares
		initilizeSquares();
		
		win = false;
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		Input input = arg0.getInput();
		
		//gets location of mouse
		mousex = Mouse.getX();
		mousey = screenHeight -Mouse.getY();
		playerCursor.setX(mousex);
		playerCursor.setY(mousey);
		
		//reinitilizes game to start over when right click
		if (input.isMousePressed(1)) {
			initilizeSquares();
			win = false;
		}
		
		//player's turn
		if (input.isMousePressed(0) && !win && playerIndex == 0) {
			
			//will store valid square to be marked
			Square clickedSquare = null;
			
			//if there player's mouse is touching several squares, don't do anything
			int multipleCollisions = 0;
			
			//iterate over each square to see if player's mouse is over it
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 3; k++) {
					//if player's mouse is hovering over multiple squares
					if (multipleCollisions > 1)
						break;
					//if player's mouse is hovering over square and the square isn't marked
					if (playerCursor.getRect().intersects(squares[i][k].getRectangle()) && !squares[i][k].isMark()) {
						//assign current square to clickedSquare to be marked if multiple collisions was not present
						if (clickedSquare == null)
							clickedSquare = squares[i][k];
						multipleCollisions++;
					}
				}
			}
			
			//mark the clickedSquare as long as there wasn't any multiple collisions
			if (multipleCollisions == 1) {
				clickedSquare.setC(playerMarker[playerIndex]);
				clickedSquare.setMark(true);
				
				win = checkWin();
				
				if (win)
					System.out.println("win");
				
				//AI turn
				playerIndex = 1;
			}
		}
		
		//AI MOVE
		else if (playerIndex == 1 && !win) {
			
			//will store valid square to be marked
			Square openSquare = null;
			
			//use keep track of potential win/lose to counteract 
			int numOfPlayerMarks;
			int numOfAIMarks;
			
			//checks potential square to mark horizontally
			for (int row = 0; row < 3; row++) {
				numOfPlayerMarks = 0;
				numOfAIMarks = 0;
				for (int col = 0; col < 3; col++) {
					//checks if the square is marked with player's marker or AI marker
					if (squares[row][col].getC() == playerMarker[0])
						numOfPlayerMarks++;
					else if (squares[row][col].getC() == playerMarker[1])
						numOfAIMarks++;
				}
				//if player has 2 marks in the row prevent then AI should prevent player from winning 
				//or attempt to win if AI has 2 marks in the row
				if (numOfPlayerMarks == 2 || numOfAIMarks == 2) {
					//iterate over the squares in the current row once again to find the last open square to assign to openSquare
					for (int col = 0; col < 3; col++) {
						if (!squares[row][col].isMark()) {
							openSquare = squares[row][col];
							break;
						}
					}
				}
				//if openSquare is found, break
				if (openSquare != null)
					break;
			}
			
			//checks vertically if open square have not been found
			if (openSquare == null) {
				for (int col = 0; col < 3; col++) {
					numOfPlayerMarks = 0;
					numOfAIMarks = 0;
					for (int row = 0; row < 3; row++) {
						//check if current square is marked with player's marker or AI marker
						if (squares[row][col].getC() == playerMarker[0]) 
							numOfPlayerMarks++;
						else if (squares[row][col].getC() == playerMarker[1]) 
							numOfAIMarks++;
					}
					//if player has 2 marks in the row prevent then AI should prevent player from winning 
					//or attempt to win if AI has 2 marks in the row
					if (numOfPlayerMarks == 2 || numOfAIMarks == 2) {
						//iterate over the squares in the current row once again to find the last open square to assign to openSquare
						for (int row = 0; row < 3; row++) {
							if (!squares[row][col].isMark()) {
								openSquare = squares[row][col];
								break;
							}
						}
					}
					//if openSquare is found, break
					if (openSquare != null)
						break;
				}
			}
			
			//checks diagonally if open square have not been found
			if (openSquare == null) {
				numOfPlayerMarks = 0;
				numOfAIMarks = 0;
				for (int rowCol = 0; rowCol < 3; rowCol++) {
					//check if current square is marked with player's marker or AI marker
					if (squares[rowCol][rowCol].getC() == playerMarker[0]) {
						numOfPlayerMarks++;
					}
					else if (squares[rowCol][rowCol].getC() == playerMarker[1]) {
						numOfAIMarks++;
					}
				}
				//if player has 2 marks in the row prevent then AI should prevent player from winning 
				//or attempt to win if AI has 2 marks in the row
				if (numOfPlayerMarks == 2 || numOfAIMarks == 2) {
					//iterate over the squares in the current row once again to find the last open square to assign to openSquare
					for (int rowCol = 0; rowCol < 3; rowCol++) {
						if (!squares[rowCol][rowCol].isMark()) {
							openSquare = squares[rowCol][rowCol];
							break;
						}
					}
				}				
			}
			
			//checks other diagonal if open square have not been found
			if (openSquare == null) {
				numOfPlayerMarks = 0;
				numOfAIMarks = 0;
				for (int row = 0, col = 2; row < 3; row++, col--) {
					//check if current square is marked with player's marker or AI marker
					if (squares[row][col].getC() == playerMarker[0]) {
						numOfPlayerMarks++;
					}
					else if (squares[row][col].getC() == playerMarker[1]) {
						numOfAIMarks++;
					}
				}
				//if player has 2 marks in the row prevent then AI should prevent player from winning 
				//or attempt to win if AI has 2 marks in the row
				if (numOfPlayerMarks == 2 || numOfAIMarks == 2) {
					//iterate over the squares in the current row once again to find the last open square to assign to openSquare
					for (int row = 0, col = 2; row < 3; row++, col--) {
						if (!squares[row][col].isMark()) {
							openSquare = squares[row][col];
							break;
						}
					}
				}
			}
			
			//AI will mark the middle square if it hasn't been marked already
			if (openSquare == null) {
				if (!squares[1][1].isMark())
					openSquare = squares[1][1];
			}
			
			//take the next open square
			if (openSquare == null) {
				for (int row = 0; row < 3; row++) {
					for (int col = 0; col < 3; col++) {
						if (!squares[row][col].isMark()) {
							openSquare = squares[row][col];
							break;
						}
					}
					if (openSquare != null)
						break;
				}				
			}
			
			if (openSquare != null) {
				openSquare.setC(playerMarker[playerIndex]);
				openSquare.setMark(true);
			}
			
			win = checkWin();
			
			if (win)
				System.out.println("lose");
			playerIndex = 0;
		}
		
		
	}
	
	void initilizeSquares() {
		playerIndex = 0;
		squareX = 0;
		squareY = 0;
		squareWidth = (float)screenWidth - screenWidth*.66f;
		squareHeight = (float)screenHeight - screenHeight*.66f;
		
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				squares[i][k] = new Square(squareX, squareY, squareWidth, squareHeight);
				squareX += squareWidth;
			}
			squareX = 0;
			squareY += squareHeight;
		}
	}
	
	boolean checkWin() {
		int counter = 0;
		
		//check horizontal
		for (int i = 0; i < 3; i++) {
			for (int k =0; k < 3; k++) {
				if (squares[i][k].getC() == playerMarker[playerIndex]) {
					counter++;
				}
			}
			if (counter == 3)
				return true;
			counter = 0;
		}
		//check vertically
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				if (squares[k][i].getC() == playerMarker[playerIndex]) {
					counter++;
				}
			}
			if (counter == 3)
				return true;
			counter = 0;
		}
		//check diagonally
		for (int i = 0; i < 3; i++){
			if (squares[i][i].getC() == playerMarker[playerIndex]) {
				counter++;
			}
			
		}
		if (counter == 3)
			return true;
		
		counter = 0;
		//check other diagonal
		for (int i = 0, k = 2; i < 3; i++, k--) {
			if (squares[i][k].getC() == playerMarker[playerIndex]) {
				counter++;
			}
		}
		if (counter == 3)
			return true;
		
		return false;
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TicTacToe());
		screenHeight = 225;
		screenWidth = 300;
		
		app.setDisplayMode(screenWidth, screenHeight, false);
		app.start();
	}
}
