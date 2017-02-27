import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class FileWritingTest {
	
	File file;
	
	@Before
	public void setup() throws IOException{
		file = new File("test.txt");
		file.createNewFile();
	}
	
	@Test
	public void fileManipulation() throws IOException{
		writeFile();
		overwriteFile();
	}
	
	public void overwriteFile() throws IOException{
		FileWriter writer = new FileWriter(file);
		writer.write("B");
		writer.close();
	}
	
	public void writeFile() throws IOException{
		FileWriter writer = new FileWriter(file);
		writer.write("A");
		writer.close();
	}

}
