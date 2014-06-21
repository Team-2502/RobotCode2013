/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.commands.vision;

import com.team2502.robot2013.commands.CommandBase;
import com.team2502.robot2013.subsystems.Shooter.ShooterPoint;
import com.team2502.robot2013.subsystems.Vision;
import com.team2502.robot2013.subsystems.Vision.VisionTarget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author josh
 */
public class ForcedVisionUpdate extends CommandBase {
	
	private double average = 0;
	
	public ForcedVisionUpdate() {
		requires(vision);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (System.currentTimeMillis() - vision.lastFrame() >= 300) {
			VisionTarget [] targets = vision.processFrame();
			String debug = "";
			int angle = 0;
			for (int i = 0; i < targets.length; i++) {
				//if (targets[i].getType() == Vision.TargetType.TOP_TARGET) {
					double distance = vision.getDistanceRegression(targets[i]);
						angle = vision.getAngleRegression(distance);
						if (average == 0)
							average = angle;
						else
							average = angle * 0.2 + average * 0.8;
						
						debug += "Distance: " + distance/* + "    Average: " + average + "    "*/;
						debug += /*"Angle: " + average + */"    Area: " + targets[i].getArea() + "    ";
				//}
			}
			if (angle != 0) {
				shooter.angleSetToPoint(new ShooterPoint((int)average));
				//shooter.startAnglePID();
			}
			SmartDashboard.putString("Debug", debug);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
