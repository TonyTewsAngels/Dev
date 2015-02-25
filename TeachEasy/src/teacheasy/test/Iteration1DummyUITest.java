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
	
	//The following tests are for the parser

	//Parsing file that doesn't exist returns error
	@Test
	public void checkParserWithNonexistentXML() {
		//Parse the XML file
		ArrayList<String> errorList = parser.parse("iDontExist.xml");
		System.out.println("non existent file test:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check that no error is returned if there is no audio, 
	//video or image data in a parsed XML file
	@Test
	public void checkParserWithMissingDataXML() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/missingImageAudioVideoData.xml");
		System.out.println("missing media test:" + errorList);
		assertTrue(errorList.size() == 0);
	}
	
	//Check that the parser doesn't crash if an XML file
	//has image with no file or location
	@Test
	public void checkParserWithNoImageLocation() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/missingImageLocation.xml");
		System.out.println("image location test:" + errorList);
		//Check that there IS an error in the list
		assertTrue(errorList.size() != 0);
	}
	
	//Check how the parser handles missing default settings
	@Test
	public void checkParserDefaults() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/missingDefaults.xml");
		System.out.println("missing defaults test:" + errorList);
		assertTrue(errorList.size() == 0);
	}
	
	//Check how parser handles missing document info section
	@Test
	public void checkMissingDocumentInfo() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/missingDocumentInfo.xml");
		System.out.println("missing document info test:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check how parser handles an xml file which does not define locations
	@Test
	public void checkNoLocations() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/noLocations.xml");
		System.out.println("no locations test:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check pass boundary
	@Test
	public void checkPassBoundary() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/passBoundaryTest.xml");
		System.out.println("Pass boundary test:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check if error if a colour is given in wrong format (not hex)
	@Test
	public void checkColourFormatting() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/colourFormatting.xml");
		System.out.println("Colour formatting test: " + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check if locations can be greater than 1 or negative
	@Test
	public void checkLocationToobig() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/incorrectLocation.xml");
		System.out.println("Location greater than 1 and negative test:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//Check if for graphic x/yend can be smaller than x/ystart
	@Test
	public void checkGraphicLocation() {
		ArrayList<String> errorList;
		errorList = parser.parse("testXML/graphicsLocation.xml");
		System.out.println("Check graphics locations:" + errorList);
		assertTrue(errorList.size() != 0);
	}
	
	//The following tests are for the writer
}
