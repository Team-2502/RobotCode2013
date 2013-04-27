/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.wavers;

import com.team2502.robot2013.commands.CommandBase;
import com.team2502.robot2013.subsystems.Wavers;

/**
 *
 * @author josh
 */
public class ResetToucher extends CommandBase {
	
	public ResetToucher() {
		requires(wavers);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		wavers.setLeftToucher(Wavers.ServoPosition.LEFT_UP);
		wavers.setRightToucher(Wavers.ServoPosition.RIGHT_UP);
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
