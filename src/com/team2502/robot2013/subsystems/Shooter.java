package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.PhotoelectricSensor;
import com.team2502.robot2013.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.shooter.ShooterUpdate;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

/**
 * The Shooter subsystem to shoot frisbees into the target,
 * and change the angles to get into it.
 */
public class Shooter extends Subsystem {

	private double zeroedAngleValue = 0;
	private long startShooting = 0;
	private Encoder angleEncoder = new Encoder(
			RobotMap.SHOOTER_ENCODER_A,
			RobotMap.SHOOTER_ENCODER_B);
	private Jaguar angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
	private Jaguar backWheel = new Jaguar(RobotMap.SHOOTER_BACK_WHEEL);
	private Jaguar frontWheel = new Jaguar(RobotMap.SHOOTER_FRONT_WHEEL);
	private Solenoid photocoderPower = new Solenoid(2, RobotMap.SHOOTER_PHOTO_ENCODERPWR);
	// ADDED
	private PhotoelectricSensor photocoder = new PhotoelectricSensor(RobotMap.SHOOTER_PHOTO_ENCODER);
	private PIDController anglePID = new PIDController(0.05, 0, 0, angleEncoder, angleMotor);
	// PID [0.05, 0, 0]
	private DigitalOutput [] arduinoPins;
	
