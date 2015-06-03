/*
 * Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import teacheasy.data.AnswerBoxObject;
import teacheasy.data.AudioObject;
import teacheasy.data.GraphicObject;
import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.TextObject;
import teacheasy.xml.XMLParser;
import teacheasy.xml.XMLWriter;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * This class is used to test the XML Parser
 * and writer and check that errors/warnings are added
 * to the appropriate lists under different circumstances
 * 
 * @author  Sam Raeburn
 * @version 1.0 24 May 2015
 *
 */
public class Iteration3XMLTest {
	private static XMLParser parser;
	private static XMLWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parser = new XMLParser();
		writer = new XMLWriter();
	}

	/** This tests parsing a non existent XML file */
	@Test
	public void nonExistentXMLFile() {
		ArrayList<XMLNotification> errorList = parser.parse("nonExistentXML.xml");
		for (int i = 0; i < errorList.size(); i++) {
			assertEquals((errorList.get(i).getLevel()), Level.ERROR);
		}
	}
	
	/** This tests parsing a file which does not contain all possible types of media */
	@Test
	public void notAllMedia() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/testXML.xml");
		assertTrue(errorList.size() == 0);
	}
	
	/** This test parsing a file which contains incorrectly formatted colour
	 *  An incorrectly formatted background colour should be set to the default value 
	/* Any other incorrectly formatted colour should cause an error*/
	@Test
	public void badBackgroundColour() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/badBackgroundColourXML.xml");
		for (int i = 0; i < errorList.size(); i++) {
			assertEquals((errorList.get(i).getLevel()), Level.WARNING);
		}
	}
	
	/** Parse a file with a text object but no information about font font size or font colour */
	@Test
	public void badTextData() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/badTextData.xml");
		for (int i = 0; i < errorList.size(); i++) {
			assertEquals((errorList.get(i).getLevel()), Level.WARNING);
		}	
	}
	
	/** Check that the data specified in an XML file is correctly stored */
	@Test
	public void checkDataStored() {
		parser.parse("testXML/checkDataStored.xml");
		Lesson lesson = parser.getLesson();
		Page page = lesson.pages.get(0);
		
		for (int i = 0; i < page.pageObjects.size(); i++) {
			PageObject pageObject = page.pageObjects.get(i);
			switch (pageObject.getType()) {
			case TEXT:
				TextObject text = (TextObject)pageObject;
				assertTrue(text.getFont().equals("arial"));
				assertTrue(text.getColor().equals("#ff000000"));
				assertTrue(text.getFontSize() == 20);
				assertTrue(text.getXStart() == 0.15f);
				assertTrue(text.getYStart() == 0.05f);
				assertFalse(text.textFragments.get(0).isItalic());
				assertTrue(text.textFragments.get(0).isBold());
				assertTrue(text.textFragments.get(0).isUnderline());
				assertTrue(text.textFragments.get(0).getFont().equals("arial"));
				assertTrue(text.textFragments.get(0).getFontSize() == 70);
				break;
			case IMAGE:
				ImageObject image = (ImageObject)pageObject;
				assertTrue(image.getRotation() == 0);
				assertTrue(image.getSourcefile().equals("Release1LessonPianoKeys.gif"));
				assertTrue(image.getXStart() == 0.15f);
				assertTrue(image.getYStart() == 0.2f);
				break;
			case GRAPHIC:
				GraphicObject graphic = (GraphicObject)pageObject;
				assertTrue(graphic.getOutlineThickness() == 4);
				assertTrue(graphic.getRotation() == 0);
				assertFalse(graphic.isShadow());
				break;
			case ANSWER_BOX:
				AnswerBoxObject answerbox = (AnswerBoxObject)pageObject;
				assertTrue(answerbox.getXStart() == 0.5f);
				assertTrue(answerbox.getYStart() == 0.9f);
				assertTrue(answerbox.getCharacterLimit() == 2);
				assertTrue(answerbox.getMarks() == 1);
				assertFalse(answerbox.isNumerical());
				break;
			default:
				break;		
			}
		}
	}
	
	/** Check that default settings are stored in the correct field */
	@Test
	public void checkDefaults() {
		parser.parse("testXML/checkDataStored.xml");
		Lesson lesson = parser.getLesson();
		Page page = lesson.pages.get(0);
		PageObject pageObject = page.pageObjects.get(0);
		TextObject text = (TextObject)pageObject;
		
		assertTrue(text.getFont().equals("arial"));
		assertTrue(text.getFontSize() == 20);
		assertTrue(text.getColor().equals("#ff000000"));
	}
	
	/** Check that the writer creates an XML file */
	@Test
	public void checkWriterCreatesXML() {
		parser.parse("testXML/testXML.xml");
		Lesson lesson = parser.getLesson();
		writer.writeXML(lesson, "testXML/writerTestXML.xml");
		File file = new File("testXML/writerTestXML.xml");
		assertTrue(file.exists());
		file.delete();
		assertFalse(file.exists());
	}
	
	/**
	 * Check to see that the XML file created by the writer contains
	 * the correct data, since it has already been shown that the parser
	 * behaves as wished this can simply be done using the parser
	 */
	@Test
	public void checkWriterXMLIsCorrect() {
		parser.parse("testXML/testXML.xml");
		Lesson lesson = parser.getLesson();
		writer.writeXML(lesson, "testXML/writerTestCorrectXML.xml");
		File file = new File("testXML/writerTestXML.xml");
		ArrayList<XMLNotification> errorList = parser.parse("testXML/writerTestCorrectXML.xml");
		file.delete();
		assertTrue(errorList.size() == 0);
	}
	
	/** Parse a file with no document info 
	/* Should add an error */
	@Test
	public void noDocumentInfo() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/noDocumentInfo.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		
		assertTrue(errorFound);
	}
	
	/** Parse a file which does not include default info */
	@Test
	public void noDefaultInfo() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/noDeafaultInfo.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		
		assertTrue(errorFound);
	}
	
	/** Parse a file which contains grade settings but no information about boundaries */
	@Test
	public void noBoundaryInfo() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/noBoundaryInfo.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		
		assertTrue(errorFound);
	}
	
	/** Parse a file which does not contain grade setting information */
	@Test
	public void noGradeSetting() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/noGradeSettingInfo.xml");
		/* Manual test since looking for a very specific warning */
		/* currently fails since no error is added */
	}
	
	/* Parse a file which does not contain information about slide number or background colour*/
	/*Background colour should be set to default */
	/* slide number should cause an error */
	@Test
	public void noSlideInformation() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/noSlideInformation.xml");
		//System.out.println(errorList);
		/* Carry out manual test since looking for a specific warning */
	}
	
	/* Parse a file which contains a text object but no start points */
	/* The parser should add an error to the list */
	@Test
	public void textNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/textNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt parsing a file which contains text but no info about rich text 
	* No error or warning should be added to the error list */
	@Test
	public void noRichText() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/noRichText.xml");
		/* looking for (lack of) a specific warning so manual test */
		//System.out.println(errorList);	
	}
	
	/* Attempt parsing a file which contains image but no info about location */
	/* Parser should add an error to the list */
	@Test
	public void imageNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/imageNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file which contains image but no sourcefile *
	* Error should be added to the list */
	@Test
	public void imageNoSourcefile() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/imageNoSourcefile.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file which contains image with badly formatted sourcefile 
	* no error should be added to the list */
	@Test
	public void imageBadSourcefile() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/imageBadSourcefile.xml");
		boolean errorFound = false;
		
		//System.out.println(errorList);
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(!errorFound);
	}
	
	/** Attempt to parse a file which contains an image with badly formatted 
	* online hosted source file 
	* no error should be added to the list */
	@Test
	public void imageBadOnlineSourcefile() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/imageBadOnlineSourcefile.xml");
		boolean errorFound = false;
		
		//System.out.println(errorList);
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(!errorFound);
	}
	
	/** Attempt to parse a file with an image but no scale information 
	* Parser should default this value to 1 
	* also no rotation information - default to 0*/
	@Test
	public void imageNoScale() {
		parser.parse("testXML/imageNosScale.xml");
		Lesson lesson = parser.getLesson();
		Page page = lesson.pages.get(0);
		PageObject pageObject = page.pageObjects.get(0);
		ImageObject image = (ImageObject)pageObject;
		
		assertTrue(image.getyScaleFactor() == 1);
		assertTrue(image.getxScaleFactor() == 1);
		assertTrue(image.getRotation() == 0);
	}

	/** Attempt to parse a file with an image but incorrectly formatted info for 
	 *position, error should be added to list */
	@Test
	public void imageBadPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/imageBadPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with audio with no information about position 
	* The parser should add an error to the list */
	@Test
	public void audioNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/audioNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt parsing a file with audio which does not state whether *
	* to hide or show the progress bar 
	* The parser should hide the progress bar by default */
	@Test
	public void audioNoProgress() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/audioNoProgress.xml");
		//System.out.println(errorList);
		Lesson lesson = parser.getLesson();
		Page page = lesson.pages.get(0);
		PageObject pageObject = page.pageObjects.get(0);
		AudioObject audio = (AudioObject)pageObject;
		
	//	assertFalse(audio.isViewProgress());

	}
	
	/** Attempt parsing a file which contains video with no position info 
	* Error should be added to the list */
	@Test
	public void videoNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/videoNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt parsing a file which contains a video with no screenshot info 
	* Warning should be added to list */
	@Test
	public void videoNoScreenshot() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/videoNoScreenshot.xml");
		/* looking for specific warning so manual test */
		System.out.println("Video No Position:" + errorList);
	}
	
	/** Attempt parsing a file with graphics object but no information about type 
	* error should be added */
	@Test
	public void graphicsNoType() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/graphicsNoType.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt parsing a file with graphics object but no information about solidness 
	* error should be added to the list */
	@Test
	public void graphicsNoSolid() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/graphicsNoSolid.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with graphics object but no information about colour 
	* error should be added to the list */
	@Test
	public void graphicsNoColour() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/graphicsNoColour.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with graphics object but no information about position 
	* error should be added to the list */
	@Test
	public void graphicsNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/graphicsNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);	
	}
	
	/** attempt to parse a file with graphics object with shading but no shading colour 
	* error should be added to the list */
	@Test
	public void graphicsNoShadingColour() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/graphicsNoShadingColour.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with a graphics object but no information about 
	* rotation, line thickness or line colour 
	* should be set to defaults */
	@Test
	public void graphicsDefaults() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/graphicsDefaults.xml");
		Lesson lesson = parser.getLesson();
		Page page = lesson.pages.get(0);
		PageObject pageObject = page.pageObjects.get(0);
		GraphicObject graphic = (GraphicObject)pageObject;
		assertTrue(graphic.getOutlineThickness() == 1.0f);
		assertTrue(graphic.getRotation() == 0);
	}
	
	/** Attempt to parse a file with an answer box but no positions 
	* error should be added */
	@Test
	public void answerNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/answerNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with an answer box but no character limit 
	* error should be added to the list */
	@Test
	public void answerNoCharacterLimit() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/answerNoCharacterLimit.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with an answer box but no correct answers 
	* error should be added to the list */
	@Test
	public void answerNoAnswers() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/answerNoAnswers.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with an answer box but no mark information 
	* error should be added to the list */
	@Test
	public void answerNoMarks() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/answerNoMarks.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with an answer box which doesn't state whether 
	* the user can retry the question - error should be added */
	@Test
	public void answerNoRetry() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/answerNoRetry.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file which contains a multiple choice object but no position 
	* error should be added */
	@Test
	public void multiNoPosition() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/multiNoPosition.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with an multiple choice object but no information about type 
	* error should be added to the list */
	@Test
	public void multiNoType() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/multiNoType.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with a multiple choice object but no information about orientation 
	* error should be added to the list */
	@Test
	public void multiNoOrientation() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/multiNoOrientation.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with a multiple choice object but no marks associated with it 
	* error should be added to the list */
	@Test
	public void multiNoMarks() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/multiNoMarks.xml");
		boolean errorFound = false;
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
	/** Attempt to parse a file with a multiple choice object but no correct answers 
	* error should be added to the list */
	@Test
	public void multiNoAnswers() {
		ArrayList<XMLNotification> errorList = parser.parse("testXML/multiNoAnswers.xml");
		boolean errorFound = false;
		System.out.println("multi no answer: " + errorList);
		
		for (int i = 0; i < errorList.size(); i++) {
			if(errorList.get(i).getLevel() == Level.ERROR) {
				errorFound = true;
			}
		}	
		assertTrue(errorFound);
	}
	
}
