/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands;

import com.team2502.robot2013.Robot;
import com.team2502.robot2013.subsystems.Shooter.ShooterLightPins;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author josh
 */
public class BackgroundLightUpdate extends CommandBase {
	
	public BackgroundLightUpdate() {
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (shooter.isShooterRunning()) {
			shooter.setShooterLEDs(ShooterLightPins.WAVE_FORWARD);
		} else if (DriverStation.getInstance().isDisabled()) {
			shooter.setShooterLEDs(ShooterLightPins.PULSE);
		} else if (storage.hasFrisbee()) {
			shooter.setShooterLEDs(ShooterLightPins.ON);
		} else if (!storage.hasFrisbee()) {
			shooter.setShooterLEDs(ShooterLightPins.WAVE_REVERSE);
		} else {
			shooter.setShooterLEDs(ShooterLightPins.OFF);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
