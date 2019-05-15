package gui;

import electric.DrawingSettings;
import electric.DrawingStyle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JPanel;

/**
 * Main panel
 */
public final class MainPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Printable {
	
	public static final double PAGE_WIDTH = 297;
	public static final double PAGE_HEIGHT = 210;
	
//	public static final int STALLVERK_WIDTH = 227;
	public static final int STALLVERK_WIDTH = 236;
	public static final int STALLVERK_HEIGHT = 100;
	
	private double centerX = STALLVERK_WIDTH / 2d;
	private double centerY = STALLVERK_HEIGHT / 2d;
//	private double scaleFactor = 1.0;
	private double scaleFactor = 6;

	private Graphics2D bufferGraphics;
	private Image offscreenImage;
	private Rectangle bounds;
	
	private boolean leftMouseButtonDown = false;
	private int lastX = 0;
	private int lastY = 0;
	
	private final SwitchBoard switchBoard = new SwitchBoard().init();
	
	public MainPanel() {
		
//		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_CENTER, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_SYMBOL, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_CONNECTORS, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_OUTLINE, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_LABEL, true);
		DrawingSettings.enableDrawingStyle(DrawingStyle.PRINT_TEXT, true);
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setFocusable(true);
        requestFocusInWindow();
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
			
			graphics.scale(scaleFactor, scaleFactor);
//			graphics.scale(scale, scale);
			
			// Rotera
//			bufferGraphics.rotate(angle);
			
			// Transformera så att ritningens centrum hamnar i origo
			graphics.translate(-centerX, -centerY);
		}
		
		graphics.setColor(Color.black);
		
		switchBoard.draw(graphics);
		
		graphics.setTransform(oldXForm); // Restore transform
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
			case '1':
				switchBoard.changeSideVisibility(SwitchBoard.Side.TOP);
				repaint();
				break;
				
			case '2':
				switchBoard.changeSideVisibility(SwitchBoard.Side.LEFT);
				repaint();
				break;
				
			case '3':
				switchBoard.changeSideVisibility(SwitchBoard.Side.RIGHT);
				repaint();
				break;
				
			case '4':
				switchBoard.changeSideVisibility(SwitchBoard.Side.GRID);
				repaint();
				break;
				
			case '5':
				switchBoard.changeSideVisibility(SwitchBoard.Side.OUTLINE_TOP);
				repaint();
				break;
				
			case '6':
				switchBoard.changeSideVisibility(SwitchBoard.Side.OUTLINE_LEFT);
				repaint();
				break;
				
			case '7':
				switchBoard.changeSideVisibility(SwitchBoard.Side.OUTLINE_RIGHT);
				repaint();
				break;
				
			case '9':
				switchBoard.changeSideVisibility(SwitchBoard.Side.COMPONENT_PINS);
				repaint();
				break;
				
			case '0':
				switchBoard.changeSideVisibility(SwitchBoard.Side.COMPONENT_LABELS);
				repaint();
				break;
				
			case '+':
				switchBoard.changeSideVisibility(SwitchBoard.Side.LEFT_SMALL_SIDINGS);
				repaint();
				break;
				
			default:
				System.out.format("Key typed: %c%n", e.getKeyChar());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
