package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
@Config
public class RedRightMedJunction extends BaseAutonomous {

    public static Pose2d STARTING_POSITION = new Pose2d(37, -60, Math.toRadians(90));
    public static int JUNCTION_LEVEL = 2;

    @Override
    public Pose2d getStartingPosition() {
        return STARTING_POSITION;
    }

    @Override
    public int getJunctionLevel() {
        return JUNCTION_LEVEL;
    }
}