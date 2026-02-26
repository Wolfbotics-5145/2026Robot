// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommandDirectAngle;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystemForRealThisTime;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {
  
  final SwerveDriveSubsystemForRealThisTime  swerveDriveSubsystem = new SwerveDriveSubsystemForRealThisTime();
  public final PowerDistribution pdh = new PowerDistribution(1, ModuleType.kRev);
  public final AHRS navx = new AHRS(NavXComType.kI2C);
  public int allianceColor = 0;
  private boolean isCompetition = false;
  
  public SendableChooser<Command> autoChooser;
  
  private boolean allianceColorRed = true;

  public final XboxController driverController = new XboxController(0);
  public static final XboxController operatorControler = new XboxController(1);
  
  private JoystickButton operatorXButton = new JoystickButton(operatorControler, 3); // X Button
  private JoystickButton operatorYButton = new JoystickButton(operatorControler, 4); // Y Button
  private JoystickButton operatorAButton = new JoystickButton(operatorControler, 1); // A Button
  private JoystickButton operatorBButton = new JoystickButton(operatorControler, 2); // B Button

  

  public void setAllianceColorRed(boolean color) {
  allianceColorRed = color;
}
  public boolean isAllianceColorRed() {
  return allianceColorRed;
}

  public RobotContainer() {
 
    
    
    DriveCommandDirectAngle driveCommand = new DriveCommandDirectAngle(
       swerveDriveSubsystem, 
       () -> -MathUtil.applyDeadband(driverController.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND), 
       () -> -MathUtil.applyDeadband(driverController.getLeftX(), OperatorConstants.LEFT_X_DEADBAND), 
       () -> -MathUtil.applyDeadband(driverController.getRightX(), OperatorConstants.RIGHT_X_DEADBAND), 
       () -> -MathUtil.applyDeadband(driverController.getRightY(), OperatorConstants.RIGHT_Y_DEADBAND),
       () -> MathUtil.applyDeadband(driverController.getRightTriggerAxis(), 0.25),
       () -> isAllianceColorRed());
       
       //System.out.println("setting SDS to the Drive Command");
       swerveDriveSubsystem.setDefaultCommand(driveCommand);
         
       
       configureBindings();
        getAllianceColor();
  
          
  
      }

 
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
   
    // Schedule `exampleMethodCommand` when the Xbox controller's X button is pressed,
    // cancelling on release.
   

  }

public void getAllianceColor() {
  try {
    while (! DriverStation.isDSAttached()) 
    {
     Thread.sleep(100);
     System.out.print(":");
    }
     
    } catch (Exception e) {
      e.printStackTrace();
    }
    Optional<Alliance> ally = DriverStation.getAlliance();
 
    if (ally.get() == Alliance.Red)
      setAllianceColorRed(true);
    else
      setAllianceColorRed(false);
   System.out.println("$$$$$$$$$$$$$$$$ Aliance color: "+ (isAllianceColorRed() ? "Red" : "Blue"));
 
}

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }



  

}
