package com.team2502.robot2013.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.RobotMap;
import com.team2502.robot2013.commands.storage.StorageUpdate;
import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Josh Larson
 */
public class Storage extends Subsystem {
	
	private Compressor    compressor    = new Compressor(
											RobotMap.STORAGE_PRESSURE_SWITCH,
											RobotMap.STORAGE_COMPRESSOR_RELAY);
	private Solenoid      frisbeePusher = new Solenoid(RobotMap.STORAGE_PUSHER);
	private AnalogChannel frisbeeDetector = new AnalogChannel(RobotMap.STORAGE_DETECTOR);
	
	public Storage() {
		if (!compressor.enabled())
			compressor.start();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new StorageUpdate());
	}
	
	public void update() {
		if (compressor.getPressureSwitchValue())
			compressor.stop();
		else
			compressor.start();
	}
	
	public void updateDashboard() {
		SmartDashboard.putBoolean("Compressor Running", isCompressorRunning());
		SmartDashboard.putBoolean("Frisbee Pushed", isPushingFrisbee());
		SmartDashboard.putBoolean("Frisbee Detector", hasFrisbee());
	}
	
	/**
	 * Returns true if the solenoid is being pushed out
	 */
	public boolean isPushingFrisbee() {
		//return frisbeePusher.get().value == Relay.Value.kForward.value;
		return frisbeePusher.get();
	}
	
	/**
	 * Push the solenoid out, to push the frisbee out
	 */
	public void pushFrisbee() {
		//frisbeePusher.set(Relay.Value.kForward);
		frisbeePusher.set(true);
	}
	
	/**
	 * Pull the solenoid back to reset it
	 */
	public void retractFrisbee() {
		//frisbeePusher.set(Relay.Value.kOff);
		frisbeePusher.set(false);
	}
	
	/**
	 * Returns true if the program should compress the compressor
	 */
	public boolean shouldCompress() {
		return !compressor.getPressureSwitchValue();
	}
	
	/**
	 * Returns true if the compressor is running
	 */
	public boolean isCompressorRunning() {
		return compressor.enabled();
	}
	
	/**
	 * Starts the compressor
	 */
	public void startCompressor() {
		compressor.start();
	}
	
	/**
	 * Stops the compressor
	 */
	public void stopCompressor() {
		compressor.stop();
	}
	
	/**
	 * Forcefully turns the compressor on
	 */
	public void turnCompressorOn() {
		compressor.setRelayValue(Value.kForward);
	}
	
	/**
	 * Forcefully turns the compressor off
	 */
	public void turnCompressorOff() {
		compressor.setRelayValue(Value.kOff);
	}
	
	/**
	 * Has the frisbee if it is at 0.2
	 * 
	 */
	public boolean hasFrisbee() {
		return (frisbeeDetector.getVoltage() >= 1.5);
	}
}
