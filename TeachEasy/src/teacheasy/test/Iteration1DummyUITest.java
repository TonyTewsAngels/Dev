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
	
	//Check that the parser doesn't crash if an XML file
	//has image with no file or location
	@Test
	public void checkParserWithNoImageLocation() {
		ArrayList<String> errorList;
		errorList = parser.parse("missingImageLocation.xml");
		System.out.println("Test 3:" + errorList);
		//Check that there IS an error in the list
		assertTrue(errorList.size() != 0);
	}
	
	//Check how the parser handles missing default settings
	@Test
	public void checkParserDefaults() {
		ArrayList<String> errorList;
		errorList = parser.parse("missingDefaults.xml");
		System.out.println("Test 4:" + errorList);
		assertTrue(errorList.size() == 0);
	}
	//PARSER DOES NOT PUT ERROR IN LIST< DOES NOT REALISE!!!!!
	
	//Check how parser handles missing document info section
	@Test
	public void checkMissingDocumentInfo() {
		ArrayList<String> errorList;
		errorList = parser.parse("missingDocumentInfo.xml");
		System.out.println("Test 5:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	//Again...no error :(
	
	//Check how parser handles an xml file which does not define locations
	@Test
	public void checkNoLocations() {
		ArrayList<String> errorList;
		errorList = parser.parse("noLocations.xml");
		System.out.println("Test 6:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check pass boundary
	@Test
	public void checkPassBoundary() {
		ArrayList<String> errorList;
		errorList = parser.parse("passBoundaryTest.xml");
		System.out.println("Test 7:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
}
