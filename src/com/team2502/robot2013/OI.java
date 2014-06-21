package com.team2502.robot2013;

import com.team2502.robot2013.commands.SetCompetitionVersion;
import com.team2502.robot2013.commands.SetNormalVersion;
import com.team2502.robot2013.commands.autonomous.DisableAutoDance;
import com.team2502.robot2013.commands.autonomous.EnableAutoDance;
import com.team2502.robot2013.commands.drive_train.DriveWithJoystickTurbo;
import com.team2502.robot2013.commands.drive_train.ToggleFan;
import com.team2502.robot2013.commands.wavers.MoveWingsLeft;
import com.team2502.robot2013.commands.wavers.MoveWingsRight;
import com.team2502.robot2013.commands.wavers.ResetToucher;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToJoysticks;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToOmniBackward;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToOmniForward;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToXbox;
import com.team2502.robot2013.commands.lifter.ToggleLifter;
import com.team2502.robot2013.commands.shooter.ChangeAngleToAutonomous;
import com.team2502.robot2013.commands.shooter.ChangeAngleToFullDown;
import com.team2502.robot2013.commands.shooter.ChangeAngleToMiddlePyramid;
import com.team2502.robot2013.commands.shooter.MoveShooterAngleUp;
import com.team2502.robot2013.commands.shooter.ResetAngleEncoder;
import com.team2502.robot2013.commands.storage.PushFrisbeeOut;
import com.team2502.robot2013.commands.shooter.SpeedUpShooter;
import com.team2502.robot2013.commands.storage.StartCompressor;
import com.team2502.robot2013.commands.tshirtlauncher.ToggleCompressTShirt;
import com.team2502.robot2013.commands.tshirtlauncher.LaunchTShirt;
import com.team2502.robot2013.commands.vision.ForcedVisionUpdate;
import edu.wpi.first.wpilibj.command.Scheduler;

public class OI {
	/*
	 * Button Map:
	 *	Shooter:
	 *		1)  Speed Up Shooter
	 *		2)  Change Angle
	 *		3)  Shoot Frisbee
	 *		4)  Compressor
	 *		5)  Lifter
	 *		6)  Reset Encoder
	 *		8)  Set Autonomous Angle
	 *		10) Set Middle Pyramid Angle
	 *		12) Set Full Down Angle
	 *	Drive Train:
	 *		1)  Pyramid Toucher
	 *		4)  Compressor
	 *		5)  Eject Frisbees
	 */
	
	// Controlled by Both
	private static final int JOYSTICK_COMPRESSOR = 4;
	private static final int JOYSTICK_COMPRESSOR_DRIVER = 2;
	// Controlled by Driver
	private static final int JOYSTICK_TSHIRT_COMPRESS = 11;
	private static final int JOYSTICK_TSHIRT_LAUNCH1 = 1;
	private static final int JOYSTICK_TSHIRT_LAUNCH2 = 1;
	private static final int JOYSTICK_FAN = 7;
	private static final int JOYSTICK_PYRAMID_TOUCHER_LEFT = 4;
	private static final int JOYSTICK_PYRAMID_TOUCHER_RIGHT = 5;
	private static final int JOYSTICK_PYRAMID_TOUCHER_RESET = 3;
	// Controlled by Shooter
	private static final int JOYSTICK_FORCE_VISION = 9;
	private static final int JOYSTICK_SPEED_UP = 1;
	private static final int JOYSTICK_CHANGE_ANGLE = 2;
	private static final int JOYSTICK_SHOOT = 3;
	private static final int JOYSTICK_LIFTER = 5;
	private static final int JOYSTICK_RESET_ENCODER = 6;
	private static final int JOYSTICK_SET_AUTO = 8;
	private static final int JOYSTICK_SET_MIDDLE = 10;
	private static final int JOYSTICK_SET_DOWN = 12;
	
	private static JoystickButton    angleAutonomousButton;
	private static JoystickButton    angleMiddlePyramidButton;
	private static JoystickButton    angleFullDownButton;
	private static JoystickButton    shootButton;
	private static JoystickButton    changeAngle;
	private static JoystickButton    shootFrisbee;
	private static JoystickButton    liftUp;
	private static JoystickButton    resetEncoder;
	private static JoystickButton    forceVision;
	private static JoystickButton    launchTShirt1;
	private static JoystickButton    launchTShirt2;
	private static JoystickButton [] compressTShirt;
	private static JoystickButton [] startFan;
	private static JoystickButton [] superSpeed;
	private static JoystickButton [] pyrToucherLeft;
	private static JoystickButton [] pyrToucherRight;
	private static JoystickButton [] pyrToucherReset;
	private static JoystickButton [] resetToucher;
	private static JoystickButton [] startCompressor;
	private static SendableChooser   omniForward;
	private static SendableChooser   competitionVersion;
	private static SendableChooser   driverDevice;
	private static SendableChooser   autoDance;
	// Process operator interface input here.
	public static Joystick left;
	public static Joystick right;
	public static Joystick shooter;
	public static XboxController xboxController;
	
