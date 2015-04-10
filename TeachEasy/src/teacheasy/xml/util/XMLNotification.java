package teacheasy.xml.util;

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
}
