package BotRunner;

import BotMap.BotMap;
import BotMap.BotMapGenerator;
import Solution.Solution;

public class PartiallyObservableRunner extends BotRunner {

	int visibilityRadius;
	final char UNOBSERVABLE_SYMBOL = 'o';
	
	public PartiallyObservableRunner(int visibilityRadius) {
		super();
		if(visibilityRadius<1){throw new RuntimeException("visibilityRadius cannot be less than 1");}
		this.visibilityRadius = visibilityRadius;
	}

	@Override
	public int execute(BotMap map) {
		try{
			int moveCnt=0;
			while(map.amountOfDirt>0){
				BotMap partialMap = makePartiallyVisible(map, visibilityRadius);
				Solution.nextMove(map.botPosition[0], map.botPosition[1], map.height, map.width, partialMap.boardAsStringArray());
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

	private BotMap makePartiallyVisible(BotMap map, int radius) {
		//visibility radius is larger than map size, so just return the map as is.
		if(map.height<=radius&&map.width<=radius){
			return map;
		}
		BotMap partialMap = new BotMap();
		
		partialMap.board = BotMapGenerator.generateEmptyBoard(map.height, map.width, this.UNOBSERVABLE_SYMBOL);
		partialMap.botPosition = map.botPosition;
		copyWithinRadius(map, partialMap.board, radius);
		return partialMap;
	}

	private void copyWithinRadius(BotMap src, char[][] des,
			int radius) {
		for(int i = 0; i < src.height; i++){
			for(int j = 0; j < src.width; j++){
				if(withinRadiusOfBot(i, j, src.botPosition[0], src.botPosition[1], radius)){
					des[i][j] = src.board[i][j];
				}
			}
		}
	}

	private boolean withinRadiusOfBot(int curY, int curX, int botY, int botX, int radius) {
		if(Math.abs(botY-curY)<=radius&&Math.abs(botX-curX)<=radius){
			return true;
		}
		return false;
	}

}
