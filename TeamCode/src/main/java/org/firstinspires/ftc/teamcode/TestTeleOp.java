package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TestTeleOp extends OpMode {

    DcMotor kingBob;

    @Override
    public void init() {
        kingBob = hardwareMap.get(DcMotor.class, "kingBob");
    }

    @Override
    public void loop() {

        float left_stick_y_value = gamepad1.left_stick_y;
        telemetry.log().add("Y value is " + left_stick_y_value);

        kingBob.setPower(left_stick_y_value);
    }
}
