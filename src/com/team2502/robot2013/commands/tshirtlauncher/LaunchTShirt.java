package com.team2502.robot2013.commands.tshirtlauncher;

import com.team2502.robot2013.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class LaunchTShirt extends CommandBase {
	
	private int launcher;
	
	public LaunchTShirt(int launcher, double launchTime) {
		requires(tshirtLauncher);
		this.launcher = launcher;
		int maxLauncher = tshirtLauncher.getLauncherCount();
		if (launcher < 1 || launcher > maxLauncher)
			throw new IllegalArgumentException("Launcher # must be between 1 and " + maxLauncher);
		setTimeout(launchTime);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		tshirtLauncher.setCompressForLaunch(launcher);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		tshirtLauncher.setCompressForLaunch(launcher);
		tshirtLauncher.startLaunch(launcher);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	// Called once after isFinished returns true
	protected void end() {
		tshirtLauncher.stopLaunch(launcher);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
