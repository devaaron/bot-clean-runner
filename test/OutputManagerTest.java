import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import OutputManager.OutputManagerImpl;


public class OutputManagerTest {
	OutputManagerImpl mgr;

	@Before
	public void setUp() throws Exception {
		mgr = new OutputManagerImpl("a.txt");
		mgr.redirect();
	}

	@Test
	public void testSync() {
		mgr.sync();
	}

	@Test
	public void testReset() {
		mgr.reset();
	}

	@Test
	public void testRead() {
		System.out.println("Something");
		assertEquals("Something",mgr.read());
		
	}

}
