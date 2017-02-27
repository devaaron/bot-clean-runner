package BotRunner;
import BotMap.BotMap;
import Solution.Solution;

public class BotRunnerFullyObservable extends BotRunner {
	
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
		}catch(RuntimeException e){
			e.printStackTrace();
			moveOutput.reset();
			throw e;
		}
	}
	
	private String retrieveActionFromFile() {
		String action = moveOutput.read();
		if(action!=null){
			switch(action){
			case("RIGHT"):
			case("LEFT"):
			case("UP"):
			case("DOWN"):
			case("CLEAN"):
				break;
			default:
				throw new RuntimeException("Unexpected text in file: "+action);
			}
		}
		return action;
	}

	private void performReturnedAction(String action, BotMap map) {
		if(map.board[map.botPosition[0]][map.botPosition[1]]=='b'){ //if bot position on map is equal to 'b', set to -
			map.board[map.botPosition[0]][map.botPosition[1]]='-';
		}
		if(action!=null){
			switch(action){
			case("RIGHT"):
				map.botPosition[1]++;
				break;
			case("LEFT"):
				map.botPosition[1]--;
				break;
			case("UP"):
				map.botPosition[0]--;
				break;
			case("DOWN"):
				map.botPosition[0]++;
				break;
			case("CLEAN"):
				if(map.board[map.botPosition[0]][map.botPosition[1]]=='d'){
					map.board[map.botPosition[0]][map.botPosition[1]] = 'b';
					map.amountOfDirt--;
				}
				return;
			default:
				throw new RuntimeException("Unexpected text in file: "+action);
			}
			
			if(map.board[map.botPosition[0]][map.botPosition[1]]=='-'){
				map.board[map.botPosition[0]][map.botPosition[1]]='b';
			}
		}
		
	}

}
