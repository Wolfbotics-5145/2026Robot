// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveDriveConfiguration;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class SwerveDriveSubsystemForRealThisTime extends SubsystemBase {
  /** Creates a new SwerveDriveSubsystem. */
static //double maximumSpeed = Units.feetToMeters(15.7);
double maximumSpeed = 3.0; // In meters per second
public static final double MAX_VELOCITY_METERS_PER_SECOND = maximumSpeed;
private double startingDirection = 0.0;
File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(),"swerve");
private SwerveDrive swerveDrive;


  public SwerveDriveSubsystemForRealThisTime() {
    try {
      
      SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
      System.out.println("****************************** Configure Drivetrain");
      swerveDrive = new SwerveParser(swerveJsonDirectory).createSwerveDrive(maximumSpeed); 
      System.out.println("############################## Drivetrain configured");
      swerveDrive.setHeadingCorrection(false);
      swerveDrive.setChassisDiscretization(true, false, 0.02);
      swerveDrive.setCosineCompensator(! SwerveDriveTelemetry.isSimulation);
      //setStartingDirection(-45.0);
      //swerveDrive.setGyroOffset(new Rotation3d(0.0, 0.0, -45.0));
      
    } catch (Exception e) {
      System.out.println("------------------------------------- Exception occurred");
      throw new RuntimeException(e);
    }
    RobotConfig config = null;
    
     try{
       config = RobotConfig.fromGUISettings();

     } catch (Exception e) {
       e.printStackTrace();
     }
    AutoBuilder.configure(
        this::getPose,
        this::resetPose,
        this::getRobotVelocity,
        (speeds,feedforwards) -> drive(speeds),
        new PPHolonomicDriveController(
          new PIDConstants(0.0020645,0.0,0.2), 
          new PIDConstants(0.02,0.0,1.5)
        ),
        config,
        () -> {
          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this 
      );
  }
  public Pose2d getPose()
  {
    return swerveDrive.getPose();
  }

  public void resetPose(Pose2d pose) {
    swerveDrive.resetOdometry(pose);
  }

  public SwerveDrive getSwerveDrive() {
    return (swerveDrive);
  }
  public Rotation2d getHeading()
  {
    return getPose().getRotation();
  }
  /**
   * Get the chassis speeds based on controller input of 2 joysticks. One for speeds in which direction. The other for
   * the angle of the robot.
   *
   * @param xInput   X joystick input for the robot to move in the X direction.
   * @param yInput   Y joystick input for the robot to move in the Y direction.
   * @param headingX X joystick which controls the angle of the robot.
   * @param headingY Y joystick which controls the angle of the robot.
   * @return {@link ChassisSpeeds} which can be sent to the Swerve Drive.
   */
  public ChassisSpeeds getTargetSpeeds(double xInput, double yInput, double headingX, double headingY, double speedControl)
  {
    double headingAngle,newHeadingAngle;
    double newHeadingX,newHeadingY;
    double speedMultiplier;

    newHeadingX = headingX;
    newHeadingY = headingY;
    xInput = Math.pow(xInput, 3);
    yInput = Math.pow(yInput, 3);
    //System.out.println("Trigger value: " + speedControl);
    //System.out.println("x: "+xInput+" y: "+yInput+" hx: "+headingX+" Hy: "+headingY+" Heading: "+getHeading().getRadians()+" Max: "+maximumSpeed);
    // Change headingX and headingY based on startingDirection
    //System.out.println("Old HeadingX: " + headingX);
    //System.out.println("Old HeadingY: " + headingY);


    headingAngle = Math.atan(headingY/headingX);
    if (headingX > 0.0 && headingY < 0.0) headingAngle += (2 * Math.PI);
    if (headingX < 0.0) headingAngle += Math.PI;
    headingAngle += Math.toRadians(startingDirection);
    newHeadingAngle = headingAngle;
    while (newHeadingAngle > 90.0) newHeadingAngle -= 90.0;
    newHeadingX = Math.cos(newHeadingAngle);
    newHeadingY = Math.sin(newHeadingAngle);
    if (headingAngle > 90.0 && headingAngle < 270.0) newHeadingX *= -1.0;
    if (headingAngle > 180.0) newHeadingY *= -1.0;
    if (speedControl<0.75) speedMultiplier = Math.abs(speedControl-1);
    else speedMultiplier = 0.25;
    //System.out.println("Maximum Speed: " + speedMultiplier*maximumSpeed + " Trigger value: " + speedControl);
    //System.out.println("New HeadingX: " + headingX);
    //System.out.println("New HeadingY: " + headingY);


        return swerveDrive.swerveController.getTargetSpeeds(xInput,
                                                        yInput,
                                                        newHeadingX,
                                                        newHeadingY,
                                                        getHeading().getRadians(),
                                                        maximumSpeed*speedMultiplier);
  }

  /**
   * Get the chassis speeds based on controller input of 1 joystick and one angle. Control the robot at an offset of
   * 90deg.
   *
   * @param xInput X joystick input for the robot to move in the X direction.
   * @param yInput Y joystick input for the robot to move in the Y direction.
   * @param angle  The angle in as a {@link Rotation2d}.
   * @return {@link ChassisSpeeds} which can be sent to the Swerve Drive.
   */
  public ChassisSpeeds getTargetSpeeds(double xInput, double yInput, Rotation2d angle)
  {
    xInput = Math.pow(xInput, 3);
    yInput = Math.pow(yInput, 3);
    return swerveDrive.swerveController.getTargetSpeeds(xInput,
                                                        yInput,
                                                        angle.getRadians(),
                                                        getHeading().getRadians(),
                                                        maximumSpeed);
  }
  public ChassisSpeeds getFieldVelocity()
  {
    return swerveDrive.getFieldVelocity();
  }

  /**
   * Gets the current velocity (x, y and omega) of the robot
   *
   * @return A {@link ChassisSpeeds} object of the current velocity
   */
  public ChassisSpeeds getRobotVelocity()
  {
    return swerveDrive.getRobotVelocity();
  }
  public SwerveDriveConfiguration getSwerveDriveConfiguration()
  {
    return swerveDrive.swerveDriveConfiguration;
  }
  /**
   * The primary method for controlling the drivebase.  Takes a {@link Translation2d} and a rotation rate, and
   * calculates and commands module states accordingly.  Can use either open-loop or closed-loop velocity control for
   * the wheel velocities.  Also has field- and robot-relative modes, which affect how the translation vector is used.
   *
   * @param translation   {@link Translation2d} that is the commanded linear velocity of the robot, in meters per
   *                      second. In robot-relative mode, positive x is torwards the bow (front) and positive y is
   *                      torwards port (left).  In field-relative mode, positive x is away from the alliance wall
   *                      (field North) and positive y is torwards the left wall when looking through the driver station
   *                      glass (field West).
   * @param rotation      Robot angular rate, in radians per second. CCW positive.  Unaffected by field/robot
   *                      relativity.
   * @param fieldRelative Drive mode.  True for field-relative, false for robot-relative.
   */
  public void drive(Translation2d translation, double rotation, boolean fieldRelative)
  {
    //System.out.println("Rotation: "+rotation+" field relative: "+fieldRelative+" translation: "+translation.toString());
    swerveDrive.drive(translation,
                      rotation,
                      fieldRelative,
                      false); // Open loop is disabled since it shouldn't be used most of the time.
  }

  /**
   * Drive the robot given a chassis field oriented velocity.
   *
   * @param velocity Velocity according to the field.
   */
  public void driveFieldOriented(ChassisSpeeds velocity)
  {
    //System.out.println(velocity.vxMetersPerSecond+", "+velocity.vyMetersPerSecond+", "+velocity.omegaRadiansPerSecond);
    swerveDrive.driveFieldOriented(velocity);
  }

  /**
   * Drive according to the chassis robot oriented velocity.
   *
   * @param velocity Robot oriented {@link ChassisSpeeds}
   */
  public void drive(ChassisSpeeds velocity)
  {
    swerveDrive.drive(velocity);
  }

  // public driveToPose(Pose2d pose2d) {vy
  //   PathConstraints contraints = new PathConstraints()
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println("In Drivetrain");
  }

  public void setStartingDirection(double direction) {
    startingDirection = direction;
    //swerveDrive.setGyro(new Rotation3d(0.0, 0.0, startingDirection));
  }

  public double getStartingDirection() {
    return startingDirection;
  }


}
