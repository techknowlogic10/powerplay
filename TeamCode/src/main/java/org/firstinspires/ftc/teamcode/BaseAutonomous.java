package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
public abstract class BaseAutonomous extends LinearOpMode {

    public static double ARM_POSITION = 0.23;

    public static int STEP1_STRAFE_LEFT = 23;
    public static int STEP2_FORWARD = 35;
    public static int STEP3_STRAFE_RIGHT = 7;

    public static int STEP8_BACK = 8;

    public static int PARKING_ONE_STRAFE_LEFT = 8;
    public static int PARKING_TWO_STRAFE_RIGHT = 23;
    public static int PARKING_THREE_STRAFE_RIGHT = 50;

    public static int ELEVATOR_HOLD_ITERATIONS = 20;

    public static int PARKING_FORWARD = 4;

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagSignalDetector detector = new AprilTagSignalDetector(hardwareMap, telemetry, false);
        detector.startDetection();

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(getStartingPosition());

        Elevator elevator = new Elevator(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);

        //Step 0 -- grab preloaded cone
        grabber.pickup();

        Trajectory step1_strafeLeft = drivetrain.trajectoryBuilder(getStartingPosition()).strafeLeft(STEP1_STRAFE_LEFT).build();
        Trajectory step2_forward = drivetrain.trajectoryBuilder(step1_strafeLeft.end()).forward(STEP2_FORWARD).build();
        Trajectory step3_strafeRight = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_STRAFE_RIGHT).build();
        Trajectory step8_back = drivetrain.trajectoryBuilder(step3_strafeRight.end()).back(STEP8_BACK).build();

        while(opModeInInit()) {
            telemetry.addLine("Parking position is " + detector.getSignalPosition());
            telemetry.update();
        }

        waitForStart();

        //scan here to get parking position
        int parkingPosition = detector.getSignalPosition();
        telemetry.log().add("Parking position is " + parkingPosition);

        elevator.goToLevel(0);

        //Step 1 - Strafe Left
        drivetrain.followTrajectory(step1_strafeLeft);

        //Step 2 - Forward
        drivetrain.followTrajectory(step2_forward);

        //Step 3 - Strafe Right
        drivetrain.followTrajectory(step3_strafeRight);

        //step-4 elevator up
        elevator.goToLevel(getJunctionLevel());
        sleep(1000);
        elevator.holdElevator(ELEVATOR_HOLD_ITERATIONS);

        //step5 - move arm to a dropping position
        arm.move(ARM_POSITION);
        elevator.holdElevator(ELEVATOR_HOLD_ITERATIONS);

        //sto6 - grabber releases the cone
        grabber.release();
        elevator.holdElevator(20);

        //step7 - move arm to home position
        arm.goHome();

        //step8 - back
        drivetrain.followTrajectory(step8_back);

        //step 9 - move elevator to home
        elevator.goToHome();

        //step10 - park
        parkRobot(parkingPosition, drivetrain);

        //step 11 - park to make teleop easy (turn 180) //TODO
    }

    private void parkRobot(int parkingPosition, SampleMecanumDrive drivetrain) {
        Trajectory park_strafe = null;

        if(parkingPosition == 1) {
            park_strafe = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeLeft(PARKING_ONE_STRAFE_LEFT).build();
        } else if(parkingPosition == 2) {
            park_strafe = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeRight(PARKING_TWO_STRAFE_RIGHT).build();
        } else {
            park_strafe = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeRight(PARKING_THREE_STRAFE_RIGHT).build();
        }

        drivetrain.followTrajectory(park_strafe);

        Trajectory parkingForward = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).forward(PARKING_FORWARD).build();
        drivetrain.followTrajectory(parkingForward);
    }

    public abstract Pose2d getStartingPosition();

    public abstract int getJunctionLevel();

}
