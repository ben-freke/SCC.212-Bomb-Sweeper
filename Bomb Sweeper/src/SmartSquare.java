import javax.swing.*;

import java.awt.*;
import java.util.*;

public class SmartSquare extends GameSquare
{
	private boolean checkedSquare = false;
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private static boolean endOfGame = false;
	public SmartSquare(int x, int y, GameBoard board)
	{
		
		super(x, y, "src/images/blank.png", board);
		
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}
	
	public void clicked()
	{
		if (!(SmartSquare.endOfGame)){
			//If the user hasn't lost 
			if (thisSquareHasBomb){
				//If the square has a bomb, then game over so blow up the entire square
				blowUp(this);
			}
			else{
				surroundSquares(this);
				//If not, run the surround squares method
			}
		}

	}
	
	public boolean testBomb(){
		return thisSquareHasBomb;
		//Check if the square has a bomb
	}

	public boolean getChecked(){
		return checkedSquare;
		//Check if the square has been checked
	}
	
	public void setChecked(boolean value){
		checkedSquare = value;
		//Set the square to either true or false
	}
	
	public void blowUp(SmartSquare hostSquare){
		int x = 0;
		int y = 0;
		while (true){
			//Loop forever (break condition is below)
			//Assign sq to the square at the current location
			SmartSquare sq = (SmartSquare)board.getSquareAt((x),(y));
			if (sq != null){
				//If the square exists (i.e. it the loop hasn't gone off the edge)
				SmartSquare.endOfGame = true;
				//They've already lost, so say end of game so the user can't click anything else
				x++;
				//Increment X location
				if (sq.testBomb()){
					sq.setImage("src/images/bomb.png");
					//If the square has a bomb, set the image to bomb
				}
				
			}
			else{
				//if the square is null
				x = 0;
				y++;
				if ((SmartSquare)board.getSquareAt((x),(y)) == null) break;
				//If the algorithm has gone through everything, break out of the loop
				 
			}

		}
	}
	
	public int getSurroundBombs(SmartSquare testSquare){
		//This function gets the number of surrounding bombs for each square
		int bombsInArea = 0;
		int x, y;
		//Set up the paramters
		for (x = -1; x<2; x++){
			for (y = -1; y<2; y++){
				//Loop for all of the 8 squares around it
				SmartSquare sq = (SmartSquare)board.getSquareAt((testSquare.xLocation)+x,(testSquare.yLocation)+y);
				//Save the square in that location to the variable
				if (!(sq == null)){
					//Disable all of the buttons
					//If the square exists
					if (sq.testBomb()){
						bombsInArea++;
						//Incremement bombs in area
					}
				}
			}
		}
		return bombsInArea;
		//Return the value of the number of bombs surround a square
	}
	
	
	public void surroundSquares(SmartSquare hostSquare){
		hostSquare.setChecked(true);
		//Say the square has been checked (as it's recursive, it could go on infinitely without this)
		int bombsInArea = getSurroundBombs(hostSquare);
		//Set the surrounding bombs in this area
		int x, y = 0;
		hostSquare.setImage("src/images/" + bombsInArea + ".png");
		//Set the image to bombs in area
		if (bombsInArea == 0){
			for (x = -1; x<2; x++){
				for (y = -1; y<2; y++){
					//Loop for each square
					SmartSquare sq = (SmartSquare)board.getSquareAt((hostSquare.xLocation)+x,(hostSquare.yLocation)+y);
					if (!(sq == null)){
						if (!sq.getChecked()){
							//If the square exists and it hasn't been checked
							sq.setChecked(true);
							surroundSquares(sq);
							//Set checked and call the function again for that square
						}
					}

				}
			}
		}
			
	}
	
}
