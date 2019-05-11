package electric;

import java.util.HashSet;
import java.util.Set;

/**
 * Drawing settings
 */
public class DrawingSettings {

	private static final DrawingSettings INSTANCE = new DrawingSettings();
	
	private final Set<DrawingStyle> drawingStyleSet = new HashSet();
	
	public static void enableDrawingStyle(DrawingStyle drawingStyle, boolean enable) {
		if (enable) {
			INSTANCE.drawingStyleSet.add(drawingStyle);
		} else {
			INSTANCE.drawingStyleSet.remove(drawingStyle);
		}
	}
	
	public static boolean isDrawingStyleEnabled(DrawingStyle drawingStyle) {
		return INSTANCE.drawingStyleSet.contains(drawingStyle);
	}
	
}
