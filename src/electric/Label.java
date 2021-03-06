package electric;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Label
 */
public class Label {

	private static final Set<String> ALL_LABELS = new HashSet<>();
	
	private String _name;
	private double _relX;
	private double _relY;
	private int _degrees;
	private int _fontSize;
	private boolean _clear;
	
	public Label(String name, double relX, double relY) {
		_name = name;
		_relX = relX;
		_relY = relY;
		_fontSize = 1;
		
		if (!ALL_LABELS.add(name)) {
			throw new IllegalArgumentException("Label already exists: "+name);
		}
	}
	
	public Label(String name, double relX, double relY, int fontSize, boolean clear) {
		this(name, relX, relY, 0, fontSize, clear, true);
	}
	
	public Label(String name, double relX, double relY, int degrees, int fontSize, boolean clear) {
		this(name, relX, relY, degrees, fontSize, clear, true);
	}
	
	public Label(String name, double relX, double relY, int fontSize, boolean clear, boolean checkDuplicate) {
		this(name, relX, relY, 0, fontSize, clear, checkDuplicate);
	}
	
	public Label(String name, double relX, double relY, int degrees, int fontSize, boolean clear, boolean checkDuplicate) {
		_name = name;
		_relX = relX;
		_relY = relY;
		_degrees = degrees;
		_fontSize = fontSize;
		_clear = clear;
		
		if (checkDuplicate && !ALL_LABELS.add(name)) {
			throw new IllegalArgumentException("Label already exists: "+name);
		}
	}
	
	public void draw(Graphics2D graphics, double x, double y) {
		
		double padX = 0.2;
		double padY = 0.2;
		
		Font font = new Font("Verdana", Font.PLAIN, _fontSize);
		graphics.setFont(font);
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		FontRenderContext frc = graphics.getFontRenderContext();
//		Font font1 = new Font("Courier", Font.BOLD, 24);
		TextLayout tl = new TextLayout(_name, font, frc);
		Rectangle2D bounds = tl.getBounds();
		
		AffineTransform origAT = graphics.getTransform();
		
		if (_degrees != 0) {
			AffineTransform at = new AffineTransform();
			at.setToRotation(Math.toRadians(_degrees), x+_relX, y+_relY);
			graphics.transform(at);
		}
		
		if (_clear) {
//			graphics.setColor(Color.GREEN);
			graphics.setColor(Color.WHITE);
			graphics.fill(new Rectangle2D.Double(x+_relX-bounds.getCenterX()-padX, y+_relY-bounds.getCenterY()-bounds.getHeight()-padY, bounds.getCenterX()*2+padX*2, bounds.getHeight()+padY*2));
//			graphics.setColor(Color.RED);
//			graphics.fill(new Rectangle2D.Double(x+_relX-bounds.getCenterX(), y+_relY-bounds.getCenterY()-bounds.getHeight(), bounds.getCenterX()*2, bounds.getHeight()));
		}
		
		graphics.setColor(Color.BLACK);
		tl.draw(graphics, (float)(x+_relX-bounds.getCenterX()), (float)(y+_relY-bounds.getCenterY()));
		
		graphics.setTransform(origAT);
	}
	
	public String getName() {
		return _name;
	}
	
/*
*/	
}
