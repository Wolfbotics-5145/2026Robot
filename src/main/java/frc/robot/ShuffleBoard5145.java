// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import frc.robot.commands.AutoLeave;
//import frc.robot.commands.AutoShoot1Note;
//import frc.robot.commands.AutoShoot2Notes;
//import frc.robot.commands.AutoShoot3Notes;

import edu.wpi.first.wpilibj2.command.Command;

/** Add your docs here. */
public class ShuffleBoard5145 {
    private String m_teleop = "Teleoperated";
    private String m_auto = "Autonomous";
    private ShuffleboardTab teleop = Shuffleboard.getTab(m_teleop);
    private ShuffleboardTab auto = Shuffleboard.getTab(m_auto);
    private String m_defaultShoot1 = "Shoot 1";
    private String m_shoot2 = "Shoot 2";
    private String m_shoot3 = "Shoot 3";
    private String m_moveForward = "Move Forward";
    private final Field2d m_field = new Field2d();
    // private Odometry m_odometry = new SwerveDriveOdometry(null, null, null);
    private double x = 1.5;
    private double y = 5.5;
    private Rotation2d rotation2d = new Rotation2d();

    // void initalizeShuffleBoardAutos(SendableChooser<Command> autoChooser, RobotContainer m_robotContainer, int allianceColor) {
    //     auto.add("Choose your Auto:", autoChooser)
    //         .withWidget(BuiltInWidgets.kComboBoxChooser).withPosition(0, 0).withSize(2, 1);
        
    //     System.out.println("Autos Complete");
    // }
    
    void initalizeCameras() {
        teleop.addCamera("Front Limelight","Front Limelight","ip:http://10.51.45.16:5800")
            .withPosition(0, 0)
            .withSize(6,5)
            .withProperties(Map.of("Show controls",false));
        teleop.addCamera("Back Limelight", "Back Limelight", "ip:http://10.51.45.17:5800")
            .withPosition(7, 0)
            .withSize(6, 5)
            .withProperties(Map.of("Show controls",false));
        System.out.println("Cameras Complete");
        
    }

    void initalizeSensorWidgets(RobotContainer m_robotContainer) {
        teleop.add("PDH", m_robotContainer.pdh)
            .withWidget(BuiltInWidgets.kPowerDistribution)
            .withPosition(13, 0)
            .withSize(4, 5);
         teleop.add("NavX Gyro", m_robotContainer.navx)
             .withWidget(BuiltInWidgets.kGyro)
             .withPosition(0, 5)
             .withSize(3, 3); 
        teleop.add("Field", m_field)
            .withWidget(BuiltInWidgets.kField)
            .withPosition(3, 5)
            .withSize(7, 4);
    
        System.out.println("Widgets Complete");
    }

    void teleopTab() {
        Shuffleboard.selectTab(m_teleop);
    }

    void autoTab() {
        Shuffleboard.selectTab(m_auto);
    }

    public void setRobotFieldPosition(Pose2d pose2d) {
        m_field.setRobotPose(pose2d);
        //x += 0.001;
        //m_field.setRobotPose(x, y, new Rotation2d(0.0));
    }

    public void setRobotFieldPositionUpdate(Pose2d pose2d) {
        m_field.setRobotPose(pose2d);
    }

    public Pose2d getRobotFieldPosition() {
        return m_field.getRobotPose();
    }
}
