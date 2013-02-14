// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package com.team2502.robot2013.commands.drive_train;

import com.team2502.robot2013.OI;
import com.team2502.robot2013.commands.CommandBase;

/**
 *
 */
public class  HalfDriveWithJoystick extends CommandBase {
	public HalfDriveWithJoystick() {
		requires(driveTrain);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		driveTrain.driveTankSlow(OI.left, OI.right);
		driveTrain.updateDashboard();
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
