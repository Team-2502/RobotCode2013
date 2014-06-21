package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.DoubleCompressor;
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
	
	private Compressor                       compressor = new Compressor(
															RobotMap.STORAGE_PRESSURE_SWITCH,
															RobotMap.STORAGE_COMPRESSOR_RELAY_3);
	private DoubleCompressor secondaryCompressionSystem = new DoubleCompressor(
															RobotMap.STORAGE_PRESSURE_SWITCH_2,
															RobotMap.STORAGE_COMPRESSOR_RELAY,
															RobotMap.STORAGE_COMPRESSOR_RELAY_2);
	private Solenoid                      frisbeePusher = new Solenoid(RobotMap.STORAGE_PUSHER);
	private AnalogChannel               frisbeeDetector = new AnalogChannel(RobotMap.STORAGE_DETECTOR);
	
	public Storage() {
		if (!compressor.enabled())
			compressor.start();
		if (!secondaryCompressionSystem.enabled())
			secondaryCompressionSystem.start();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new StorageUpdate());
	}
	
	public void update() {
		
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
	 * Returns true if the compressor is running
	 */
	public boolean isCompressorRunning() {
		return compressor.enabled() || secondaryCompressionSystem.enabled();
	}
	
	/**
	 * Starts the compressor
	 */
	public void startCompressor() {
		compressor.start();
		secondaryCompressionSystem.start();
	}
	
	/**
	 * Stops the compressor
	 */
	public void stopCompressor() {
		compressor.stop();
		secondaryCompressionSystem.stop();
	}
	
	/**
	 * Forcefully turns the compressor on
	 */
	public void turnCompressorOn() {
		compressor.setRelayValue(Value.kForward);
		secondaryCompressionSystem.setRelayValue(Value.kForward);
	}
	
	/**
	 * Forcefully turns the compressor off
	 */
	public void turnCompressorOff() {
		compressor.setRelayValue(Value.kOff);
		secondaryCompressionSystem.setRelayValue(Value.kOff);
	}
	
	/**
	 * Has the frisbee if it is at 0.8
	 * 
	 */
	public boolean hasFrisbee() {
		return (frisbeeDetector.getVoltage() >= 0.8);
	}
}
