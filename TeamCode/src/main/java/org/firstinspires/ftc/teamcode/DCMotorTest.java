package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class DCMotorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        DcMotor kingBob = hardwareMap.get(DcMotor.class, "bob");

        kingBob.setDirection(DcMotorSimple.Direction.FORWARD);
        kingBob.setPower(1.0);

        sleep(5000);

        telemetry.log().add("Now, changing the direction to REVERSE and giving the power again");
        kingBob.setDirection(DcMotorSimple.Direction.REVERSE);
        kingBob.setPower(1.0);
        sleep(5000);

    }
}
