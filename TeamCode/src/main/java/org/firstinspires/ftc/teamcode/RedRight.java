package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous (name = "Red Right States")
@Config
public class RedRight extends LinearOpMode {

    public static Pose2d STARTING_POSITION = new Pose2d(37, -60, Math.toRadians(90));
    public static Pose2d MID_WAY = new Pose2d(34.5,-40,Math.toRadians(15));
    public static Pose2d CONE_DROP_POSITION = new Pose2d(34.5,-12,Math.toRadians(15));
    public static Pose2d PARKING_STEP1_POSITION = new Pose2d(34.5, -31, Math.toRadians(0));
    public static int PARKING_STEP2_FORWARD_PARKING_POSITION_1 = -22;
    public static int PARKING_STEP2_FORWARD_PARKING_POSITION_3 = 22;

    public static int JUNCTION_LEVEL = 2;
    public static double ARM_POSITION = .673564;

    private int numberOfConesLeftInStack = 5;

    private boolean sliderThreadWorking = true;
    private boolean elevatorThreadWorking = true;
    private boolean armThreadWorking = true;

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagSignalDetector detector = new AprilTagSignalDetector(hardwareMap, telemetry, false);
        detector.startDetection();

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(STARTING_POSITION);

        Elevator elevator = new Elevator(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);
        Slider slider = new Slider(hardwareMap);

        TrajectorySequence positionToMedJunctionTrajectory = drivetrain.trajectorySequenceBuilder(STARTING_POSITION).lineToLinearHeading(MID_WAY).lineToLinearHeading(CONE_DROP_POSITION).build();

        Trajectory parkingStep1Trajectory = drivetrain.trajectoryBuilder(CONE_DROP_POSITION).lineToLinearHeading(PARKING_STEP1_POSITION).build();

        Runnable elevatorThreadForPreloadDrop = new Runnable() {
            @Override
            public void run() {
                elevator.goToLevel(JUNCTION_LEVEL);
            }
        };

        Runnable armThreadForPreloadDrop = new Runnable() {
            @Override
            public void run() {
                arm.move(ARM_POSITION);
            }
        };

        Runnable sliderThreadForStackPickup = new Runnable() {
            @Override
            public void run() {
                slider.fullyExtend();
                sliderThreadWorking = false;
            }
        };

        Runnable elevatorThreadForStackPickup = new Runnable() {
            @Override
            public void run() {
                elevator.prepareForPickup();
                elevatorThreadWorking = false;
            }
        };

        Runnable armThreadForStackPickup = new Runnable() {
            @Override
            public void run() {
                arm.goHome();
                armThreadWorking = false;
            }
        };

        Runnable sliderThreadForStackDrop = new Runnable() {
            @Override
            public void run() {
                slider.goToHome();
                sliderThreadWorking = false;
            }
        };

        Runnable elevatorThreadForStackDrop = new Runnable() {
            @Override
            public void run() {
                elevator.goToLevel(JUNCTION_LEVEL);
                elevatorThreadWorking = false;
            }
        };

        grabber.pickup();

        sleep(2000);

        //raise the elevator so that it will not be on its way to drivetrain
        elevator.goToLevel(0);

        while (opModeInInit()) {
            telemetry.addLine("Parking position is " + detector.getSignalPosition());
            telemetry.update();

            //TODO check if we need to hold the elevator from dropping
            elevator.holdElevatorForTicks(Elevator.INITIAL_RISE_TICKS);
        }

        waitForStart();

        //scan here to get parking position
        int parkingPosition = detector.getSignalPosition();

        new Thread(elevatorThreadForPreloadDrop).start();
        new Thread(armThreadForPreloadDrop).start();
        drivetrain.followTrajectorySequence(positionToMedJunctionTrajectory);

        elevator.dropBeforeRelease();
        //release the preloaded cone
        grabber.release();

        // FIRST Additional Cone START
        sleep(300);

        new Thread(sliderThreadForStackPickup).start();
        new Thread(elevatorThreadForStackPickup).start();
        new Thread(armThreadForStackPickup).start();

        while(elevatorThreadWorking||sliderThreadWorking) {
            sleep(50);
        }

        arm.goHome();
        elevator.goToStackPickup(numberOfConesLeftInStack);

        sleep(300);

        grabber.pickup();
        numberOfConesLeftInStack--;

        sliderThreadWorking = true;
        elevatorThreadWorking = true;
        armThreadWorking = true;

