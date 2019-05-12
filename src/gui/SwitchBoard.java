package gui;

import electric.Button;
import electric.Circle;
import electric.Component;
import static electric.Component.SPACING;
import electric.Label;
import electric.Line;
import electric.Orientation;
import static electric.Orientation.EAST;
import static electric.Orientation.NORTH;
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
	}
	
	public void createBoard() {
		
		double thickness = 0.1;
		
		// Draw grid
		for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_HEIGHT; i++)
			components.add(new Line(0, i, (int)(MainPanel.STALLVERK_WIDTH/Component.SPACING), i, thickness, Color.LIGHT_GRAY));
		
		for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_WIDTH; i++)
			components.add(new Line(i, 0, i, (int)(MainPanel.STALLVERK_HEIGHT/Component.SPACING), thickness, Color.LIGHT_GRAY));
		
		// Draw board outline
		components.add(new Line(0, 0, 89, 0, thickness*2, Color.BLACK));
		components.add(new Line(0, 0, 0, 39, thickness*2, Color.BLACK));
		components.add(new Line(0, 39, 89, 39, thickness*2, Color.BLACK));
		components.add(new Line(89, 0, 89, 39, thickness*2, Color.BLACK));
	}
	
	public void createLayout() {
//		int CY = 19;	// Y position of main track
		int CY = 14;	// Y position of main track
		int DIST = 3;	// Y distance between tracks
		int t;
		
		// Main track
//		components.add(new Line(0, CY, 106, CY, 1, Color.BLACK));
		components.add(new Line(0, CY, 89, CY, 1, Color.BLACK));
		
		// Vertical Main track
		components.add(new Line(85, 0, 85, 39, 1, Color.BLACK));
		
		// Main track on freight yard
		components.add(new Line(4, CY+4, 77, CY+4, 0.8, Color.BLACK));
		components.add(new Line(0, CY+8, 4, CY+4, 0.8, Color.BLACK));
		components.add(new Line(77, CY+4, 81, CY+8, 0.8, Color.BLACK));
		components.add(new Line(81, CY+8, 81, 39, 0.8, Color.BLACK));
		
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
		
		// Crossover little left to the right
		components.add(new Line(67, CY, 63, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(64, CY, EAST, new Label("D201",0,-2)));
		components.add(new SingleLED(65, CY+2, EAST, new Label("D202",0,-2)));
		components.add(new SingleLED(66, CY+4, EAST, new Label("D203",0,-2)));
//		components.add(new Button(25, CY, EAST, new Label("S2+",0,-2)));
		components.add(new Button(67, CY, EAST, new Label("S201",0,-2)));
		
		// Crossover at the right
		components.add(new Line(71, CY, 75, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(74, CY, EAST, new Label("D204",0,-2)));
		components.add(new SingleLED(73, CY+2, EAST, new Label("D205",0,-2)));
		components.add(new SingleLED(72, CY+4, EAST, new Label("D206",0,-2)));
//		components.add(new Button(4, CY, EAST, new Label("S1+",0,-2)));
		components.add(new Button(71, CY, EAST, new Label("S202",0,-2)));
		
		// Crossover vertical 1
		components.add(new Line(81, CY+9, 85, CY+13, 0, 3/2f, 0.5, Color.BLACK));
		components.add(new SingleLED(85, CY+10, NORTH, new Label("D207",3,0)));
		components.add(new SingleLED(83, CY+11, NORTH, new Label("D208",0,-3)));
		components.add(new SingleLED(81, CY+12, NORTH, new Label("D209",-3,0)));
//		components.add(new Button(4, CY, EAST, new Label("S1+",0,-2)));
		components.add(new Button(85, CY+13, NORTH, new Label("S203",3,0)));
		
		// Crossover vertical 2
		components.add(new Line(85, CY+17, 81, CY+21, 0, 3/2f, 0.5, Color.BLACK));
		components.add(new SingleLED(85, CY+20, NORTH, new Label("D210",3,0)));
		components.add(new SingleLED(83, CY+19, NORTH, new Label("D211",0,-3)));
		components.add(new SingleLED(81, CY+18, NORTH, new Label("D212",-3,0)));
//		components.add(new Button(4, CY, EAST, new Label("S1+",0,-2)));
		components.add(new Button(85, CY+17, NORTH, new Label("S204",3,0)));
		
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
		components.add(new Text(20.5, CY-3, Orientation.EAST, new Label("11",0,0,4,true)));
		
		// Siding to the left from the above siding
		components.add(new Line(18, CY - 3, 15, CY - 6, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(15, CY - 6, 8, CY - 6, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(15, CY - 3, EAST, new Label("D11",0,-2)));
		components.add(new SingleLED(16, CY - 3 - 2, EAST, new Label("D12",0,-2)));
		components.add(new Button(18, CY - 3, EAST, new Label("S5",0,-2)));
		components.add(new Text(11.5, CY-6, Orientation.EAST, new Label("12",0,0,4,true)));
		
		// Siding to the right from the above siding
		components.add(new Line(23, CY - 3, 27, CY - 7, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(27, CY - 7, 38, CY - 7, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(26, CY - 3, EAST, new Label("D13",0,-2)));
		components.add(new SingleLED(25, CY - 3 - 2, EAST, new Label("D14",0,-2)));
		components.add(new Button(23, CY - 3, EAST, new Label("S6",0,-2)));
		components.add(new Text(34, CY-7, Orientation.EAST, new Label("13",0,0,4,true)));
		
		// Siding to the right from the above siding
		components.add(new Line(27, CY - 7, 27 + 3, CY - 10, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(30, CY - 10, 38, CY - 10, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(30, CY - 7, EAST, new Label("D15",0,-2)));
		components.add(new SingleLED(29, CY - 7 - 2, EAST, new Label("D16",0,-2)));
		components.add(new Button(27, CY - 7, EAST, new Label("S7",0,-2)));
		components.add(new Text(34, CY-10, Orientation.EAST, new Label("14",0,0,4,true)));
		
		
		
		
		// Passenger track 3
		components.add(new Line(41, CY, 45, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(45, CY - 4, 57, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(44, CY, EAST, new Label("D23",0,-2)));
		components.add(new SingleLED(43, CY-2, EAST, new Label("D24",0,-2)));
		components.add(new Button(41, CY, EAST, new Label("S11",0,-2)));
		components.add(new Line(57, CY - 4, 61, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(58, CY, EAST, new Label("D25",0,-2)));
		components.add(new SingleLED(59, CY-2, EAST, new Label("D26",0,-2)));
		components.add(new Button(61, CY, EAST, new Label("S12",0,-2)));
		components.add(new Text(48, CY-4, Orientation.EAST, new Label("3",0,0,4,true)));
		
		// Passenger track 2
		components.add(new Line(35, CY, 43, CY - 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(43, CY - 8, 61, CY - 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(38, CY, EAST, new Label("D17",0,-2)));
		components.add(new SingleLED(37, CY-2, EAST, new Label("D18",0,-2)));
		components.add(new Button(35, CY, EAST, new Label("S8",0,-2)));
		components.add(new Line(53, CY - 8, 57, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Text(48, CY-8, Orientation.EAST, new Label("2",0,0,4,true)));
		
		// Engine storage track
		components.add(new SingleLED(56, CY-8, EAST, new Label("D27",0,-2)));
		components.add(new SingleLED(55, CY-8+2, EAST, new Label("D28",0,-2)));
		components.add(new SingleLED(54, CY-4, EAST, new Label("D29",0,-2)));
		components.add(new Button(57, CY-4, EAST, new Label("S13",0,-2)));
		
//		components.add(new SingleLED(29+1+20, CY, EAST, new Label("D19",0,-2)));
//		components.add(new SingleLED(30+1+20, CY-2, EAST, new Label("D20",0,-2)));
//		components.add(new Button(32+1+20, CY, EAST, new Label("S9",0,-2)));
//		components.add(new Text(49, CY-8, Orientation.EAST, new Label("2",0,0,4,true)));
		
		// Passenger track 1
		components.add(new Line(43, CY-8, 46, CY - 11, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(46, CY - 11, 50, CY - 11, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(46, CY-8, EAST, new Label("D19",0,-2)));
		components.add(new SingleLED(45, CY-8-2, EAST, new Label("D20",0,-2)));
		components.add(new Button(43, CY-8, EAST, new Label("S9",0,-2)));
		components.add(new Line(50, CY-11, 53, CY-8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(50, CY-8, EAST, new Label("D21",0,-2)));
		components.add(new SingleLED(51, CY-8-2, EAST, new Label("D22",0,-2)));
		components.add(new Button(53, CY-8, EAST, new Label("S10",0,-2)));
		components.add(new Text(48, CY-11, Orientation.EAST, new Label("1",0,0,4,true)));
		
		components.add(new Text(48, CY, Orientation.EAST, new Label("4",0,0,4,true)));
		components.add(new Text(48, CY+4, Orientation.EAST, new Label("5",0,0,4,true)));
		components.add(new Text(48, CY+8, Orientation.EAST, new Label("6",0,0,4,true)));
		components.add(new Text(48, CY+12, Orientation.EAST, new Label("7",0,0,4,true)));
		components.add(new Text(48, CY+15, Orientation.EAST, new Label("8",0,0,4,true)));
/*		
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
		
		
		// -------------------------------------------------
		
		
		
		// Siding for cabooses
		components.add(new Line(14, CY + 4, 18, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(18, CY + 8, 27, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(17, CY + 4, EAST, new Label("D101",0,-2)));
		components.add(new SingleLED(16, CY + 4 + 2, EAST, new Label("D102",0,-2)));
		components.add(new Button(14, CY + 4, EAST, new Label("S101",0,-2)));
		components.add(new Line(31, CY + 4, 27, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(28, CY + 4, EAST, new Label("D103",0,-2)));
		components.add(new SingleLED(29, CY + 4 + 2, EAST, new Label("D104",0,-2)));
		components.add(new Button(31, CY + 4, EAST, new Label("S102",0,-2)));
		components.add(new Text(21, CY+8, Orientation.EAST, new Label("15",0,0,4,true)));
		
		// Siding for engines
		components.add(new Line(27, CY + 8, 23, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(13, CY + 12, 23, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(24, CY + 8, EAST, new Label("D105",0,-2)));
		components.add(new SingleLED(25, CY + 8 + 2, EAST, new Label("D106",0,-2)));
		components.add(new Button(27, CY + 8, EAST, new Label("S103",0,-2)));
		components.add(new Text(17, CY+12, Orientation.EAST, new Label("16",0,0,4,true)));
		
		// Siding for engines
		components.add(new Line(23, CY + 12, 20, CY + 15, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new Line(13, CY + 15, 20, CY + 15, SPACING/2f, 0, 0.5, Color.BLACK));
		components.add(new SingleLED(20, CY + 12, EAST, new Label("D107",0,-2)));
		components.add(new SingleLED(21, CY + 12 + 2, EAST, new Label("D108",0,-2)));
		components.add(new Button(23, CY + 12, EAST, new Label("S104",0,-2)));
		components.add(new Text(17, CY+15, Orientation.EAST, new Label("17",0,0,4,true)));
		
		// Turntable
		components.add(new Circle(10, CY+13.5, 3.7, 0.5, Color.GRAY));
		components.add(new Line(7, CY + 16, 12, CY + 11, SPACING/2f, 0, 0.5, Color.BLACK));
	}
	
	
	public void draw(Graphics2D graphics)
	{
		for (Component component : components) {
			component.draw(graphics);
		}
	}
	
}
