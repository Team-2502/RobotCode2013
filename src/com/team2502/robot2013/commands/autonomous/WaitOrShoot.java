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
public class WaitOrShoot extends CommandBase {
	
	public WaitOrShoot(double delay) {
		requires(shooter);
		setTimeout(delay);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		shooter.startShooter();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (isTimedOut() || shooter.isShooterUpToSpeed()) && shooter.isAnglePIDAtSetpoint();
	}
	
	// Called once after isFinished returns true
	protected void end() {
		
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		
	}
}