        sleep(600);

        new Thread(elevatorThreadForStackDrop).start();
        sleep(300);
        new Thread(armThreadForPreloadDrop).start();
        new Thread(sliderThreadForStackDrop).start();

        while(elevatorThreadWorking||sliderThreadWorking) {
            sleep(50);
        }

        elevator.dropBeforeRelease();
        grabber.release();
        // 1st Additional Cone END


        //2nd Additional Cone START
        sleep(300);

        //additional cones drop
        new Thread(sliderThreadForStackPickup).start();
        new Thread(elevatorThreadForStackPickup).start();
        new Thread(armThreadForStackPickup).start();

        while(elevatorThreadWorking||sliderThreadWorking) {
            sleep(50);
        }
        sleep(1000);
        arm.goHome();
        elevator.goToStackPickup(numberOfConesLeftInStack);
        sleep(300);

        grabber.pickup();
        numberOfConesLeftInStack--;

        sliderThreadWorking = true;
        elevatorThreadWorking = true;
        armThreadWorking = true;

        sleep(300);
        new Thread(elevatorThreadForStackDrop).start();
        sleep(300);
        new Thread(armThreadForPreloadDrop).start();
        new Thread(sliderThreadForStackDrop).start();

        while(elevatorThreadWorking||sliderThreadWorking) {
            sleep(50);
        }

        elevator.dropBeforeRelease();
        grabber.release();

        sleep(300);


        new Thread(sliderThreadForStackPickup).start();
        new Thread(elevatorThreadForStackPickup).start();
        new Thread(armThreadForStackPickup).start();

        //TODO is it possible for any of these threads to be continuously working (something got messed up?)?
        //If so, we will NOT be parking..take a look at that
        while(elevatorThreadWorking||sliderThreadWorking) {
            sleep(50);
        }

        sleep(1000);
        arm.goHome();
        elevator.goToStackPickup(numberOfConesLeftInStack);

        sleep(500);

        grabber.pickup();
        numberOfConesLeftInStack--;

        sliderThreadWorking = true;
        elevatorThreadWorking = true;
        armThreadWorking = true;

        sleep(300);
        new Thread(elevatorThreadForStackDrop).start();
        sleep(300);
        new Thread(armThreadForPreloadDrop).start();
        new Thread(sliderThreadForStackDrop).start();


        while(elevatorThreadWorking||sliderThreadWorking) {


            sleep(50);
        }


        elevator.dropBeforeRelease();


        grabber.release();


        sleep(300);


        new Thread(sliderThreadForStackPickup).start();
        new Thread(elevatorThreadForStackPickup).start();
        new Thread(armThreadForStackPickup).start();

        //TODO is it possible for any of these threads to be continuously working (something got messed up?)?
        //If so, we will NOT be parking..take a look at that
        while(elevatorThreadWorking||sliderThreadWorking) {
            sleep(50);
        }
        sleep(1000);
        arm.goHome();
        elevator.goToStackPickup(numberOfConesLeftInStack);

        sleep(300);

        grabber.pickup();
        numberOfConesLeftInStack--;

        sliderThreadWorking = true;
        elevatorThreadWorking = true;
        armThreadWorking = true;

        sleep(300);
        new Thread(elevatorThreadForStackDrop).start();
        sleep(300);
        new Thread(armThreadForPreloadDrop).start();
        new Thread(sliderThreadForStackDrop).start();


        while(elevatorThreadWorking||sliderThreadWorking) {


            sleep(50);
        }


        elevator.dropBeforeRelease();

        grabber.release();

        //TODO repeat this again

        //TODO park the robot

        new Thread(armThreadForStackPickup).start();

        drivetrain.followTrajectory(parkingStep1Trajectory);

        if(parkingPosition == 1) {
            Trajectory parkingStep2Trajectory = drivetrain.trajectoryBuilder(PARKING_STEP1_POSITION).forward(PARKING_STEP2_FORWARD_PARKING_POSITION_1).build();
            drivetrain.followTrajectory(parkingStep2Trajectory);
        } else if(parkingPosition == 3) {
            Trajectory parkingStep2Trajectory = drivetrain.trajectoryBuilder(PARKING_STEP1_POSITION).forward(PARKING_STEP2_FORWARD_PARKING_POSITION_3).build();
            drivetrain.followTrajectory(parkingStep2Trajectory);
        }

    }
}
