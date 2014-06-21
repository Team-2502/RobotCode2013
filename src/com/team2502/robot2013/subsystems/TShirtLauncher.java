package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Josh Larson
 */
public class TShirtLauncher extends Subsystem {
	
	private static final int launcherCount = 2;
	private Solenoid launcher1 = new Solenoid(RobotMap.TSHIRTLAUNCHER_LAUNCHER1);
	private Solenoid launcher2 = new Solenoid(RobotMap.TSHIRTLAUNCHER_LAUNCHER2);
	private Solenoid compress = new Solenoid(RobotMap.TSHIRTLAUNCHER_COMPRESSOR);
	
	public void initDefaultCommand() {
		
	}
	
	public int getLauncherCount() {
		return launcherCount;
	}
	
	public void startLaunch(int launcherNum) {
		if (launcherNum < 1 || launcherNum > launcherCount) {
			System.err.println("Warning! Launcher # is out of range (" + launcherNum + ")");
			return;
		}
		Solenoid launcher = getLauncher(launcherNum);
		launcher.set(true);
	}
	
	public void stopLaunch(int launcherNum) {
		if (launcherNum < 1 || launcherNum > launcherCount) {
			System.err.println("Warning! Launcher # is out of range (" + launcherNum + ")");
			return;
		}
		Solenoid launcher = getLauncher(launcherNum);
		launcher.set(false);
	}
	
	private Solenoid getLauncher(int launcherNum) {
		switch (launcherNum) {
			case 1:
				return launcher1;
			case 2:
				return launcher2;
			default:
				return null;
		}
	}
	
	public void setCompress(boolean compress) {
		this.compress.set(compress);
	}
	
	public boolean getCompress() {
		return compress.get();
	}
	
	public void setCompressForLaunch(int launcherNum) {
		switch (launcherNum) {
			case 1:
				this.compress.set(true);
				break;
			case 2:
				this.compress.set(false);
				break;
		}
	}
	
	public boolean isSafeToLaunch(int launcherNum) {
		switch (launcherNum) {
			case 1:
				return compress.get();
			case 2:
				return !compress.get();
			default:
				return false;
		}
	}
	
}
