/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.lifter;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ToggleLifter extends CommandBase {
	
	public ToggleLifter() {
		requires(lifter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (lifter.isLifting())
			lifter.moveLifterDown();
		else
			lifter.moveLifterUp();
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
		end();
	}
}
