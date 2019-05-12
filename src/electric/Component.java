package electric;

import java.awt.Graphics2D;

/**
 *
 */
public abstract class Component {

	public static final double SPACING = 2.54;
	
	protected double _x;
	protected double _y;
	protected double _column = -1;
	protected double _row = -1;
	protected Orientation _orientation;
	
	protected Component(double column, double row, Orientation orientation) {
		_x = column * SPACING;
		_y = row * SPACING;
		_orientation = orientation;
	}
	
	abstract public void draw(Graphics2D graphics);
	
}
