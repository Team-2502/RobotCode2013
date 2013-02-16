/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.autonomous;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author josh
 */
public class AutonomousShootFrisbee extends CommandBase {
	
	public AutonomousShootFrisbee(double delay) {
		requires(storage);
		setTimeout(delay);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		storage.pushFrisbee();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		storage.retractFrisbee();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
