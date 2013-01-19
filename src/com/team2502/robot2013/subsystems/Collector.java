package com.team2502.robot2013.subsystems;

import com.team2502.robot2013.RobotMap;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Collector subsystem of the robot.
 */
public class Collector extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private Jaguar collectorMotor;
    private AnalogChannel collectorIr;
    private int frisbees;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Creates a Jaguar and an AnalogChannel for the collector motor and the IR sensor based on the values from RobotMap.
     */
    public Collector() {
        collectorMotor = new Jaguar(RobotMap.COLLECTOR_MOTOR_JAGUAR);
        collectorIr = new AnalogChannel(RobotMap.COLLECTOR_IR_SENSOR);
        frisbees = 0;
        collectorMotor.setSafetyEnabled(false);
    }
    
    /**
     * Gets the collector IR sensor voltage.
     * @return IR sensor voltage.
     */
    public double getIrVoltage() {
        return collectorIr.getVoltage();
    }
    
    
    /**
     * Sets collector motor to move forward.
     */
    public void pullFrisbees() {
        collectorMotor.set(1);
    }
    
    /**
     * Sets collector motor to move in reverse.
     */
    public void ejectFrisbees() {
        collectorMotor.set(-1);
    }
    
    /**
     * Stops the collector motor.
     */
    public void stopMotor() {
        collectorMotor.stopMotor();
    }
    
    /**
     * Sets running total of frisbees.
     * @param amount Number of frisbees.
     */
    public void setFrisbees(int amount) {
        frisbees = amount;
    }
    
    /**
     * Adds a frisbee to the running total.
     */
    public void addFrisbee() {
        frisbees ++;
    }
    
    /**
     * Removes a frisbee from the running total.
     */
    public void subtractFrisbee() {
        frisbees --;
    }
    
    /**
     * Gets number of frisbees currently stored;
     * @return Number of frisbees.
     */
    public int getFrisbees() {
        return frisbees;
    }
    
}