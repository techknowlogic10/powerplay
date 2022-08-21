package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TestTeleOp extends OpMode {

    DcMotor kinBob;

    @Override
    public void init() {
        kinBob = hardwareMap.get(DcMotor.class, "bob");
    }

    @Override
    public void loop() {

        float left_stick_y_value = gamepad1.left_stick_y;
        telemetry.log().add("Y value is " + left_stick_y_value);

        kinBob.setPower(left_stick_y_value);
    }
}
