package teacheasy.xml.util;

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
}
