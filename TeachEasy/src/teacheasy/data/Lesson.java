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
 * This class encapsulates a single Lesson
 * within the TeachEasy product.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class Lesson {
	
	/** Container for the pages within this lesson */
	public List<Page> pages;
	
	/** Constructor Method */
	public Lesson() {
		
		/* Instantiate the pages container */
		pages = new ArrayList<Page>();
	}
}