	public Shooter() {
		arduinoPins = new DigitalOutput[3];
		arduinoPins[0] = new DigitalOutput(RobotMap.SHOOTER_ARDUINO_PIN1);
		arduinoPins[1] = new DigitalOutput(RobotMap.SHOOTER_ARDUINO_PIN2);
		arduinoPins[2] = new DigitalOutput(RobotMap.SHOOTER_ARDUINO_PIN3);
		
		angleEncoder.setReverseDirection(true);
		angleEncoder.setDistancePerPulse(1);
		angleEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		angleEncoder.start();
		//SmartDashboard.putBoolean("Shooter Angle PID", true);
		anglePID.setInputRange(0, 1200);
		anglePID.setPercentTolerance(1);
		anglePID.setContinuous(true);
		angleSetToPoint(new ShooterPoint("FULL_DOWN"));
		photocoder.start(); // ADDED 
		photocoderPower.set(true);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new ShooterUpdate());
	}

	/**
	 * Fire it up!
	 */
	public void startShooter() {
		frontWheel.set(1.0);
		backWheel.set(1.0);
		if (startShooting == 0)
			startShooting = System.currentTimeMillis();
	}

	/**
	 * STOP THE SHOOTER!!
	 */
	public void stopShooter() {
		frontWheel.set(0);
		backWheel.set(0);
		startShooting = 0;
	}
	
	/**
	 * Reset the angle determined by the encoder 
	 */
	public void resetAngle() {
		angleEncoder.reset();
	}
	
	/**
	 * Start moving the shooter to angle up.
	 * @param speed The speed to move the shooter up [1, 3]
	 */
	public void angleUp(double speed) {
		if (anglePID.isEnable()) {
			anglePID.disable();
		}
		angleMotor.set(speed);
	}

	/**
	 * Start moving the shooter to angle down.
	 * @param speed The speed to move the shooter down [1, 3]
	 */
	public void angleDown(double speed) {
		if (anglePID.isEnable()) {
			anglePID.disable();
		}
		angleMotor.set(-1 * speed);
	}

	/**
	 * Start the manual aiming by setting the motor to stop moving, and disabling
	 * the PID loop
	 */
	public void startManualAiming() {
		angleMotor.set(0);
		if (anglePID.isEnable()) {
			anglePID.disable();
		}
	}

	/**
	 * Stops the manual aiming by setting the motor to stop moving, and enabling
	 * the PID loop
	 */
	public void stopManualAiming() {
		angleMotor.set(0);
	}

	/**
	 * Starts the angle PID, to control the angle
	 */
	public void startAnglePID() {
		anglePID.enable();
	}

	/**
	 * Stops the angle PID from controlling the angle
	 */
	public void stopAnglePID() {
		anglePID.disable();
	}

	/**
	 * Returns if the angle PID is at the specified setpoint
	 * @return True if the PID is at the setpoint
	 */
	public boolean isAnglePIDAtSetpoint() {
		return anglePID.onTarget();
	}
	
	/**
	 * Returns true if the shooter is running at 30% speed or higher
	 */
	public boolean isShooterRunning() {
		return (frontWheel.get() >= 0.3);
	}
	
	/**
	 * Returns true if the shooter is running at 12 rotations per second or faster
	 */
	public boolean isShooterUpToSpeed() {
		return photocoder.getAverageRate() >= 800;
	}
	
	/**
	 * Updates the SmartDashboard GUI with the Shooter data
	 */
	public void updateDashboard() {
		long timeout = 3000 - (System.currentTimeMillis() - startShooting);
		if (timeout < 0) timeout = 0;
		SmartDashboard.putNumber("Front Shooter Wheel", frontWheel.get());
		SmartDashboard.putNumber("Back Shooter Wheel", backWheel.get());
		SmartDashboard.putNumber("Shooter Timeout: ", timeout);
		//SmartDashboard.putData("Shooter Angle PID", anglePID);
		//SmartDashboard.putData("Shooter Encoder", angleEncoder);
		//SmartDashboard.putNumber("Current Angle Value", angleEncoder.getDistance());
		SmartDashboard.putNumber("Angle Encoder", angleEncoder.getDistance());
		SmartDashboard.putNumber("Front Wheel Encoder", photocoder.getAverageRate()); // ADDED
		SmartDashboard.putBoolean("Shooter Spun Up", photocoder.getAverageRate() >= 600);
	}
	
	/**
	 * Sets the shooter's angle to a specific point specified by the argument
	 * @param p The point to set the PID to.
	 */
	public final void angleSetToPoint(ShooterPoint p) {
		// Rotations * Encoder Pulses per Rotation
		anglePID.setSetpoint(p.getRotations());
	}
	
	/**
	 * Set the shooter LED bit pins
	 * @param mode The ShooterLightPin value
	 */
	public void setShooterLEDs(int mode) {
		for (int i = 0; i < 3; i++) {
			/*
			 * Checking if i << 1 is set to non-zero
			 * If i = 0, (1 << i) = 1
			 * If i = 1, (1 << i) = 2
			 * If i = 2, (1 << i) = 4
			 */
			arduinoPins[i].set((mode & (1 << i)) != 0);
		}
	}
	
	/**
	 * A specific angling point the shooter goes to when specified.
	 */
	public static class ShooterPoint {
		
		private static final int FULL_DOWN = 50;
		private static final int AUTONOMOUS = 450;
		private static final int MIDDLE_PYRAMID = 530;
		
		private int rotations = 0;

		/**
		 * A specific angling point the shooter goes to when specified.
		 * Default: 0 (Full-Down)
		 */
		public ShooterPoint() {
			
		}
		
		public ShooterPoint(int rotations) {
			this.rotations = rotations;
		}

		public ShooterPoint(String name) {
			if (name.compareTo("FULL_DOWN") == 0)
				rotations = FULL_DOWN;
			else if (name.compareTo("AUTONOMOUS") == 0)
				rotations = AUTONOMOUS;
			else if (name.compareTo("MIDDLE_PYRAMID") == 0)
				rotations = MIDDLE_PYRAMID;
		}
		
		public void setRotations(int rotations) {
			this.rotations = rotations;
		}

		public int getRotations() {
			return rotations;
		}

		public String toString() {
			String name = "ShooterPoint: ";
			switch (rotations) {
				case FULL_DOWN:
					name += "Full Down [" + FULL_DOWN + "]";
					break;
				case AUTONOMOUS:
					name += "Autonomous [" + AUTONOMOUS + "]";
					break;
				case MIDDLE_PYRAMID:
					name += "Middle Pyramid [" + MIDDLE_PYRAMID + "]";
					break;
				default:
					name += rotations;
					break;
			}
			return name;
		}
	}
	
	public class ShooterLightPins {
		public static final int OFF          = 0;
		public static final int ON           = 1;
		public static final int PULSE        = 2;
		public static final int WAVE_FORWARD = 3;
		public static final int WAVE_REVERSE = 4;
	}
}
