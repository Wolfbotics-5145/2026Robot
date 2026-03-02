// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag

    public static final int INTAKE_ARM_MOTOR = 50;
    public static final int INTAKE_ROLLER_MOTOR = 51;

         public static final int SHOOTER_SHOOTING_MOTOR = 52;
         public static final int SHOOTER_FEED_MOTOR = 53;

                public static final int HOPPER_AGITATOR_MOTOR = 54;

                     public static final int CLIMBER_MASTER_MOTOR = 55;
                     public static final int CLIMBER_SLAVE_MOTOR = 56;



    public static final double INTAKE_ARM_MIN_POSITION = 0.0;
    public static final double INTAKE_ARM_MAX_POSITION = 90.0; /// placeholder, needs to be tuned -BM 3/1/26
    public static final double INTAKE_ARM_START_POSITION =0.0;
    // Start position,must move intake arm back to 0 (upright position) before turing robot on, or bad stuff WILL happen - BM

                    public static final double CLIMBER_HOME_POSITION = 0.0;    /// all of these (most likely not home_pos)
                    //public static final double CLIMBER_UP_POSITION = -7.5;   /// are placeholders and are subject to change
                    public static final double CLIMBER_MAX_POSITION = 0.0;     /// -BM 3/1/26
                    public static final double CLIMBER_MIN_POSITION = -10.00;


  public static final class LimelightConstants {


    /**
     * PID constants for the autoalign
     */
     public static final double kPdrive = 0.08;
     public static final double kIdrive = 0;
     public static final double kDdrive = 0;

     public static final double kPstrafe = 0.04;
     public static final double kIstrafe = 0.0;
     public static final double kDstrafe = 0.0;

     public static final double kProtation = 0.05;
     public static final double kIrotation = 0.0;
     public static final double kDrotation = 0.0;



      }

  public static class OperatorConstants {
     public static final double LEFT_X_DEADBAND  = 0.1;
      public static final double LEFT_Y_DEADBAND  = 0.1;
      public static final double RIGHT_X_DEADBAND = 0.0;
      public static final double RIGHT_Y_DEADBAND = 0.0;
      public static final double TURN_CONSTANT    = 6;
  }
}
