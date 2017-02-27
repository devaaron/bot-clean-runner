package Solution;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Solution implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int [] pos = new int[2];
        int [] dim = new int[2];
        for(int i=0;i<2;i++) pos[i] = in.nextInt();
        for(int i=0;i<2;i++) dim[i] = in.nextInt();
        String board[] = new String[dim[0]];
        for(int i=0;i<dim[0];i++) board[i] = in.next();
        nextMove(pos[0], pos[1], dim[0], dim[1], board);
    }
	
	public enum Corner{
		LEFT_UP(1,1), 
		RIGHT_UP(3,1), 
		LEFT_DOWN(1,3), 
		RIGHT_DOWN(3,3);
		Position position;
		Corner(int x, int y){
			this.position = new Solution().new Position(x, y);
		}
		public Position getPosition(){
			return position;
		}
	}
	
	public class DiffStruct{
		public DiffStruct(int xDiff, int yDiff) {
			super();
			this.xDiff = xDiff;
			this.yDiff = yDiff;
		}
		public int xDiff;
		public int yDiff;
	}
	
	public class Position implements Serializable{ 
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		public int x;
		public int y;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		public boolean equals(Position obj) {
			if(obj.x == this.x && obj.y == this.y){
				return true;
			}
			return false;
		}
		private Solution getOuterType() {
			return Solution.this;
		}
		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}
	}
	
	public enum ACTION implements Serializable{
		LEFT,RIGHT,UP,DOWN,CLEAN;
	}
	
	public static void nextMove(int r, int c, int h, int w, String[] grid) {
//		StringBuffer b = new StringBuffer();
//		for(String s : grid){
//			b.append(s+", ");
//		}
//		System.out.println(String.format("r: %s, c: %s, h: %s, w: %s, grid: %s", r,c,h,w,b.toString()));
		System.out.println(getNextMove(r, c, h, w, grid).toString());
	}
	
	public static ACTION getNextMove(int r, int c, int h, int w, String[] grid){
		Position curPos = new Solution().new Position(c, r);
		Position goalPos = null;
		ACTION action = null;
		try{
			goalPos = findDirt(grid, h, w);
			DiffStruct diff = determineDiff(goalPos.x, goalPos.y, c, r);
			action = determineAction(diff);
			
		}catch(RuntimeException e){ //can't find dirt

			Corner chosenCorner = findNearestCorner(r, c);
			goalPos = chosenCorner.position;
			DiffStruct diff = determineDiff(goalPos.x, goalPos.y, c, r);
			action = determineAction(diff);

			if((!action.equals(ACTION.CLEAN))&&hasBeenTraveled(goalPos)){
				printState();
				throw new RuntimeException("You've already been here and you're not cleaning... what are you doing!?");
			}
		}
		addAndWriteHistory(curPos, action);
		return action;
	}
	
	private static boolean hasBeenTraveled(Position newPos){
		for(Position pos : posHistory){
			if(newPos.equals(pos))
				return true;
		}
		return false;
	}
	
	private static Corner retrieveCornerIfCorner(Position goalPos) {
		for(Corner corner : allCorners){
			if(corner.position.equals(goalPos)){
				return corner;
			}
		}
		return null;
	}

	public static void addAndWriteHistory(Position newPos, ACTION newAction){
		posHistory.add(newPos);
		actionHistory.add(newAction);
		Corner newCorner = retrieveCornerIfCorner(newPos);
		if(newCorner!=null){ //don't write corner until you're there.
			cornerHistory.add(newCorner);
		}
		writeHistory();
	}
	
	private static Corner findNearestCorner(int botx, int boty) {
		int cost = 999999;
		Solution.Corner corner = null;
		
		for(Solution.Corner currentCorner : allCorners){
			boolean traveled = isCornerTraveled(currentCorner);
//			System.out.println("isCornerTraveled: "+traveled+", Corner: "+currentCorner+" CornerPos: "+currentCorner.position.toString());
			if(!traveled){
				Solution.Position curPos = currentCorner.position;
				int temp = determineTotalDiff(determineDiff(curPos.x, curPos.y, botx, boty));
				if(temp<cost&&cost>0){ //if zero than thats my current pos
					cost=temp;
					corner=currentCorner;
				}
			}
		}
		
		return corner;
	}

	private static boolean isCornerTraveled(Solution.Corner currentCorner) {
		for(Corner corner : cornerHistory){
//			System.out.println("historicalPos: "+historicalPos.toString()+" currentCornerPos: "+currentCorner.position.toString());
			if(corner.equals(currentCorner)){
				return true;
			}
		}
		return false;
	}

	private static int determineTotalDiff(DiffStruct diff){
		return Math.abs(diff.xDiff) + Math.abs(diff.yDiff);
	}
	
	public static Position findDirt(String[] grid, int h, int w){
		for(int i=0; i<h; i++){ //row
			for(int j=0; j<w; j++){ //column
				String ch = Character.toString(grid[i].charAt(j));
//				System.out.print(ch);
				if(ch.equals("d")){
					return new Solution().new Position(j, i);
				}
			}
//			System.out.println();
		}
		throw new RuntimeException("Dirt position not found");
	}
	
	

	private static DiffStruct determineDiff(int xP, int yP , int xB, int yB){		
		return new Solution().new DiffStruct(xP-xB, yP-yB);
	}
	
	private static ACTION determineAction(DiffStruct diff){ 
		
		if(isNegative(diff.yDiff)){
			return ACTION.UP;
		}else if(!isPerfect(diff.yDiff)){
			return ACTION.DOWN;
		}
		
		if(isNegative(diff.xDiff)){
			return ACTION.LEFT;
		}else if(!isPerfect(diff.xDiff)){
			return ACTION.RIGHT;
		}
		
		return ACTION.CLEAN;
	}
	
	private static boolean isPerfect(int var){
		if(var!=0){
			return false;
		}
		return true;
	}
	
	private static boolean isNegative(int var){
		if(var<0){
			return true;
		}
		return false;
	}

	static void printState(){
		for(int i=0; i<posHistory.size(); i++){
			System.out.println("Pos "+i+" "+posHistory.get(i).toString());
			System.out.println("Action taken : "+actionHistory.get(i).toString());
		}
		for(Corner c : cornerHistory){
			System.out.println("CornerHistory corner: "+c.toString());
		}
		if(cornerHistory.isEmpty()){
			System.out.println("No cornerHistory recorded.");
		}
		cleanDirectory();
	}
	
	static List<Corner> allCorners = new ArrayList<Corner>(){/**
		 * 
		 */
		private static final long serialVersionUID = 7080226030586517646L;
	{
			add(Corner.LEFT_UP);
			add(Corner.LEFT_DOWN);
			add(Corner.RIGHT_UP);
			add(Corner.RIGHT_DOWN);
		}};
		
	static List<Position> posHistory;
	static List<ACTION> actionHistory;
	static List<Corner> cornerHistory;

	static{
		try {
			retrieveHistory();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	static public void cleanDirectory(){
		cleanActionHistory();
		cleanPosHistory();
		cleanCornerHistory();
	}
	
	private static void cleanCornerHistory() {
		new File("corner").delete();
	}

	private static void cleanPosHistory() {
		new File("position").delete();
	}

	private static void cleanActionHistory() {
		new File("action").delete();
	}

	static public void retrieveHistory() throws ClassNotFoundException, IOException{
		retrieveActionHistory();
		retrievePosHistory();
		retrieveCornerHistory();
	}

	@SuppressWarnings("unchecked")
	private static void retrieveCornerHistory() throws ClassNotFoundException, IOException {
		try{
			ObjectInputStream cornerOs = new ObjectInputStream(new FileInputStream("corner"));
			cornerHistory = (List<Corner>) cornerOs.readObject();
			cornerOs.close();
		}catch(FileNotFoundException e){
			cornerHistory = new ArrayList<Corner>();
		}		
	}

	@SuppressWarnings("unchecked")
	private static void retrievePosHistory() throws IOException,
			ClassNotFoundException {
		try{
			ObjectInputStream posOs = new ObjectInputStream(new FileInputStream("position"));
			posHistory = (List<Position>) posOs.readObject();
			posOs.close();
		}catch(FileNotFoundException e){
			posHistory = new ArrayList<Position>();
		}
	}
	@SuppressWarnings("unchecked")
	private static void retrieveActionHistory() throws IOException,
			ClassNotFoundException {
		try{
			ObjectInputStream actionOs = new ObjectInputStream(new FileInputStream("action"));
			actionHistory = (List<ACTION>) actionOs.readObject();
			actionOs.close();
		}catch(FileNotFoundException e){
			actionHistory = new ArrayList<ACTION>();
		}
	}
	static public void writeHistory() {
		try {
			writeActionHistory(actionHistory);
			writePosHistory(posHistory);
			writeCornerHistory(cornerHistory);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void writeCornerHistory(List<Corner> corner) throws IOException {
		ObjectOutputStream cornerOs = new ObjectOutputStream(new FileOutputStream("corner"));
		cornerOs.writeObject(corner);
		cornerOs.close();		
	}

	private static void writePosHistory(Object position) throws IOException,
			FileNotFoundException {
		ObjectOutputStream posOs = new ObjectOutputStream(new FileOutputStream("position"));
		posOs.writeObject(position);
		posOs.close();
	}

	private static void writeActionHistory(Object action) throws IOException,
			FileNotFoundException {
		ObjectOutputStream actionOs = new ObjectOutputStream(new FileOutputStream("action"));
		actionOs.writeObject(action);
		actionOs.close();
	}
	
	
}
