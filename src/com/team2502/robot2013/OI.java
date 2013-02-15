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
	private static JoystickButton    shootButton;
	private static JoystickButton    changeAngle;
	private static JoystickButton    shootFrisbee;
	private static JoystickButton [] startCompressor;
	private static SendableChooser   omniForward;
        
        private static SendableChooser driverDevice;
	
    // Process operator interface input here.
	public static Joystick left;
	public static Joystick right;
	public static Joystick shooter;
        public static XboxController xboxController;

	public static void init() {
		shooter = new Joystick(1);
		left  = new Joystick(2);
		right = new Joystick(3);
                xboxController = new XboxController(left);
		
		initDashboard();
		
        shootButton = new JoystickButton(shooter, 1);
		shootButton.whileHeld(new SpeedUpShooter());
        
		changeAngle  = new JoystickButton(shooter, 2);
		changeAngle.whileHeld(new MoveShooterAngleUp());
		
		shootFrisbee = new JoystickButton(shooter, 3);
		shootFrisbee.whenPressed(new PushFrisbeeOut());
		
		startCompressor = new JoystickButton[3];
		startCompressor[0] = new JoystickButton(left, 4);
		startCompressor[0].whileHeld(new StartCompressor());
		startCompressor[1] = new JoystickButton(right, 4);
		startCompressor[1].whileHeld(new StartCompressor());
		startCompressor[2] = new JoystickButton(shooter, 4);
		startCompressor[2].whileHeld(new StartCompressor());
	}
	
	private static void initDashboard() {
		omniForward = new SendableChooser();
		omniForward.addDefault("Omni Forward", new SwitchDriveToOmniForward());
		omniForward.addObject("Omni Backward", new SwitchDriveToOmniBackward());
                
		SmartDashboard.putData("Omni Direction", omniForward);
        
                
                
                driverDevice = new SendableChooser();
                driverDevice.addDefault("Joystics", new SwitchDriveToOmniForward());
		driverDevice.addObject("Xbox Controller", new SwitchDriveToOmniBackward());
                
	}
	
        public static boolean useXboxController() {
		return driverDevice.getSelected().toString().compareTo("SwitchDrivingDeviceToXboxController") == 0;
	}
        
	public static boolean isOmniForward() {
		return omniForward.getSelected().toString().compareTo("SwitchDriveToOmniForward") == 0;
	}
}

