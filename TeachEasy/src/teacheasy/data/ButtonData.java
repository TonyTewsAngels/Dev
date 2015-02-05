package teacheasy.data;

/** Class to hold Button Data */
public class ButtonData extends PageObject {
	
	public static enum ButtonInteractionType {
		NORMAL
	}
	
	float xEnd, yEnd, outlineThickness;
	boolean outline, shadow, visible;
	String text;
	int function;
	private ButtonInteractionType interaction;
	
	/** Contructor for ButtonData */
	public ButtonData(PageObjectType nType, float nXStart, float nYStart) {
		super(nType, nXStart, nYStart);
	}
	
	public float getxEnd() {
		return xEnd;
	}
	public void setxEnd(float xEnd) {
		this.xEnd = xEnd;
	}
	public float getyEnd() {
		return yEnd;
	}
	public void setyEnd(float yEnd) {
		this.yEnd = yEnd;
	}
	public float getOutlineThickness() {
		return outlineThickness;
	}
	public void setOutlineThickness(float outlineThickness) {
		this.outlineThickness = outlineThickness;
	}
	public boolean isOutline() {
		return outline;
	}
	public void setOutline(boolean outline) {
		this.outline = outline;
	}
	public boolean isShadow() {
		return shadow;
	}
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getFunction() {
		return function;
	}
	public void setFunction(int function) {
		this.function = function;
	}

	public ButtonInteractionType getInteraction() {
		return interaction;
	}

	public void setInteraction(ButtonInteractionType interaction) {
		this.interaction = interaction;
	}
	
	
	

}
