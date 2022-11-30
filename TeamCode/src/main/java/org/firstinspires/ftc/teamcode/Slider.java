package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class Slider {

    //TODO find the ticks
    public static int HOME_DISTANCE_CM = 10;

    public static double EXTENDED_DISTANCE_CM = 53.5;
    public static double SLIDER_SPEED = 1;



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
            slider.setPower(-SLIDER_SPEED);
            sleep(25);
        }

        slider.setPower(0);
    }

    public void fullyExtend() {


        while (SliderDistance.getDistance(DistanceUnit.CM) < EXTENDED_DISTANCE_CM) {
            slider.setPower(SLIDER_SPEED);
            sleep(15);
        }

        slider.setPower(0.1);

    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}