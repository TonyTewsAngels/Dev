package teacheasy.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import teacheasy.data.Lesson;
import teacheasy.xml.*;

public class Iteration1DummyUITest {
	private static XMLHandler handler;
	private static XMLParser parser;
	private static XMLWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		handler = new XMLHandler();
		parser = new XMLParser();
		writer = new XMLWriter();
	}

	//Parsing file that doesn't exist returns error
	@Test
	public void checkParserWithNonexistentXML() {
		//Parse the XML file
		ArrayList<String> errorList = parser.parse("iDontExist.xml");
		System.out.println("Test 1:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check that no error is returned if there is no audio, 
	//video or image data in a parsed XML file
	@Test
	public void checkParserWithMissingDataXML() {
		ArrayList<String> errorList;
		errorList = parser.parse("missingImageAudioVideoData.xml");
		System.out.println("Test 2:" + errorList);
		assertTrue(errorList.size() == 0);
	}
	
	//TESTS TO DO TOMORROW:
	//PARSER:
	//Check if image no file/location
	//Check defaults
	//Check video no file/location
	//Check text no location
	//Same with other objects
	//Essentially check that all necessary information gives error in list
	//Check if documentinfo missing throws error since it is always needed
	
	//WRITER:
	
	
	
	//
}
