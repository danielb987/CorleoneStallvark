package electric;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/**
 * Label
 */
public class Label {

	private String _name;
	private double _relX;
	private double _relY;
	
	public Label(String name, double relX, double relY) {
		_name = name;
		_relX = relX;
		_relY = relY;
	}
	
	public void draw(Graphics2D graphics, double x, double y) {
		Font font = new Font("Verdana", Font.PLAIN, 1);
		graphics.setFont(font);
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		FontRenderContext frc = graphics.getFontRenderContext();
//		Font font1 = new Font("Courier", Font.BOLD, 24);
		TextLayout tl = new TextLayout(_name, font, frc);
		Rectangle2D bounds = tl.getBounds();
		graphics.setColor(Color.BLACK);
		tl.draw(graphics, (float)(x+_relX-bounds.getCenterX()), (float)(y+_relY-bounds.getCenterY()));
	}
	
/*
*/	
}
