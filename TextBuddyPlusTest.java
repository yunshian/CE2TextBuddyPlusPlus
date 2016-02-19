import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextBuddyPlusTest extends TextBuddy {
	String output = null;
	static String filename = "testFile.txt";
	
	// Messages for user
	private static final String MESSAGE_EMPTY = "testFile.txt is empty.";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// set up TextBuddy
		TextBuddy.readFile(filename);
		TextBuddy.filename = filename;
	}
	
	@Test
	public void testAddText(){
		TextBuddy.executeCommand("clear");
		TextBuddy.executeCommand("add c");
		TextBuddy.executeCommand("add b");
		TextBuddy.executeCommand("add a");
		output = TextBuddy.executeCommand("Display");
		assertEquals("1. c\n2. b\n3. a",output);
	}
	
	@Test
	public void testSortText(){
		TextBuddy.executeCommand("sort");
		output = TextBuddy.executeCommand("Display");
		assertEquals("1. a\n2. b\n3. c",output);
	}
}