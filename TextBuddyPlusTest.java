import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
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
	
	@Before
	public void clearBeforeTest(){
		// Clear text before each test case
		TextBuddy.clearText();
	}
	
	@Test
	public void testAddText(){
		TextBuddy.addText("corn");
		TextBuddy.addText("barrel");
		TextBuddy.addText("airport");
		TextBuddy.addText("apple");
		output = TextBuddy.displayText();
		assertEquals("1. corn\n2. barrel\n3. airport\n4. apple",output);
	}
	
	@Test
	public void testSortText(){		
		TextBuddy.addText("corn");
		TextBuddy.addText("barrel");
		TextBuddy.addText("airport");
		TextBuddy.addText("apple");
		TextBuddy.sortText();
		output = TextBuddy.displayText();
		assertEquals("1. airport\n2. apple\n3. barrel\n4. corn",output);
	}
	
	@Test
	public void testCaseInsenstitiveSortText(){		
		TextBuddy.addText("corn");
		TextBuddy.addText("barrel");
		TextBuddy.addText("Airport");
		TextBuddy.addText("apple");
		TextBuddy.sortText();
		output = TextBuddy.displayText();
		assertEquals("1. Airport\n2. apple\n3. barrel\n4. corn",output);
	}
	
	@Test
	public void testSortEmptyList(){	
		output = TextBuddy.sortText();
		assertEquals(MESSAGE_EMPTY,output);
	}
	
	@Test
	public void testSearchText(){
		TextBuddy.addText("corn");
		TextBuddy.addText("barrel");
		TextBuddy.addText("airport");
		TextBuddy.addText("apple");
		output = TextBuddy.searchText("air");
		assertEquals("List of texts containing air:\n3. airport\n",output);
	}
	
	@Test
	public void testSearchEmptyList(){
		output = TextBuddy.searchText("hello");
		assertEquals(MESSAGE_EMPTY,output);
	}
}