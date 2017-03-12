package BotRunner;
import BotMap.BotMap;
import Solution.Solution;

public class FullyObservableRunner extends BotRunner {
	
	public int execute(BotMap map){
		try{
			int moveCnt=0;
			while(map.amountOfDirt>0){
				Solution.nextMove(map.botPosition[0], map.botPosition[1], map.height, map.width, map.boardAsStringArray());
				String action = retrieveActionFromFile();
				performReturnedAction(action, map);
				moveCnt++;
				moveOutput.reset();
			}
			return moveCnt;
		}catch(Exception e){
			e.printStackTrace();
			moveOutput.reset();
			return -1;
		}
	}

}
