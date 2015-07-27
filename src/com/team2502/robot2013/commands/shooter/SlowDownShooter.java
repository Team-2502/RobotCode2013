/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.shooter;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author Brady
 */
public class SlowDownShooter extends CommandBase {
    
    private double time;
    
    public SlowDownShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        requires(shooter);
        time = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (System.currentTimeMillis() - time < 500) {
            shooter.slowDownShooter();
        } else {
            shooter.stopShooter();
        }
    shooter.updateDashboard();
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
