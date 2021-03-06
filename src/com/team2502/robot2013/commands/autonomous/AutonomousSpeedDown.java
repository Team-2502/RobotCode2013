package com.team2502.robot2013.commands.autonomous;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class AutonomousSpeedDown extends CommandBase {
	
	public AutonomousSpeedDown() {
		requires(shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		shooter.stopShooter();
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
