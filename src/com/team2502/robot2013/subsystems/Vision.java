/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.commands.vision.VisionUpdate;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;

/**
 *
 * @author Josh Larson
 */
public class Vision extends Subsystem {
	
	private final int greenLow = 240;
	private final int greenHigh = 255;
	
	private AxisCamera camera;
	private CriteriaCollection cc;
	private Regression distanceReg;
	private Regression angleReg;
	private long frameProcess;
	private long lastFrame;
	
	public void initDefaultCommand() {
		setDefaultCommand(new VisionUpdate());
	}
	
	public Vision() {
		camera = AxisCamera.getInstance("10.25.2.11");
		lastFrame = System.currentTimeMillis();
		frameProcess = 0;
		cc = new CriteriaCollection();
		distanceReg = new Regression(1, 1); // y = x + 1
		angleReg = new Regression(4, 3, 2); // y = 4x^2 + 3x + 2
		SmartDashboard.putNumber("Angle Regression Constant", angleReg.getConstant());
	}
	
	public VisionTarget [] processFrame() {
		lastFrame = System.currentTimeMillis();
		try {
			ColorImage image   = camera.getImage();
			BinaryImage bImage = image.thresholdRGB(0, 255, greenLow, greenHigh, 0, 255);
			BinaryImage fImage = bImage.particleFilter(cc);
			ParticleAnalysisReport [] report = fImage.getOrderedParticleAnalysisReports();
			VisionTarget [] targets = new VisionTarget[report.length];
			for (int i = 0; i < report.length; i++) {
				double centerX = report[i].center_mass_x;
				double centerY = report[i].center_mass_y;
				double width = report[i].boundingRectWidth;
				double height = report[i].boundingRectHeight;
				targets[i] = new VisionTarget(centerX, centerY, width, height);
			}
			frameProcess = System.currentTimeMillis() - lastFrame;
			image.free();
			bImage.free();
			fImage.free();
			return targets;
		} catch (Exception ex) {
			ex.printStackTrace();
			frameProcess = System.currentTimeMillis() - lastFrame;
			return new VisionTarget[0];
		}
	}
	
	public long lastFrame() {
		return lastFrame;
	}
	
	public int getFrameProcessingTime() {
		return (int)frameProcess;
	}
	
	public int getDistanceRegression(VisionTarget target) {
		return (int)distanceReg.getRegression(target.getArea());
	}
	
	public int getAngleRegression(int distance) {
		double constant = angleReg.getConstant();
		constant = SmartDashboard.getNumber("Angle Regression Constant", constant);
		angleReg.setConstant(constant);
		return (int)angleReg.getRegression(distance);
	}
	
	public class TargetType {
		public static final int TOP_TARGET = 2;
		public static final int BOTTOM_TARGET = 1;
		public static final int NOT_TARGET = 0;
	}
	
	public class VisionTarget {
		private Point2D center;
		private Point2D top_left;
		private Point2D top_right;
		private Point2D bottom_left;
		private Point2D bottom_right;
		private int width;
		private int height;
		private int type;
		
		public VisionTarget(double centerX, double centerY, double width, double height) {
			this.width   = (int)width;
			this.height  = (int)height;
			center       = new Point2D(centerX, centerY);
			top_left     = new Point2D(centerX - width/2, centerY - height/2);
			top_right    = new Point2D(centerX + width/2, centerY - height/2);
			bottom_left  = new Point2D(centerX - width/2, centerY + height/2);
			bottom_right = new Point2D(centerX + width/2, centerY + height/2);
		}
		
		public void setType(int type) {
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		public Point2D getCenter() {
			return center;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getArea() {
			return width * height;
		}
		
		public Point2D getTopLeft() {
			return top_left;
		}
		
		public Point2D getTopRight() {
			return top_right;
		}
		
		public Point2D getBottomLeft() {
			return bottom_left;
		}
		
		public Point2D getBottomRight() {
			return bottom_right;
		}
	}
	
	public class Point2D {
		private int x;
		private int y;
		
		public Point2D(double x, double y) {
			this((int)x, (int)y);
		}
		
		public Point2D(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	public class Regression {
		public int degrees;
		public double a;
		public double b;
		public double c;
		
		public Regression(double a, double b, double c) {
			this.degrees = 2;
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		public Regression(double a, double b) {
			this.degrees = 1;
			this.a = a;
			this.b = b;
		}
		
		public double getRegression(double input) {
			if (degrees == 2) {
				return a * input * input + b * input + c;
			} else {
				return a * input + b;
			}
		}
		
		public void setConstant(double constant) {
			if (degrees == 2) {
				c = constant;
			} else {
				b = constant;
			}
		}
		
		public double getConstant() {
			if (degrees == 2) {
				return c;
			} else {
				return b;
			}
		}
	}
}
