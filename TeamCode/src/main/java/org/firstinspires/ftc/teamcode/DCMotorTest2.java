package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class DCMotorTest2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        DcMotor kingBob = hardwareMap.get(DcMotor.class, "bob");

        kingBob.setPower(1.0);
        sleep(5000);

        kingBob.setPower(-1.0);
        sleep(5000);

    }
}
