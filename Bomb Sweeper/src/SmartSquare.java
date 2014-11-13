import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SmartSquare extends GameSquare
{
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

	public void clicked()
	{
		
			//If this square has a bomb, set the square to show a bomb
			if (thisSquareHasBomb){
				this.setImage("src/images/bomb.png");
				
			}
			else{
				
				for (int x = -1; x<2; x++){
					for (int y = -1; y<2; y++){
						if (!(x == 0 && y == 0)){
						SmartSquare sq = (SmartSquare)board.getSquareAt((this.xLocation)+x,(this.yLocation)+y);
						if (sq.testBomb()){
							sq.setImage("src/images/bomb.png");

						}
						else {

						}
						}
					}
				}
				
				
			}
		

	}
}
