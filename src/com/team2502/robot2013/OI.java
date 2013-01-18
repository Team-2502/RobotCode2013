
package com.team2502.robot2013;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    public static final int JOYSTICK_1_PORT = 1;
    public static final int Joystick_2_PORT = 2;
    private Joystick joystick1;
    private Joystick joystick2;
    
    public OI() {
        joystick1 = new Joystick(JOYSTICK_1_PORT);
        joystick2 = new Joystick(Joystick_2_PORT);
    }
    
    /**
     * Returns the joystick of the specified port.
     * @param joystick - Which joystick to return.
     * @return - Joystick object from specified port, or null for no joystick;
     */
    public Joystick getJoystick(int joystick) {
        switch(joystick){
            case 1:
                return joystick1;
            case 2:
                return joystick2;
            default:
                return null;
        }
    }
}

