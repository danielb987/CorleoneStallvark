package gui;

import electric.Button;
import electric.Circle;
import electric.Component;
import static electric.Component.SPACING;
import electric.DrawingSettings;
import electric.DrawingStyle;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ställverket
 */
public final class SwitchBoard {

	public enum Side {
		GRID,
		LEFT,
		RIGHT,
		TOP,
		OUTLINE_TOP,
		OUTLINE_LEFT,
		LEFT_SMALL_SIDINGS,
		OUTLINE_RIGHT,
		COMPONENT_PINS,
		COMPONENT_LABELS,
	}
	
	private boolean drawGrid = true;
	
	private final List<Component> componentsGrid = new ArrayList<>();
	private final List<Component> componentsTopSide = new ArrayList<>();
	private final List<Component> componentsLeftSide = new ArrayList<>();
	private final List<Component> componentsLeftSideSmallSidings = new ArrayList<>();
	private final List<Component> componentsRightSide = new ArrayList<>();
	private final List<Component> componentsOutlineTopSide = new ArrayList<>();
	private final List<Component> componentsOutlineLeftSide = new ArrayList<>();
	private final List<Component> componentsOutlineRightSide = new ArrayList<>();
	
	Set<Side> sides = new HashSet<>();
	int numButtons = 0;
	int numLEDs = 0;
	int numLines = 0;
	int numCircles = 0;
	int numLabels = 0;
	int numTexts = 0;
	int numPins = 0;
	
	public void countComponents(List<Component> components) {
		for (Component c : components) {
			if (c instanceof Button) numButtons++;
			else if (c instanceof SingleLED) numLEDs++;
			else if (c instanceof Line) numLines++;
			else if (c instanceof Circle) numCircles++;
			else if (c instanceof Text) numTexts++;
			else throw new RuntimeException("Unknown component: "+c.getClass().getName());
			
			if (! (c instanceof Text) && (c.getLabel() != null)) numLabels++;
			
			numPins += c.getNumPins();
		}
	}
	
	public SwitchBoard init() {
		
		sides.add(Side.LEFT);
		sides.add(Side.RIGHT);
		sides.add(Side.TOP);
		sides.add(Side.OUTLINE_TOP);
		sides.add(Side.OUTLINE_LEFT);
		sides.add(Side.OUTLINE_RIGHT);
		
		createBoard();
		createLayout();
		
		countComponents(componentsTopSide);
		countComponents(componentsLeftSide);
		countComponents(componentsRightSide);
		
		System.out.format("Num pins: %d%n", numPins);
		System.out.format("Num buttons: %d%n", numButtons);
		System.out.format("Num LEDs: %d%n", numLEDs);
		System.out.format("Num texts: %d%n", numTexts);
		System.out.format("Num labels: %d%n", numLabels);
		System.out.format("Num lines: %d%n", numLines);
		System.out.format("Num circles: %d%n", numCircles);
		
		System.out.format("Num LocoIO cards for buttons: %1.0f%n", Math.ceil(numButtons/16d));
		System.out.format("Num LocoIO cards for LEDs: %1.0f%n", Math.ceil(numLEDs/16d));
		
//		CreateSchematicsAndPCB createSchematicsAndPCB = new CreateSchematicsAndPCB(componentsLeftSide);
//		createSchematicsAndPCB.doIt();
		
		return this;
	}
	
