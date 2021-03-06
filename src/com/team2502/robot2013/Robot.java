/*----------------------------------------------------------------------------*
 * Copyright (c) FIRST 2008. All Rights Reserved.                             *
 * Open Source Software - may be modified and shared by FRC teams. The code   *
 * must be accompanied by the FIRST BSD license file in the root directory of *
 * the project.                                                               *
 *----------------------------------------------------------------------------*/

package com.team2502.robot2013;


import com.team2502.robot2013.black_box.BlackBoxProtocol;
import com.team2502.robot2013.commands.BackgroundLightUpdate;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import com.team2502.robot2013.commands.autonomous.AutonomousCommand;
import com.team2502.robot2013.commands.CommandBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
    private BlackBoxProtocol protocol;
    private Command autonomousCommand;
    private Command lightUpdate;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all subsystems
        CommandBase.init();
		OI.initDashboard();
		
        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand();
		lightUpdate = new BackgroundLightUpdate();
		
		getWatchdog().setEnabled(false);
		//getWatchdog().setExpiration(0.5);
		lightUpdate.setRunWhenDisabled(true);
		lightUpdate.start();
		BlackBoxProtocol.initialize();
		BlackBoxProtocol.start(new String[]{"10.24.85.5"}, 1180, 25);
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
