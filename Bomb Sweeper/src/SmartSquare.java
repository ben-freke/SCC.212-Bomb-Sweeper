import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SmartSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;

	public SmartSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/bomb.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}

	public void clicked()
	{
		
			//If this square has a bomb, set the square to show a bomb
			System.out.println(thisSquareHasBomb);

	}
}
