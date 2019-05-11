package gui;

import electric.Component;
import electric.DrawingSettings;
import electric.DrawingStyle;
import electric.Line;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Main panel
 */
public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, Printable {
	
	double PAGE_WIDTH = 297;
	double PAGE_HEIGHT = 210;
	
	int STALLVERK_WIDTH = 270;
	int STALLVERK_HEIGHT = 100;
	
	double centerX = STALLVERK_WIDTH / 2d;
	double centerY = STALLVERK_HEIGHT / 2d;
	double scaleFactor = 1.0;
//	double scaleFactor = 2.5;

	private Graphics2D bufferGraphics;
	private Image offscreenImage;
	private Rectangle bounds;
	
	boolean leftMouseButtonDown = false;
	int lastX = 0;
	int lastY = 0;
	
	private final List<Component> components = new ArrayList<>();
	
	public MainPanel() {
		components.add(new Line(0, 0, Component.SPACING, Component.SPACING));
		components.add(new Line(0, Component.SPACING, Component.SPACING, 0));
		
		components.add(new Line(0d, 0, STALLVERK_WIDTH, 0));
		components.add(new Line(0d, STALLVERK_HEIGHT, STALLVERK_WIDTH, STALLVERK_HEIGHT));
		components.add(new Line(0d, 0, 0, STALLVERK_HEIGHT));
		components.add(new Line(STALLVERK_WIDTH, 0d, STALLVERK_WIDTH, STALLVERK_HEIGHT));
		
		for (int i=0; i*Component.SPACING < STALLVERK_HEIGHT; i++)
//		for (int i=0; i < 100; i++)
			components.add(new Line(0, i, (int)(STALLVERK_WIDTH/Component.SPACING), i));
		
		for (int i=0; i*Component.SPACING < STALLVERK_WIDTH; i++)
			components.add(new Line(i, 0, i, (int)(STALLVERK_HEIGHT/Component.SPACING)));
		
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_SYMBOL, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_CONNECTORS, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_OUTLINE, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_IDENTIFIER, true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			leftMouseButtonDown = true;
			lastX = e.getX();
			lastY = e.getY();
			this.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			leftMouseButtonDown = false;
			
//			System.out.format("X: %d, Y: %d, lastX: %d, lastY: %d\n", e.getX(), e.getY(), lastX, lastY);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (leftMouseButtonDown)
		{
			double tempLastX = (lastX - bounds.width/2.0) / scaleFactor + centerX;
			double tempLastY = (lastY - bounds.height/2.0) / scaleFactor + centerY;
			double x = (e.getX() - bounds.width/2.0) / scaleFactor + centerX;
			double y = (e.getY() - bounds.height/2.0) / scaleFactor + centerY;
			
			centerX += tempLastX - x;
			centerY += tempLastY - y;
			
			lastX = e.getX();
			lastY = e.getY();
			
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRotation = e.getWheelRotation();
		
		boolean isShiftDown = e.isShiftDown();
		
//		System.out.format("rotation: %d\n", wheelRotation);
		
		if (wheelRotation > 0)
		{
			for (int i=0; i < wheelRotation; i++)
				scaleFactor /= (isShiftDown ? 1.05 : 1.3);
			
			this.repaint();
		}
		else if (wheelRotation < 0)
		{
			for (int i=0; i < -wheelRotation; i++)
				scaleFactor *= (isShiftDown ? 1.05 : 1.3);
//				scaleFactor *= 2;
			
			this.repaint();
		}
	}

	public void componentResized()
	{
		// Force update of text size
//		bufferGraphicsText = null;
	}
	
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	
	private void draw(Graphics2D graphics, boolean print, PageFormat pageFormat, double x0, double y0, int pageX, int pageY)
	{
		AffineTransform oldXForm = graphics.getTransform();
		
		if (print)
			graphics.setStroke(new BasicStroke(0.1f));
		else
			graphics.setStroke(new BasicStroke(0.01f));
		
		// Transformera så att ritningen får lagom storlek
		if (print)
		{
/*			
			double realWidth = 250.0;
			double paperWidth = 88.05;
//			double paperWidth = 176.1;
			double realHeight = 200.0;
			double paperHeigth = 70.0;
			
//			System.out.format("ScaleX: %1.2f, ScaleY: %1.2f\n", realWidth/paperWidth, realHeight/paperHeigth);
			
//			graphics.translate(pageFormat.getImageableX() + pageFormat.getImageableWidth()/2, pageFormat.getImageableY() + pageFormat.getImageableHeight()/2);
			graphics.translate(pageFormat.getImageableWidth()/2, pageFormat.getImageableHeight()/2);
			
			graphics.scale(realWidth/paperWidth, realHeight/paperHeigth);
			
//			double scale = 1.0;
//			graphics.scale(scale, scale);
			
			// Rotera
//			bufferGraphics.rotate(angle);
			
			// Transformera så att ritningens centrum hamnar i origo
//			graphics.translate(-250/2, -200/2);
			
			double x = x0 + 250*(1.0/2+pageX);
			double y = y0 + 200*(1.0/2+pageY);
			
			System.out.format("X: %1.0f, Y: %1.0f, pageX: %d, pageY: %d\n", x, y, pageX, pageY);
//			System.out.format("X: %1.2f, Y: %1.2f, pageX: %d, pageY: %d\n", x, y, pageX, pageY);
			
			graphics.translate(-x, -y);
/.*			
			graphics.setColor(Color.black);
			Font f = new Font("Dialog", Font.PLAIN, 12);
			graphics.setFont(f);
			graphics.drawString(String.format("x: %d, y: %d", pageX, pageY), (float)x+10, (float)y+10);
*./			
//			graphics.translate(-(x0 + 250*(1.0/2+pageX)), -(y0+200*(1.0/2+pageY)));
//			graphics.translate(-centerX, -centerY);
*/			
		}
		else
		{
			// Transformera till fönstret
			graphics.translate(bounds.width/2, bounds.height/2);
			
//			double scale = 0.09;
////			double scale = 0.18;	// Hela banan
			
//			if (ONLY_SINGLE_SWITCH)
//				scale = 3.0;		// En växel
			
//			scale = 1.0;		// Test
			
			graphics.scale(scaleFactor, scaleFactor);
//			graphics.scale(scale, scale);
			
			// Rotera
//			bufferGraphics.rotate(angle);
			
			// Transformera så att ritningens centrum hamnar i origo
			graphics.translate(-centerX, -centerY);
		}
		
		graphics.setColor(Color.black);
		
		for (Component component : components) {
			component.draw(graphics);
		}
		
		graphics.draw(new Line2D.Double(0,0,100,300));
		
		graphics.setTransform(oldXForm); // Restore transform
		graphics.draw(new Line2D.Double(0,0,100,200));
	}
	
	
	@Override
    protected void paintComponent(Graphics g)
	{
		Rectangle newBounds = this.getBounds();
		
		if ((bounds == null) || (! bounds.equals(newBounds)))
		{
			// Force creation of new double buffer
			bufferGraphics = null;
			bounds = newBounds;
		}
		
		// Init double buffering
		if (bufferGraphics == null)
		{
			offscreenImage = createImage(bounds.width, bounds.height);
			bufferGraphics = (Graphics2D) offscreenImage.getGraphics();
		}
		
		
//		bufferGraphics.drawRect(0, 0, bounds.width-1, bounds.height-1);
		
		// Vit bakgrundsfärg
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0, 0, bounds.width, bounds.height);
		
		draw(bufferGraphics, false, null, 0, 0, 0, 0);
		
		Font font = new Font("Verdana", Font.PLAIN, 10);
		bufferGraphics.setFont(font);
		String str = String.format("%1.2f", scaleFactor);
		bufferGraphics.drawString(str, 2, 10);
		
		g.drawImage(offscreenImage, 0, 0, this);
		((Graphics2D)g).draw(new Line2D.Double(0,0,100,100));
	}

}
