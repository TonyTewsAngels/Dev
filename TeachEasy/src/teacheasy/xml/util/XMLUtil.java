package teacheasy.xml.util;

import java.util.ArrayList;

import teacheasy.xml.util.XMLNotification.Level;

public class XMLUtil {
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
    
    public static String checkColor(String hexStr, String defaultStr, Level errorLevel, ArrayList<XMLNotification> errorList, String errorMessage) {              
        if(hexStr == null) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " missing. Default inserted."));
            return defaultStr;
        }
        
        /** Check that the first character is a hash to indicate a hex value and that the string has 9 characters */
        if(!hexStr.startsWith("#") || hexStr.length() != 9) {
            errorList.add(new XMLNotification(errorLevel, errorMessage + " incorrect hex string format."));
            return defaultStr;
        }
        
        /** Check that the last 8 characters are hex digits */
        for(int i = 1; i < 9; i++) {
            String s = String.valueOf(hexStr.charAt(i));
            
            if(!s.matches("[a-zA-Z0-9]")) {
                errorList.add(new XMLNotification(errorLevel, errorMessage + " non hex digit found."));
                return defaultStr;
            }
        }
        
        /** If all checks pass return true */
        return hexStr;
    }
    
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
