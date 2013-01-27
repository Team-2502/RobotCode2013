package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import com.team2502.robot2013.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Drive train subsystem of the robot.
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private Talon topLeft = new Talon(RobotMap.TOP_LEFT_DRIVE);
    private Talon bottomLeft = new Talon(RobotMap.BOTTOM_LEFT_DRIVE);
    private Talon topRight = new Talon(RobotMap.TOP_RIGHT_DRIVE);
    private Talon bottomRight = new Talon(RobotMap.BOTTOM_RIGHT_DRIVE);
    private RobotDrive drive;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithJoystick());
    }
    
    /**
     * Creates new 4 wheel RobotDrive using data from RobotMap and disables motor safety.
     */
    public Drivetrain() {
        drive = new RobotDrive(topLeft, bottomLeft, topRight, bottomRight);
        drive.setSafetyEnabled(false);
    }
    
    /**
     * Drive using one joystick using arcade drive.
     * WARNING - Do not use with robots with a tank drive!
     * @param joystick The joystick.
     */
    public void driveWithJoystick(Joystick joystick) {
        drive.arcadeDrive(joystick);
    }
    
    /**
     * Drive using two joysticks using tank drive.
     * @param leftJoystick The joystick controlling the left wheels.
     * @param rightJoystick the joystick controlling the right wheels.
     */
    public void driveWithJoystick(Joystick leftJoystick, Joystick rightJoystick) {
        drive.tankDrive(leftJoystick, rightJoystick);
    }
    
    /**
     * Stops all drive train motors.
     */
    public void stopMotors() {
        drive.stopMotor();
    }
    
    /**
     * Drives forward at a set speed.
     * @param speed Speed to move at.
     */
    public void driveForward(double speed) {
        drive.tankDrive(speed, speed);
    }
    
    /**
     * Drives left and right motors at separate set speeds.
     * @param leftSpeed Speed for left motor.
     * @param rightSpeed SPeed for right motor.
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);
    }
    /**
     * Drives robot using speed and turn value.
     * WARNING - Do not use with robots with a tank drive!
     * @param speed Speed for robot.
     * @param turn Turn value for movement.
     */
    public void arcadeDrive(double speed, double turn) {
        drive.arcadeDrive(speed, turn);
    }
}