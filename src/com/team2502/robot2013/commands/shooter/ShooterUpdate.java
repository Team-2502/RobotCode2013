/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ShooterUpdate extends CommandBase {
	
	public ShooterUpdate() {
		requires(shooter);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		shooter.startFan();
		shooter.stopShooter();
		shooter.updateDashboard();
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
}
