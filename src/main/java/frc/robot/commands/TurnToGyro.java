// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.sensors.RomiGyro;
import frc.robot.subsystems.Drivetrain;

public class TurnToGyro extends CommandBase {
  /** Creates a new TurnToGyro. */

  private double targetAngle = Constants.GYRO_TURN_TO_UNSET_VALUE;
  private RomiGyro m_gyro;
  private Drivetrain m_drive; 
  private double m_speed;
  private double m_delta;


  public TurnToGyro(Drivetrain drive, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
      
    m_speed = speed;
    m_drive = drive;
    m_gyro = m_drive.getGyro();
    addRequirements(drive);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double gyroAngle = m_gyro.getAngleZ() % 360;
    if(targetAngle == Constants.GYRO_TURN_TO_UNSET_VALUE) {
      // targetAngle = m_gyro.getAngleZ();
      targetAngle = 180;

    }

     m_delta = targetAngle - gyroAngle;
     m_drive.arcadeDrive(0, m_speed);
     System.out.println(gyroAngle + " " + m_delta);
    

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    targetAngle = Constants.GYRO_TURN_TO_UNSET_VALUE;
    m_drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return (Math.abs(m_delta) < 5);
  }
}
