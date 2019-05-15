package electric;

import gui.LineStyle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Circle
 */
public class Circle extends Component {
	
	protected double _radius;
	protected double _radiusColumn;
	protected double _thickness;
	protected LineStyle _lineStyle = LineStyle.SOLID;
	protected Color _color;
	
	public Circle(double column, double row, double radiusColumn, double thickness, LineStyle lineStyle) {
		super(column, row, null, null);
		_radius = radiusColumn * SPACING;
		_radiusColumn = radiusColumn;
		_thickness = thickness;
		_lineStyle = lineStyle;
	}
	
	public Circle(double column, double row, double radiusColumn, double thickness, Color color) {
		super(column, row, null, null);
		_radius = radiusColumn * SPACING;
		_radiusColumn = radiusColumn;
		_thickness = thickness;
		_color = color;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		_lineStyle.setLineStyle(graphics, _thickness);
		graphics.setColor(_color);
		
		for (DrawingStyle drawingStyle : DrawingStyle.values()) {
			if (DrawingSettings.isDrawingStyleEnabled(drawingStyle)) {
				switch (drawingStyle) {
					case PRINT_SYMBOL:
						graphics.draw(new Ellipse2D.Double(_x-_radius, _y-_radius, _radius*2, _radius*2));
						break;
						
					case PRINT_CENTER:
						graphics.setColor(Color.GREEN);
						graphics.fill(new Ellipse2D.Double(_x - 1f/2, _y - 1f/2, 1, 1));
						break;
						
					case PRINT_CONNECTORS:
					case PRINT_OUTLINE:
					case PRINT_LABEL:
					case PRINT_TEXT:
						// Do nothing
						break;
						
					default:
						throw new UnsupportedOperationException();
				}
			}
		}
	}
	
	@Override
	public int getNumPins() {
		return 0;
	}

}
