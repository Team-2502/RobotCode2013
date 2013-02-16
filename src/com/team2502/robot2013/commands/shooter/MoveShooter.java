/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.shooter;

import com.team2502.robot2013.OI;
import com.team2502.robot2013.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author MLM
 */
public class MoveShooter extends CommandBase {
    
    public MoveShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
              
        SmartDashboard.putString("shooterGoing", "" + OI.xboxController.getTriggerAxis());
        
        if (OI.xboxController.getTriggerAxis() < -.05)
        {
            shooter.angleUp(Math.abs(OI.xboxController.getTriggerAxis()));
        }
        else if ((OI.xboxController.getTriggerAxis() > .05))
        {
            shooter.angleDown(Math.abs(OI.xboxController.getTriggerAxis()));
        }
        else
            shooter.angleUp(0);
        
        shooter.updateDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.angleUp(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}