package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Arm {

    public static double ARM_HOME = 0.005;
    public static int WAIT_MILLS_TO_GO_HOME = 1000;
    public static int WAIT_MILLS_TO_GO_TO_POSITION = 1000;

    Servo arm = null;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.servo.get("arm");
        arm.setPosition(0.0);
    }

    public void move(double desiredArmPosition) {
        arm.setPosition(desiredArmPosition);
        sleep(WAIT_MILLS_TO_GO_TO_POSITION);
    }

    public void goHome() {
        arm.setPosition(ARM_HOME);
        sleep(WAIT_MILLS_TO_GO_HOME);
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
