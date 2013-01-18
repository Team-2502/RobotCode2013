/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands;

/**
 *
 * @author Virinchi
 */
public class DriveWithJoystick extends CommandBase {
    
    public DriveWithJoystick() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.driveWithJoystick(oi.getJoystick(1));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
