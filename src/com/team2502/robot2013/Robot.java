/*----------------------------------------------------------------------------*
 * Copyright (c) FIRST 2008. All Rights Reserved.                             *
 * Open Source Software - may be modified and shared by FRC teams. The code   *
 * must be accompanied by the FIRST BSD license file in the root directory of *
 * the project.                                                               *
 *----------------------------------------------------------------------------*/

package com.team2502.robot2013;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import com.team2502.robot2013.commands.AutonomousCommand;
import com.team2502.robot2013.commands.CommandBase;
import com.team2502.robot2013.commands.shooter.MoveShooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;
    Command moveShooter;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand();
        moveShooter = new MoveShooter();
		
		getWatchdog().setEnabled(false);
		//getWatchdog().setExpiration(0.1);
		
        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
	
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        moveShooter.start();
        
        autonomousCommand.cancel();
    }
	
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		//getWatchdog().feed();
        Scheduler.getInstance().run();
    }
}
