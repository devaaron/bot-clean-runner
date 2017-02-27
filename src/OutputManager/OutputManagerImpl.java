package OutputManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;


public class OutputManagerImpl implements OutputManager {
	
	private PrintStream stream;
	private ByteArrayOutputStream bas;
	private File mvFile;
	private FileWriter fw;
	
	public OutputManagerImpl(String moveOutputFileName) {
		super();
		initMoveOutputStream(moveOutputFileName);
	}
	
	private void initMoveOutputStream(String moveOutputFileName) {
		mvFile = new File(moveOutputFileName);
		try {
			fw = new FileWriter(mvFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bas = new ByteArrayOutputStream();
		stream = new PrintStream(bas);
	}
	
	public void redirect(){
		System.setOut(stream);
	}
	
	public void directToDefault(){
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
	
	public void sync(){
		try{
			fw.write(bas.toString());
			fw.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void reset(){
		sync();
		bas.reset();
	}
	
	public String read(){
		return bas.toString().trim();
	}
	
}