	public void createBoard() {
		
		double thickness = 0.1;
		
		if (drawGrid) {
			// Draw grid
			for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_HEIGHT; i++)
				componentsGrid.add(new Line(0, i, (int)(MainPanel.STALLVERK_WIDTH/Component.SPACING), i, thickness, Color.LIGHT_GRAY));

			for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_WIDTH; i++)
				componentsGrid.add(new Line(i, 0, i, (int)(MainPanel.STALLVERK_HEIGHT/Component.SPACING), thickness, Color.LIGHT_GRAY));
		}
		
		componentsOutlineTopSide.add(new Line(0, 0, 92, 0, thickness*2, Color.BLACK));
		componentsOutlineTopSide.add(new Line(0, 0, 0, 39, thickness*2, Color.BLACK));
		componentsOutlineTopSide.add(new Line(0, 39, 92, 39, thickness*2, Color.BLACK));
		componentsOutlineTopSide.add(new Line(92, 0, 92, 39, thickness*2, Color.BLACK));
		
		componentsOutlineLeftSide.add(new Line(0, 0, 49, 0, thickness*2, Color.BLACK));
		componentsOutlineLeftSide.add(new Line(0, 0, 0, 39, thickness*2, Color.BLACK));
		componentsOutlineLeftSide.add(new Line(0, 39, 49, 39, thickness*2, Color.BLACK));
		componentsOutlineLeftSide.add(new Line(49, 0, 49, 39, thickness*2, Color.BLACK));
		
		componentsOutlineRightSide.add(new Line(51, 0, 92, 0, thickness*2, Color.BLACK));
		componentsOutlineRightSide.add(new Line(51, 0, 51, 39, thickness*2, Color.BLACK));
		componentsOutlineRightSide.add(new Line(51, 39, 92, 39, thickness*2, Color.BLACK));
		componentsOutlineRightSide.add(new Line(92, 0, 92, 39, thickness*2, Color.BLACK));
	}
	
	public void createLayout() {
		int CY = 14;	// Y position of main track
		
		// Main track
//		components.add(new Line(0, CY, 106, CY, 1, Color.BLACK));
		componentsTopSide.add(new Line(0, CY, 92, CY, 1, Color.BLACK));
		
		// Vertical Main track
		componentsTopSide.add(new Line(88, 0, 88, 39, 1, Color.BLACK));
		
		// Main track on freight yard
		componentsTopSide.add(new Line(4, CY+4, 80, CY+4, 0.8, Color.BLACK));
		componentsTopSide.add(new Line(0, CY+8, 4, CY+4, 0.8, Color.BLACK));
		componentsTopSide.add(new Line(80, CY+4, 84, CY+8, 0.8, Color.BLACK));
		componentsTopSide.add(new Line(84, CY+8, 84, 39, 0.8, Color.BLACK));
		
		// Crossover at the left
		componentsTopSide.add(new Line(4, CY, 8, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(7, CY, EAST, new Label("D1",0,-2)));
		componentsLeftSide.add(new SingleLED(6, CY+2, EAST, new Label("D2",0,-2)));
		componentsLeftSide.add(new SingleLED(5, CY+4, EAST, new Label("D3",0,-2)));
		componentsLeftSide.add(new Button(4, CY, EAST, new Label("S1",0,-2)));
		
		// Crossover little right
		componentsTopSide.add(new Line(25, CY, 25 - 4, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(22, CY, EAST, new Label("D4",0,-2)));
		componentsLeftSide.add(new SingleLED(23, CY+2, EAST, new Label("D5",0,-2)));
		componentsLeftSide.add(new SingleLED(24, CY+4, EAST, new Label("D6",0,-2)));
		componentsLeftSide.add(new Button(25, CY, EAST, new Label("S2",0,-2)));
		
		// Crossover little left to the right
		componentsTopSide.add(new Line(70, CY, 66, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(67, CY, EAST, new Label("D201",0,-2)));
		componentsLeftSide.add(new SingleLED(68, CY+2, EAST, new Label("D202",0,-2)));
		componentsLeftSide.add(new SingleLED(69, CY+4, EAST, new Label("D203",0,-2)));
//		components.add(new Button(25, CY, EAST, new Label("S2+",0,-2)));
		componentsLeftSide.add(new Button(70, CY, EAST, new Label("S201",0,-2)));
		
		// Crossover at the right
		componentsTopSide.add(new Line(74, CY, 78, CY + 4, 3/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(77, CY, EAST, new Label("D204",0,-2)));
		componentsLeftSide.add(new SingleLED(76, CY+2, EAST, new Label("D205",0,-2)));
		componentsLeftSide.add(new SingleLED(75, CY+4, EAST, new Label("D206",0,-2)));
//		components.add(new Button(4, CY, EAST, new Label("S1+",0,-2)));
		componentsLeftSide.add(new Button(74, CY, EAST, new Label("S202",0,-2)));
		
		// Crossover vertical 1
		componentsTopSide.add(new Line(84, CY+9, 88, CY+13, 0, 3/2f, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(88, CY+10, NORTH, new Label("D207",3,0)));
		componentsLeftSide.add(new SingleLED(86, CY+11, NORTH, new Label("D208",0,-3)));
		componentsLeftSide.add(new SingleLED(84, CY+12, NORTH, new Label("D209",-3,0)));
//		components.add(new Button(4, CY, EAST, new Label("S1+",0,-2)));
		componentsLeftSide.add(new Button(88, CY+13, NORTH, new Label("S203",3,0)));
		
		// Crossover vertical 2
		componentsTopSide.add(new Line(88, CY+17, 84, CY+21, 0, 3/2f, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(88, CY+20, NORTH, new Label("D210",3,0)));
		componentsLeftSide.add(new SingleLED(86, CY+19, NORTH, new Label("D211",0,-3)));
		componentsLeftSide.add(new SingleLED(84, CY+18, NORTH, new Label("D212",-3,0)));
//		components.add(new Button(4, CY, EAST, new Label("S1+",0,-2)));
		componentsLeftSide.add(new Button(88, CY+17, NORTH, new Label("S204",3,0)));
		
		// Siding for storage of passenger cars
		componentsTopSide.add(new Line(10, CY, 13, CY - 3, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(13, CY - 3, 28, CY - 3, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(13, CY, EAST, new Label("D7",0,-2)));
		componentsLeftSide.add(new SingleLED(12, CY-2, EAST, new Label("D8",0,-2)));
		componentsLeftSide.add(new Button(10, CY, EAST, new Label("S3",0,-2)));
		componentsTopSide.add(new Line(28, CY - 3, 31, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(28, CY, EAST, new Label("D9",0,-2)));
		componentsLeftSide.add(new SingleLED(29, CY-2, EAST, new Label("D10",0,-2)));
		componentsLeftSide.add(new Button(31, CY, EAST, new Label("S4",0,-2)));
		componentsTopSide.add(new Text(20.5, CY-3, Orientation.EAST, new Label("11",0,0,4,true)));
		
		// Siding to the left from the above siding
		componentsTopSide.add(new Line(18, CY - 3, 15, CY - 6, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(15, CY - 6, 8, CY - 6, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSideSmallSidings.add(new SingleLED(15, CY - 3, EAST, new Label("D11",0,-2)));
		componentsLeftSideSmallSidings.add(new SingleLED(16, CY - 3 - 2, EAST, new Label("D12",0,-2)));
		componentsLeftSideSmallSidings.add(new Button(18, CY - 3, EAST, new Label("S5",0,-2)));
		componentsTopSide.add(new Text(11.5, CY-6, Orientation.EAST, new Label("12",0,0,4,true)));
		
		// Siding to the right from the above siding
		componentsTopSide.add(new Line(23, CY - 3, 27, CY - 7, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(27, CY - 7, 38, CY - 7, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSideSmallSidings.add(new SingleLED(26, CY - 3, EAST, new Label("D13",0,-2)));
		componentsLeftSideSmallSidings.add(new SingleLED(25, CY - 3 - 2, EAST, new Label("D14",0,-2)));
		componentsLeftSideSmallSidings.add(new Button(23, CY - 3, EAST, new Label("S6",0,-2)));
		componentsTopSide.add(new Text(34, CY-7, Orientation.EAST, new Label("13",0,0,4,true)));
		
		// Siding to the right from the above siding
		componentsTopSide.add(new Line(27, CY - 7, 27 + 3, CY - 10, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(30, CY - 10, 38, CY - 10, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSideSmallSidings.add(new SingleLED(30, CY - 7, EAST, new Label("D15",0,-2)));
		componentsLeftSideSmallSidings.add(new SingleLED(29, CY - 7 - 2, EAST, new Label("D16",0,-2)));
		componentsLeftSideSmallSidings.add(new Button(27, CY - 7, EAST, new Label("S7",0,-2)));
		componentsTopSide.add(new Text(34, CY-10, Orientation.EAST, new Label("14",0,0,4,true)));
		
		
		
		
		// Passenger track 3
		componentsTopSide.add(new Line(41, CY, 45, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(45, CY - 4, 60, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(44, CY, EAST, new Label("D23",0,-2)));
		componentsLeftSide.add(new SingleLED(43, CY-2, EAST, new Label("D24",0,-2)));
		componentsLeftSide.add(new Button(41, CY, EAST, new Label("S11",0,-2)));
		componentsTopSide.add(new Line(60, CY - 4, 64, CY, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(61, CY, EAST, new Label("D25",0,-2)));
		componentsLeftSide.add(new SingleLED(62, CY-2, EAST, new Label("D26",0,-2)));
		componentsLeftSide.add(new Button(64, CY, EAST, new Label("S12",0,-2)));
		componentsTopSide.add(new Text(49.5, CY-4, Orientation.EAST, new Label("3",0,0,4,true)));
		
		// Passenger track 2
		componentsTopSide.add(new Line(35, CY, 43, CY - 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(43, CY - 8, 64, CY - 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(38, CY, EAST, new Label("D17",0,-2)));
		componentsLeftSide.add(new SingleLED(37, CY-2, EAST, new Label("D18",0,-2)));
		componentsLeftSide.add(new Button(35, CY, EAST, new Label("S8",0,-2)));
		componentsTopSide.add(new Line(56, CY - 8, 60, CY - 4, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Text(49.5, CY-8, Orientation.EAST, new Label("2",0,0,4,true)));
		
		// Engine storage track
		componentsLeftSide.add(new SingleLED(59, CY-8, EAST, new Label("D27",0,-2)));
		componentsLeftSide.add(new SingleLED(58, CY-8+2, EAST, new Label("D28",0,-2)));
		componentsLeftSide.add(new SingleLED(57, CY-4, EAST, new Label("D29",0,-2)));
		componentsLeftSide.add(new Button(60, CY-4, EAST, new Label("S13",0,-2)));
		componentsTopSide.add(new Text(62, CY-8, Orientation.EAST, new Label("21",0,0,4,true)));
		
//		components.add(new SingleLED(29+1+20, CY, EAST, new Label("D19",0,-2)));
//		components.add(new SingleLED(30+1+20, CY-2, EAST, new Label("D20",0,-2)));
//		components.add(new Button(32+1+20, CY, EAST, new Label("S9",0,-2)));
//		components.add(new Text(49, CY-8, Orientation.EAST, new Label("2",0,0,4,true)));
		
		// Passenger track 1
		componentsTopSide.add(new Line(43, CY-8, 46, CY - 11, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(46, CY - 11, 53, CY - 11, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(46, CY-8, EAST, new Label("D19",0,-2)));
		componentsLeftSide.add(new SingleLED(45, CY-8-2, EAST, new Label("D20",0,-2)));
		componentsLeftSide.add(new Button(43, CY-8, EAST, new Label("S9",0,-2)));
		componentsTopSide.add(new Line(53, CY-11, 56, CY-8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(53, CY-8, EAST, new Label("D21",0,-2)));
		componentsLeftSide.add(new SingleLED(54, CY-8-2, EAST, new Label("D22",0,-2)));
		componentsLeftSide.add(new Button(56, CY-8, EAST, new Label("S10",0,-2)));
		componentsTopSide.add(new Text(49.5, CY-11, Orientation.EAST, new Label("1",0,0,4,true)));
		
		componentsTopSide.add(new Text(49.5, CY, Orientation.EAST, new Label("4",0,0,4,true)));
		componentsTopSide.add(new Text(49.5, CY+4, Orientation.EAST, new Label("5",0,0,4,true)));
//		components.add(new Text(48, CY+8, Orientation.EAST, new Label("6",0,0,4,true)));
//		components.add(new Text(48, CY+12, Orientation.EAST, new Label("7",0,0,4,true)));
//		components.add(new Text(48, CY+15, Orientation.EAST, new Label("8",0,0,4,true)));
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
		componentsTopSide.add(new Line(14, CY + 4, 18, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(18, CY + 8, 27, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(17, CY + 4, EAST, new Label("D101",0,-2)));
		componentsLeftSide.add(new SingleLED(16, CY + 4 + 2, EAST, new Label("D102",0,-2)));
		componentsLeftSide.add(new Button(14, CY + 4, EAST, new Label("S101",0,-2)));
		componentsTopSide.add(new Line(31, CY + 4, 27, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(28, CY + 4, EAST, new Label("D103",0,-2)));
		componentsLeftSide.add(new SingleLED(29, CY + 4 + 2, EAST, new Label("D104",0,-2)));
		componentsLeftSide.add(new Button(31, CY + 4, EAST, new Label("S102",0,-2)));
		componentsTopSide.add(new Text(21, CY+8, Orientation.EAST, new Label("15",0,0,4,true)));
		
		// Siding for engines
		componentsTopSide.add(new Line(27, CY + 8, 23, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(13, CY + 12, 23, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSideSmallSidings.add(new SingleLED(24, CY + 8, EAST, new Label("D105",0,-2)));
		componentsLeftSideSmallSidings.add(new SingleLED(25, CY + 8 + 2, EAST, new Label("D106",0,-2)));
		componentsLeftSideSmallSidings.add(new Button(27, CY + 8, EAST, new Label("S103",0,-2)));
		componentsTopSide.add(new Text(17, CY+12, Orientation.EAST, new Label("16",0,0,4,true)));
		
		// Siding for engines
		componentsTopSide.add(new Line(23, CY + 12, 19, CY + 16, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(10, CY + 16, 19, CY + 16, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSideSmallSidings.add(new SingleLED(20, CY + 12, EAST, new Label("D107",0,-2)));
		componentsLeftSideSmallSidings.add(new SingleLED(21, CY + 12 + 2, EAST, new Label("D108",0,-2)));
		componentsLeftSideSmallSidings.add(new Button(23, CY + 12, EAST, new Label("S104",0,-2)));
		componentsTopSide.add(new Text(13, CY+16, Orientation.EAST, new Label("17",0,0,4,true)));
		
		// Siding for engines
		componentsTopSide.add(new Line(19, CY + 16, 16, CY + 19, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(10, CY + 19, 16, CY + 19, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSideSmallSidings.add(new SingleLED(16, CY + 16, EAST, new Label("D109",0,-2)));
		componentsLeftSideSmallSidings.add(new SingleLED(17, CY + 16 + 2, EAST, new Label("D110",0,-2)));
		componentsLeftSideSmallSidings.add(new Button(19, CY + 16, EAST, new Label("S105",0,-2)));
		componentsTopSide.add(new Text(13, CY+19, Orientation.EAST, new Label("18",0,0,4,true)));
		
		// Turntable
		componentsTopSide.add(new Circle(7, CY+17.5, 3.7, 0.5, Color.GRAY));
		componentsTopSide.add(new Line(4, CY + 20, 9, CY + 15, SPACING/2f, 0, 0.5, Color.BLACK));
		
		
		// Siding 6 for freight wagons
		componentsTopSide.add(new Line(35, CY + 4, 39, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(39, CY + 8, 60, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(38, CY + 4, EAST, new Label("D51",0,-2)));
		componentsLeftSide.add(new SingleLED(37, CY + 4 + 2, EAST, new Label("D52",0,-2)));
		componentsLeftSide.add(new Button(35, CY + 4, EAST, new Label("S51",0,-2)));
		componentsTopSide.add(new Line(64, CY + 4, 60, CY + 8, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(61, CY + 4, EAST, new Label("D53",0,-2)));
		componentsLeftSide.add(new SingleLED(62, CY + 4 + 2, EAST, new Label("D54",0,-2)));
		componentsLeftSide.add(new Button(64, CY + 4, EAST, new Label("S52",0,-2)));
		componentsTopSide.add(new Text(49.5, CY+8, Orientation.EAST, new Label("6",0,0,4,true)));
		
		// Siding 7 for freight wagons
		componentsTopSide.add(new Line(39, CY + 8, 43, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(43, CY + 12, 56, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(42, CY + 8, EAST, new Label("D55",0,-2)));
		componentsLeftSide.add(new SingleLED(41, CY + 8 + 2, EAST, new Label("D56",0,-2)));
		componentsLeftSide.add(new Button(39, CY + 8, EAST, new Label("S53",0,-2)));
		componentsTopSide.add(new Line(60, CY + 8, 56, CY + 12, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(57, CY + 8, EAST, new Label("D57",0,-2)));
		componentsLeftSide.add(new SingleLED(58, CY + 8 + 2, EAST, new Label("D58",0,-2)));
		componentsLeftSide.add(new Button(60, CY + 8, EAST, new Label("S54",0,-2)));
		componentsTopSide.add(new Text(49.5, CY+12, Orientation.EAST, new Label("7",0,0,4,true)));
		
		// Siding 8 for freight wagons
		componentsTopSide.add(new Line(43, CY + 12, 46, CY + 15, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsTopSide.add(new Line(46, CY + 15, 53, CY + 15, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(46, CY + 12, EAST, new Label("D59",0,-2)));
		componentsLeftSide.add(new SingleLED(45, CY + 12 + 2, EAST, new Label("D60",0,-2)));
		componentsLeftSide.add(new Button(43, CY + 12, EAST, new Label("S55",0,-2)));
		componentsTopSide.add(new Line(56, CY + 12, 53, CY + 15, SPACING/2f, 0, 0.5, Color.BLACK));
		componentsLeftSide.add(new SingleLED(53, CY + 12, EAST, new Label("D61",0,-2)));
		componentsLeftSide.add(new SingleLED(54, CY + 12 + 2, EAST, new Label("D62",0,-2)));
		componentsLeftSide.add(new Button(56, CY + 12, EAST, new Label("S56",0,-2)));
		componentsTopSide.add(new Text(49.5, CY+15, Orientation.EAST, new Label("8",0,0,4,true)));
		
	}
	
	
	public void draw(Graphics2D graphics)
	{
		if (sides.contains(Side.GRID)) {
			for (Component component : componentsGrid) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.TOP)) {
			for (Component component : componentsTopSide) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.LEFT)) {
			for (Component component : componentsLeftSide) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.LEFT_SMALL_SIDINGS)) {
			for (Component component : componentsLeftSideSmallSidings) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.RIGHT)) {
			for (Component component : componentsRightSide) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.OUTLINE_LEFT)) {
			for (Component component : componentsOutlineLeftSide) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.OUTLINE_RIGHT)) {
			for (Component component : componentsOutlineRightSide) {
				component.draw(graphics);
			}
		}
		if (sides.contains(Side.OUTLINE_TOP)) {
			for (Component component : componentsOutlineTopSide) {
				component.draw(graphics);
			}
		}
	}
	
	public void changeSideVisibility(Side s) {
		switch (s) {
			case COMPONENT_LABELS:
				DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_LABEL,
					! DrawingSettings.isDrawingStyleEnabled(DrawingStyle.PRINT_LABEL));
				break;
				
			case COMPONENT_PINS:
				DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_CONNECTORS,
					! DrawingSettings.isDrawingStyleEnabled(DrawingStyle.PRINT_CONNECTORS));
				break;
				
			default:
				if (sides.contains(s)) {
					sides.remove(s);
				} else {
					sides.add(s);
				}
		}
	}
	
}
