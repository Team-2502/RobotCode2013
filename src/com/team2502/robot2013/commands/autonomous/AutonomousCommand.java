package com.team2502.robot2013.commands.autonomous;

import com.team2502.robot2013.OI;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * The autonomous command for the 2013 competition
 */
public class  AutonomousCommand extends CommandGroup {
	
	public AutonomousCommand() {
		setTimeout(15);
		
		// Calibrates the shooter by moving it all the way down
		addSequential(new AutonomousCalibrate());
		
		// Sets the angle to Autonomous, so it can shoot
		addSequential(new AutonomousSetAngle());
		
		// Speeds up the shooter motors
		addParallel(new AutonomousSpeedUp());
		addSequential(new WaitCommand(3));
		
		addSequential(new AutonomousShootFrisbees(0.05, 1));
		
		// Spins down the motors
		addSequential(new AutonomousSpeedDown());
		
		// Do a dance
		addSequential(new AutonomousDance());
	}
}
