// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {

  SparkMax masterClimberMotor = new SparkMax(Constants.CLIMBER_MASTER_MOTOR, SparkLowLevel.MotorType.kBrushless);
  SparkMax slaveClawMotor = new SparkMax(Constants.CLIMBER_SLAVE_MOTOR, SparkLowLevel.MotorType.kBrushless);
  SparkMaxConfig slaveConfig = new SparkMaxConfig();
  SparkMaxConfig globalConfig = new SparkMaxConfig();


  public ClimberSubsystem() {

    globalConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
    masterClimberMotor.configure(globalConfig, ResetMode.kResetSafeParameters , PersistMode.kPersistParameters);
    slaveConfig.apply(globalConfig).follow(masterClimberMotor,true);

    slaveClawMotor.configure(slaveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    System.out.println("+++++++++++++++++++++ The Climber has been configured");


  }

  public void startMotorIn() {
    masterClimberMotor.set(0.75);}


  public void stopMotor() {
    masterClimberMotor.set(0.0);}


  public void startMotorOut() {
    masterClimberMotor.set(-0.75);}


}


