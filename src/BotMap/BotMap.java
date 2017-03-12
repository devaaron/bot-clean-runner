package BotMap;
import java.util.Arrays;


public class BotMap {

	public int height;
	public int width;
	public int amountOfDirt;
	public int[] botPosition;
	public char[][] board;
	
	public BotMap() {
		super();
	}

	public BotMap(int height, int width, int amountOfDirt, int[] botPosition,
			char[][] board) {
		super();
		this.height = height;
		this.width = width;
		this.amountOfDirt = amountOfDirt;
		this.botPosition = botPosition;
		this.board = board;
	}

	public String[] asStringArray(){
		String[] rv = new String[height*4];
		rv[0] = new Integer(botPosition[0]).toString();
		rv[1] = new Integer(botPosition[1]).toString();
		rv[2] = new Integer(height).toString();
		rv[3] = new Integer(width).toString();
		for(int i = 0; i<height; i++){
			rv[i+4]= new String();
			for(int j = 0; j<width; j++){
				rv[i+4]+=board[i][j];
			}
		}
		return rv;
	}
	
	public String[] boardAsStringArray(){
		String[] rv = new String[height];
		for(int i = 0; i<height; i++){
			rv[i]= new String();
			for(int j = 0; j<width; j++){
				rv[i]+=board[i][j];
			}
		}
		return rv;
	}

	@Override
	public String toString() {
		return "BotMap [height=" + height + ", width=" + width
				+ ", amountOfDirt=" + amountOfDirt + ", botPosition="
				+ Arrays.toString(botPosition) + ", board="
				+ Arrays.toString(board) + "]";
	}
}