	public static void init() {
		shooter = new Joystick(1);
		left = new Joystick(2);
		right = new Joystick(3);
		xboxController = new XboxController(left);
		
		angleAutonomousButton = new JoystickButton(shooter, JOYSTICK_SET_AUTO);
		angleAutonomousButton.whenPressed(new ChangeAngleToAutonomous());
		
		angleMiddlePyramidButton = new JoystickButton(shooter, JOYSTICK_SET_MIDDLE);
		angleMiddlePyramidButton.whenPressed(new ChangeAngleToMiddlePyramid());
		
		angleFullDownButton = new JoystickButton(shooter, JOYSTICK_SET_DOWN);
		angleFullDownButton.whenPressed(new ChangeAngleToFullDown());
		
		pyrToucherLeft = new JoystickButton[2];
		pyrToucherLeft[0] = new JoystickButton(left, JOYSTICK_PYRAMID_TOUCHER_LEFT);
		pyrToucherLeft[0].whenPressed(new MoveWingsLeft());
		pyrToucherLeft[1] = new JoystickButton(right, JOYSTICK_PYRAMID_TOUCHER_LEFT);
		pyrToucherLeft[1].whenPressed(new MoveWingsLeft());
		pyrToucherRight = new JoystickButton[2];
		pyrToucherRight[0] = new JoystickButton(left, JOYSTICK_PYRAMID_TOUCHER_RIGHT);
		pyrToucherRight[0].whenPressed(new MoveWingsRight());
		pyrToucherRight[1] = new JoystickButton(right, JOYSTICK_PYRAMID_TOUCHER_RIGHT);
		pyrToucherRight[1].whenPressed(new MoveWingsRight());
		pyrToucherReset = new JoystickButton[2];
		pyrToucherReset[0] = new JoystickButton(left, JOYSTICK_PYRAMID_TOUCHER_RESET);
		pyrToucherReset[0].whenPressed(new ResetToucher());
		pyrToucherReset[1] = new JoystickButton(right, JOYSTICK_PYRAMID_TOUCHER_RESET);
		pyrToucherReset[1].whenPressed(new ResetToucher());
		
		shootButton = new JoystickButton(shooter, JOYSTICK_SPEED_UP);
		shootButton.whileHeld(new SpeedUpShooter());
		
		changeAngle = new JoystickButton(shooter, JOYSTICK_CHANGE_ANGLE);
		changeAngle.whileHeld(new MoveShooterAngleUp());
		
		shootFrisbee = new JoystickButton(shooter, JOYSTICK_SHOOT);
		shootFrisbee.whenPressed(new PushFrisbeeOut());
		
		startFan = new JoystickButton[2];
		startFan[0] = new JoystickButton(left, JOYSTICK_FAN);
		startFan[0].whenPressed(new ToggleFan());
		startFan[1] = new JoystickButton(left, JOYSTICK_FAN);
		startFan[1].whenPressed(new ToggleFan());
		
		startCompressor = new JoystickButton[3];
		startCompressor[0] = new JoystickButton(left, JOYSTICK_COMPRESSOR_DRIVER);
		startCompressor[0].whileHeld(new StartCompressor());
		startCompressor[1] = new JoystickButton(right, JOYSTICK_COMPRESSOR_DRIVER);
		startCompressor[1].whileHeld(new StartCompressor());
		startCompressor[2] = new JoystickButton(shooter, JOYSTICK_COMPRESSOR);
		startCompressor[2].whileHeld(new StartCompressor());
		
		liftUp = new JoystickButton(shooter, JOYSTICK_LIFTER);
		liftUp.whenPressed(new ToggleLifter());
		
		resetEncoder = new JoystickButton(shooter, JOYSTICK_RESET_ENCODER);
		resetEncoder.whenPressed(new ResetAngleEncoder());
		
		forceVision = new JoystickButton(shooter, JOYSTICK_FORCE_VISION);
		forceVision.whileHeld(new ForcedVisionUpdate());
		
		launchTShirt1 = new JoystickButton(left, JOYSTICK_TSHIRT_LAUNCH1);
		launchTShirt1.whenPressed(new LaunchTShirt(1, 0.25));
		
		launchTShirt2 = new JoystickButton(right, JOYSTICK_TSHIRT_LAUNCH2);
		launchTShirt2.whenPressed(new LaunchTShirt(2, 0.25));
		
		compressTShirt = new JoystickButton[2];
		compressTShirt[0] = new JoystickButton(left, JOYSTICK_TSHIRT_COMPRESS);
		compressTShirt[0].whenPressed(new ToggleCompressTShirt());
		compressTShirt[1] = new JoystickButton(right, JOYSTICK_TSHIRT_COMPRESS);
		compressTShirt[1].whenPressed(new ToggleCompressTShirt());
	}
	
	public static void initDashboard() {
		omniForward = new SendableChooser();
		omniForward.addDefault("Omni Forward", new SwitchDriveToOmniForward());
		omniForward.addObject("Omni Backward", new SwitchDriveToOmniBackward());

		competitionVersion = new SendableChooser();
		competitionVersion.addDefault("Competition Version", new SetCompetitionVersion());
		competitionVersion.addObject("Normal Version", new SetNormalVersion());

		driverDevice = new SendableChooser();
		driverDevice.addDefault("Joystics", new SwitchDriveToJoysticks());
		driverDevice.addObject("Xbox Controller", new SwitchDriveToXbox());
		
		autoDance = new SendableChooser();
		autoDance.addDefault("Disabled", new DisableAutoDance());
		autoDance.addObject("Enabled", new EnableAutoDance());
		
		SmartDashboard.putData("Competition/Normal Version", competitionVersion);
		SmartDashboard.putData("Omni Direction", omniForward);
		SmartDashboard.putData("Xbox Controller", driverDevice);
		SmartDashboard.putData("Autonomous Dance", autoDance);
	}
	
	public static boolean isXboxController() {
		return driverDevice.getSelected().toString().compareTo("SwitchDrivingDeviceToXboxController") == 0;
	}

	public static boolean isOmniForward() {
		return omniForward.getSelected().toString().compareTo("SwitchDriveToOmniForward") == 0;
	}

	public static boolean isCompetitionVersion() {
		//return competitionVersion.getSelected().toString().compareTo("SetCompetitionVersion") == 0;
		return true;
	}
	
	public static boolean isDanceEnabled() {
		return autoDance.getSelected().toString().compareTo("EnableAutoDance") == 0;
	}
}
