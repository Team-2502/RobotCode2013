/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Drive train subsystem of the robot.
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private RobotDrive drive;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Creates new 4 wheel RobotDrive using data from RobotMap and disables motor safety.
     */
    public Drivetrain() {
        drive = new RobotDrive(RobotMap.FRONT_LEFT_DRIVE, RobotMap.FRONT_RIGHT_DRIVE, RobotMap.BACK_LEFT_DRIVE, RobotMap.BACK_RIGHT_DRIVE);
        drive.setSafetyEnabled(false);
    }
    
    /**
     * Drive using one joystick using arcade drive.
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
     * @param speed Speed for robot.
     * @param turn Turn value for movement.
     */
    public void arcadeDrive(double speed, double turn) {
        drive.arcadeDrive(speed, turn);
    }
}
