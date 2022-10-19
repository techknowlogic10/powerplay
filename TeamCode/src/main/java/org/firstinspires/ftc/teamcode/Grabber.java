package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Grabber {

    public static double GRABBER_RELEASE_POSITION = 0.01;
    public static double GRABBER_PICKUP_POSITION = 0.4;

    Servo grabber = null;

    public Grabber(HardwareMap hardwareMap) {
        grabber = hardwareMap.servo.get("grabber");
    }

    public void release() {
        grabber.setPosition(GRABBER_RELEASE_POSITION);
    }
    public void pickup() {
        grabber.setPosition(GRABBER_PICKUP_POSITION);
    }

}
