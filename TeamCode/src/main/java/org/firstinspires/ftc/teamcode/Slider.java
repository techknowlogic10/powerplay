package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class Slider {

    //TODO find the ticks
    public static int HOME_TICKS = 750;
    public static int FULL_EXTENSION_TICKS = 100;
    public static int HOME_DISTANCE_CM = 10;
    public static int EXTENDED_DISTANCE_CM = 53;

    private HardwareMap hardwareMap;
    DcMotor slider = null;
    DistanceSensor SliderDistance = null;

    public Slider(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        slider = hardwareMap.dcMotor.get("slider");
        SliderDistance = hardwareMap.get(DistanceSensor.class, "Slider Distance");
        //elevator.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void goToHome() {
        while (SliderDistance.getDistance(DistanceUnit.CM) > HOME_DISTANCE_CM) {
            slider.setPower(-1.0);
            sleep(25);
        }

        slider.setPower(0);
    }

    public void fullyExtend() {


        while (SliderDistance.getDistance(DistanceUnit.CM) < EXTENDED_DISTANCE_CM) {
            slider.setPower(1.0);
            sleep(25);
        }

        slider.setPower(0);

    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
