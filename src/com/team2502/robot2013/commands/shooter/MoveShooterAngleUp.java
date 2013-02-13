/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.shooter;

import com.team2502.robot2013.OI;
import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author josh
 */
public class MoveShooterAngleUp extends CommandBase {
	
	public MoveShooterAngleUp() {
		requires(shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		shooter.angleUp(OI.shooter.getY());
		shooter.updateDashboard();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		shooter.angleUp(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
