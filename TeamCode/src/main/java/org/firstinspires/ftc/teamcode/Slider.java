package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Slider {

    //TODO find the ticks
    public static int HOME_TICKS = 750;
    public static int FULL_EXTENSION_TICKS = 1550;

    private HardwareMap hardwareMap;
    DcMotor slider = null;

    public Slider(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        slider = hardwareMap.dcMotor.get("slider");
        //elevator.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void goToHome() {
        slider.setTargetPosition(HOME_TICKS);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slider.setPower(1.0);

        while (slider.isBusy()) {
            sleep(50);
        }
    }

    public void fullyExtend() {

        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slider.setTargetPosition(FULL_EXTENSION_TICKS);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slider.setPower(1.0);

        while (slider.isBusy()) {
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
