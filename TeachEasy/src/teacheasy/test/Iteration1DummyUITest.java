package teacheasy.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import teacheasy.data.lessondata.*;
import teacheasy.data.GraphicObject.GraphicType;
import teacheasy.data.*;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
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
	
	//Check data is correctly stored once it has been parsed
	@Test
	public void checkDataIsCorrectlyStored(){
		parser.parse("/testXML/testXML.xml");
		Lesson lesson = parser.getLesson();
		Page page = lesson.pages.get(0);
		for (int i = 0; i <page.pageObjects.size(); i++) {
			PageObject pageObject = page.pageObjects.get(0);
			switch (pageObject.getType()) {
			case TEXT:
				TextObject text = (TextObject)pageObject;
				//Check positions
				assertTrue(text.getXStart() == 0.6);
				assertTrue(text.getYStart() == 0.2);
				//Check font 
				assertTrue(text.getFont() == "arial");
				break;
			case IMAGE:
				ImageObject image = (ImageObject)pageObject;
				//Check positions
				assertTrue(image.getXStart() == 0.5);
				assertTrue(image.getYStart() == 0.5);
				//Check source file
				assertTrue(image.getSourcefile() == "C:/Users/sadjlsd/imagefile.jpg");
				//Check rotation
				assertTrue(image.getRotation() == 0);
				break;
			case AUDIO:
				AudioObject audio = (AudioObject)pageObject;
				//Check positions
				assertTrue(audio.getXStart() == 0.5);
				assertTrue(audio.getYStart() == 0.1);
				//Check source file
				assertTrue(audio.getSourcefile() == "gavel.wav");
				break;
			case VIDEO:
				VideoObject video = (VideoObject)pageObject;
				//Check positions
				assertTrue(video.getXStart() == 0.1);
				assertTrue(video.getYStart() == 0.1);
				//Check source file and screenshot
				assertTrue(video.getScreenshotFile() == "C:/Users/sadjlsd/videoicon.jpeg");
				assertTrue(video.getSourcefile() == "avengers.mkv");
				break;
			case GRAPHIC:
				GraphicObject graphics = (GraphicObject)pageObject;
				//Check positions
				assertTrue(graphics.getXStart() == 0.5);
				assertTrue(graphics.getXEnd() == 0.7);
				assertTrue(graphics.getYStart() == 0.5);
				assertTrue(graphics.getYEnd() == 0.7);
				//Check other parameters
				assertTrue(graphics.getGraphicType() == GraphicType.OVAL);
				assertTrue(graphics.getOutlineThickness() == 0.5);
				assertTrue(graphics.getGraphicColour() == "#11223344");
				break;
			case ANSWER_BOX:
				AnswerBoxObject answerbox = (AnswerBoxObject)pageObject;
				//Check positions
				assertTrue(answerbox.getXStart() == 0.1);
				assertTrue(answerbox.getYStart() == 0.1);
				
				assertTrue(answerbox.getCorrectAnswers() == "springs~spring");
				assertTrue(answerbox.isRetry() == false);
				assertTrue(answerbox.getMarks() == 5);
				break;
			case BUTTON:
				ButtonObject button = (ButtonObject)pageObject;
				//Check positions
				assertTrue(button.getXStart() == 0.1);
				assertTrue(button.getYStart() == 0.1);
				
				assertTrue(button.isVisible() == true);
				assertTrue(button.getFunction() == 1);
				break;
			case MULTIPLE_CHOICE:
				MultipleChoiceObject multipleChoice = (MultipleChoiceObject)pageObject;
				//Check positions
				assertTrue(multipleChoice.getXStart() == 0.2);
				assertTrue(multipleChoice.getYStart() == 0.2);
				
				assertTrue(multipleChoice.getMultiChoiceType() == MultiChoiceType.CHECKBOX);
				
				assertTrue(multipleChoice.correctAnswers.get(0) == "Fish");
				assertTrue(multipleChoice.correctAnswers.get(1) == "Bear");
				assertTrue(multipleChoice.correctAnswers.get(2) == "Zebra");
				assertTrue(multipleChoice.correctAnswers.get(3) == "Komodo Dragon");
				break;				
			default:
				break;
			}
		}
	}
	
	//Check defaults
	//The following tests are for the writer
}
