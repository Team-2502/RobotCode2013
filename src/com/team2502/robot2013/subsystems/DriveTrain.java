package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import com.team2502.robot2013.commands.drive_train.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.OI;
import com.team2502.robot2013.XboxController;

/**
 * The drive train that will be moving the robot around.
 */
public class DriveTrain extends Subsystem {
	
	private double leftPower  = 0;
	private double rightPower = 0;
	
	private Talon      frontLeft  = new Talon(RobotMap.DRIVETRAIN_TOP_LEFT);
	private Talon      frontRight = new Talon(RobotMap.DRIVETRAIN_TOP_RIGHT);
	private Talon      backLeft   = new Talon(RobotMap.DRIVETRAIN_BOTTOM_LEFT);
	private Talon      backRight  = new Talon(RobotMap.DRIVETRAIN_BOTTOM_RIGHT);
	private RobotDrive robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	
	public DriveTrain() {
		
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	/**
	 * Arcade drive with a joystick
	 * @param joy Joystick to drive with
	 */
	public void driveArcade(Joystick joy) {
		leftPower =        -joy.getZ() - joy.getY();
		rightPower = -(0 - joy.getY() + joy.getZ());
		
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		
		frontLeft.set(leftPower);
		backLeft.set(leftPower);
		frontRight.set(rightPower);
		backRight.set(rightPower);
		
		robotDrive.tankDrive(0, 0);
	}
	
	/**
	 * Tank drive using the left and right joysticks
	 * @param left Left Joystick
	 * @param right Right Joystick
	 */
	public void driveTank(Joystick left, Joystick right) {
		leftPower  = left.getY();
		rightPower = right.getY();
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		robotDrive.tankDrive(leftPower, rightPower, true);
	}
        
        /**
	 * Tank drive using the left and right joysticks
	 * @param xboxController xbox controller
	 */
	public void driveTankWithXbox(XboxController xboxController) {
		leftPower  = -xboxController.getLeftYAxis();
		rightPower = -xboxController.getRightYAxis();
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		robotDrive.tankDrive(rightPower, leftPower, true);
                
                
                SmartDashboard.putString("Leftaxis", "" + xboxController.getLeftYAxis());
                SmartDashboard.putString("Rightaxis", "" + xboxController.getRightYAxis());
	}
	
	/**
	 * STOP ALL MOTORS
	 */
	public void driveStop() {
		robotDrive.stopMotor();
	}
	
	/**
	 * Drives forward a specified speed
	 * @param speed The speed to drive at [-1, 1]
	 */
	public void driveForward(double speed) {
		robotDrive.tankDrive(speed, speed);
	}
	
	/**
	 * Turns a certain angle
	 * @param speed Speed of which to turn [-1, 1]
	 * @param right True if you want to turn right
	 */
	public void driveTurn(double speed, boolean right) {
		robotDrive.tankDrive(speed * (right ? 1 : -1), speed * (right ? -1 : 1));
	}
	
	public void updateDashboard() {
		SmartDashboard.putNumber("Left Drive Wheel", leftPower);
		SmartDashboard.putNumber("Right Drive Wheel", rightPower);
	}
}
