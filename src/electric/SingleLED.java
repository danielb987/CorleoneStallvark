package electric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

/**
 * Button
 */
public class SingleLED extends Component {

	public enum LED_Color {
		RED,
		GREEN,
		YELLOW,
		GREEN_YELLOW,
		BLUE,
	}
	
	public static boolean printLED = true;
	
	public boolean isLit = true;
//	private LED_Color _ledColor = LED_Color.BLUE;
	private LED_Color _ledColor = LED_Color.YELLOW;
	
	public SingleLED(int column, int row, Orientation orientation, Label label) {
		super(column, row, orientation, label);
	}
	
	public SingleLED(int column, int row, Orientation orientation, Label label, LED_Color ledColor) {
		super(column, row, orientation, label);
		this._ledColor = ledColor;
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
						
					case PRINT_LABEL:
						printLabel(graphics);
						break;
						
					case PRINT_OUTLINE:
					case PRINT_TEXT:
						// Do nothing
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
	
	private void printCircle(Graphics2D graphics, double x, double y, Color c1, Color c2) {
		graphics.setColor(c1);
		graphics.fill(new Ellipse2D.Double(x, y, 3, 3));
		
		if (c2 != null) {
			Shape clip = graphics.getClip();
			GeneralPath gp = new GeneralPath();
			gp.moveTo(x, y);
			gp.lineTo(x, y+3);
			gp.lineTo(x+3, y);
			gp.closePath();
//			gp.lineTo(x, y);
			graphics.setClip(gp);
			graphics.setColor(c2);
			graphics.fill(new Ellipse2D.Double(x, y, 3, 3));
			graphics.setClip(clip);
		}
	}
	
	private void printSymbol(Graphics2D graphics) {
		
		Color c1;
		Color c2 = null;
		
		final double SPC = Component.SPACING;
		
		if (printLED) {
			if (isLit) {
				graphics.setColor(Color.YELLOW);
				
				switch (_ledColor) {
					case RED: c1 = new Color(255,63,63); break;
					case GREEN_YELLOW: c1 = Color.GREEN; c2 = Color.YELLOW; break;
					case GREEN: c1 = Color.GREEN; break;
					case YELLOW: c1 = Color.YELLOW; break;
					case BLUE: c1 = Color.BLUE; break;
					default: throw new RuntimeException("ledColor has invalid value: "+_ledColor.name());
				}
			} else {
				c1 = Color.LIGHT_GRAY;
			}
		} else {
			c1 = Color.GREEN;
		}
		
		switch (_orientation) {
			case HORIZONTAL:
			case EAST:
				printCircle(graphics, _x + SPC/2 - 3f/2, _y - 3f/2, c1, c2);
//				graphics.fill(new Ellipse2D.Double(_x + SPC/2 - 3f/2, _y - 3f/2, 3, 3));
				break;
				
			case VERTICAL:
			case NORTH:
				printCircle(graphics, _x - 3f/2, _y + SPC/2 - 3f/2, c1, c2);
//				graphics.fill(new Ellipse2D.Double(_x - 3f/2, _y + SPC/2 - 3f/2, 3, 3));
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
	
	@Override
	public int getNumPins() {
		return 2;
	}
	
}
