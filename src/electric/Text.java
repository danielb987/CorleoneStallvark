package electric;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Text
 */
public class Text extends Component {

	public Text(double column, double row, Orientation orientation, Label label) {
		super(column, row, orientation, label);
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		for (DrawingStyle drawingStyle : DrawingStyle.values()) {
			if (DrawingSettings.isDrawingStyleEnabled(drawingStyle)) {
				switch (drawingStyle) {
					case PRINT_TEXT:
						printLabel(graphics);
						break;
						
					case PRINT_CENTER:
					case PRINT_SYMBOL:
					case PRINT_CONNECTORS:
					case PRINT_OUTLINE:
					case PRINT_LABEL:
						// Do nothing
						break;
						
					default:
						throw new UnsupportedOperationException();
				}
			}
		}
	}
	
	private void printLabel(Graphics2D graphics) {
		final double SPC = Component.SPACING;
//		graphics.setColor(Color.BLUE);
		graphics.setColor(Color.BLACK);
		
		switch (_orientation) {
			case HORIZONTAL:
			case EAST:
				_label.draw(graphics, _x + SPC/2, _y);
				break;
				
			case VERTICAL:
			case NORTH:
				_label.draw(graphics, _x, _y + SPC/2);
				break;
				
			case WEST:
				_label.draw(graphics, _x - SPC/2, _y);
				break;
				
			case SOUTH:
				_label.draw(graphics, _x, _y - SPC/2);
				break;
				
			default:
				throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public int getNumPins() {
		return 0;
	}
	
}
