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
	
	private Talon      topLeft    = new Talon(RobotMap.DRIVETRAIN_TOP_LEFT);
	private Talon      topRight   = new Talon(RobotMap.DRIVETRAIN_TOP_RIGHT);
	private Talon      bottomLeft     = new Talon(RobotMap.DRIVETRAIN_BOTTOM_LEFT);
	private Talon      bottomRight    = new Talon(RobotMap.DRIVETRAIN_BOTTOM_RIGHT);
	private RobotDrive robotDrive   = new RobotDrive(topLeft, bottomLeft, topRight, bottomRight);
	//private Relay      defenseFan   = new Relay(RobotMap.DRIVETRAIN_DEFENSE_FAN);
	private Talon      defenseFan   = new Talon(RobotMap.DRIVETRAIN_DEFENSE_FAN);
	
	public DriveTrain() {
		robotDrive.setSafetyEnabled(false);
		timeStarted = System.currentTimeMillis();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	/**
	 * Does this require an explanation?
	 */
	public void dance() {
		robotDrive.tankDrive(-0.75, 0.75);
	}
	
	/**
	 * You will try to explain...
	 */
	public void danceSlow() {
		robotDrive.tankDrive(-0.6, 0.6);
	}
	
	/**
	 * Tank drive using the left and right joysticks
	 * @param left Left Joystick
	 * @param right Right Joystick
	 */
	public void driveTank(Joystick left, Joystick right) {
		rightPower  = left.getY() * -(left.getZ() - 1) / 2 * Math.abs(left.getY());         //these are backwords because josh. trust me they are correct as is :)
		leftPower = right.getY() * -(right.getZ() - 1) / 2 * Math.abs(right.getY());        //squared joystick made by michael and untested
		if (OI.isOmniForward()) {
			leftPower  = -leftPower;
			rightPower = -rightPower;
		}
		bottomLeft.set(leftPower);
                topLeft.set(leftPower);
                bottomRight.set(-rightPower);
                topRight.set(-rightPower);
	}
        
       
	public void driveArcade(Joystick joy) {
                boolean squared = true;
		double y = joy.getY() * (squared ? Math.abs(joy.getY()) : 1);
		double z = joy.getZ() * (squared ? Math.abs(joy.getZ()) : 1);
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
		topLeft.set(-speed);
		topRight.set(speed);
		bottomLeft.set(-speed);
		bottomRight.set(speed);
	}
	
	/**
	 * Turns a certain angle
	 * @param speed Speed of which to turn [-1, 1]
	 * @param right True if you want to turn right
	 */
	public void driveTurn(double speed, boolean right) {
		robotDrive.tankDrive(speed * (right ? 1 : -1), speed * (right ? -1 : 1));
	}
	
	/**
	 * Start spinning the fan
	 */
	public void startFan() {
		//defenseFan.set(Relay.Value.kForward);
		defenseFan.set(1);
	}
	
	/**
	 * Stops spinning the fan
	 */
	public void stopFan() {
		//defenseFan.set(Relay.Value.kOff);
		defenseFan.set(0);
	}
	
	public boolean isFanStarted() {
		//return defenseFan.get() != Relay.Value.kOff;
		return defenseFan.get() != 0;
	}
	
	public void updateDashboard() {
		SmartDashboard.putNumber("Left Drive Wheel", leftPower);
		SmartDashboard.putNumber("Right Drive Wheel", rightPower);
		SmartDashboard.putNumber("Fan", defenseFan.get());
	}
}
