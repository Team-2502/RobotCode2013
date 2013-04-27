/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import com.team2502.robot2013.commands.lifter.LifterUpdate;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class Lifter extends Subsystem {
	
	private DoubleSolenoid lift = new DoubleSolenoid(RobotMap.LIFTER_SOLENOID_UP, RobotMap.LIFTER_SOLENOID_DOWN);
	
	public void initDefaultCommand() {
		setDefaultCommand(new LifterUpdate());
	}
	
	public void moveLifterUp() {
		lift.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void moveLifterDown() {
		lift.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isLifting() {
		return lift.get() == DoubleSolenoid.Value.kReverse;
	}
	
	public void updateDashboard() {
		SmartDashboard.putBoolean("Lifter Up", isLifting());
	}
}
