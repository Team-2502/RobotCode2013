package com.team2502.robot2013;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SensorBase;

/**
 *
 * @author Josh Larson
 */
public class PhotoelectricSensor extends SensorBase {
	private DigitalInput input;
	private Counter counter;
	private Thread rateThread;
	private RateCalculator rateCalc;
	
	public PhotoelectricSensor(int channel) {
		input = new DigitalInput(channel);
		initialize();
	}
	
	public PhotoelectricSensor(int slot, int channel) {
		input = new DigitalInput(slot, channel);
		initialize();
	}
	
	/**
	 * Initializes important objects for the 
	 */
	private void initialize() {
		counter    = new Counter(input);
		counter.setSemiPeriodMode(false);
		rateCalc   = new RateCalculator(counter);
		rateThread = new Thread(rateCalc);
	}
	
	/**
	 * Starts the sensor. Meaning it will only start collecting data after you
	 * call this method.
	 */
	public void start() {
		counter.start();
		rateThread.start();
	}
	
	/**
	 * Resets the counter. Including last updated, current rate, and current
	 * count
	 */
	public void reset() {
		rateCalc.reset();
	}
	
	/**
	 * Returns the digital input state
	 * @return True/False based on the digital input pin
	 */
	public boolean getState() {
		return input.get();
	}
	
	/**
	 * @return The number of ticks the counter is currently at
	 */
	public int getTicks() {
		return counter.get();
	}
	
	/**
	 * Returns an "exponential moving average" where it keeps 80% of the last
	 * average rate recorded (lastRate + rate) / 2 and it keeps the other 20%
	 * of the new rate. In other terms: it's a smoothed out rate
	 * @return The exponential moving average in rotations per second
	 */
	public double getAverageRate() {
		return rateCalc.getAverageRate();
	}
	
	/**
	 * The rate of the encoder, recorded as (lastRate + rate) / 2.
	 * @return The "raw" rate of the encoder in rotations per second
	 */
	public double getRate() {
		return rateCalc.getRate();
	}
	
	/**
	 * Same as getRate() but it is now in RPMs
	 * @return The "raw" rate of the encoder in rotations per minute
	 */
	public double getRateRPM() {
		return rateCalc.getRate() * 60;
	}
	
	/**
	 * Sets the delay the rate calculation thread will run at. Default is 10ms.
	 * It is best if it is set no more than 20
	 * @param delayPerLoop The delay rate per rate calculation loop.
	 */
	public synchronized void setThreadSpeed(int delayPerLoop) {
		rateCalc.delayPerLoop = delayPerLoop;
	}
	
	/**
	 * Sets how fast the rate calculation thread will record updates. Default is
	 * 200ms. 
	 * @param updateRate 
	 */
	public synchronized void setUpdateRate(int updateRate) {
		rateCalc.updateRate = updateRate;
	}
	
	private class RateCalculator implements Runnable {
		
		private Counter c;
		private double lastRate;
		private double avgRate;
		private long lastCount;
		private long lastTick;
		private volatile int delayPerLoop;
		private volatile int updateRate;
		
		public RateCalculator(Counter c) {
			this.c       = c;
			lastRate     = 0;
			lastCount    = 0;
			lastTick     = System.currentTimeMillis();
			delayPerLoop = 10;
			updateRate   = 200;
		}
		
		public void run() {
			boolean running = true;
			while (running) {
				long _msNow = System.currentTimeMillis();
				int _ticksCurrent = counter.get();
				long _lastTick = this.lastTick;
				long _lastCount = this.lastCount;
				if (_msNow - _lastTick >= updateRate) {
					long _msElapsed = _msNow - _lastTick;
					long _rps = (_ticksCurrent - _lastCount) * 1000 / _msElapsed;
					synchronized (this) {
						avgRate = (lastRate * 0.8) + (_rps * 0.2);
						lastRate = (lastRate + _rps) / 2;
						lastCount = _ticksCurrent;
						lastTick = _msNow;
					}
				}
				try {
					Thread.sleep(delayPerLoop);
				} catch (InterruptedException e) {
					running = false;
				}
			}
		}
		
		private void reset() {
			synchronized (this) {
				lastTick  = System.currentTimeMillis();
				lastCount = 0;
				lastRate  = 0;
			}
		}
		
		private double getAverageRate() {
			synchronized (this) { return avgRate; }
		}
		
		private double getRate() {
			synchronized (this) { return lastRate; }
		}
	}
}
