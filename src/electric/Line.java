package electric;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Button
 */
public class Line extends Component {

	protected double _x2;
	protected double _y2;
	protected int _column2 = -1;
	protected int _row2 = -1;
	
	public Line(double x1, double y1, double x2, double y2) {
		super(x1, y1, null);
		_x2 = x2;
		_y2 = y2;
	}
	
	public Line(int column1, int row1, int column2, int row2) {
		super(column1, row1, null);
		_x2 = column2 * SPACING;
		_y2 = row2 * SPACING;
		_column2 = column2;
		_row2 = row2;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		for (DrawingStyle drawingStyle : DrawingStyle.values()) {
			if (DrawingSettings.isDrawingStyleEnabled(drawingStyle)) {
				switch (drawingStyle) {
					case PRINT_SYMBOL:
						graphics.draw(new Line2D.Double(_x, _y, _x2, _y2));
						break;
						
					case PRINT_CONNECTORS:
						break;
						
					case PRINT_OUTLINE:
						break;
						
					case PRINT_IDENTIFIER:
						break;
						
					default:
						throw new UnsupportedOperationException();
				}
			}
		}
	}

}
