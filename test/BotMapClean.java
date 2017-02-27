import org.junit.Before;
import org.junit.Test;

import Solution.Solution;
import BotMap.BotMap;
import BotMap.BotMapGenerator;
import BotRunner.BotRunner;
import BotRunner.BotRunnerFullyObservable;


public class BotMapClean {

	BotMap map;
	
	@Before
	public void init(){
		map = BotMapGenerator.createMap();
	}
	
	@Test
	public void cleanMap(){
		BotRunner runner = new BotRunnerFullyObservable();
		runner.run(map);
	}
	
	@Test
	public void cleanMap_stringArrayInput(){
		Solution.main(map.asStringArray());
	}
	
}
