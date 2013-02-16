package com.team2502.robot2013.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * The autonomous command for the 2013 competition
 */
public class  AutonomousCommand extends CommandGroup {
	
	public AutonomousCommand() {
		setTimeout(15);
		addParallel(new AutonomousSpeedUp());
		addSequential(new WaitCommand(3));
		for (int i = 0; i < 3; i++) {
			addSequential(new AutonomousShootFrisbee(0.2));
			addSequential(new WaitCommand(1));
		}
		addSequential(new AutonomousSpeedDown());
	}
}
