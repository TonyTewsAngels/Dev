/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.xml.util;

import java.util.ArrayList;

import teacheasy.xml.util.XMLNotification.Level;

/**
 * Contains utility methods to be used when working with the
 * digital lesson XML format.
 * 
 * @author Alistair Jewers
 * @version 1.1 10 Apr 2015
 */
public class XMLUtil {
    
    /**
     * Static method to verify that a string conforms to the
     * #AARRGGBB hex colour specification.
     * 
     * @param hexCol The string to check.
     * @return true if the colour string is valid, false otherwise.
     */
    public static boolean checkColor(String hexCol) {       
        /** Check that the first character is a hash to indicate a hex value */
        if(!hexCol.startsWith("#")) {
            return false;
        }
        
        /** Check that the string has 9 characters */
        if(hexCol.length() != 9) {
            return false;
        }
        
        /** Check that the last 8 characters are numbers */
        for(int i = 1; i < 9; i++) {
            String s = String.valueOf(hexCol.charAt(i));
            
            if(!s.matches("[a-zA-Z0-9]")) {
                return false;
            }
        }
        
        /** If all checks pass return true */
        return true;
    }
    
    /**
     * Static method to check a colour string, and add an error to
     * an error list if the colour is invalid.
     * 
     * @param hexStr The string to check.
     * @param defaultStr The default to return if invalid.
     * @param errorLevel The level of error to log if invalid.
     * @param errorList The list to log the error in.
     * @param errorMessage The message to log with the error.
     * @return either the string if it is valid, or the default.
     */
    public static String checkColor(String hexStr, String defaultStr, Level errorLevel, ArrayList<XMLNotification> errorList, String errorMessage) {
        /* Return the default if the string to check is empty */
        if(hexStr == null) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " missing. Default inserted."));
            return defaultStr;
        }
        
        /* Check that the first character is a hash to indicate a hex value and that the string has 9 characters */
        if(!hexStr.startsWith("#") || hexStr.length() != 9) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " incorrect hex string format."));
            return defaultStr;
        }
        
        /* Check that the last 8 characters are hex digits */
        for(int i = 1; i < 9; i++) {
            String s = String.valueOf(hexStr.charAt(i));
            
            if(!s.matches("[a-zA-Z0-9]")) {
                errorList.add(new XMLNotification(errorLevel, errorMessage + " non hex digit found."));
                return defaultStr;
            }
        }
        
        /* If all checks pass return true */
        return hexStr;
    }
    
    /**
     * Static method to attempt to parse a float from a string, and log
     * an error if a value cannot be parsed.
     * 
     * @param str The string to parse from.
     * @param defaultValue The default value to insert if parsing fails.
     * @param errorLevel The level of the error to log if invalid.
     * @param errorList The list to log the error in.
     * @param errorMessage The message to log with the error.
     * @return either the value parsed from the string or the default if invalid.
     */
    public static float checkFloat(String str, float defaultValue, Level errorLevel, ArrayList<XMLNotification> errorList, String errorMessage) {
        float val = defaultValue;
        
        if(str == null) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " missing. Default inserted."));
        } else {
            try {
                val = Float.parseFloat(str);
            } catch (NumberFormatException e) {
                errorList.add(new XMLNotification(errorLevel, errorMessage + " non-float. Default inserted."));
            }
        }
        
        return val;
    }
    
    /**
     * Static method to attempt to parse a int from a string, and log
     * an error if a value cannot be parsed.
     * 
     * @param str The string to parse from.
     * @param defaultValue The default value to insert if parsing fails.
     * @param errorLevel The level of the error to log if invalid.
     * @param errorList The list to log the error in.
     * @param errorMessage The message to log with the error.
     * @return either the value parsed from the string or the default if invalid.
     */
    public static int checkInt(String str, int defaultValue, Level errorLevel, ArrayList<XMLNotification> errorList, String errorMessage) {
        int val = defaultValue;
        
        if(str == null) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " missing. Default inserted."));
        } else {
            try {
                val = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                errorList.add(new XMLNotification(errorLevel, errorMessage + " non-integer. Default inserted."));
            }
        }
        
        return val;
    }
    
    /**
     * A static method to attempt to parse a boolean from a string, and
     * add an error to an error list if invalid.
     * 
     * @param str The string to parse from.
     * @param defaultValue The default value to insert if invalid.
     * @param errorLevel The error level to log if invalid.
     * @param errorList The error list to log to.
     * @param errorMessage The message to log with the error.
     * @return the parsed value or the default if invalid.
     */
    public static boolean checkBool(String str, boolean defaultValue, Level errorLevel, ArrayList<XMLNotification> errorList, String errorMessage) {
        boolean val = defaultValue;
        
        if(str == null) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " missing. Default inserted."));
        } else {
            try {
                val = Boolean.parseBoolean(str);
            } catch (NumberFormatException e) {
                errorList.add(new XMLNotification(errorLevel, errorMessage + " non-boolean. Default inserted."));
            }
        }
        
        return val;
    }
}
