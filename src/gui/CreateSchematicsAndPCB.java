package gui;

import electric.Button;
import electric.Circle;
import electric.Component;
import electric.Line;
import electric.SingleLED;
import electric.Text;
import java.io.IOException;
import java.util.List;
import javadiptraceasciilib.DiptraceComponent;
import javadiptraceasciilib.DiptraceComponentPin;
import javadiptraceasciilib.DiptraceItem;
import javadiptraceasciilib.DiptraceNet;
import javadiptraceasciilib.DiptraceNetNameAlreadyExistsException;
import javadiptraceasciilib.DiptraceNotFoundException;
import javadiptraceasciilib.DiptraceProject;
import javadiptraceasciilib.DiptraceRefDesAlreadyExistsException;

/**
 * Create the Diptrace schematics and PCB
 */
public final class CreateSchematicsAndPCB {

	private final List<Component> _components;
	
	public CreateSchematicsAndPCB(List<Component> components) {
		_components = components;
		
		Component S1ppp = getComponentByLabel("S1");
		Component S10 = getComponentByLabel("S10");
		Component S204 = getComponentByLabel("S204");
		
		System.out.format("S1ppp - S204: %1.0f%n", S204.getX()-S1ppp.getX());
		System.out.format("S10 - S204: %1.0f%n", S204.getY()-S10.getY());
	}
	
