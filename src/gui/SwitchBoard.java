package gui;

import electric.Component;
import electric.Line;
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
		create();
	}
	
	public void create() {
		
		double thickness = 0.1;
		
//		components.add(new Line(0, 0, Component.SPACING, Component.SPACING, thickness, Color.BLACK));
//		components.add(new Line(0, Component.SPACING, Component.SPACING, 0, thickness, LineStyle.SOLID));
		
		for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_HEIGHT; i++)
//		for (int i=0; i < 100; i++)
			components.add(new Line(0, i, (int)(MainPanel.STALLVERK_WIDTH/Component.SPACING), i, thickness, Color.LIGHT_GRAY));
		
		for (int i=1; i*Component.SPACING < MainPanel.STALLVERK_WIDTH; i++)
			components.add(new Line(i, 0, i, (int)(MainPanel.STALLVERK_HEIGHT/Component.SPACING), thickness, Color.LIGHT_GRAY));
		
		components.add(new Line(0d, 0, MainPanel.STALLVERK_WIDTH, 0, thickness, Color.BLACK));
		components.add(new Line(0d, MainPanel.STALLVERK_HEIGHT, MainPanel.STALLVERK_WIDTH, MainPanel.STALLVERK_HEIGHT, thickness, Color.BLACK));
		components.add(new Line(0d, 0, 0, MainPanel.STALLVERK_HEIGHT, thickness, Color.BLACK));
		components.add(new Line(MainPanel.STALLVERK_WIDTH, 0d, MainPanel.STALLVERK_WIDTH, MainPanel.STALLVERK_HEIGHT, thickness, Color.BLACK));
/*		
		components.add(new Line(0, 0, Component.SPACING, Component.SPACING, thickness, LineStyle.SOLID));
		components.add(new Line(0, Component.SPACING, Component.SPACING, 0, thickness, LineStyle.SOLID));
		
		components.add(new Line(0d, 0, MainPanel.STALLVERK_WIDTH, 0, thickness, LineStyle.SOLID));
		components.add(new Line(0d, MainPanel.STALLVERK_HEIGHT, MainPanel.STALLVERK_WIDTH, MainPanel.STALLVERK_HEIGHT, thickness, LineStyle.SOLID));
		components.add(new Line(0d, 0, 0, MainPanel.STALLVERK_HEIGHT, thickness, LineStyle.SOLID));
		components.add(new Line(MainPanel.STALLVERK_WIDTH, 0d, MainPanel.STALLVERK_WIDTH, MainPanel.STALLVERK_HEIGHT, thickness, LineStyle.SOLID));
		
		for (int i=0; i*Component.SPACING < MainPanel.STALLVERK_HEIGHT; i++)
//		for (int i=0; i < 100; i++)
			components.add(new Line(0, i, (int)(MainPanel.STALLVERK_WIDTH/Component.SPACING), i, thickness, LineStyle.DOTTED));
		
		for (int i=0; i*Component.SPACING < MainPanel.STALLVERK_WIDTH; i++)
			components.add(new Line(i, 0, i, (int)(MainPanel.STALLVERK_HEIGHT/Component.SPACING), thickness, LineStyle.DOTTED));
*/		
	}
	
	public void draw(Graphics2D graphics)
	{
		for (Component component : components) {
			component.draw(graphics);
		}
	}
	
}
