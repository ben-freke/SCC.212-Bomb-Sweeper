import javax.swing.*;

import java.awt.*;
import java.util.*;

public class SmartSquare extends GameSquare
{
	private boolean checkedSquare = false;
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;

	public SmartSquare(int x, int y, GameBoard board)
	{
		
		super(x, y, "src/images/blank.png", board);
		
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}
	
	public void clicked()
	{
		
			//If this square has a bomb, set the square to show a bomb
			if (thisSquareHasBomb){
				blowUp(this);
			}
			else{
				surroundSquares(this);
			}
		

	}
	
	public boolean testBomb(){
		return thisSquareHasBomb;
	}

	public boolean getChecked(){
		return checkedSquare;
	}
	
	public void setChecked(boolean value){
		checkedSquare = value;
	}
	
	public void blowUp(SmartSquare hostSquare){
		int x = 0;
		int y = 0;
		while (true){
			SmartSquare sq = (SmartSquare)board.getSquareAt((x),(y));
			if (sq != null){
				x++;
				if (sq.testBomb()){
					sq.setImage("src/images/bomb.png");
				}
			}
			else{
				x = 0;
				y++;
				if ((SmartSquare)board.getSquareAt((x),(y)) == null) break;
				 
			}

		}
	}
	
	public int getSurroundBombs(SmartSquare testSquare){
		int bombsInArea = 0;
		int x, y;
		for (x = -1; x<2; x++){
			for (y = -1; y<2; y++){
				SmartSquare sq = (SmartSquare)board.getSquareAt((testSquare.xLocation)+x,(testSquare.yLocation)+y);
				if (!(sq == null)){
					if (sq.testBomb()){
						bombsInArea++;
					}
				}
			}
		}
		return bombsInArea;
	}
	
	public void surroundSquares(SmartSquare hostSquare){
		hostSquare.setChecked(true);
		int bombsInArea = getSurroundBombs(hostSquare);
		int x, y = 0;
		hostSquare.setImage("src/images/" + bombsInArea + ".png");
		if (bombsInArea == 0){
			for (x = -1; x<2; x++){
				for (y = -1; y<2; y++){
					SmartSquare sq = (SmartSquare)board.getSquareAt((hostSquare.xLocation)+x,(hostSquare.yLocation)+y);
					if (!(sq == null)){
						if (!sq.getChecked()){
							sq.setChecked(true);
							surroundSquares(sq);
						}
					}

				}
			}
		}
			
	}
	
}
