/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author josh
 */
public class DoubleCompressor {
	
	private CompressorUpdater updater;
	private DigitalInput pressureSwitch;
	private Relay compressor1;
	private Relay compressor2;
	
	public DoubleCompressor(int pressureChannel, int comp1Channel, int comp2Channel) {
		pressureSwitch = new DigitalInput(pressureChannel);
		compressor1 = new Relay(comp1Channel);
		compressor2 = new Relay(comp2Channel);
		updater = new CompressorUpdater();
	}
	
	public boolean enabled() {
		return updater.running;
	}
	
	public void start() {
		updater.start();
	}
	
	public void stop() {
		updater.stop();
	}
	
	public boolean getPressureSwitchValue() {
		return pressureSwitch.get();
	}
	
	public void setRelayValue(Relay.Value value) {
		compressor1.set(value);
		compressor2.set(value);
	}
	
	private class CompressorUpdater implements Runnable {
		
		private Thread thread;
		private boolean running = false;
		
		public void start() {
			if (!running) {
				thread = new Thread(updater);
				running = true;
				thread.start();
			}
		}
		
		public void stop() {
			running = false;
			thread.interrupt();
		}
		
		public void run() {
			System.out.println("Double compressor running");
			while (running) {
				if (!getPressureSwitchValue()) {
					compressor1.set(Relay.Value.kForward);
					compressor2.set(Relay.Value.kForward);
				} else {
					compressor1.set(Relay.Value.kOff);
					compressor2.set(Relay.Value.kOff);
				}
				try { Thread.sleep(200); } catch (InterruptedException e) { running = false; }
			}
			System.out.println("Double compressor stopped");
		}
		
	}
	
}
