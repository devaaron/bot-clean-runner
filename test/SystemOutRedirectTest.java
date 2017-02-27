import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.Test;


public class SystemOutRedirectTest {

	PrintStream stream;
	ByteArrayOutputStream bas = new ByteArrayOutputStream();
	File file;
	FileWriter fw;
	
	{
		file = new File("sysout.txt");
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stream = new PrintStream(bas);
		System.setOut(stream);
	}
	
	public void sync() throws IOException{
		fw.write(bas.toString());
		fw.flush();
	}
	
	@Test
	public void write(){
		System.out.println("This.  That.  The otherthing.");
		System.out.println("Also this");
	}
	
	public void reset() throws FileNotFoundException{
		bas.reset();
	}
	
	@Test
	public void full() throws IOException{
		write();
		reset();
		sync();
		write();
		reset();
		sync();
	}
	
}
