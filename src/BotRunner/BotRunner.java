package BotRunner;
import BotMap.BotMap;
import OutputManager.OutputManager;
import OutputManager.OutputManagerImpl;


public abstract class BotRunner{
	
	OutputManager moveOutput;
	
	{
		moveOutput = new OutputManagerImpl("src/out.txt");
	}

	public void before(String openingMsg)
	{
		System.out.println(openingMsg);
		moveOutput.redirect();
	}
	
	public void after(String closingMsg){
		moveOutput.directToDefault();
		System.out.println(closingMsg);
	}
	
	public int run(BotMap map){
		before("Testing ./Solution.java with map:\n"+map.toString());
		int numberOfMoves = execute(map);
		after("Number of moves taken: "+numberOfMoves);
		return numberOfMoves;
	}

	public abstract int execute(BotMap map);
	
}
