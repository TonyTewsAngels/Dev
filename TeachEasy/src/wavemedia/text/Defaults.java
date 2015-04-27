package wavemedia.text;

import javafx.scene.text.Font;

public class Defaults {

	private String backgroundColor = "#00FFFFFF";
	private String font = Font.getDefault().getName();
	private double fontSize = Font.getDefault().getSize();
	private String fontColor = "#FF000000";
	private String graphicColor = "#FF000000";
	private String highlightColor = "#FFFF0000";
	private float startTime = 0;
	private float duration = Float.MAX_VALUE;
	private String alignment = "left";
	private float scale = 1;
	private int rotation = 0;
	private float cropX1 = 0;
	private float cropY1 = 0;
	private float cropX2 = 1;
	private float cropY2 = 1;
	private boolean shapeSolidity = true;
	private float stopValue = 0.5f;
	private float outlineThickness = 1;
	private String outlineColor = "#FF000000";

	public Defaults() {

	}

	public void printDefaults() {
		System.out.println("Start of Document Defaults:");
		System.out.println("BackgroundColour: " + backgroundColor);
		System.out.println("Font: " + font);
		System.out.println("FontSize: " + fontSize);
		System.out.println("FontColor: " + fontColor);
		System.out.println("GraphicColor: " + graphicColor);
		System.out.println("HighlightColor: " + highlightColor);
		System.out.println("StartTime: " + startTime);
		System.out.println("Duration: " + duration);
		System.out.println("Alignment: " + alignment);
		System.out.println("Scale: " + scale);
		System.out.println("Rotation: " + rotation);
		System.out.println("CropX1: " + cropX1);
		System.out.println("CropY1: " + cropY1);
		System.out.println("CropX2: " + cropX2);
		System.out.println("CropY2: " + cropY2);
		System.out.println("End of Document Defaults\n");
	}

	/**
	 * @return the backgroundColour
	 */
	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * @param backgroundColour
	 *            the backgroundColour to set
	 */
	public void setBackgroundColor(String string) {
		if (Utils.validARGB(string)) {
			this.backgroundColor = string;
		}
	}
	
	/**
	 * @return the outlineColor
	 */
	public String getOutlineColor() {
		return this.outlineColor;
	}

	/**
	 * @param string
	 *            the outlineColor to set
	 */
	public void setOutlineColor(String string) {
		if (Utils.validARGB(string)) {
			this.outlineColor = string;
		}
	}
	
	public float getOutlineThickness() {
		return this.outlineThickness;
	}

	public void setOutlineThickness(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.outlineThickness = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return this.font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(String string) {
		string = Utils.capitaliseEachFirstLetter(string);
		if (Font.getFontNames().contains(string)) {
			this.font = string;
		}
	}

	/**
	 * @return the fontSize
	 */
	public double getFontSize() {
		return this.fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(String string) {
		try {
			double d = Double.parseDouble(string);
			if ((d > 0)) {
				this.fontSize = d;
			}
		} catch (Exception e) {
			/* Do nothing */
		}
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return this.fontColor;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(String string) {
		if (Utils.validARGB(string)) {
			this.fontColor = string;
		}
	}

	/**
	 * @return the graphicColour
	 */
	public String getGraphicColor() {
		return this.graphicColor;
	}

	/**
	 * @param fillColour
	 *            the graphicColour to set
	 */
	public void setGraphicColor(String string) {
		if (Utils.validARGB(string)) {
			this.graphicColor = string;
		}
	}

	/**
	 * @return the graphicColour
	 */
	public String getHighlightColor() {
		return this.highlightColor;
	}

	/**
	 * @param string
	 *            the graphicColor to set
	 */
	public void setHighlightColor(String string) {
		if (Utils.validARGB(string)) {
			this.highlightColor = string;
		}
	}

	/**
	 * @return the startTime
	 */
	public float getStartTime() {
		return this.startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f >= 0) {
				this.startTime = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the duration
	 */
	public float getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String string) {
		try {
			if (string.toLowerCase().matches("float.max_value|infinite")) {
				this.duration = Float.MAX_VALUE;
			} else {
				float f = Float.parseFloat(string);
				if (f > 0) {
					this.duration = f;
				}
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the alignment
	 */
	public String getAlignment() {
		return this.alignment;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(String string) {
		if (Utils.validAlignment(string)) {
			this.alignment = string;
		}
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return this.scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(String string) {
		try {
			float f = Float.parseFloat(string);
			if (f > 0) {
				this.scale = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the rotation
	 */
	public int getRotation() {
		return this.rotation;
	}

	/**
	 * @param rotation
	 *            the rotation to set
	 */
	public void setRotation(String string) {
		try {
			int i = Integer.parseInt(string);
			if (Utils.withinRangeInclusive(0, 360, i)) {
				this.rotation = i;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropX1
	 */
	public float getCropX1() {
		return this.cropX1;
	}

	/**
	 * @param cropX1
	 *            the cropX1 to set
	 */
	public void setCropX1(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropX1 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropY1
	 */
	public float getCropY1() {
		return this.cropY1;
	}

	/**
	 * @param cropY1
	 *            the cropY1 to set
	 */
	public void setCropY1(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropY1 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropX2
	 */
	public float getCropX2() {
		return this.cropX2;
	}

	/**
	 * @param cropX2
	 *            the cropX2 to set
	 */
	public void setCropX2(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropX2 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the cropY2
	 */
	public float getCropY2() {
		return this.cropY2;
	}

	/**
	 * @param cropY2
	 *            the cropY2 to set
	 */
	public void setCropY2(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.cropY2 = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the shapeSolidity
	 */
	public boolean getShapeSolidity() {
		return shapeSolidity;
	}

	/**
	 * @param string the shapeSolidity to set
	 */
	public void setShapeSolidity(String string) {
		this.shapeSolidity = Boolean.parseBoolean(string);
	}

	/**
	 * @return the stopValue
	 */
	public float getStopValue() {
		return stopValue;
	}
	
	/**
	 * @param cropX1
	 *            the cropX1 to set
	 */
	public void setStopValue(String string) {
		try {
			float f = Float.parseFloat(string);
			if (Utils.withinRangeInclusive(0, 1, f)) {
				this.stopValue = f;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}
}
