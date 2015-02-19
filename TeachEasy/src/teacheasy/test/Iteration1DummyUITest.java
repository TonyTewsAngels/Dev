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
	public void checkWriterCreatesXML() {
		//Parse the XML file
		ArrayList<String> errorList = parser.parse("iDontExist.xml");
		assertTrue(errorList.size() != 0);
	}
	
	//Parsing file with incorrectly formatted data returns error
}
