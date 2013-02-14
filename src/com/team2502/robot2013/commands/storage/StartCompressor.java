/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.storage;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author josh
 */
public class StartCompressor extends CommandBase {
	
	public StartCompressor() {
		requires(storage);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		storage.stopCompressor();
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		storage.turnCompressorOn();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		storage.turnCompressorOff();
		storage.startCompressor();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}