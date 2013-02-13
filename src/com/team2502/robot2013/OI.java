package com.team2502.robot2013;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToArcadeDrive;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToOmniBackward;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToOmniForward;
import com.team2502.robot2013.commands.drive_train.SwitchDriveToTankDrive;
import com.team2502.robot2013.commands.shooter.MoveShooterAngleUp;
import com.team2502.robot2013.commands.storage.PushFrisbeeOut;
import com.team2502.robot2013.commands.shooter.SpeedUpShooter;
import com.team2502.robot2013.commands.storage.StartCompressor;

public class OI {
	private static final int JOYSTICK_SPEED_UP     = 1;
	private static final int JOYSTICK_CHANGE_ANGLE = 2;
	private static final int JOYSTICK_SHOOT        = 3;
	private static JoystickButton [] shootButton;
	private static JoystickButton [] changeAngle;
	private static JoystickButton [] shootFrisbee;
	private static JoystickButton [] startCompressor;
	private static SendableChooser   omniForward;
	private static SendableChooser   tankDriveChoice;
	
    // Process operator interface input here.
	public static  Joystick left;
	public static  Joystick right;
	
	public static void init() {
		left  = new Joystick(1);
		right = new Joystick(2);
		
		initDashboard();
		
		shootButton    = new JoystickButton[2];
        shootButton[0] = new JoystickButton(left, 1);
		shootButton[0].whileHeld(new SpeedUpShooter());
		shootButton[1] = new JoystickButton(right, 1);
		shootButton[1].whileHeld(new SpeedUpShooter());
        
		changeAngle     = new JoystickButton[2];
		changeAngle[0]  = new JoystickButton(left, 2);
		changeAngle[0].whileHeld(new MoveShooterAngleUp(true));
		changeAngle[1]  = new JoystickButton(right, 2);
		changeAngle[1].whileHeld(new MoveShooterAngleUp(false));
		
		shootFrisbee    = new JoystickButton[2];
		shootFrisbee[0] = new JoystickButton(left, 3);
		shootFrisbee[0].whenPressed(new PushFrisbeeOut());
		shootFrisbee[1] = new JoystickButton(right, 3);
		shootFrisbee[1].whenPressed(new PushFrisbeeOut());
		
		startCompressor = new JoystickButton[2];
		startCompressor[0] = new JoystickButton(left, 4);
		startCompressor[0].whileHeld(new StartCompressor());
		startCompressor[1] = new JoystickButton(right, 4);
		startCompressor[1].whileHeld(new StartCompressor());
	}
	
	private static void initDashboard() {
		tankDriveChoice = new SendableChooser();
		tankDriveChoice.addDefault("Tank Drive", new SwitchDriveToTankDrive());
		tankDriveChoice.addObject("Arcade Drive", new SwitchDriveToArcadeDrive());
		
		omniForward = new SendableChooser();
		omniForward.addDefault("Omni Forward", new SwitchDriveToOmniForward());
		omniForward.addObject("Omni Backward", new SwitchDriveToOmniBackward());
        
		SmartDashboard.putData("Drive Type", tankDriveChoice);
		SmartDashboard.putData("Omni Direction", omniForward);
	}
	
	public static boolean isOmniForward() {
		return omniForward.getSelected().toString().compareTo("SwitchDriveToOmniForward") == 0;
	}
	
	public static boolean isTankDrive() {
		return tankDriveChoice.getSelected().toString().compareTo("SwitchDriveToTankDrive") == 0;
	}
}

