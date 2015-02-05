/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a single page
 * within a Lesson.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class Page {
	
	/** Container for the objects on this page */
	public List<PageObject> pageObjects;
	
	/** Constructor Method */
	public Page() {
		
		/* Instantiate the page object container */
		pageObjects = new ArrayList<PageObject>();
	}
}
