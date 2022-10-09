package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
@Config
public class RedLeft extends LinearOpMode {

    public static Pose2d STARTING_POSITION = new Pose2d(-37, -60, Math.toRadians(90));

    public static int STEP1_STRAFE_RIGHT = 24;
    public static int STEP2_FORWARD = 34;

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(STARTING_POSITION);

        waitForStart();

        //Strafe
        Trajectory strafeRight = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeRight(STEP1_STRAFE_RIGHT).build();
        drivetrain.followTrajectory(strafeRight);

        //Forward
        Trajectory forward = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).forward(STEP2_FORWARD).build();
        drivetrain.followTrajectory(forward);

    }
}
