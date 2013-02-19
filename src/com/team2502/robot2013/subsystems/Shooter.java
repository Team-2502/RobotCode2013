package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.shooter.ShooterUpdate;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;

/**
 * The Shooter subsystem to shoot frisbees into the target,
 * and change the angles to get into it.
 */
public class Shooter extends Subsystem {

	private double zeroedAngleValue = 0;
	private Encoder angleEncoder = new Encoder(
			RobotMap.SHOOTER_ENCODER_A,
			RobotMap.SHOOTER_ENCODER_B);
	private Jaguar angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
	private Jaguar backWheel = new Jaguar(RobotMap.SHOOTER_BACK_WHEEL);
	private Jaguar frontWheel = new Jaguar(RobotMap.SHOOTER_FRONT_WHEEL);
	private PIDController anglePID = new PIDController(0.05, 0, 0, angleEncoder, angleMotor);
	
	public Shooter() {
		angleEncoder.setReverseDirection(true);
		angleEncoder.setDistancePerPulse(1);
		angleEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		angleEncoder.start();
		//SmartDashboard.putBoolean("Shooter Angle PID", true);
		anglePID.setInputRange(0, 1200);
		anglePID.setPercentTolerance(5);
		anglePID.setContinuous(true);
		angleSetToPoint(new ShooterPoint("FULL_DOWN"));
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
	}

	/**
	 * STOP THE SHOOTER!!
	 */
	public void stopShooter() {
		frontWheel.set(0);
		backWheel.set(0);
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
	 * Updates the SmartDashboard GUI with the Shooter data
	 */
	public void updateDashboard() {
		SmartDashboard.putNumber("Front Shooter Wheel", frontWheel.get());
		SmartDashboard.putNumber("Back Shooter Wheel", backWheel.get());
		//SmartDashboard.putData("Shooter Angle PID", anglePID);
		//SmartDashboard.putData("Shooter Encoder", angleEncoder);
		//SmartDashboard.putNumber("Current Angle Value", angleEncoder.getDistance());
		SmartDashboard.putNumber("Angle Encoder", angleEncoder.getDistance());
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
	 * A specific angling point the shooter goes to when specified.
	 */
	public static class ShooterPoint {
		
		private static final int FULL_DOWN = 50;
		private static final int AUTONOMOUS = 260;
		private static final int MIDDLE_PYRAMID = 440;
		
		private int rotations = 0;

		/**
		 * A specific angling point the shooter goes to when specified.
		 * Default: 0 (Full-Down)
		 */
		public ShooterPoint() {
			
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
}
