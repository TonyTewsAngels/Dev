/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.certificate;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * A runnable class to print buffered images (swing). Used to
 * print the LearEasy lesson certificate. 
 * 
 * @author  Alistair Jewers
 * @version 1.0 27 May 2015
 */
public class PrintRunnable implements Runnable {
    /* The image to print */
    private BufferedImage image;
    
    /**
     * Constructor to create the runnable.
     * 
     * @param image The image to print as a swing buffered image.
     */
    public PrintRunnable(BufferedImage image) {
        /* Set the class level object reference */
        this.image = image;
    }

    /**
     * Override the run method of the runnable to print the image
     * when run is invoked.
     */
    @Override
    public void run() {
        /* Get a printer job */
        PrinterJob printJob = PrinterJob.getPrinterJob();
        
        /* 
         * Set the print job to print a new instance of the ImagePrintable class
         * with the image referenced by this runnable 
         */
        printJob.setPrintable(new ImagePrintable(printJob, image));

        /* Attempt to print, printing exceptions if they occur */
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException prt) {
                prt.printStackTrace();
            }
        }
    }
    
    /**
     * Inner class to encapsulate a printable image.
     */
    public class ImagePrintable implements Printable {
        /* Size and position variables */
        private double x, y, width;

        /* Portrait or landscape orientation */
        private int orientation;

        /* The image to print */
        private BufferedImage image;

        /**
         * Constructor method to create the printable image.
         * 
         * @param printJob The print job associated with printing this image.
         * @param image The image to print.
         */
        public ImagePrintable(PrinterJob printJob, BufferedImage image) {
            /* Create a new format object */
            PageFormat pageFormat = printJob.defaultPage();
            
            /* Load in the size and orientation information */
            this.x = pageFormat.getImageableX();
            this.y = pageFormat.getImageableY();
            this.width = pageFormat.getImageableWidth();
            this.orientation = pageFormat.getOrientation();
            
            /* Set the image reference */
            this.image = image;
        }

        /**
         * Override the print method of the printable to print the image when
         * invoked.
         * 
         * @param g The graphics object to draw the image on.
         * @param pageFormat The page layout information.
         * @param pageIndex The page index.
         * @return Whether the page exists or not.
         * @throws PrinterException
         */
        @Override
        public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
            /* Check that this is the first page */
            if (pageIndex == 0) {
                /* Variables to hold the print size */
                int pWidth = 0;
                int pHeight = 0;
                
                /* Check the orientation */
                if (orientation == PageFormat.PORTRAIT) {
                    /* Calculate print size */
                    pWidth = (int) Math.min(width, (double) image.getWidth());
                    pHeight = pWidth * image.getHeight() / image.getWidth();
                } else {
                    /* Calculate print size */
                    pHeight = (int) Math.min(width, (double) image.getHeight());
                    pWidth = pHeight * image.getWidth() / image.getHeight();
                }
                
                /* Draw the image to a graphics context */
                g.drawImage(image, (int) x, (int) y, pWidth, pHeight, null);
                
                /* Indicate to print */
                return PAGE_EXISTS;
            } else {
                /* Indicate not to print */
                return NO_SUCH_PAGE;
            }
        }
    }
}
