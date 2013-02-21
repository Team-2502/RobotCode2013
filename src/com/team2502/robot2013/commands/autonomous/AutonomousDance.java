/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.autonomous;

import com.team2502.robot2013.OI;
import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author josh
 */
public class AutonomousDance extends CommandBase {
	
	private long started = 0;
	
	public AutonomousDance() {
		requires(driveTrain);
		this.started = 0;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (started == 0) {
			started = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - started < 1000)
			driveTrain.driveForward(0.5);
		else if (System.currentTimeMillis() - started < 2500)
			driveTrain.dance();
		else
			driveTrain.danceSlow();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !OI.isDanceEnabled();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
