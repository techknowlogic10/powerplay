package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class EncoderLift {

    Servo leftEncoderLift = null;
    Servo rightEncoderLift = null;

    public EncoderLift(HardwareMap hardwareMap) {
        leftEncoderLift = hardwareMap.get(Servo.class, "LeftEncoder");
        rightEncoderLift = hardwareMap.get(Servo.class, "RightEncoder");

        rightEncoderLift.setDirection(Servo.Direction.REVERSE);
    }

    public void raise() {
        leftEncoderLift.setPosition(1.0);
        rightEncoderLift.setPosition(1.0);
    }

    public void lower() {
        leftEncoderLift.setPosition(0.0);
        rightEncoderLift.setPosition(0.0);
    }
}
