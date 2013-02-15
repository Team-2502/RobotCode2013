/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class Lifter extends Subsystem {
	
	private Solenoid lifterUp   = new Solenoid(RobotMap.LIFTER_SOLENOID_UP);
	private Solenoid lifterDown = new Solenoid(RobotMap.LIFTER_SOLENOID_DOWN);
	
	public void initDefaultCommand() {
		
	}
	
	public void moveLifterUp() {
		lifterUp.set(true);
		lifterDown.set(false);
	}
	
	public void moveLifterDown() {
		lifterUp.set(false);
		lifterDown.set(true);
	}
	
	public void updateDashboard() {
		SmartDashboard.putBoolean("Lifter Up", lifterUp.get());
	}
}
