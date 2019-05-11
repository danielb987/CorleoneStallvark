package gui;

import java.awt.BasicStroke;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * LineStyle
 */
public enum LineStyle {

	SOLID,
	DOTTED;
	
	private static final float[] DOTTED_ARRAY = {0.0f, 0.2f};
	
	public void setLineStyle(Graphics2D g, double thickness) {
		Stroke stroke;
		
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		switch (this) {
			case SOLID:
				stroke = new BasicStroke((float) thickness, CAP_ROUND, JOIN_ROUND);
				break;
				
			case DOTTED:
				float[] dottedArray = {0.0f, (float)thickness*2};
				stroke = new BasicStroke((float) thickness, CAP_ROUND, JOIN_ROUND, (float) thickness, dottedArray, 0f);
				break;
				
			default:
				throw new IllegalArgumentException("Line style not defined: "+this.name());
		}
		
		g.setStroke(stroke);
	}
	
}
