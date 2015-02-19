package teacheasy.test;

import static org.junit.Assert.*;

import java.io.File;

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

	//This test will check that if a valid XML file
	//is parsed it returns correct information
	//The XML file "testXML.xml" is formatted correctly
	@Test
	public void checkWriterCreatesXML() {
		//Parse the XML file
		parser.parse("textXML.xml");
		Lesson lesson = parser.getLesson();
		writer.writeXML(lesson, "autoTest.xml");
		assert(new File("autoTest.xml").exists());
	}
	
}
