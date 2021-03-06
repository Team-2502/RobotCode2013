/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.autonomous;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class AutonomousShootFrisbees extends CommandBase {
	
	private long started;
	private double holdFrisbee;
	private double maxDelay;
	
	public AutonomousShootFrisbees(double holdFrisbee, double maxDelay) {
		requires(storage);
		requires(shooter);
		this.started = System.currentTimeMillis();
		this.holdFrisbee = holdFrisbee;
		this.maxDelay = maxDelay;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		shooter.startShooter();
		long timeDiff = System.currentTimeMillis() - started;
		if (timeDiff <= holdFrisbee * 1000) {
			storage.pushFrisbee();
		} else if (timeDiff >= maxDelay * 1000) {
			started = System.currentTimeMillis();
		} else {
			storage.retractFrisbee();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !storage.hasFrisbee();
	}
	
	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
