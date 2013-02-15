/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.storage;

import com.team2502.robot2013.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class PushFrisbeeOut extends CommandBase {
	
	public PushFrisbeeOut() {
		setTimeout(0.3);
		requires(storage);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//if (shooter.isShooterRunning())
			storage.pushFrisbee();
		storage.update();
		storage.updateDashboard();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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
