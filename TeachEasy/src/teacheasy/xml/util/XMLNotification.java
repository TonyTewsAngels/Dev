package teacheasy.xml.util;

import java.util.ArrayList;

public class XMLNotification {
    public enum Level {
        WARNING,
        ERROR
    }
    
    private Level level;
    private String text;
    
    public XMLNotification(Level nLevel, String nText) {
        this.level = nLevel;
        this.text = nText;
    }
    
    public String getText() {
        return text;
    }
    
    public Level getLevel() {
        return level;
    }
    
    public String toString() {
        return new String("[" + level.toString() + "] " + text);
    }
    
    public static int countLevel(ArrayList<XMLNotification> errorList, Level level) {
        int n = 0;
        
        for(XMLNotification i : errorList) {
            if(i.getLevel() == level) {
                n++;
            }
        }
        
        return n;
    }
}
