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

	public String retrieveActionFromFile() {
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

	public void performReturnedAction(String action, BotMap map) {
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
	
	public abstract int execute(BotMap map);
	
}
