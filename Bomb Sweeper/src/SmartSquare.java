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
	
	public boolean testBomb(){
		return thisSquareHasBomb;
	}

	public boolean getChecked(){
		return checkedSquare;
	}
	
	public void setChecked(boolean value){
		checkedSquare = value;
	}
	
	public void clicked()
	{
		
			//If this square has a bomb, set the square to show a bomb
			if (thisSquareHasBomb){
				this.setImage("src/images/bomb.png");
			}
			else{
				surroundSquares(this);
			}
		

	}
	
	public void surroundSquares(SmartSquare hostSquare){
		hostSquare.setChecked(true);
		int bombsInArea = 0;
		if (!(hostSquare == null)){
			for (int x = -1; x<2; x++){
				for (int y = -1; y<2; y++){
					SmartSquare sq = (SmartSquare)board.getSquareAt((hostSquare.xLocation)+x,(hostSquare.yLocation)+y);
					if (!(sq == null)){
						if (!sq.getChecked()){
						if (sq.testBomb()){
							bombsInArea++;
						}
						else{
							sq.setImage("");
							surroundSquares(sq);
						}
						}
					}
					else{
						break;
					}
				}
			}
		}
		
	}

	
	
	
}
