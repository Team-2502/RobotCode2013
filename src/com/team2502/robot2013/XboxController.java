/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// https://github.com/pordonj/frc1675-2012/blob/master/src/org/frc1675/driver/XBoxController.java

package com.team2502.robot2013;

import edu.wpi.first.wpilibj.Joystick;

//TODO Code support for Start/Back/XBox(?) buttons.
/**
 * This is a wrapper for the WPILib Joystick class that represents an XBox 
 * controller.
 * @author frc1675
 */
public class XboxController {
    private static final int LEFT_X_AXIS = 1;
    private static final int LEFT_Y_AXIS = 2;
    private static final int TRIGGER_AXIS = 3;
    private static final int RIGHT_X_AXIS = 4;
    private static final int RIGHT_Y_AXIS = 5;
    private static final int DPAD_AXIS = 6;
    
    private static final int A_BUTTON = 1;
    private static final int B_BUTTON = 2;
    private static final int X_BUTTON = 3;
    private static final int Y_BUTTON = 4;
    private static final int LEFT_BUMPER_BUTTON = 5;
    private static final int RIGHT_BUMPER_BUTTON = 6;
    
    private static final int LEFT_JOYSTICK_BUTTON = 9;
    private static final int RIGHT_JOYSTICK_BUTTON = 10;
    
    private static final double LEFT_DPAD_TOLERANCE = -0.9;
    private static final double RIGHT_DPAD_TOLERANCE = 0.9;
    
    private static final double LEFT_TRIGGER_TOLERANCE = 0.9;
    private static final double RIGHT_TRIGGER_TOLERANCE =-0.9;
    
    private static final double DEADZONE = 0.1;
    
    private Joystick stick;
    
    public XboxController(Joystick joystick){
        stick = joystick;
    }
    
    private boolean inDeadZone(double input){
        boolean inDeadZone; 
        if(Math.abs(input) < DEADZONE){
            inDeadZone = true;
        }else{
            inDeadZone = false;
        }
        return inDeadZone;
    }
    
    private double getAxisWithDeadZoneCheck(double input){
       if(inDeadZone(input)){
            input = 0.0;       
        }
        return input; 
    }
    
    public boolean getAButton(){
        return stick.getRawButton(A_BUTTON);
    }
    
    public boolean getXButton(){
        return stick.getRawButton(X_BUTTON);    
    }
    
    public boolean getBButton(){
         return stick.getRawButton(B_BUTTON);
    }
    
    public boolean getYButton(){
        return stick.getRawButton(Y_BUTTON);
    } 
    
    public boolean getLeftBumperButton(){
        return stick.getRawButton(LEFT_BUMPER_BUTTON);
    }
    
    public boolean getRightBumperButton(){
        return stick.getRawButton(RIGHT_BUMPER_BUTTON);
    }
    
    public boolean getLeftJoystickButton(){
        return stick.getRawButton(LEFT_JOYSTICK_BUTTON);
    }
    
    public boolean getRightJoystickButton(){
        return stick.getRawButton(RIGHT_JOYSTICK_BUTTON);
    }
    
    public double getLeftXAxis(){         
        return getAxisWithDeadZoneCheck(stick.getRawAxis(LEFT_X_AXIS)); 
    }
    
    public double getLeftYAxis(){
        return getAxisWithDeadZoneCheck(-stick.getRawAxis(LEFT_Y_AXIS));
    }
    
    public double getRightXAxis(){
       return getAxisWithDeadZoneCheck(stick.getRawAxis(RIGHT_X_AXIS));
    }
    
    public double getRightYAxis(){
        return getAxisWithDeadZoneCheck(-stick.getRawAxis(RIGHT_Y_AXIS)); 
    }
    
    private double getTriggerAxis(){
        return getAxisWithDeadZoneCheck(stick.getRawAxis(TRIGGER_AXIS)); 
    }
    
    public boolean getDPadLeft(){
        return (stick.getRawAxis(DPAD_AXIS) < LEFT_DPAD_TOLERANCE);
    }
            
    public boolean getDPadRight(){
        return (stick.getRawAxis(DPAD_AXIS) > RIGHT_DPAD_TOLERANCE);
    }
    
    public boolean getLeftTrigger(){
        return (getTriggerAxis() > LEFT_TRIGGER_TOLERANCE);
    }
    public boolean getRightTrigger(){
        return (getTriggerAxis() > RIGHT_TRIGGER_TOLERANCE);
    }   
    
}
