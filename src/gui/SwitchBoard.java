package gui;

import electric.Button;
import electric.Component;
import static electric.Component.SPACING;
import electric.Line;
import static electric.Orientation.EAST;
import static electric.Orientation.NORTH;
import static electric.Orientation.SOUTH;
import static electric.Orientation.WEST;
import electric.SingleLED;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Ställverket
 */
public final class SwitchBoard {

	private final List<Component> components = new ArrayList<>();
	
	public SwitchBoard() {
		createBoard();
		createLayout();
		createComponents();
	}
	
	public void createBoard() {
		
		double thickness = 0.1;
		
		// Draw grid
		for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_HEIGHT; i++)
			components.add(new Line(0, i, (int)(MainPanel.STALLVERK_WIDTH/Component.SPACING), i, thickness, Color.LIGHT_GRAY));
		
		for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_WIDTH; i++)
			components.add(new Line(i, 0, i, (int)(MainPanel.STALLVERK_HEIGHT/Component.SPACING), thickness, Color.LIGHT_GRAY));
		
		// Draw board outline
		components.add(new Line(0, 0, 106, 0, thickness*2, Color.BLACK));
		components.add(new Line(0, 0, 0, 47, thickness*2, Color.BLACK));
		components.add(new Line(0, 47, 106, 47, thickness*2, Color.BLACK));
		components.add(new Line(106, 0, 106, 47, thickness*2, Color.BLACK));
	}
	
	public void createLayout() {
		int CY = 19;	// Y position of main track
		int DIST = 3;	// Y distance between tracks
		int t;
		
		// Main track
		components.add(new Line(0, CY, 106, CY, 1, Color.BLACK));
		
		// Main track on freight yard
		components.add(new Line(5, CY+DIST, 106, CY+DIST, 0.8, Color.BLACK));
		components.add(new Line(0, CY+DIST+5, 5, CY+DIST, 0.8, Color.BLACK));
		
		// Crossover at the left
		components.add(new Line(6, CY, 6 + DIST, CY + DIST, DIST/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(9, CY, EAST));
		components.add(new SingleLED(8, CY+1, NORTH));
		components.add(new SingleLED(6, CY+DIST, EAST));
		components.add(new Button(5, CY, EAST));
		
		// Crossover little right
		components.add(new Line(25, CY, 25 - DIST, CY + DIST, DIST/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(22, CY, EAST));
		components.add(new SingleLED(24, CY+1, NORTH));
		components.add(new SingleLED(25, CY+DIST, EAST));
		components.add(new Button(26, CY, EAST));
		
		// Siding for storage of passenger cars
		components.add(new Line(12, CY, 12 + DIST, CY - DIST, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(15, CY - DIST, 29, CY - DIST, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(15, CY, EAST));
		components.add(new SingleLED(14, CY-2, EAST));
		components.add(new Button(12, CY, EAST));
		components.add(new Line(29, CY - DIST, 29 + DIST, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(29, CY, EAST));
		components.add(new SingleLED(30, CY-2, EAST));
		components.add(new Button(32, CY, EAST));
		
		// Siding to the left from the above siding
		components.add(new Line(20, CY - DIST, 20 - DIST, CY - DIST*2, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(17, CY - DIST*2, 10, CY - DIST*2, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(17, CY - DIST, EAST));
		components.add(new SingleLED(18, CY - DIST - 2, EAST));
		components.add(new Button(20, CY - DIST, EAST));
		
		// Siding to the right from the above siding
		components.add(new Line(24, CY - DIST, 24 + DIST, CY - DIST*2, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(27, CY - DIST*2, 34, CY - DIST*2, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(27, CY - DIST, EAST));
		components.add(new SingleLED(26, CY - DIST - 2, EAST));
		components.add(new Button(24, CY - DIST, EAST));
	}
	
	public void createComponents() {
		components.add(new Button(2,4,EAST));
		components.add(new Button(12,4,NORTH));
		components.add(new Button(22,4,WEST));
		components.add(new Button(32,4,SOUTH));
	}
	
	public void draw(Graphics2D graphics)
	{
		for (Component component : components) {
			component.draw(graphics);
		}
	}
	
}
