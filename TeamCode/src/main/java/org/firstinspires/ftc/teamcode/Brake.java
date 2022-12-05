package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Brake {

    public static double BRAKE_HOME = 1;
    public static double BRAKE_POS = 0.8;


    Servo brake = null;

    public Brake(HardwareMap hardwareMap) {
        brake = hardwareMap.servo.get("brake");
        brake.setPosition(BRAKE_HOME);
    }

    public void move(double desiredArmPosition) {
        brake.setPosition(desiredArmPosition);

    }

    public void brake(){
        brake.setPosition(BRAKE_POS);
    }

    public void goHome() {
        brake.setPosition(BRAKE_HOME);

    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
