package BotMap;

public class BotMapGenerator {

	static final char DEFAULT_CHAR = '-';
	
	public static BotMap createMap() {
		BotMap map = new BotMap();
		map.height = 8;
		map.width = 8;
		map.board = generateEmptyBoard(map.height, map.width, DEFAULT_CHAR);
		map.amountOfDirt = dirtyBoard(map.board);
		map.botPosition = placeBot(map.board);
		return map;
	}
	
	public static char[][] generateEmptyBoard(int height, int width, char charToFillWith) {
		char[][] rv = new char[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				rv[i][j]=charToFillWith;
			}
		}
		return rv;
	}

	private static int dirtyBoard(char[][] board) {
		board[1][5] = 'd';
		board[2][7] = 'd';
		board[6][3] = 'd';
		return 3;
	}

	private static int[] placeBot(char[][] board) {
		int[] rv = new int[2];
		board[0][0] = 'b';
		rv[0] = 0;
		rv[1] = 0;
		return rv;
	}

}
