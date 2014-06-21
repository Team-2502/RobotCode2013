package com.team2502.robot2013;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
	
	/* DriveTrain Constants */
	public static final int DRIVETRAIN_TOP_LEFT        = 1; // PWM
	public static final int DRIVETRAIN_TOP_RIGHT       = 3; // PWM
	public static final int DRIVETRAIN_BOTTOM_LEFT     = 4; // PWM
	public static final int DRIVETRAIN_BOTTOM_RIGHT    = 2; // PWM
	public static final int WAVERS_SERVO_LEFT          = 8; // PWM
	public static final int WAVERS_SERVO_RIGHT         = 9; // PWM
	public static final int DRIVETRAIN_DEFENSE_FAN     = 10; // PWM
	
	
	/* Shooter Constants */
	public static final int SHOOTER_ARDUINO_PIN1       = 6; // Digital Output
	public static final int SHOOTER_ARDUINO_PIN2       = 7; // Digital Output
	public static final int SHOOTER_ARDUINO_PIN3       = 8; // Digital Output
	public static final int SHOOTER_ENCODER_A          = 4; // Digital Input
	public static final int SHOOTER_ENCODER_B          = 5; // Digital Input
	public static final int SHOOTER_PHOTO_ENCODER      = 9; // Digital Input
	public static final int SHOOTER_PHOTO_ENCODERPWR   = 3; // Relay 24v
	public static final int SHOOTER_ANGLE_MOTOR        = 5; // PWM
	public static final int SHOOTER_FRONT_WHEEL        = 6; // PWM
	public static final int SHOOTER_BACK_WHEEL         = 7; // PWM
	
	/* Storage Constants */
	public static final int STORAGE_DETECTOR           = 1; // Analog Channel
	public static final int STORAGE_PRESSURE_SWITCH    = 1; // Digital Input
	public static final int STORAGE_PRESSURE_SWITCH_2  = 2; // Digital Input
	public static final int STORAGE_COMPRESSOR_RELAY   = 1; // Relay
	public static final int STORAGE_COMPRESSOR_RELAY_2 = 2; // Relay
	public static final int STORAGE_COMPRESSOR_RELAY_3 = 3; // Relay
	public static final int STORAGE_PUSHER             = 1; // Relay
	
	/* Lifter Constants */
	public static final int LIFTER_SOLENOID_UP         = 4; // Relay
	public static final int LIFTER_SOLENOID_DOWN       = 3; // Relay
	
	/* T-Shirt Launcher Constants */
	public static final int TSHIRTLAUNCHER_LAUNCHER1   = 5; // Solneoid
	public static final int TSHIRTLAUNCHER_LAUNCHER2   = 7; // Solneoid
	public static final int TSHIRTLAUNCHER_COMPRESSOR  = 6; // Solenoid
}
