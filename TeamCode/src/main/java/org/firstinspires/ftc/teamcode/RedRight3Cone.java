package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous (name = "Right States")
@Config

public class RedRight3Cone extends LinearOpMode {

    public static Pose2d STARTING_POSITION = new Pose2d(37, -60, Math.toRadians(90));
    public static Pose2d MID_WAY = new Pose2d(36,-40,Math.toRadians(15));
    public static Pose2d CONE_DROP_POSITION = new Pose2d(34,-12.25,Math.toRadians(15));
    public static Pose2d PARKING_STEP1_POSITION = new Pose2d(34.5, -31, Math.toRadians(0));
    public static int PARKING_STEP2_FORWARD_PARKING_POSITION_1 = -22;
    public static int PARKING_STEP2_FORWARD_PARKING_POSITION_3 = 22;

    public static int JUNCTION_LEVEL = 2;
    public static double ARM_POSITION = .7;

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagSignalDetector detector = new AprilTagSignalDetector(hardwareMap, telemetry, false);
        detector.startDetection();

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(STARTING_POSITION);

        Elevator elevator = new Elevator(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);
        Brake brake = new Brake(hardwareMap);

        AdditionalConeDropper additionalConeDropper = new AdditionalConeDropper(hardwareMap, 2);

        TrajectorySequence positionToMedJunctionTrajectory = drivetrain.trajectorySequenceBuilder(STARTING_POSITION).lineToLinearHeading(MID_WAY).lineToLinearHeading(CONE_DROP_POSITION).build();
        Trajectory parkingStep1Trajectory = drivetrain.trajectoryBuilder(CONE_DROP_POSITION).lineToLinearHeading(PARKING_STEP1_POSITION).build();


        Runnable elevatorRunnableForPreloadDrop = new Runnable() {
            @Override
            public void run() {
                elevator.goToLevel(JUNCTION_LEVEL);
            }
        };
        Thread elevatorThreadForPreloadDrop = new Thread(elevatorRunnableForPreloadDrop);

        Runnable armRunnableForPreloadDrop = new Runnable() {
            @Override
            public void run() {
                arm.move(RedRight3Cone.ARM_POSITION);
            }
        };
        Thread armThreadForPreloadDrop = new Thread(armRunnableForPreloadDrop);

        Runnable armRunnableToGoHome = new Runnable() {
            @Override
            public void run() {
                arm.goHome();
            }
        };
        Thread armThreadToGoHome = new Thread(armRunnableToGoHome);

        Runnable brakeRunnableAfterEnd = new Runnable() {
            @Override
            public void run() {
                while (opModeIsActive()){
                    sleep(25);
                }
                brake.brake();
            }
        };

        Thread brakeAfterEnd = new Thread(brakeRunnableAfterEnd);

        grabber.pickup();

        sleep(2000);

        //raise the elevator so that it will not be on its way to drivetrain
        elevator.goToLevel(0);

        sleep(1000);

        arm.move(0.12);

        while (opModeInInit()) {
            telemetry.addLine("Parking position is " + detector.getSignalPosition());
            telemetry.update();

            elevator.holdElevatorForTicks(Elevator.INITIAL_RISE_TICKS);
        }

        waitForStart();

        //scan here to get parking position
        int parkingPosition = detector.getSignalPosition();

        elevatorThreadForPreloadDrop.start();
        armThreadForPreloadDrop.start();

        drivetrain.followTrajectorySequence(positionToMedJunctionTrajectory);
        brake.brake();

        elevator.dropBeforeRelease();

        //release the preloaded cone
        grabber.release();
        elevator.liftAfterRelease();

        sleep(100);


        additionalConeDropper.pickAndDropAdditionalCone();
        additionalConeDropper.pickAndDropAdditionalCone();
        additionalConeDropper.pickAndDropAdditionalCone();




        brake.goHome();

        //Reset the arm position so that it will not cross the parking barrier
        armThreadToGoHome.start();

        //Park the robot

        drivetrain.followTrajectory(parkingStep1Trajectory);

        brakeAfterEnd.start();

        if (parkingPosition == 1) {
            Trajectory parkingStep2Trajectory = drivetrain.trajectoryBuilder(PARKING_STEP1_POSITION).forward(PARKING_STEP2_FORWARD_PARKING_POSITION_1).build();
            drivetrain.followTrajectory(parkingStep2Trajectory);
        } else if (parkingPosition == 3) {
            Trajectory parkingStep2Trajectory = drivetrain.trajectoryBuilder(PARKING_STEP1_POSITION).forward(PARKING_STEP2_FORWARD_PARKING_POSITION_3).build();
            drivetrain.followTrajectory(parkingStep2Trajectory);


        }
    }


}
