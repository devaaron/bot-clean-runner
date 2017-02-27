import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Solution.Solution;

public class NextMoveTest {

	Solution m = new Solution();
	
	@Test
	public void nextMove(){
		int h = 5;
		int w = 5;
		
		String [] grid = 
		{
		"b-ooo",
		"dooo",
		"ooooo",
		"ooooo",
		"ooooo"
		};

		assertEquals(Solution.ACTION.DOWN, Solution.getNextMove(0, 1, h, w, grid));
	}
	
	@Test
	public void nextMove_noDirt(){
		int h = 5;
		int w = 5;
		
		String [] grid = 
		{
		"b-ooo",
		"oooo",
		"ooooo",
		"ooooo",
		"ooooo"
		};

		assertEquals(Solution.ACTION.DOWN, Solution.getNextMove(0, 1, h, w, grid));
	}
	
	@Test
	public void allMoves(){
		String [] grid1 = 
			{
			"b-ooo",
			"oooo",
			"ooooo",
			"ooooo",
			"ooooo"
			};
		
		String [] grid2 = 
			{
			"b-ooo",
			"oooo",
			"ooooo",
			"ooooo",
			"ooooo"
			};
	}

}
