package teacheasy.mediahandler.video;

public class FullscreenInfo {
    private double originalX;
    private double originalY;
    private double originalWidth;
    private boolean fullscreen;
    
    public FullscreenInfo(double nOriginalX, double nOriginalY, double nOriginalWidth, boolean nFullscreen) {
        setOriginalX(nOriginalX);
        setOriginalY(nOriginalY);
        setOriginalWidth(nOriginalWidth);
        setFullscreen(nFullscreen);
    }
    
    public double getOriginalX() {
        return originalX;
    }
    public void setOriginalX(double originalX) {
        this.originalX = originalX;
    }

    public double getOriginalY() {
        return originalY;
    }

    public void setOriginalY(double originalY) {
        this.originalY = originalY;
    }

    public double getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(double originalWidth) {
        this.originalWidth = originalWidth;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
}
