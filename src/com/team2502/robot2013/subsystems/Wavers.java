/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author josh
 */
public class Wavers extends Subsystem {
	private Servo      leftToucher  = new Servo(RobotMap.WAVERS_SERVO_LEFT);
	private Servo      rightToucher = new Servo(RobotMap.WAVERS_SERVO_RIGHT);

	public void initDefaultCommand() {
		
	}
	
	/**
	 * Sets the servo position between [0, 1]
	 * @param position Position to set
	 */
	public void setLeftToucher(double position) {
		leftToucher.set(position);
	}
	
	/**
	 * Sets the servo position between [0, 1]
	 * @param position Position to set
	 */
	public void setRightToucher(double position) {
		rightToucher.set(position);
	}
	
	public class ServoPosition {
		public static final double LEFT_IN   = 1;
		public static final double LEFT_OUT  = 0;
		public static final double LEFT_UP   = 0.7;
		
		public static final double RIGHT_IN  = 0.3;
		public static final double RIGHT_OUT = 1;
		public static final double RIGHT_UP  = 0.7;
	}
}
