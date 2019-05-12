package electric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Button
 */
public class SingleLED extends Component {

	protected Label _label;
	
	public SingleLED(int column, int row, Orientation orientation, Label label) {
		super(column, row, orientation);
		_label = label;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		for (DrawingStyle drawingStyle : DrawingStyle.values()) {
			if (DrawingSettings.isDrawingStyleEnabled(drawingStyle)) {
				switch (drawingStyle) {
					case PRINT_CENTER:
						printCenter(graphics);
						break;
						
					case PRINT_SYMBOL:
						printSymbol(graphics);
						break;
						
					case PRINT_CONNECTORS:
						printConnectors(graphics);
						break;
						
					case PRINT_OUTLINE:
						break;
						
					case PRINT_LABEL:
						printLabel(graphics);
						break;
						
					default:
						throw new UnsupportedOperationException();
				}
			}
		}
	}
	
	private void printCenter(Graphics2D graphics) {
		graphics.setColor(Color.GREEN);
		graphics.setColor(Color.BLACK);
		
		graphics.fill(new Ellipse2D.Double(_x - 1f/4, _y - 1f/4, 0.5, 0.5));
	}
	
	private void printLabel(Graphics2D graphics) {
		final double SPC = Component.SPACING;
		graphics.setColor(Color.BLUE);
//		graphics.setColor(Color.BLACK);
		
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
	
	private void printSymbol(Graphics2D graphics) {
		final double SPC = Component.SPACING;
//		graphics.setColor(Color.RED);
		graphics.setColor(Color.GREEN);
		
		switch (_orientation) {
			case HORIZONTAL:
			case EAST:
				graphics.fill(new Ellipse2D.Double(_x + SPC/2 - 3f/2, _y - 3f/2, 3, 3));
				break;
				
			case VERTICAL:
			case NORTH:
				graphics.fill(new Ellipse2D.Double(_x - 3f/2, _y + SPC/2 - 3f/2, 3, 3));
				break;
				
			case WEST:
				break;
				
			case SOUTH:
				break;
				
			default:
				throw new UnsupportedOperationException();
		}
	}
	
	private void printConnectors(Graphics2D graphics) {
		final double SPC = Component.SPACING;
		graphics.setColor(Color.RED);
		
		switch (_orientation) {
			case HORIZONTAL:
			case EAST:
				graphics.fill(new Ellipse2D.Double(_x - SPC/4, _y - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y - SPC/4, SPC/2, SPC/2));
				break;
				
			case VERTICAL:
			case NORTH:
				graphics.fill(new Ellipse2D.Double(_x - SPC/4, _y - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				break;
				
			case WEST:
				break;
				
			case SOUTH:
				break;
				
			default:
				throw new UnsupportedOperationException();
		}
	}
	
}