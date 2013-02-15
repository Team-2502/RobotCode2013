package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.shooter.ShooterUpdate;

/**
 * The Shooter subsystem to shoot frisbees into the target,
 * and change the angles to get into it.
 */
public class Shooter extends Subsystem {
	private double zeroedAngleValue = 0;
	
	private AnalogChannel angle         = new AnalogChannel(RobotMap.SHOOTER_ANGLE);
	private Jaguar        angleMotor    = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
	private Jaguar        backWheel     = new Jaguar(RobotMap.SHOOTER_BACK_WHEEL);
	private Jaguar        frontWheel    = new Jaguar(RobotMap.SHOOTER_FRONT_WHEEL);
	
	public Shooter() {
		
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		setDefaultCommand(new ShooterUpdate());
	}
	
	/**
	 * Fire it up!
	 */
	public void startShooter() {
		frontWheel.set(1.0);
		backWheel.set(0.75);
	}
	
	/**
	 * STOP THE SHOOTER!!
	 */
	public void stopShooter() {
		frontWheel.set(0);
		backWheel.set(0);
	}
	
	/**
	 * Reset the angle determined by the potentiometer. 
	 */
	public void resetAngle() {
		zeroedAngleValue = angle.getVoltage();
	}
	
	/**
	 * Returns the angle that the shooter is pointed at
	 */
	public double getAngle() {
		return angle.getVoltage() - zeroedAngleValue;
	}
	
	/**
	 * Start moving the shooter to angle up.
	 * @param speed The speed to move the shooter up [1, 3]
	 */
	public void angleUp(double speed) {
		angleMotor.set(speed);
	}
	
	/**
	 * Start moving the shooter to angle down.
	 * @param speed The speed to move the shooter down [1, 3]
	 */
	public void angleDown(double speed) {
		angleMotor.set(-1 * speed);
	}
	
	/**
	 * Returns true if the shooter is running at 30% speed or higher
	 */
	public boolean isShooterRunning() {
		return (frontWheel.get() > 0.3 && backWheel.get() > 0.3);
	}
	
	/**
	 * Updates the SmartDashboard GUI with the Shooter data
	 */
	public void updateDashboard() {
		SmartDashboard.putNumber("Front Shooter Wheel", frontWheel.get());
		SmartDashboard.putNumber("Back Shooter Wheel", backWheel.get());
                
	}
}
