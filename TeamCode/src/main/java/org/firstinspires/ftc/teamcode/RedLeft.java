package org.firstinspires.ftc.teamcode;

<<<<<<< HEAD

=======
>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous (name = "Red Left States")
@Config
public class RedLeft extends LinearOpMode {

    public static Pose2d STARTING_POSITION = new Pose2d(-40.3, -60, Math.toRadians(90));
    public static Pose2d MID_WAY = new Pose2d(-34.5, -40, Math.toRadians(165));
    public static Pose2d CONE_DROP_POSITION = new Pose2d(-34, -12, Math.toRadians(165));
<<<<<<< HEAD
    public static Pose2d PARKING_STEP1_POSITION = new Pose2d(-34.5, -31, Math.toRadians(180));
=======

    public static Pose2d PARKING_STEP1_POSITION = new Pose2d(-34.5, -31, Math.toRadians(180));

>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072
    public static int PARKING_STEP2_FORWARD_PARKING_POSITION_1 = 22;
    public static int PARKING_STEP2_FORWARD_PARKING_POSITION_3 = -22;

    public static int JUNCTION_LEVEL = 2;
    public static double ARM_POSITION = .68;

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

<<<<<<< HEAD
        AdditionalConeDropper additionalConeDropper = new AdditionalConeDropper(hardwareMap, 2);
=======
        AdditionalConeDropper additionalConeDropper = new AdditionalConeDropper(hardwareMap);
>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072

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
                arm.move(RedLeft.ARM_POSITION);
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

        grabber.pickup();

        sleep(2000);

        //raise the elevator so that it will not be on its way to drivetrain
        elevator.goToLevel(0);

        while (opModeInInit()) {
            telemetry.addLine("Parking position is " + detector.getSignalPosition());
            telemetry.update();

            elevator.holdElevatorForTicks(Elevator.INITIAL_RISE_TICKS);
        }

        waitForStart();

        int parkingPosition = detector.getSignalPosition();

        elevatorThreadForPreloadDrop.start();
        armThreadForPreloadDrop.start();

        drivetrain.followTrajectorySequence(positionToMedJunctionTrajectory);

<<<<<<< HEAD

        elevator.dropBeforeRelease();

        grabber.release();
        elevator.liftAfterRelease();

=======
        elevator.dropBeforeRelease();
        grabber.release();
        elevator.liftAfterRelease();
>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072
        brake.brake();

        additionalConeDropper.pickAndDropAdditionalCone();
        additionalConeDropper.pickAndDropAdditionalCone();
<<<<<<< HEAD
        additionalConeDropper.pickAndDropAdditionalCone();
        additionalConeDropper.pickAndDropAdditionalCone();
        if (parkingPosition == 2|| parkingPosition == -1) {
            additionalConeDropper.pickAndDropAdditionalCone();
        }
=======
>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072

        brake.goHome();
        armThreadToGoHome.start();

<<<<<<< HEAD

=======
>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072
        drivetrain.followTrajectory(parkingStep1Trajectory);

        if (parkingPosition == 1) {
            Trajectory parkingStep2Trajectory = drivetrain.trajectoryBuilder(PARKING_STEP1_POSITION).forward(PARKING_STEP2_FORWARD_PARKING_POSITION_1).build();
            drivetrain.followTrajectory(parkingStep2Trajectory);
        } else if (parkingPosition == 3) {
            Trajectory parkingStep2Trajectory = drivetrain.trajectoryBuilder(PARKING_STEP1_POSITION).forward(PARKING_STEP2_FORWARD_PARKING_POSITION_3).build();
            drivetrain.followTrajectory(parkingStep2Trajectory);
<<<<<<< HEAD


        }
    }
}
=======
        }
    }
}
>>>>>>> ea98b50d6b2027ea60856808e208b9fe54c76072
