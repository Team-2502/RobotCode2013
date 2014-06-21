package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.commands.vision.VisionUpdate;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class Vision extends Subsystem {
	
	private final boolean enableVision = false;
	private final int redLow = 153;
	private final int redHigh = 255;
	private final int greenLow = 212;
	private final int greenHigh = 255;
	private final int blueLow = 0;
	private final int blueHigh = 255;
	
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
		if (enableVision) camera = AxisCamera.getInstance("10.25.2.11");
		lastFrame = System.currentTimeMillis();
		frameProcess = 0;
		cc = new CriteriaCollection();
		cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 3000, 6000, false);
		distanceReg = new Regression(0.0000086131992, -0.0893246981, 244.5414525); // a, b, c
		angleReg = new Regression(15.32142857, -565.2928571, 5720.678571); // a, b, c
		SmartDashboard.putNumber("Angle Regression Constant", angleReg.getConstant());
	}
	
	public VisionTarget [] processFrame() {
		if (enableVision) {
			lastFrame = System.currentTimeMillis();
			try {
				ColorImage image   = camera.getImage();
				BinaryImage bImage = image.thresholdRGB(
						redLow, redHigh,
						greenLow, greenHigh,
						blueLow, blueHigh);
				BinaryImage fImage = bImage.particleFilter(cc);
				ParticleAnalysisReport [] report = fImage.getOrderedParticleAnalysisReports();
				VisionTarget [] targets = new VisionTarget[report.length];
				for (int i = 0; i < report.length; i++) {
					double centerX = report[i].center_mass_x;
					double centerY = report[i].center_mass_y;
					double width = report[i].boundingRectWidth;
					double height = report[i].boundingRectHeight;
					int area = (int)report[i].particleArea;
					targets[i] = new VisionTarget(centerX, centerY, width, height, area);
				}
				frameProcess = System.currentTimeMillis() - lastFrame;
				image.free();
				bImage.free();
				fImage.free();
				return targets;
			} catch (AxisCameraException e) {
				System.out.println("No Image From Camera: ");
				frameProcess = System.currentTimeMillis() - lastFrame;
				return new VisionTarget[0];
			} catch (Exception ex) {
				System.out.println("Camera Exception Thrown: " + ex.getMessage());
				frameProcess = System.currentTimeMillis() - lastFrame;
				return new VisionTarget[0];
			}
		} else { // Vision is not enabled
			return new VisionTarget[0];
		}
	}
	
	public long lastFrame() {
		return lastFrame;
	}
	
	public int getFrameProcessingTime() {
		return (int)frameProcess;
	}
	
	public double getDistanceRegression(VisionTarget target) {
		return distanceReg.getRegression(target.getArea());
	}
	
	public int getAngleRegression(double distance) {
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
		private int area;
		private int width;
		private int height;
		private int type;
		
		public VisionTarget(double centerX, double centerY, double width, double height, int area) {
			this.width   = (int)width;
			this.height  = (int)height;
			this.area    = area;
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
			return area;
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
