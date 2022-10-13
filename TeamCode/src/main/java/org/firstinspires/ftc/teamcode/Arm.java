package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Arm {

    public static double ARM_HOME = 0.0;

    Servo arm = null;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.servo.get("arm");
        arm.setPosition(0.0);
    }

    public void move(double desiredArmPosition) {

        while(arm.getPosition() < desiredArmPosition) {
            arm.setPosition(arm.getPosition() + 0.001);
        }
    }

    public void goHome() {
        arm.setPosition(ARM_HOME);
    }
}
