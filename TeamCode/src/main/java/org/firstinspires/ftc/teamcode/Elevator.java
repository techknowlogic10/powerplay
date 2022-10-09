package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Elevator {

    public static int LOW_JUNCTION_TICKS = 2700;
    public static int MID_JUNCTION_TICKS = 4500;
    public static int HIGH_JUNCTION_TICKS = 6200;

    private HardwareMap hardwareMap;
    DcMotor elevator = null;

    //diameter of spool = 50mm
    //REV HEX 40:1..ticks per rev :

    public Elevator(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        elevator = hardwareMap.dcMotor.get("elevator");
        elevator.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void goToLevel(int junctionLevel) {

        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(junctionLevel == 1) {
            goToPosition(LOW_JUNCTION_TICKS);
        } else if (junctionLevel == 2) {
            goToPosition(MID_JUNCTION_TICKS);
        } else {
            goToPosition(HIGH_JUNCTION_TICKS);
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

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}