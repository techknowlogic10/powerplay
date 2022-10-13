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

    public static int PARKING_POSITION = 1;

    public static int JUNCTION_LEVEL = 3;

    public static double ARM_POSITION = 0.21;

    public static int STEP1_STRAFE_RIGHT = 28;
    public static int STEP2_FORWARD = 36;
    public static int STEP3_STRAFE_RIGHT = 5;

    public static int STEP8_BACK = 8;

    public static int PARKING_ONE_STRAFE_LEFT = 50;
    public static int PARKING_TWO_STRAFE_LEFT = 20;
    public static int PARKING_THREE_STRAFE_LEFT = 2;

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(STARTING_POSITION);

        //Step 0 -- grab preloaded cone

        Elevator elevator = new Elevator(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);
        grabber.pickup();

        waitForStart();

        //scan here to get parking position

        elevator.goToLevel(0);

        //Step 1 - Strafe Right

        Trajectory step1_strafeRight = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeRight(STEP1_STRAFE_RIGHT).build();
        drivetrain.followTrajectory(step1_strafeRight);

        //Step 2 - Forward
        Trajectory step2_forward = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).forward(STEP2_FORWARD).build();
        drivetrain.followTrajectory(step2_forward);

        //Step 3 - Strafe Right
        Trajectory step3_strafeRight = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeRight(STEP3_STRAFE_RIGHT).build();
        drivetrain.followTrajectory(step3_strafeRight);

        //step-4 elevator up
        elevator.goToLevel(JUNCTION_LEVEL);
        sleep(5000);

        //step5 - move arm to a dropping position
        arm.move(ARM_POSITION);
        sleep(3000);

        //sto6 - grabber releases the cone
        grabber.release();

        //step7 - move arm to home position
        arm.goHome();

        //step8 - back
        Trajectory step8_back = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).back(STEP8_BACK).build();
        drivetrain.followTrajectory(step8_back);

        //step 9 - move elevator to home
        elevator.goToHome();

        //step10 - park
        parkRobot(PARKING_POSITION, drivetrain);

        //step 11 - park to make teleop easy (turn 180) //TODO
    }

    private void parkRobot(int parkingPosition, SampleMecanumDrive drivetrain) {

        int strafeLeft = 0;

        if(parkingPosition == 1) {
            strafeLeft = PARKING_ONE_STRAFE_LEFT;
        } else if(parkingPosition == 2) {
            strafeLeft = PARKING_TWO_STRAFE_LEFT;
        } else {
            strafeLeft = PARKING_THREE_STRAFE_LEFT;
        }

        Trajectory park_strafe_left = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeLeft(strafeLeft).build();
        drivetrain.followTrajectory(park_strafe_left);
    }
}
