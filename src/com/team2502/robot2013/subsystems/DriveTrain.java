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
	
	private long timeStarted  = 0;
	private double leftPower  = 0;
	private double rightPower = 0;
	
	private Talon      frontLeft  = new Talon(RobotMap.DRIVETRAIN_TOP_LEFT);
	private Talon      frontRight = new Talon(RobotMap.DRIVETRAIN_TOP_RIGHT);
	private Talon      backLeft   = new Talon(RobotMap.DRIVETRAIN_BOTTOM_LEFT);
	private Talon      backRight  = new Talon(RobotMap.DRIVETRAIN_BOTTOM_RIGHT);
	private RobotDrive robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	
	public DriveTrain() {
		robotDrive.setSafetyEnabled(false);
		timeStarted = System.currentTimeMillis();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	/**
	 * Tank drive using the left and right joysticks
	 * @param xboxController xbox controller
	 */
	public void driveTankWithXbox(XboxController xboxController) {
		leftPower  = xboxController.getLeftYAxis();
		rightPower = xboxController.getRightYAxis();
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		robotDrive.tankDrive(leftPower, rightPower, true);
	}
	
	/**
	 * Tank drive using the left and right joysticks
	 * @param left Left Joystick
	 * @param right Right Joystick
	 */
	public void driveTank(Joystick left, Joystick right) {
		leftPower  = left.getY() * -(left.getZ() - 1) / 2;
		rightPower = right.getY() * -(right.getZ() - 1) / 2;
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		if (OI.isCompetitionVersion()) {
			robotDrive.tankDrive(leftPower, rightPower, true);
		} else {
			long diff = System.currentTimeMillis() - timeStarted;
			SmartDashboard.putNumber("Debug", diff);
			if (diff < 15000) {
				frontLeft.set(-leftPower);
				frontRight.set(rightPower);
				backLeft.set(0);
				backRight.set(0);
			} else {
				frontLeft.set(0);
				frontRight.set(0);
				backLeft.set(-leftPower);
				backRight.set(rightPower);
				if (diff >= 30000) {
					timeStarted = System.currentTimeMillis();
				}
			}
		}
	}
	
	public void driveTankSlow(Joystick left, Joystick right) {
		leftPower  = left.getY() * .75;
		rightPower = right.getY() * .75;
		
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		
		robotDrive.tankDrive(rightPower, leftPower, true);
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
                
                // If analog stick pushed then half speed
                if(xboxController.getLeftJoystickButton())
                {
                    leftPower = leftPower * .5;
                }
                
                if(xboxController.getRightJoystickButton())
                {
                    rightPower = rightPower * .5;
                }
                
                
                // Make precision controlls for the dpad.
                if(xboxController.getDPadLeft())
                {
                    leftPower = -.6;
                    rightPower = .6;
                }
                else if(xboxController.getDPadRight())
                {
                    leftPower = .6;
                    rightPower = -.6;
                }
                
                
		robotDrive.tankDrive(rightPower, leftPower, true);
                
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
