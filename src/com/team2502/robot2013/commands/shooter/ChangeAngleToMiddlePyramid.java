/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.shooter;

import com.team2502.robot2013.commands.CommandBase;
import com.team2502.robot2013.subsystems.Shooter.ShooterPoint;

/**
 *
 * @author Josh Larson
 */
public class ChangeAngleToMiddlePyramid extends CommandBase {
	
	public ChangeAngleToMiddlePyramid() {
		requires(shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		shooter.angleSetToPoint(new ShooterPoint("MIDDLE_PYRAMID"));
		shooter.startAnglePID();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		
	}
}
