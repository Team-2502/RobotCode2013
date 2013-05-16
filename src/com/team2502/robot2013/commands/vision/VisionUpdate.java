/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.vision;

import com.team2502.robot2013.commands.CommandBase;
import com.team2502.robot2013.subsystems.Vision.VisionTarget;

/**
 *
 * @author josh
 */
public class VisionUpdate extends CommandBase {
	
	private int frameDelay;
	
	public VisionUpdate() {
		requires(vision);
		frameDelay = 1000;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (System.currentTimeMillis() - vision.lastFrame() >= frameDelay) {
			VisionTarget [] targets = vision.processFrame();
			if (targets.length > 0) {
				frameDelay = 300;
			} else {
				frameDelay = 1000;
			}
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