	public void doIt() {
        final String schematicsInputFile = "CorleoneSwitchboard_Schematics.asc";
        final String schematicsOutputFile = "CorleoneSwitchboard_Schematics_New.asc";
        final String pcbInputFile = "CorleoneSwitchboard_PCB.asc";
        final String pcbOutputFile = "CorleoneSwitchboard_PCB_New.asc";
		
        final double x0 = 0;
        final double y0 = 0;
		
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		try {
            // Create a diptrace project. It holds both the schematics and
            // the pcb.
            DiptraceProject diptraceProject = new DiptraceProject();
            
            // Read the diptrace ascii files.
            diptraceProject.readSchematicsAndPCB(
                schematicsInputFile,
                pcbInputFile);
            
            // Get the S1 component, the D1 component and the R1 component.
            DiptraceComponent diptraceComponentS1
                = diptraceProject.getComponentByRefDes("S1orig");
            DiptraceComponent diptraceComponentD1
                = diptraceProject.getComponentByRefDes("D_Yellow");
            DiptraceComponent diptraceComponentR1
                = diptraceProject.getComponentByRefDes("R1orig");
			
            DiptraceItem diptraceComponent_ShapeLineMainTrack
                = diptraceProject.getShapeByRefDes("ShapeLineMainTrack");
            DiptraceItem diptraceComponent_ShapeLineMainSidingTrack
                = diptraceProject.getShapeByRefDes("ShapeLineMainSidingTrack");
            DiptraceItem diptraceComponent_ShapeLineSidingTrack
                = diptraceProject.getShapeByRefDes("ShapeLineSidingTrack");
            
            DiptraceNet diptraceNetNet1
                = diptraceProject.getNetByName("GND_1");
//            DiptraceNet diptraceNetNet3a5
//                = diptraceOperations.getNetByName("Net   3 a     5");
            
            // The DipTrace ascii files keeps the data in a tree structure
            // and a DiptraceItem is a node in that tree. Note that the
            // schematics has one tree and the pcb as another tree. So we work
            // with both these two trees at the same time.
            
            diptraceComponentD1.moveAbsoluteOnPCB(x0, y0);
            diptraceComponentR1.moveAbsoluteOnPCB(x0, y0);
			
			
			
			
            // Count how many LEDs and resistors we have.
			int numSwitches = 0;
            int numDiodes = 0;
            int numResistors = 0;
			int numLines = 0;
			
			for (Component c : _components) {
				
				if (c instanceof Button) {
					
                    // What name should the new components have?
                    String newButtonName = c.getLabel().getName();
                    
                    String newNetName
                        = String.format("Net %d", numResistors);
                    
                    DiptraceComponent newButtonComponent =
                        diptraceComponentS1.duplicate(newButtonName);
                    
                    newButtonComponent
//                        .moveRelativeOnSchematics(schematicsX, schematicsY);
//                        .moveAbsoluteOnSchematics(c.getX()*3, c.getY()*3);
                        .moveAbsoluteOnSchematics(c.getX()*2*3, c.getY()*2*3);
                    
                    newButtonComponent.moveAbsoluteOnPCB(c.getX()*3, c.getY()*3);
/*AAA                    
                    DiptraceNet newNet =
                        diptraceNetNet1.duplicateNet(newNetName);
                    
                    DiptraceComponentPin componentPinNewButtonPin0
                        = newButtonComponent.getPin(0);
                    DiptraceComponentPin componentPinNewButtonPin1
                        = newButtonComponent.getPin(1);
//                    newNet.connectToPin(componentPinNewButtonPin0);
//                    newNet.connectToPin(componentPinNewButtonPin1);
*/
//				} else if (false && c instanceof SingleLED) {
				} else if (c instanceof SingleLED) {
					
                    // What name should the new components have?
                    String newDiodeName
                        = String.format("D%d", ++numDiodes);
                    String newResistorName
                        = String.format("R%d", ++numResistors);
                    
                    String newNetName
                        = String.format("Net %d", numResistors);
                    
                    DiptraceComponent newDiodeComponent =
                        diptraceComponentD1.duplicate(newDiodeName);
                    
                    DiptraceComponent newResistorComponent =
                        diptraceComponentR1.duplicate(newResistorName);
                    
                    newDiodeComponent
//                        .moveRelativeOnSchematics(schematicsX, schematicsY);
//                        .moveAbsoluteOnSchematics(c.getX()*3, c.getY()*3);
                        .moveAbsoluteOnSchematics(c.getX()*2*3, c.getY()*2*3);
                    newResistorComponent
//                        .moveRelativeOnSchematics(schematicsX, schematicsY);
//                        .moveAbsoluteOnSchematics(c.getX()*3, c.getY()*3);
                        .moveAbsoluteOnSchematics(c.getX()*2*3, c.getY()*2*3+500);
                    
                    newDiodeComponent.moveAbsoluteOnPCB(c.getX()*3, c.getY()*3);
                    newResistorComponent.moveAbsoluteOnPCB(c.getX()*3, c.getY()*3);
                    
                    DiptraceNet newNet =
                        diptraceNetNet1.duplicateNet(newNetName);
                    
                    DiptraceComponentPin componentPinNewDiodePin0
                        = newDiodeComponent.getPin(0);
                    DiptraceComponentPin componentPinNewDiodePin1
                        = newDiodeComponent.getPin(1);
                    DiptraceComponentPin componentPinNewResistorPin0
                        = newDiodeComponent.getPin(0);
                    DiptraceComponentPin componentPinNewResistorPin1
                        = newDiodeComponent.getPin(1);
//                    newNet.connectToPin(componentPinNewDiodePin0);
//                    newNet.connectToPin(componentPinNewDiodePin1);
//                    newNet.connectToPin(componentPinNewResistorPin0);
//                    newNet.connectToPin(componentPinNewResistorPin1);
				} else if (c instanceof Text) {
					
				} else if (c instanceof Line) {
					
                    // What name should the new components have?
                    String newLineName
                        = String.format("L%d", ++numLines);
                    
                    DiptraceComponent newLineComponent =
                        diptraceComponent_ShapeLineMainTrack.duplicate();
//                        diptraceComponent_ShapeLineMainTrack.duplicate(newLineName);
                    
                    newLineComponent
                        .moveAbsoluteOnSchematics(c.getX()*2*3, c.getY()*2*3);
                    
                    newLineComponent.moveAbsoluteOnPCB(c.getX()*3, c.getY()*3);
					
				} else if (c instanceof Circle) {
					
				} else if (c instanceof SingleLED) {
					
				} else {
					throw new RuntimeException("Unknown component: " + c.getClass().getName());
				}
				
			
			}
            
            // Write the diptrace ascii files.
            diptraceProject.writeSchematicsAndPCB(
                schematicsOutputFile, pcbOutputFile);
            
        // Since thise is an example, we don't do any fancy error handling.
        } catch (DiptraceRefDesAlreadyExistsException
            | DiptraceNetNameAlreadyExistsException
            | DiptraceNotFoundException
            | IOException ex) {
            
            ex.printStackTrace();
        }
	}
	
	
	
	public Component getComponentByLabel(String label) {
		for (Component c : _components) {
			if (c.getLabel() != null && label.equals(c.getLabel().getName())) {
				return c;
			}
		}
		throw new IllegalArgumentException("Label is not found: " + label);
	}
}
