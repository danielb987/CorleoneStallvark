package electric;

import gui.LineStyle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Line
 */
public class Line extends Component {
	
	protected double _x2;
	protected double _y2;
	protected double _column2 = -1;
	protected double _row2 = -1;
	protected double _offsetX = 0;
	protected double _offsetY = 0;
	protected double _thickness;
	protected LineStyle _lineStyle = LineStyle.SOLID;
	protected Color _color;
	
	public Line(double column1, double row1, double column2, double row2, double thickness, LineStyle lineStyle) {
		super(column1, row1, null, null);
		_x2 = column2 * SPACING;
		_y2 = row2 * SPACING;
		_column2 = column2;
		_row2 = row2;
		_thickness = thickness;
		_lineStyle = lineStyle;
	}
	
	public Line(int column1, int row1, int column2, int row2, double thickness, LineStyle lineStyle) {
		super(column1, row1, null, null);
		_x2 = column2 * SPACING;
		_y2 = row2 * SPACING;
		_column2 = column2;
		_row2 = row2;
		_thickness = thickness;
		_lineStyle = lineStyle;
	}
	
	public Line(double column1, double row1, double column2, double row2, double thickness, Color color) {
		super(column1, row1, null, null);
		_x2 = column2 * SPACING;
		_y2 = row2 * SPACING;
		_column2 = column2;
		_row2 = row2;
		_thickness = thickness;
		_color = color;
	}
	
	public Line(int column1, int row1, int column2, int row2, double offsetX, double offsetY, double thickness, Color color) {
		super(column1, row1, null, null);
		_x2 = column2 * SPACING;
		_y2 = row2 * SPACING;
		_column2 = column2;
		_row2 = row2;
		_offsetX = offsetX;
		_offsetY = offsetY;
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
						graphics.draw(new Line2D.Double(_x+_offsetX, _y+_offsetY, _x2+_offsetX, _y2+_offsetY));
						break;
						
					case PRINT_CENTER:
						final double r = 0.3;
						graphics.setColor(Color.GREEN);
						graphics.fill(new Ellipse2D.Double((_x+_x2)/2+_offsetX-r, (_y+_y2)/2+_offsetY-r, r*2, r*2));
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
	
	public double getX2() {
		return _x2;
	}
	
	public double getY2() {
		return _y2;
	}
	
	@Override
	public int getNumPins() {
		return 0;
	}
	
}
