// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ShooterSubsystem extends SubsystemBase {

  TalonFX shooterShootingMotor = new TalonFX(Constants.SHOOTER_SHOOTING_MOTOR);
  TalonFX shooterFeedingMotor = new TalonFX(Constants.SHOOTER_FEED_MOTOR);

  public ShooterSubsystem() {}

  public double getShootingMotorSpeed(){
    return shooterShootingMotor.get();
  }

  public void startShootingMotorIn() {
    shooterShootingMotor.set(0.75);}


  public void stopShootingMotor() {
    shooterShootingMotor.set(0.0);}




  public void startFeedingMotorIn() {
    shooterFeedingMotor.set(0.75);}



  public void stopFeedingMotor() {
    shooterFeedingMotor.set(0.0);}


}
