package gui;

import electric.Button;
import electric.Component;
import static electric.Component.SPACING;
import electric.Label;
import electric.Line;
import electric.Orientation;
import static electric.Orientation.EAST;
import static electric.Orientation.NORTH;
import static electric.Orientation.SOUTH;
import static electric.Orientation.WEST;
import electric.SingleLED;
import electric.Text;
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
		components.add(new Line(4, CY+4, 106, CY+4, 0.8, Color.BLACK));
		components.add(new Line(0, CY+8, 4, CY+4, 0.8, Color.BLACK));
		
		// Crossover at the left
		components.add(new Line(4, CY, 8, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(7, CY, EAST, new Label("D1",0,-2)));
		components.add(new SingleLED(6, CY+2, EAST, new Label("D2",0,-2)));
		components.add(new SingleLED(5, CY+4, EAST, new Label("D3",0,-2)));
		components.add(new Button(4, CY, EAST, new Label("S1",0,-2)));
		
		// Crossover little right
		components.add(new Line(25, CY, 25 - 4, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(22, CY, EAST, new Label("D4",0,-2)));
		components.add(new SingleLED(23, CY+2, EAST, new Label("D5",0,-2)));
		components.add(new SingleLED(24, CY+4, EAST, new Label("D6",0,-2)));
		components.add(new Button(25, CY, EAST, new Label("S2",0,-2)));
		
		// Siding for storage of passenger cars
		components.add(new Line(10, CY, 13, CY - 3, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(13, CY - 3, 28, CY - 3, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(13, CY, EAST, new Label("D7",0,-2)));
		components.add(new SingleLED(12, CY-2, EAST, new Label("D8",0,-2)));
		components.add(new Button(10, CY, EAST, new Label("S3",0,-2)));
		components.add(new Line(28, CY - 3, 31, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(28, CY, EAST, new Label("D9",0,-2)));
		components.add(new SingleLED(29, CY-2, EAST, new Label("D10",0,-2)));
		components.add(new Button(31, CY, EAST, new Label("S4",0,-2)));
		
		// Siding to the left from the above siding
		components.add(new Line(18, CY - 3, 15, CY - 6, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(15, CY - 6, 8, CY - 6, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(15, CY - 3, EAST, new Label("D11",0,-2)));
		components.add(new SingleLED(16, CY - 3 - 2, EAST, new Label("D12",0,-2)));
		components.add(new Button(18, CY - 3, EAST, new Label("S5",0,-2)));
		
		// Siding to the right from the above siding
		components.add(new Line(23, CY - 3, 23 + 3, CY - 3*2, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(26, CY - 3*2, 40, CY - 3*2, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(26, CY - 3, EAST, new Label("D13",0,-2)));
		components.add(new SingleLED(25, CY - 3 - 2, EAST, new Label("D14",0,-2)));
		components.add(new Button(23, CY - 3, EAST, new Label("S6",0,-2)));
		
		// Siding to the right from the above siding
		components.add(new Line(29, CY - 3*2, 29 + 3, CY - 3*3, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(32, CY - 3*3, 40, CY - 3*3, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(32, CY - 3*2, EAST, new Label("D15",0,-2)));
		components.add(new SingleLED(31, CY - 3*2 - 2, EAST, new Label("D16",0,-2)));
		components.add(new Button(29, CY - 3*2, EAST, new Label("S7",0,-2)));
		
		
		
		
		// Passenger track 3
		components.add(new Line(36, CY, 36 + 4, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(40, CY - 4, 59, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(39, CY, EAST, new Label("D17",0,-2)));
		components.add(new SingleLED(38, CY-2, EAST, new Label("D18",0,-2)));
		components.add(new Button(36, CY, EAST, new Label("S8",0,-2)));
		components.add(new Line(29+1, CY - 3, 29 + 3, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(29+1+20, CY, EAST, new Label("D19",0,-2)));
		components.add(new SingleLED(30+1+20, CY-2, EAST, new Label("D20",0,-2)));
		components.add(new Button(32+1+20, CY, EAST, new Label("S9",0,-2)));
		components.add(new Text(49, CY-4, Orientation.EAST, new Label("3",0,0,5,true)));
		
		components.add(new Text(49, CY, Orientation.EAST, new Label("4",0,0,5,true)));
		components.add(new Text(49, CY+4, Orientation.EAST, new Label("5",0,0,5,true)));
		components.add(new Text(49, CY+8, Orientation.EAST, new Label("6",0,0,5,true)));
		components.add(new Text(49, CY+12, Orientation.EAST, new Label("7",0,0,5,true)));
		components.add(new Text(49, CY+15, Orientation.EAST, new Label("8",0,0,5,true)));
		
		// Passenger track 2
		components.add(new Line(40, CY-4, 44, CY - 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(44, CY - 8, 54, CY - 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(43, CY-4, EAST, new Label("D21",0,-2)));
		components.add(new SingleLED(42, CY-4-2, EAST, new Label("D22",0,-2)));
		components.add(new Button(40, CY-4, EAST, new Label("S10",0,-2)));
/*		components.add(new Line(29, CY - 4, 29 + 4*2, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(29, CY, EAST, new Label("D23",0,-2)));
		components.add(new SingleLED(30, CY-2, EAST, new Label("D24",0,-2)));
		components.add(new Button(32, CY, EAST, new Label("S11",0,-2)));
*/		
		components.add(new Text(49, CY-8, Orientation.EAST, new Label("2",0,0,5,true)));
		
		// Passenger track 1
		components.add(new Line(44, CY-8, 47, CY - 11, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(47, CY - 11, 51, CY - 11, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(47, CY-8, EAST, new Label("D25",0,-2)));
		components.add(new SingleLED(46, CY-8-2, EAST, new Label("D26",0,-2)));
		components.add(new Button(44, CY-8, EAST, new Label("S12",0,-2)));
		components.add(new Line(51, CY-11, 51 + 3, CY-8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(51, CY-8, EAST, new Label("D27",0,-2)));
		components.add(new SingleLED(52, CY-8-2, EAST, new Label("D28",0,-2)));
		components.add(new Button(54, CY-8, EAST, new Label("S13",0,-2)));
		components.add(new Text(49, CY-11, Orientation.EAST, new Label("1",0,0,5,true)));
		
		
		// -------------------------------------------------
		
		
		
		// Siding for cabooses
		components.add(new Line(17, CY + 4, 17 + 3, CY + 7, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(20, CY + 7, 34, CY + 7, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(20, CY + 4, EAST, new Label("D101",0,-2)));
		components.add(new SingleLED(19, CY + 4 + 2, EAST, new Label("D102",0,-2)));
		components.add(new Button(17, CY + 4, EAST, new Label("S101",0,-2)));
		
		// Siding for engines
		components.add(new Line(32, CY + 7, 32 - 3, CY + 10, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(20, CY + 10, 34, CY + 10, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(20, CY + 7, EAST, new Label("D103",0,-2)));
		components.add(new SingleLED(19, CY + 7 + 2, EAST, new Label("D104",0,-2)));
		components.add(new Button(17, CY + 7, EAST, new Label("S102",0,-2)));
	}
	
	
	public void createComponents() {
		components.add(new Button(2,4,EAST, new Label("Sa",0,-2)));
		components.add(new Button(12,4,NORTH, new Label("Sb",0,-2)));
		components.add(new Button(22,4,WEST, new Label("Sc",0,-2)));
		components.add(new Button(32,4,SOUTH, new Label("Sd",0,-2)));
	}
	
	
	public void draw(Graphics2D graphics)
	{
		for (Component component : components) {
			component.draw(graphics);
		}
	}
	
}
