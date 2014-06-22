




package com.team2502.robot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.robot2013.OI;
import com.team2502.robot2013.subsystems.*;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Josh Larson
 */
public abstract class CommandBase extends Command {
	
	public static OI oi;
	// Create a single static instance of all of your subsystems
	protected static DriveTrain     driveTrain     = new DriveTrain();
	protected static Shooter        shooter        = new Shooter();
	protected static Storage        storage        = new Storage();
	protected static Lifter         lifter         = new Lifter();
	protected static Vision         vision         = new Vision();
	
	public static void init() {
		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		oi = new OI();
		OI.init();
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(storage);
		SmartDashboard.putData(lifter);
		SmartDashboard.putData(vision);
	}
	
	public CommandBase(String name) {
		super(name);
	}
	
	public CommandBase() {
		super();
	}
}
