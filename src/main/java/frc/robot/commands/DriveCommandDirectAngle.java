// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.nio.channels.Pipe.SourceChannel;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveDriveSubsystemForRealThisTime;
import swervelib.math.SwerveMath;

public class DriveCommandDirectAngle extends Command {
  
  private final SwerveDriveSubsystemForRealThisTime swerve;
  private final DoubleSupplier  vX, vY, hX, hY, speedControl;
  private final BooleanSupplier isAllianceRed;

  /** Creates a new DriveCommandDirectAngle. */
  public DriveCommandDirectAngle(SwerveDriveSubsystemForRealThisTime swerve,
                                 DoubleSupplier translationX, 
                                 DoubleSupplier translationY, 
                                 DoubleSupplier headingX,
                                 DoubleSupplier headingY,
                                 DoubleSupplier speedControl,
                                 BooleanSupplier isAllianceRed) {
            
    this.swerve = swerve;
    this.vX = translationX;
    this.vY = translationY;
    this.hX = headingX;
    this.hY = headingY;
    this.speedControl = speedControl; 
    this.isAllianceRed = isAllianceRed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double lhX,lhY;
    boolean flipRedControls;
    
    flipRedControls = isAllianceRed.getAsBoolean();
    
    //System.out.println("Lx: "+vX.getAsDouble()+"Ly: "+vY.getAsDouble());
         Translation2d scaledInputs = SwerveMath.cubeTranslation(new Translation2d((flipRedControls ? -1.0 : 1.0 ) * vX.getAsDouble(),(flipRedControls ? -1.0 : 1.0 ) * vY.getAsDouble()));
         lhX = (! flipRedControls ? -1.0 : 1.0 ) * hX.getAsDouble();
         lhY = (! flipRedControls ? -1.0 : 1.0 ) * hY.getAsDouble();

        //  if (lhX < 0.5 && lhX > -0.5) lhX = 0.0;
        //  if (lhY < 0.5 && lhY > -0.5) lhY = 0.0;

         //System.out.println("Controller: "+ scaledInputs.getX() + ", "+ scaledInputs.getY()+ ", "+lhX+", "+lhY);
         
      // Make the robot move
      /* */
      swerve.driveFieldOriented(swerve.getTargetSpeeds(
        scaledInputs.getX(), 
        scaledInputs.getY(),
        lhX,
        lhY,
        speedControl.getAsDouble()));
        /**/
        //swerve.driveFieldOriented(swerve.getSwerveDrive().getSwerveController().getTargetSpeeds(
        //  scaledInputs.getX(), 
        //  scaledInputs.getY(),
        //  lhX,
        //  lhY,
        //  swerve.getSwerveDrive().getOdometryHeading().getRadians(),
        //  swerve.getSwerveDrive().getMaximumChassisVelocity()));
          
          
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
