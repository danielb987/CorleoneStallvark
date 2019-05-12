package electric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

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
						
					case PRINT_IDENTIFIER:
						break;
						
					default:
						throw new UnsupportedOperationException();
				}
			}
		}
	}
	
	private void printCenter(Graphics2D graphics) {
		graphics.setColor(Color.GREEN);
		
		graphics.fill(new Ellipse2D.Double(_x - 1f/2, _y - 1f/2, 1, 1));
	}
	
	private void printSymbol(Graphics2D graphics) {
		final double SPC = Component.SPACING;
		graphics.setColor(Color.BLUE);
//		graphics.setColor(Color.BLACK);
		
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
				graphics.fill(new Ellipse2D.Double(_x - SPC/2 - 3f/2, _y - 3f/2, 3, 3));
				break;
				
			case SOUTH:
				graphics.fill(new Ellipse2D.Double(_x - 3f/2, _y - SPC/2 - 3f/2, 3, 3));
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
				graphics.fill(new Ellipse2D.Double(_x - SPC - SPC/4, _y - SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x - SPC - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC*2 - SPC/4, _y - SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC*2 - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				break;
				
			case VERTICAL:
			case NORTH:
				graphics.fill(new Ellipse2D.Double(_x - SPC - SPC/4, _y - SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y - SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x - SPC - SPC/4, _y + SPC*2 - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y + SPC*2 - SPC/4, SPC/2, SPC/2));
				break;
				
			case WEST:
				graphics.fill(new Ellipse2D.Double(_x - SPC*2 - SPC/4, _y - SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x - SPC*2 - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y - SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				break;
				
			case SOUTH:
				graphics.fill(new Ellipse2D.Double(_x - SPC - SPC/4, _y - SPC*2 - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y - SPC*2 - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x - SPC - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				graphics.fill(new Ellipse2D.Double(_x + SPC - SPC/4, _y + SPC - SPC/4, SPC/2, SPC/2));
				break;
				
			default:
				throw new UnsupportedOperationException();
		}
	}
	
}
