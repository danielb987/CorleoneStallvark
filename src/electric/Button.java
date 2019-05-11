package electric;

import java.awt.Graphics2D;

/**
 * Button
 */
public class Button extends Component {

	public Button(double x, double y, Orientation orientation) {
		super(x, y, orientation);
	}
	
	public Button(int column, int row, Orientation orientation) {
		super(column, row, orientation);
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		for (DrawingStyle drawingStyle : DrawingStyle.values()) {
			if (DrawingSettings.isDrawingStyleEnabled(drawingStyle)) {
				switch (drawingStyle) {
					case PRINT_SYMBOL:
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
