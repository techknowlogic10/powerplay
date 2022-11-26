package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Elevator {

    public static int TICKS_DROP_BEFORE_RELEASE = 200;
    public static int TICKS_LIFT_AFTER_RELEASE = 100;

    public static int INITIAL_RISE_TICKS = 300;

    public static int TICKS_DOWN_TO_GO_TO_TOP_OF_STACK = 1200;
    public static int TICKS_DOWN_FROM_TOP_OF_STACK_FOR_EACH_CONE = 100;

    private HardwareMap hardwareMap;
    DcMotor elevator = null;
    DistanceSensor elevatorSensor = null;

    //diameter of spool = 50mm
    //REV HEX 40:1..ticks per rev :''

    public Elevator(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        elevator = hardwareMap.dcMotor.get("elevator");
        elevatorSensor = hardwareMap.get(DistanceSensor.class, "cone to arm sensor");
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //elevator.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void goToLevel(int junctionLevel) {

        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (junctionLevel == 0) {
            goToPosition(INITIAL_RISE_TICKS);
        } else if (junctionLevel == 1) {
            goToPosition(ElevatorPositions.getLowJunctionTicks());
        } else if (junctionLevel == 2) {
            goToPosition(ElevatorPositions.getMediumJunctionTicks());
        } else {
            goToPosition(ElevatorPositions.getHighJunctionTicks());
        }
    }

    public void goToStackPickup(int numberOfConesLeftInStack) {


        int targetPosition = ElevatorPositions.getStackLevelTicks(numberOfConesLeftInStack);
        elevator.setTargetPosition(targetPosition);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(0.2);

        while (elevator.isBusy()) {
            sleep(50);
        }
    }

    public void prepareForPickup() {

        int targetPosition = ElevatorPositions.getPrepareForPickup();
        elevator.setTargetPosition(targetPosition);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(1.0);


        while (elevator.isBusy()) {
            sleep(50);
        }
    }


    private void goToPosition(int desiredPosition) {

        elevator.setTargetPosition(desiredPosition);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(1.0);

        while (elevator.isBusy()) {
            sleep(50);
        }
    }

    public void holdElevator(int holdIterations) {
        for (int i = 0; i < holdIterations; i++) {
            elevator.setPower(0.05);
            sleep(50);
        }
    }

    public void holdElevatorForTicks(int desiredTicks) {
        if (elevator.getCurrentPosition() < desiredTicks) {
            elevator.setPower(0.05);
            sleep(50);
        }
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void goToHome() {
        elevator.setTargetPosition(0);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(1.0);

        while (elevator.isBusy()) {
            sleep(50);
        }
    }

    public void dropBeforeRelease() {
        int currentTicks = elevator.getCurrentPosition();
        elevator.setTargetPosition(currentTicks - TICKS_DROP_BEFORE_RELEASE);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(0.5);
        while (elevator.isBusy()) {
            sleep(25);
        }
    }

    public void liftAfterRelease() {
        int currentTicks = elevator.getCurrentPosition();
        elevator.setTargetPosition(currentTicks + TICKS_LIFT_AFTER_RELEASE);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(0.5);
        while (elevator.isBusy()) {
            sleep(25);
        }
    }
}
