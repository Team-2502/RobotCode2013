package com.team2502.robot2013;

import com.team2502.robot2013.commands.SetCompetitionVersion;
import com.team2502.robot2013.commands.SetNormalVersion;
import com.team2502.robot2013.commands.autonomous.DisableAutoDance;
import com.team2502.robot2013.commands.autonomous.EnableAutoDance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToJoysticks;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToOmniBackward;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToOmniForward;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToXbox;
import com.team2502.robot2013.commands.lifter.ToggleLifter;
import com.team2502.robot2013.commands.shooter.MoveShooterAngleUp;
import com.team2502.robot2013.commands.shooter.ResetAngleEncoder;
import com.team2502.robot2013.commands.storage.PushFrisbeeOut;
import com.team2502.robot2013.commands.shooter.SpeedUpShooter;
import com.team2502.robot2013.commands.storage.StartCompressor;

public class OI {
	
        private static final int JOYSTICK_COMPRESSOR_DRIVER = 2;
	private static final int JOYSTICK_SPIN_UP_SHOOTER = 1;
	private static final int JOYSTICK_CHANGE_ANGLE = 2;
	private static final int JOYSTICK_SHOOT = 1;
	private static final int JOYSTICK_LIFTER = 4;
	private static final int JOYSTICK_RESET_ENCODER = 3;
	
	private static JoystickButton    shootButton;
	private static JoystickButton    changeAngle;
	private static JoystickButton    shootFrisbee;
	private static JoystickButton    liftUp;
	private static JoystickButton    resetEncoder;
	private static JoystickButton    startCompressor;
	private static SendableChooser   omniForward;
	private static SendableChooser   competitionVersion;
	private static SendableChooser   driverDevice;
	private static SendableChooser   autoDance;
	// Process operator interface input here.
	public static Joystick left; 
	public static Joystick right;
	public static XboxController xboxController;
	
	public static void init() {
		left = new Joystick(1);
		right = new Joystick(2);
		xboxController = new XboxController(left);
                		
		shootButton = new JoystickButton(left, JOYSTICK_SPIN_UP_SHOOTER);
		shootButton.whileHeld(new SpeedUpShooter());
		
//                new MoveShooterAngleUp();
		changeAngle = new JoystickButton(right, JOYSTICK_CHANGE_ANGLE);
		changeAngle.whenReleased(new MoveShooterAngleUp());
		
		shootFrisbee = new JoystickButton(right, JOYSTICK_SHOOT);
		shootFrisbee.whenPressed(new PushFrisbeeOut());
				
		startCompressor = new JoystickButton(left, JOYSTICK_COMPRESSOR_DRIVER);
		startCompressor.whileHeld(new StartCompressor());
		
		liftUp = new JoystickButton(right, JOYSTICK_LIFTER);
		liftUp.whenPressed(new ToggleLifter());
		
		resetEncoder = new JoystickButton(right, JOYSTICK_RESET_ENCODER);
		resetEncoder.whenPressed(new ResetAngleEncoder());
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
