// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.awt.image.Kernel;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new Intake. */

  TalonFX intakeArmMotor = new TalonFX(Constants.INTAKE_ARM_MOTOR);
  /// our motor for the arm itself, will most likely be a kraken when we can find the new ones
  SparkMax intakeRollerMotor = new SparkMax(Constants.INTAKE_ROLLER_MOTOR, SparkLowLevel.MotorType.kBrushless);
  ///our motor for the intake roller(the thingie that intakes the fuel into the hopper) most likely a spark max because they're lighter,
  /// and they like to be paired up with the krakens as sidekicks, just like batman and robin

  public IntakeSubsystem() {

    var talonConfig = new TalonFXConfiguration(); ///creates a new config for the MOTION MAGIC(tm) stuff
    var slot0Config = talonConfig.Slot0; /// sets it at slot 0 because if we set it at slot 100 people would look at us funny

    slot0Config.kS = 0.25;  /// this is the output to overcome static friction
    slot0Config.kV = 0.06;  //0.12  this is the output per 1 unit of target velocity (how fast we want to move)
    slot0Config.kA = 0.01; /// this is the output per 1 unit of target acceleration (how fast we want to... accelerate?)

   /// this is our normal PID (Proportional Integral Derivative) stuff
    slot0Config.kP = 4.0; /// how fast we are going to our target position - higher = faster
    slot0Config.kI = 0;   /// how much faster we need to go if we aren't getting there (we get told not to use this in frc, so we don't :P)
    slot0Config.kD = 0.1; /// how much slower we need to go if we keep overshooting, kind of like pressing on the brakes little by little


    /// more of our motion magic stuff
    var motionMagicConfig = talonConfig.MotionMagic; /// Basically just creates the config
    motionMagicConfig.MotionMagicCruiseVelocity = 0; /// sets the cruise velocity to zero (lower is faster, I think?)
    motionMagicConfig.MotionMagicExpo_kV = 0.12; /// same as the velocity up at the top
    motionMagicConfig.MotionMagicExpo_kA = 0.1; /// also same as the acceleration at the top


    intakeArmMotor.getConfigurator().apply(talonConfig);
    /// applies the config to college, where it will get defered and waits 2 years to start in the spring semester
    /// later the config gets a bachelor's in communications where it then struggles to find a job for 5 years as
    /// any job that needs a communications degree has been taken up by AI
  }


  public void moveToPosition (double position) {


    final MotionMagicExpoVoltage request = new MotionMagicExpoVoltage(0);
    /// creates a voltage variable (consonance) at voltage 0 (it changes later, I swear)

    if (position > Constants.INTAKE_ARM_MAX_POSITION) position = Constants.INTAKE_ARM_MAX_POSITION;
    /// if the arm is bigger than the max position, make him go back at least to the max position (hopefully less than the max)
    if (position < Constants.INTAKE_ARM_MIN_POSITION) position = Constants.INTAKE_ARM_MIN_POSITION;
    /// same thing as above, but the opposite. if it's too small, make it big, or at least 0

    /// in general if these need to be called, the intake arm is either inside of the floor (How???)
    /// or the arm is inside of the robot (HOW?!?!?!??!??!)

    intakeArmMotor.setControl(request.withPosition(position));
    /// sets the control with the voltage variable set to the position in created above (I told you it changes later)
  }

  public double getIntakeArmPosition () {

    return intakeArmMotor.getPosition().getValueAsDouble();
    /// basically just asking for what the position of the arm motor is, and returns it as a double, because we aren't allowed to return it as a triple
  }

  public void moveArm(double speed) {
    intakeArmMotor.set(speed);
    /// MOVE ARM MOVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  }

  /// for roller motors, the 0.75 is a magic number rn because I stole it from last year's code -BM 3/1/26

  public void startMotorIn() {
    intakeRollerMotor.set(0.75);}

  /// makes the roller motor go inwards at .75 rotations per second

  public void stopMotor() {
    intakeRollerMotor.set(0.0);}

  /// makes the motors.... stop. very self explanitory

  public void startMotorOut() {
    intakeRollerMotor.set(-0.75);}

  /// makes the roller motor go outwards at .75 rotations per second, the negative symbol is to change the direction

  /// the startMotorOut command will probably only be used for oscillating the roller back and forth to get stuff unjammed



}
